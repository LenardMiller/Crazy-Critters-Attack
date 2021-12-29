package main.gui;

import main.enemies.Enemy;
import main.gui.guiObjects.PopupText;
import main.gui.guiObjects.buttons.TowerBuy;
import main.misc.Tile;
import main.towers.IceWall;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;
import static main.sound.SoundUtilities.playSound;

public class Hand {

    private final PApplet P;

    public String held;
    private PImage heldSprite;
    private PVector offset;
    private boolean implacable;
    public String displayInfo;
    public int price;

    public final float MIN_ENEMY_DISTANCE;

    public Hand(PApplet p) {
        this.P = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;
        displayInfo = "null";

        MIN_ENEMY_DISTANCE = 50;
    }

    public void main() {
        if (!levelBuilder) {
            implacable = isNotPlaceable();
            checkDisplay();
        }
        else implacable = false;
        if (!paused) {
            if (inputHandler.rightMousePressedPulse) remove();
            if ((clickOnSidebar() || rclickNotWall())) clearHand();
            if (inputHandler.leftMousePressedPulse) tryPlace();
        } displayHeld();
    }

    private void tryPlace() {
        if (!held.equals("wall") && !held.equals("null")) {
            selection.towerJustPlaced = true; //prevents selection click sounds from playing
            playSound(sounds.get("clickOut"), 1, 1);
        }
        if (held.equals("null")) return;
        if (!implacable) place();
        else displayWhyCantPlace();
    }

    private void displayWhyCantPlace() {
        Tile tileTower = tiles.get(
                (roundTo(matrixMousePosition.x, 50) / 50) + 1,
                (roundTo(matrixMousePosition.y, 50) / 50) + 1);
        String errorText = "Can't place there!";
        if (price > money || (
                tileTower != null &&
                tileTower.tower instanceof Wall &&
                tileTower.tower.nextLevelB < tileTower.tower.upgradePrices.length &&
                tileTower.tower.nextLevelB < currentLevel &&
                money < tileTower.tower.upgradePrices[tileTower.tower.nextLevelB]
        ))
            errorText = "Can't afford!";
        else if (
                tileTower != null &&
                tileTower.tower instanceof Wall &&
                !upgradable(tileTower)
        )
            errorText = "Max level!";
        else if (enemyNearby()) errorText = "Critter blocking placement!";
        popupTexts.add(new PopupText(P, 16, new Color(255, 0, 0, 254),
                new Color(50, 0, 0, 200), new PVector(matrixMousePosition.x, matrixMousePosition.y),
                errorText));
    }

    private void clearHand() {
        if (!held.equals("null")) {
            inGameGui.wallBuyButton.timer = 0;
            playSound(sounds.get("clickOut"), 1, 1);
        }
        held = "null";
        for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.depressed = false;
    }

    private boolean rclickNotWall() {
        return inputHandler.rightMousePressedPulse && !held.equals("wall");
    }

    private boolean clickOnSidebar() {
        return inputHandler.leftMousePressedPulse && matrixMousePosition.x > BOARD_WIDTH;
    }

    private boolean isNotPlaceable() {
        Tile tileTower = tiles.get((roundTo(matrixMousePosition.x, 50) / 50) + 1, (roundTo(matrixMousePosition.y, 50) / 50) + 1);
        Tile tileObstacle = tiles.get((roundTo(matrixMousePosition.x, 50) / 50), (roundTo(matrixMousePosition.y, 50) / 50));
        if (tileObstacle == null) return true;
        if (tileTower != null && tileTower.tower != null) {
            if (held.equals("wall")) {
                if (!(tileTower.tower instanceof Wall)) return true;
            } else return true;
        }
        if (enemyNearby()) return true;
        if (tileObstacle.obstacle != null || tileObstacle.machine) return true;
        return price > money;
    }

    private boolean enemyNearby() {
        if (enemies.size() == 0) return false;
        for (Enemy enemy : enemies) {
            if (findDistBetween(
                    enemy.position,
                    new PVector(
                            roundTo(matrixMousePosition.x, 50) + 25,
                            roundTo(matrixMousePosition.y, 50) + 25
                    )
            ) < MIN_ENEMY_DISTANCE * enemy.pfSize) {
                if (!hand.held.equals("null")) {
                    P.fill(255, 0, 0, 100);
                    P.circle(enemy.position.x, enemy.position.y, (MIN_ENEMY_DISTANCE * enemy.pfSize * 2) - 25);
                }
                return true;
            }
        }
        return false;
    }

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(matrixMousePosition.x, 50) / 50) + 1, (roundTo(matrixMousePosition.y, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower instanceof Wall) {
                if (upgradable(tile)) {
                    heldSprite = staticSprites.get("upgradeTW");
                    if (money < tile.tower.upgradePrices[tile.tower.nextLevelB]) implacable = true;
                    displayInfo = "upgradeWall";
                } else {
                    heldSprite = staticSprites.get("placeTW"); //reset wall sprite
                    implacable = true;
                    displayInfo = "maxWallUpgrade";
                }
            } else {
                heldSprite = staticSprites.get("placeTW"); //reset wall sprite
                displayInfo = "placeWall";
            }
        } else {
            setHeld(held); //reset sprite
            displayInfo = "null";
        }
    }

    private boolean upgradable(Tile tile) {
        return tile.tower.nextLevelB < tile.tower.upgradeIcons.length &&
                tile.tower.nextLevelB < currentLevel;
    }

    /**
     * Shows what's held at reduced opacity
     */
    private void displayHeld() {
        if (!held.equals("null") && heldSprite != null && alive) {
            //red if implacable
            if (implacable) P.tint(255, 0, 0, 150);
            else P.tint(255, 150);
            P.image(heldSprite, (roundTo(matrixMousePosition.x, 50)) - (25f / 2) - offset.x + 13, roundTo(matrixMousePosition.y, 50) - (25f / 2) - offset.y + 13);
            P.tint(255);
//            if (held.equals("wall")) {
//                for (Enemy enemy : enemies) {
//                    P.fill(new Color(255, 0, 0, 50).getRGB());
//                    P.stroke(new Color(255, 0, 0).getRGB());
//                    P.circle(enemy.position.x, enemy.position.y, (MIN_ENEMY_DISTANCE * enemy.pfSize * 2));
//                }
//            }
        }
    }

    public void displayHeldInfo() {
        if (displayInfo.equals("placeWall")) placeWallInfo();
        if (displayInfo.equals("upgradeWall")) upgradeWallInfo();
        if (displayInfo.equals("maxWallUpgrade")) maxWallUpgradeInfo();
        if (!displayInfo.equals("null")) universalWallInfo();
    }

    private void placeWallInfo() {
        P.fill(235);
        P.noStroke();
        P.rect(900, 212, 200, 707);
        boolean canAfford = money >= Wall.BUY_PRICE;
        if (canAfford) P.fill(195, 232, 188);
        else P.fill(230, 181, 181);
        P.rect(905, 247, 190, 119);
        P.textAlign(CENTER);
        P.fill(0, 254);
        P.textFont(mediumLargeFont);
        P.text("Placing:", 1000, 241);
        P.textFont(largeFont);
        P.text("Wooden", 1000, 276);
        P.text("Wall", 1000, 301);
        P.textFont(mediumFont);
        P.text("50 HP", 1000, 331);
        if (canAfford) P.text("$" + Wall.BUY_PRICE, 1000, 356);
        else {
            strikethroughText(P, "$" + Wall.BUY_PRICE, new PVector(1000, 356), new Color(150, 0, 0, 254),
              mediumFont.getSize(), CENTER);
        }
    }

    private void upgradeWallInfo() {
        Wall wall = getWall();
        if (wall == null) return;
        if (wall.nextLevelB > wall.upgradeTitles.length - 1) displayInfo = "maxWallUpgrade";
        else {
            P.fill(235, 254);
            P.noStroke();
            P.rect(900, 212, 200, 707);
            P.textAlign(CENTER);
            //tower info
            P.fill(231, 232, 190, 254);
            P.rect(905, 247, 190, 119);
            P.textFont(mediumLargeFont);
            P.fill(0, 254);
            P.text("Selected:", 1000, 241);
            P.textFont(largeFont);
            if (wall.nextLevelB > 0) {
                P.text(wall.upgradeTitles[wall.nextLevelB - 1], 1000, 276); //if not base level
            } else P.text("Wooden", 1000, 276);
            P.text("Wall", 1000, 301);
            P.textFont(mediumFont);
            if (wall.hp > wall.maxHp) P.fill(InGameGui.BOOSTED_TEXT_COLOR.getRGB(), 254);
            P.text(wall.hp + " hp", 1000, 331);
            P.fill(0, 254);
            P.text("Sell for: $" + (int) (0.8f * (float) wall.value), 1000, 356);
            //upgrade info
            boolean canAfford = money >= wall.upgradePrices[wall.nextLevelB];
            if (canAfford) P.fill(195, 232, 188, 254);
            else P.fill(230, 181, 181, 254);
            P.rect(905, 401, 190, 119);
            P.textFont(mediumLargeFont);
            P.fill(0, 254);
            P.text("Upgrade:", 1000, 395);
            P.textFont(largeFont);
            P.text(wall.upgradeTitles[wall.nextLevelB], 1000, 430);
            P.text("Wall", 1000, 455);
            P.textFont(mediumFont);
            P.text("+" + wall.UPGRADE_HP[wall.nextLevelB] + " HP", 1000, 485);
            if (canAfford) P.text("$" + wall.upgradePrices[wall.nextLevelB], 1000, 510);
            else {
                strikethroughText(P, "$" + wall.upgradePrices[wall.nextLevelB], new PVector(1000, 510),
                  new Color(150, 0, 0, 254), mediumFont.getSize(), CENTER);
            }
        }
    }

    private void maxWallUpgradeInfo() {
        Wall wall = getWall();
        if (wall == null) return;
        P.fill(235);
        P.noStroke();
        P.rect(900, 212, 200, 707);
        P.textAlign(CENTER);
        //tower info
        P.fill(231, 232, 190);
        P.rect(905, 247, 190, 119);
        P.textFont(mediumLargeFont);
        P.fill(0, 254);
        P.text("Selected:", 1000, 241);
        P.textFont(largeFont);
        if (currentLevel == 0) P.text("Wooden", 1000, 276);
        else if (wall instanceof IceWall) P.text("Ice", 1000, 276);
        else P.text(wall.upgradeTitles[currentLevel - 1], 1000, 276);
        P.text("Wall", 1000, 301);
        P.textFont(mediumFont);
        P.text(wall.hp + " hp", 1000, 331);
        P.text("Sell for: $" + (int) (0.8f * (float) wall.value), 1000, 356);
    }

    private Wall getWall() {
        return (Wall) tiles.get((roundTo(matrixMousePosition.x, 50) / 50) + 1, (roundTo(matrixMousePosition.y, 50) / 50) + 1).tower;
    }

    private void universalWallInfo() {
        P.fill(200);
        P.rect(905, 700, 190, 195);
        P.fill(0, 254);
        P.textFont(mediumFont);
        P.textAlign(LEFT);
        P.text("LClick to place", 910, 720);
        P.text("wall", 910, 740);
        P.text("LClick on wall to", 910, 770);
        P.text("upgrade", 910, 790);
        P.text("RClick on wall", 910, 820);
        P.text("to Sell", 910, 840);
        P.text("Press tab or click on", 910, 870);
        P.text("sidebar to deselect", 910, 890);
    }

    /**
     * Swaps what's held.
     * @param heldSet name of what should be held
     */
    public void setHeld(String heldSet) {
        switch (heldSet) {
            case "slingshot":
                heldSprite = staticSprites.get("slingshotFullTR");
                offset = new PVector(0, 0);
                price = SLINGSHOT_PRICE;
                break;
            case "crossbow":
                heldSprite = staticSprites.get("crossbowFullTR");
                offset = new PVector(2, 2);
                price = CROSSBOW_PRICE;
                break;
            case "miscCannon":
                heldSprite = staticSprites.get("miscCannonFullTR");
                offset = new PVector(0, 0);
                price = RANDOM_CANNON_PRICE;
                break;
            case "cannon":
                heldSprite = staticSprites.get("cannonFullTR");
                offset = new PVector(5, 5);
                price = CANNON_PRICE;
                break;
            case "gluer":
                heldSprite = staticSprites.get("gluerFullTR");
                offset = new PVector(0, 0);
                price = GLUER_PRICE;
                break;
            case "seismic":
                heldSprite = staticSprites.get("seismicFullTR");
                offset = new PVector(4, 4);
                price = SEISMIC_PRICE;
                break;
            case "energyBlaster":
                heldSprite = staticSprites.get("energyBlasterFullTR");
                offset = new PVector(13, 13);
                price = ENERGY_BLASTER_PRICE;
                break;
            case "magicMissleer":
                heldSprite = staticSprites.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = MAGIC_MISSILEER_PRICE;
                break;
            case "tesla":
                heldSprite = staticSprites.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = TESLA_TOWER_PRICE;
                break;
            case "nightmare":
                heldSprite = staticSprites.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = NIGHTMARE_PRICE;
                break;
            case "flamethrower":
                heldSprite = staticSprites.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = FLAMETHROWER_PRICE;
                break;
            case "iceTower":
                heldSprite = staticSprites.get("iceTowerFullTR");
                offset = new PVector(0, 0);
                price = ICE_TOWER_PRICE;
                break;
            case "booster":
                heldSprite = staticSprites.get("boosterFullTR");
                offset = new PVector(0, 0);
                price = BOOSTER_PRICE;
                break;
            case "railgun":
                heldSprite = staticSprites.get("railgunFullTR");
                offset = new PVector(6, 6);
                price = RAILGUN_PRICE;
                break;
            case "waveMotion":
                heldSprite = staticSprites.get("waveMotionFullTR");
                offset = new PVector(0, 0);
                price = WAVE_MOTION_PRICE;
                break;
            case "wall":
                heldSprite = staticSprites.get("woodWallTW");
                offset = new PVector(0, 0);
                price = Wall.BUY_PRICE;
                break;
            case "null":
                heldSprite = null;
                break;
        }
        if (heldSet.contains("TL")) {
            offset = new PVector(0,0);
            price = 0;
            heldSprite = staticSprites.get(heldSet);
        }
        held = heldSet;
    }

    private void remove() {
        Tile tile = tiles.get((roundTo(matrixMousePosition.x, 50) / 50) + 1, (roundTo(matrixMousePosition.y, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower instanceof Wall) tile.tower.die(true);
        }
    }

    /**
     * Puts down tower and subtracts price.
     */
    private void place() {
        Tile tile = tiles.get((roundTo(matrixMousePosition.x, 50) / 50) + 1, (roundTo(matrixMousePosition.y, 50) / 50) + 1);
        boolean changeHeld = true;
        if (!alive) return;
        switch (held) {
            case "slingshot":
                tile.tower = new Slingshot(P, tile);
                break;
            case "crossbow":
                tile.tower = new Crossbow(P, tile);
                break;
            case "miscCannon":
                tile.tower = new RandomCannon(P, tile);
                break;
            case "cannon":
                tile.tower = new Cannon(P, tile);
                break;
            case "gluer":
                tile.tower = new Gluer(P, tile);
                break;
            case "seismic":
                tile.tower = new SeismicTower(P, tile);
                break;
            case "energyBlaster":
                tile.tower = new EnergyBlaster(P, tile);
                break;
            case "magicMissleer":
                tile.tower = new MagicMissileer(P, tile);
                break;
            case "tesla":
                tile.tower = new TeslaTower(P, tile);
                break;
            case "nightmare":
                tile.tower = new Nightmare(P, tile);
                break;
            case "flamethrower":
                tile.tower = new Flamethrower(P, tile);
                break;
            case "iceTower":
                tile.tower = new IceTower(P, tile);
                break;
            case "booster":
                tile.tower = new Booster(P, tile);
                break;
            case "railgun":
                tile.tower = new Railgun(P, tile);
                break;
            case "waveMotion":
                tile.tower = new WaveMotion(P, tile);
                break;
            case "wall":
                if (tile.tower instanceof Wall) { //upgrade
                    if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && money >= tile.tower.price) { //upgrade
                        money -= tile.tower.upgradePrices[tile.tower.nextLevelB];
                        tile.tower.upgrade(0);
                        connectWallQueues++;
                    }
                    money += price; //cancel out price change later
                } else {
                    tile.tower = new Wall(P, tile);
                    Wall wall = (Wall) tile.tower;
                    wall.placeEffects();
                }
                changeHeld = false;
                break;
        }
        if (held.contains("TL")) {
            tile = tiles.get((roundTo(matrixMousePosition.x, 50) / 50), (roundTo(matrixMousePosition.y, 50) / 50));
            changeHeld = false;
            if (held.contains("Ba")) tile.setBase(held);
            else if (held.contains("De")) tile.setDecoration(held);
            else if (held.contains("Fl")) tile.setFlooring(held);
            else if (held.contains("Br")) tile.setBreakable(held);
            else if (held.contains("Ob")) tile.setObstacle(held);
            else if (held.contains("Ma")) {
                tile.machine = !tile.machine;
                machine.updateNodes();
            } else if (held.contains("Na")) {
                tile.setDecoration(null);
                tile.setBreakable(null);
                tile.setObstacle(null);
            } connectWallQueues++;
        }
        if (!held.equals("null")) money -= price;
        if (changeHeld) held = "null";
        updateNodes();
        updateTowerArray();
    }
}