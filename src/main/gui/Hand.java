package main.gui;

import main.enemies.Enemy;
import main.gui.guiObjects.buttons.TowerBuy;
import main.misc.Tile;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.misc.WallSpecialVisuals.updateWallTiles;

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
        if (paused && !held.equals("null")) setHeld("null");
        if (!levelBuilder) checkPlaceable();
        else implacable = false;
        if (inputHandler.rightMousePressedPulse) remove();
        if ((inputHandler.leftMousePressedPulse && P.mouseX > BOARD_WIDTH) || (inputHandler.rightMousePressedPulse && !held.equals("wall"))) {
            if (!held.equals("null")) {
                inGameGui.wallBuyButton.timer = 0;
                sounds.get("clickOut").stop();
                sounds.get("clickOut").play(1, volume);
            }
            held = "null";
            for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.depressed = false;
        }
        if (!levelBuilder) checkDisplay();
        checkPlaceableEnemies();
        displayHeld();
        if (inputHandler.leftMousePressedPulse) {
            if (!held.equals("wall") && !held.equals("null")) {
                selection.towerJustPlaced = true; //prevents selection click sounds from playing
                sounds.get("clickOut").stop();
                sounds.get("clickOut").play(1, volume);
            }
            if (!implacable) place();
        }
    }

    private void checkPlaceable() {
        implacable = false;
        Tile tileTower = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1);
        Tile tileObstacle = tiles.get((roundTo(P.mouseX, 50) / 50), (roundTo(P.mouseY, 50) / 50));
        if (tileTower == null) implacable = true;
        else if (!held.equals("wall")) implacable = (tileTower.tower != null);
        else implacable = (tileTower.tower != null && tileTower.tower.turret);
        if (tileObstacle != null && (tileObstacle.obstacle != null || tileObstacle.machine)) implacable = true;
        if (price > money) implacable = true;
    }

    private void checkPlaceableEnemies() {
        if (held.equals("wall") && enemyNearby()) {
            implacable = true;
        }
    }

    private boolean enemyNearby() {
        if (enemies.size() == 0) return false;
        for (Enemy enemy : enemies) {
            if (findDistBetween (
                    new PVector (
                            roundTo(enemy.position.x, 50) + 25,
                            roundTo(enemy.position.y, 50) + 25
                    ),
                    new PVector (
                            roundTo(P.mouseX, 50) + 25,
                            roundTo(P.mouseY, 50) + 25
                    )
            ) > MIN_ENEMY_DISTANCE * enemy.pfSize) {
                return false;
            }
        }
        return true;
    }

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower != null && !tile.tower.turret) { //if wall
                if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && tile.tower.nextLevelB < currentLevel) { //if upgradeable
                    heldSprite = staticSprites.get("upgradeTW");
                    implacable = money < tile.tower.upgradePrices[tile.tower.nextLevelB];
                    displayInfo = "upgradeWall";
                } else {
                    if (currentLevel > 0) heldSprite = staticSprites.get("upgradeTW");
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

    /**
     * Shows what's held at reduced opacity
     */
    private void displayHeld() {
        if (!held.equals("null") && heldSprite != null && alive) {
            //red if implacable
            if (implacable) P.tint(255, 0, 0, 150);
            else P.tint(255, 150);
            P.image(heldSprite, (roundTo(P.mouseX, 50)) - (25f / 2) - offset.x + 13, roundTo(P.mouseY, 50) - (25f / 2) - offset.y + 13);
            P.tint(255);
        }
    }

    public void displayHeldInfo() {
        if (displayInfo.equals("placeWall")) {
            P.fill(235);
            P.noStroke();
            P.rect(900, 212, 200, 707);
            boolean canAfford = money >= Wall.BUY_PRICE;
            if (canAfford) P.fill(195, 232, 188);
            else P.fill(230, 181, 181);
            P.rect(905, 247, 190, 119);
            P.textAlign(CENTER);
            P.fill(0);
            P.textFont(mediumLargeFont);
            P.text("Placing:", 1000, 241);
            P.textFont(largeFont);
            P.text("Wooden", 1000, 276);
            P.text("Wall", 1000, 301);
            P.textFont(mediumFont);
            P.text("50 HP", 1000, 331);
            if (canAfford) P.text("$" + Wall.BUY_PRICE, 1000, 356);
            else {
                strikethroughText(P, "$" + Wall.BUY_PRICE, new PVector(1000, 356), new Color(150, 0, 0),
                        mediumFont.getSize(), CENTER);
            }
        }
        if (displayInfo.equals("upgradeWall")) {
            Wall wall = (Wall) tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            if (wall.nextLevelB > wall.upgradeTitles.length - 1) displayInfo = "maxWallUpgrade";
            else {
                P.fill(235);
                P.noStroke();
                P.rect(900, 212, 200, 707);
                P.textAlign(CENTER);
                //tower info
                P.fill(231, 232, 190);
                P.rect(905, 247, 190, 119);
                P.textFont(mediumLargeFont);
                P.fill(0);
                P.text("Selected:", 1000, 241);
                P.textFont(largeFont);
                if (wall.nextLevelB > 0) {
                    P.text(wall.upgradeTitles[wall.nextLevelB - 1], 1000, 276); //if not base level
                } else P.text("Wooden", 1000, 276);
                P.text("Wall", 1000, 301);
                P.textFont(mediumFont);
                P.text(wall.hp + " hp", 1000, 331);
                P.text("Sell for: $" + (int) (0.8f * (float) wall.value), 1000, 356);
                //upgrade info
                boolean canAfford = money >= wall.upgradePrices[wall.nextLevelB];
                if (canAfford) P.fill(195, 232, 188);
                else P.fill(230, 181, 181);
                P.rect(905, 401, 190, 119);
                P.textFont(mediumLargeFont);
                P.fill(0);
                P.text("Upgrade:", 1000, 395);
                P.textFont(largeFont);
                P.text(wall.upgradeTitles[wall.nextLevelB], 1000, 430);
                P.text("Wall", 1000, 455);
                P.textFont(mediumFont);
                P.text("+" + wall.UPGRADE_HP[wall.nextLevelB] + " HP", 1000, 485);
                if (canAfford) P.text("$" + wall.upgradePrices[wall.nextLevelB], 1000, 510);
                else {
                    strikethroughText(P, "$" + wall.upgradePrices[wall.nextLevelB], new PVector(1000, 510),
                            new Color(150, 0, 0), mediumFont.getSize(), CENTER);
                }
            }
        }
        if (displayInfo.equals("maxWallUpgrade")) {
            Tower tower = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            P.fill(235);
            P.noStroke();
            P.rect(900, 212, 200, 707);
            P.textAlign(CENTER);
            //tower info
            P.fill(231, 232, 190);
            P.rect(905, 247, 190, 119);
            P.textFont(mediumLargeFont);
            P.fill(0);
            P.text("Selected:", 1000, 241);
            P.textFont(largeFont);
            if (currentLevel == 0) P.text("Wooden", 1000, 276);
            else P.text(tower.upgradeTitles[currentLevel - 1], 1000, 276);
            P.text("Wall", 1000, 301);
            P.textFont(mediumFont);
            P.text(tower.hp + " hp", 1000, 331);
            P.text("Sell for: $" + (int) (0.8f * (float) tower.value), 1000, 356);
        }
        if (!displayInfo.equals("null")) {
            //universal info
            P.fill(200);
            P.rect(905, 700, 190, 195);
            P.fill(0);
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
                price = RANDOMCANNON_PRICE;
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
                price = ENERGYBLASTER_PRICE;
                break;
            case "magicMissleer":
                heldSprite = staticSprites.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = 150;
                break;
            case "tesla":
                heldSprite = staticSprites.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = TESLATOWER_PRICE;
                break;
            case "nightmare":
                heldSprite = staticSprites.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = 200;
                break;
            case "flamethrower":
                heldSprite = staticSprites.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = FLAMETHROWER_PRICE;
                break;
            case "railgun":
                heldSprite = staticSprites.get("railgunFullTR");
                offset = new PVector(6, 6);
                price = 200;
                break;
            case "waveMotion":
                heldSprite = staticSprites.get("waveMotionFullTR");
                offset = new PVector(0, 0);
                price = 250;
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
        Tile tile = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower != null && !tile.tower.turret) tile.tower.sell();
        }
    }

    /**
     * Puts down tower and subtracts price.
     */
    private void place() {
        Tile tile = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1);
        boolean changeHeld = true;
        if (held.equals("slingshot") && alive) tile.tower = new Slingshot(P, tile);
        else if (held.equals("crossbow") && alive) tile.tower = new Crossbow(P, tile);
        else if (held.equals("miscCannon") && alive) tile.tower = new RandomCannon(P, tile);
        else if (held.equals("cannon") && alive) tile.tower = new Cannon(P, tile);
        else if (held.equals("gluer") && alive) tile.tower = new Gluer(P, tile);
        else if (held.equals("seismic") && alive) tile.tower = new SeismicTower(P, tile);
        else if (held.equals("energyBlaster") && alive) tile.tower = new EnergyBlaster(P, tile);
        else if (held.equals("magicMissleer") && alive) tile.tower = new MagicMissileer(P, tile);
        else if (held.equals("tesla") && alive) tile.tower = new TeslaTower(P, tile);
        else if (held.equals("nightmare") && alive) tile.tower = new Nightmare(P, tile);
        else if (held.equals("flamethrower") && alive) tile.tower = new Flamethrower(P, tile);
        else if (held.equals("railgun") && alive) tile.tower = new Railgun(P, tile);
        else if (held.equals("waveMotion") && alive) tile.tower = new WaveMotion(P, tile);
        else if (held.equals("wall") && alive) {
            if (tile.tower != null && !tile.tower.turret) { //upgrade
                if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && money >= tile.tower.price) { //upgrade
                    money -= tile.tower.upgradePrices[tile.tower.nextLevelB];
                    tile.tower.upgrade(0);
                    connectWallQueues++;
                }
                money += price; //cancel out price change later
            } else {
                tile.tower = new Wall(P, tile);
                updateWallTiles();
                connectWallQueues++;
            }
            changeHeld = false;
        }
        if (held.contains("TL")) {
            tile = tiles.get((roundTo(P.mouseX, 50) / 50), (roundTo(P.mouseY, 50) / 50));
            changeHeld = false;
            if (held.contains("BGA")) tile.setBgA(held);
            if (held.contains("BGB")) tile.setBgB(held);
            if (held.contains("BGW")) tile.setBgW(held);
            if (held.contains("BGC")) tile.setBgC(held);
            if (held.contains("Ob")) tile.setObstacle(held);
            if (held.contains("Ma")) {
                tile.machine = !tile.machine;
                machine.updateNodes();
            }
            connectWallQueues++;
        }
        if (!held.equals("null")) money -= price;
        if (changeHeld) held = "null";
        updateNodes();
        updateTowerArray();
    }
}