package main.gui.inGame;

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
import static main.gui.inGame.TowerInfo.displayTurretInfo;
import static main.misc.Utilities.*;
import static main.misc.Tile.*;
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static main.sound.SoundUtilities.playSound;

public class Hand {

    enum DisplayInfo {
        UpgradeWall,
        MaxWallUpgrade,
        PlaceWall
    }

    public static final float MIN_ENEMY_DISTANCE = 50;

    private static final float BOB_SPEED = TWO_PI / (30f * 2f);
    private static final float BOB_MULT_SPEED = 1 / (30f * 2f);
    private static final float BOB_SCALE = 10;

    public String held;
    public DisplayInfo displayInfo;
    public int price;

    private final PApplet p;

    private PImage heldSprite;
    private PVector offset;
    private boolean implacable;
    private int setHeldNullTimer;
    private float bobbingCycle;
    private float bobbingMultCycle;
    private float bobbingMultX;
    private float bobbingNextMultX;
    private float bobbingMultY;
    private float bobbingNextMultY;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;

        bobbingNextMultX = p.random(1);
        bobbingNextMultY = p.random(1);
    }

    public void update() {
        if (setHeldNullTimer == 0) {
            setHeldNullTimer = -1;
            setHeld("null");
        } else if (setHeldNullTimer > 0) {
            setHeldNullTimer--;
        }
        if (!levelBuilder) {
            implacable = isNotPlaceable();
            checkDisplay();
        }
        else implacable = false;
        if (!paused) {
            if (inputHandler.rightMousePressedPulse) remove();
            if ((clickOnSidebar() || rclickNotWall())) clearHand();
            if (inputHandler.leftMousePressedPulse) tryPlace();
        }
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
                (roundTo(boardMousePosition.x, 50) / 50) + 1,
                (roundTo(boardMousePosition.y, 50) / 50) + 1);
        String errorText = "Can't place there!";
        if (price > money || (
                tileTower != null &&
                tileTower.tower instanceof Wall &&
                tileTower.tower.nextLevelB < tileTower.tower.upgradePrices.length &&
                tileTower.tower.nextLevelB < currentLevel &&
                money < tileTower.tower.upgradePrices[tileTower.tower.nextLevelB]))
            errorText = "Can't afford!";
        else if (
                tileTower != null &&
                tileTower.tower instanceof Wall &&
                held.equals("wall") &&
                !upgradable(tileTower))
            errorText = "Max level!";
        else if (enemyNearby()) errorText = "Critter blocking placement!";
        popupTexts.add(new PopupText(p, 16, new Color(255, 0, 0, 254),
                new Color(50, 0, 0, 200), new PVector(boardMousePosition.x, boardMousePosition.y),
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
        return inputHandler.leftMousePressedPulse && boardMousePosition.x > BOARD_WIDTH;
    }

    private boolean isNotPlaceable() {
        Tile tileTower = tiles.get((roundTo(boardMousePosition.x, 50) / 50) + 1, (roundTo(boardMousePosition.y, 50) / 50) + 1);
        Tile tileObstacle = tiles.get((roundTo(boardMousePosition.x, 50) / 50), (roundTo(boardMousePosition.y, 50) / 50));
        if (tileObstacle == null) return true;
        if (tileTower != null && tileTower.tower != null) {
            if (held.equals("wall")) {
                if (!(tileTower.tower instanceof Wall)) return true;
            } else return true;
        }
        if (enemyNearby()) return true;
        if (tileObstacle.obstacleLayer.exists() || tileObstacle.machine) return true;
        return price > money;
    }

    private boolean enemyNearby() {
        if (enemies.size() == 0) return false;
        for (Enemy enemy : enemies) {
            if (findDistBetween(
                    enemy.position,
                    new PVector(
                            roundTo(boardMousePosition.x, 50) + 25,
                            roundTo(boardMousePosition.y, 50) + 25
                    )
            ) < MIN_ENEMY_DISTANCE * enemy.pfSize) {
                return true;
            }
        }
        return false;
    }

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(boardMousePosition.x, 50) / 50) + 1, (roundTo(boardMousePosition.y, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower instanceof Wall) {
                if (upgradable(tile)) {
                    heldSprite = ((Wall) tile.tower).upgradeSprites[tile.tower.nextLevelB][4];
                    if (money < tile.tower.upgradePrices[tile.tower.nextLevelB]) implacable = true;
                    displayInfo = DisplayInfo.UpgradeWall;
                } else {
                    heldSprite = animatedSprites.get("woodWallTW")[4]; //reset wall sprite
                    implacable = true;
                    displayInfo = DisplayInfo.MaxWallUpgrade;
                }
            } else {
                heldSprite = animatedSprites.get("woodWallTW")[4]; //reset wall sprite
                displayInfo = DisplayInfo.PlaceWall;
            }
        } else {
            setHeld(held); //reset sprite
            displayInfo = null;
        }
    }

    private boolean upgradable(Tile tile) {
        return tile.tower.nextLevelB < tile.tower.upgradeIcons.length && tile.tower.nextLevelB < currentLevel;
    }

    /** Shows what's held at reduced opacity */
    public void displayHeld() {
        if (held.equals("null") || heldSprite == null || !alive) return;
        //red if implacable
        if (implacable) p.tint(Color.RED.getRGB(), 180);
        else p.tint(new Color(0xFFDF8D).getRGB(), 180);
        PVector pos = new PVector(
                (roundTo(boardMousePosition.x, 50)) - (25f / 2) - offset.x + 13,
                roundTo(boardMousePosition.y, 50) - (25f / 2) - offset.y + 13);
//        pos.add(bobbing());
        p.image(heldSprite, pos.x, pos.y);
        p.tint(255);
        displayCritterCircles();
    }

    // TODO: shelved, check out later?
//    private PVector bobbing() {
//        bobbingCycle += BOB_SPEED;
//        if (bobbingCycle >= TWO_PI) bobbingCycle = 0;
//        bobbingMultCycle += BOB_MULT_SPEED;
//        if (bobbingMultCycle >= 1) {
//            bobbingMultCycle = 0;
//            bobbingMultX = bobbingNextMultX;
//            bobbingNextMultX = p.random(1);
//            bobbingMultY = bobbingNextMultY;
//            bobbingNextMultY = p.random(1);
//        }
//        return new PVector(
//                sin(bobbingCycle) * ((bobbingMultCycle * (bobbingMultX + 1 - bobbingMultCycle) * bobbingNextMultX) / 2f),
//                cos(bobbingCycle) * ((bobbingMultCycle * (bobbingMultY + 1 - bobbingMultCycle) * bobbingNextMultY) / 2f)
//        ).setMag(BOB_SCALE);
//    }

    private void displayCritterCircles() {
        if (enemies.size() == 0) return;
        for (Enemy enemy : enemies) {
            if (findDistBetween(
                    enemy.position,
                    new PVector(
                            roundTo(boardMousePosition.x, 50) + 25,
                            roundTo(boardMousePosition.y, 50) + 25
                    )
            ) < MIN_ENEMY_DISTANCE * enemy.pfSize) {
                if (!hand.held.equals("null")) {
                    p.noStroke();
                    p.fill(255, 0, 0, 100);
                    p.circle(enemy.position.x, enemy.position.y, (MIN_ENEMY_DISTANCE * enemy.pfSize * 2) - 25);
                }
                return;
            }
        }
    }

    public void displayHeldInfo() {
        if (displayInfo != null) {
            switch (displayInfo) {
                case PlaceWall -> placeWallInfo();
                case UpgradeWall -> upgradeWallInfo();
                case MaxWallUpgrade -> maxWallUpgradeInfo();
            }
            universalWallInfo();
        }
        if (!held.equals("null") && !held.equals("wall") && !held.endsWith("TL")) displayTurretInfo(p, held);
    }

    private void placeWallInfo() {
        p.fill(235);
        p.noStroke();
        p.rect(900, 212, 200, 707);
        boolean canAfford = money >= Wall.BUY_PRICE;
        if (canAfford) p.fill(195, 232, 188);
        else p.fill(230, 181, 181);
        p.rect(905, 247, 190, 119);
        p.textAlign(CENTER);
        p.fill(0, 254);
        p.textFont(h3);
        p.text("Placing:", 1000, 241);
        p.textFont(h2);
        p.text("Wooden", 1000, 276);
        p.text("Wall", 1000, 301);
        p.textFont(h4);
        p.text("50 HP", 1000, 331);
        if (canAfford) p.text("$" + Wall.BUY_PRICE, 1000, 356);
        else {
            strikethroughText(p, "$" + Wall.BUY_PRICE, new PVector(1000, 356), new Color(150, 0, 0, 254),
              h4.getSize(), CENTER);
        }
    }

    private void upgradeWallInfo() {
        Wall wall = getWall();
        if (wall == null) {
            placeWallInfo();
            return;
        }
        if (wall.nextLevelB > wall.upgradeTitles.length - 1) {
            displayInfo = DisplayInfo.MaxWallUpgrade;
            maxWallUpgradeInfo();
            return;
        }
        p.fill(235, 254);
        p.noStroke();
        p.rect(900, 212, 200, 707);
        p.textAlign(CENTER);
        //tower info
        p.fill(231, 232, 190, 254);
        p.rect(905, 247, 190, 119);
        p.textFont(h3);
        p.fill(0, 254);
        p.text("Selected:", 1000, 241);
        p.textFont(h2);
        if (wall.nextLevelB > 0) {
            p.text(wall.upgradeTitles[wall.nextLevelB - 1], 1000, 276); //if not base level
        } else p.text("Wooden", 1000, 276);
        p.text("Wall", 1000, 301);
        p.textFont(h4);
        if (wall.hp > wall.maxHp) p.fill(InGameGui.BOOSTED_TEXT_COLOR.getRGB(), 254);
        p.text(wall.hp + " hp", 1000, 331);
        p.fill(0, 254);
        p.text("Sell for: $" + (int) (0.8f * (float) wall.getValue()), 1000, 356);
        //upgrade info
        boolean canAfford = money >= wall.upgradePrices[wall.nextLevelB];
        if (canAfford) p.fill(195, 232, 188, 254);
        else p.fill(230, 181, 181, 254);
        p.rect(905, 401, 190, 119);
        p.textFont(h3);
        p.fill(0, 254);
        p.text("Upgrade:", 1000, 395);
        p.textFont(h2);
        p.text(wall.upgradeTitles[wall.nextLevelB], 1000, 430);
        p.text("Wall", 1000, 455);
        p.textFont(h4);
        p.text("+" + wall.upgradeHp[wall.nextLevelB] + " HP", 1000, 485);
        if (canAfford) p.text("$" + wall.upgradePrices[wall.nextLevelB], 1000, 510);
        else {
            strikethroughText(p, "$" + wall.upgradePrices[wall.nextLevelB], new PVector(1000, 510),
              new Color(150, 0, 0, 254), h4.getSize(), CENTER);
        }
    }

    private void maxWallUpgradeInfo() {
        Wall wall = getWall();
        if (wall == null) {
            placeWallInfo();
            return;
        }
        p.fill(235);
        p.noStroke();
        p.rect(900, 212, 200, 707);
        p.textAlign(CENTER);
        //tower info
        p.fill(231, 232, 190);
        p.rect(905, 247, 190, 119);
        p.textFont(h3);
        p.fill(0, 254);
        p.text("Selected:", 1000, 241);
        p.textFont(h2);
        if (currentLevel == 0) p.text("Wooden", 1000, 276);
        else if (wall instanceof IceWall) p.text("Ice", 1000, 276);
        else p.text(wall.upgradeTitles[currentLevel - 1], 1000, 276);
        p.text("Wall", 1000, 301);
        p.textFont(h4);
        p.text(wall.hp + " hp", 1000, 331);
        p.text("Sell for: $" + (int) (0.8f * (float) wall.getValue()), 1000, 356);
    }

    private Wall getWall() {
        return (Wall) tiles.get((roundTo(boardMousePosition.x, 50) / 50) + 1, (roundTo(boardMousePosition.y, 50) / 50) + 1).tower;
    }

    private void universalWallInfo() {
        p.fill(200);
        p.rect(905, 700, 190, 195);
        p.fill(0, 254);
        p.textFont(h4);
        p.textAlign(LEFT);
        p.text("LClick to place", 910, 720);
        p.text("wall", 910, 740);
        p.text("LClick on wall to", 910, 770);
        p.text("upgrade", 910, 790);
        p.text("RClick on wall", 910, 820);
        p.text("to Sell", 910, 840);
        p.text("Press tab or click on", 910, 870);
        p.text("sidebar to deselect", 910, 890);
    }

    /**
     * Swaps what's held.
     * @param heldSet name of what should be held
     */
    public void setHeld(String heldSet) {
        switch (heldSet) {
            case "slingshot" -> {
                heldSprite = staticSprites.get("slingshotFullTR");
                offset = new PVector(0, 0);
                price = SLINGSHOT_PRICE;
            } case "crossbow" -> {
                heldSprite = staticSprites.get("crossbowFullTR");
                offset = new PVector(2, 2);
                price = CROSSBOW_PRICE;
            } case "miscCannon" -> {
                heldSprite = staticSprites.get("miscCannonFullTR");
                offset = new PVector(0, 0);
                price = RANDOM_CANNON_PRICE;
            } case "cannon" -> {
                heldSprite = staticSprites.get("cannonFullTR");
                offset = new PVector(5, 5);
                price = CANNON_PRICE;
            } case "gluer" -> {
                heldSprite = staticSprites.get("gluerFullTR");
                offset = new PVector(0, 0);
                price = GLUER_PRICE;
            } case "seismic" -> {
                heldSprite = staticSprites.get("seismicFullTR");
                offset = new PVector(4, 4);
                price = SEISMIC_PRICE;
            } case "energyBlaster" -> {
                heldSprite = staticSprites.get("energyBlasterFullTR");
                offset = new PVector(13, 13);
                price = ENERGY_BLASTER_PRICE;
            } case "magicMissleer" -> {
                heldSprite = staticSprites.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = MAGIC_MISSILEER_PRICE;
            } case "tesla" -> {
                heldSprite = staticSprites.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = TESLA_TOWER_PRICE;
            } case "nightmare" -> {
                heldSprite = staticSprites.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = NIGHTMARE_PRICE;
            } case "flamethrower" -> {
                heldSprite = staticSprites.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = FLAMETHROWER_PRICE;
            } case "iceTower" -> {
                heldSprite = staticSprites.get("iceTowerFullTR");
                offset = new PVector(0, 0);
                price = ICE_TOWER_PRICE;
            } case "booster" -> {
                heldSprite = staticSprites.get("boosterFullTR");
                offset = new PVector(0, 0);
                price = BOOSTER_PRICE;
            } case "railgun" -> {
                heldSprite = staticSprites.get("railgunFullTR");
                offset = new PVector(6, 6);
                price = RAILGUN_PRICE;
            } case "waveMotion" -> {
                heldSprite = staticSprites.get("waveMotionFullTR");
                offset = new PVector(14, 14);
                price = WAVE_MOTION_PRICE;
            } case "wall" -> {
                heldSprite = staticSprites.get("woodWallTW");
                offset = new PVector(0, 0);
                price = Wall.BUY_PRICE;
            }
            case "null" -> heldSprite = null;
        }
        if (heldSet.contains("TL")) {
            offset = new PVector(0,0);
            price = 0;
            heldSprite = staticSprites.get(heldSet);
        }
        held = heldSet;
    }

    private void remove() {
        Tile tile = tiles.get((roundTo(boardMousePosition.x, 50) / 50) + 1, (roundTo(boardMousePosition.y, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower instanceof Wall) tile.tower.die(true);
        }
    }

    /** Puts down tower and subtracts price.
     * Assumes it is possible to do so */
    private void place() {
        Tile tile = tiles.get((roundTo(boardMousePosition.x, 50) / 50) + 1, (roundTo(boardMousePosition.y, 50) / 50) + 1);
        if (tile == null) return;
        boolean changeHeld = true;
        if (!alive) return;
        if (held.equals("wall")) {
            if (tile.tower instanceof Wall wall) { //upgrade
                if (wall.nextLevelB < wall.upgradeIcons.length && money >= wall.upgradePrices[wall.nextLevelB]) { //upgrade
                    money -= wall.upgradePrices[wall.nextLevelB];
                    wall.upgrade(0, false);
                    connectWallQueues++;
                }
                money += price; //cancel out price change later
            } else tile.tower = new Wall(p, tile);
            changeHeld = false;
            tile.tower.place(false);
        } else {
//            System.out.println(held.substring(0,1).toUpperCase() + held.substring(1));
            tile.tower = Turret.get(p, held.substring(0,1).toUpperCase() + held.substring(1), tile);
            if (tile.tower != null) {
                tile.tower.place(false);
            }
        }
        if (held.contains("TL")) {
            tile = tiles.get((roundTo(boardMousePosition.x, 50) / 50), (roundTo(boardMousePosition.y, 50) / 50));
            if (tile == null) return;
            changeHeld = false;
            String shortName = held.replace("_TL", "");
            if (shortName.contains("Ba")) tile.baseLayer.set(held);
            else if (shortName.endsWith("De")) tile.decorationLayer.set(held);
            else if (shortName.endsWith("Fl")) tile.flooringLayer.set(held);
            else if (shortName.endsWith("Br")) tile.breakableLayer.set(held);
            else if (shortName.endsWith("Ob")) tile.obstacleLayer.set(held);
            else if (shortName.endsWith("Ma")) {
                tile.machine = !tile.machine;
                machine.updateNodes();
            } else if (held.contains("Na")) {
                tile.decorationLayer.set(null);
                tile.breakableLayer.set(null);
                tile.obstacleLayer.set(null);
            } connectWallQueues++;
        }
        if (!held.equals("null")) money -= price;
        if (changeHeld) setHeldNullTimer = 1;
        checkDisplay();
        updateCombatPoints();
        updateTowerArray();
    }
}