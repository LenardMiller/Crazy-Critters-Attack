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
    public static final int PANEL_WIDTH = 190 - 9;
    public static final Color CANT_AFFORD_COLOR = new Color(0xAB0000);

    public Class<?> heldClass;
    public String held;
    public DisplayInfo displayInfo;
    public int price;

    private final PApplet p;

    private PImage heldSprite;
    private PVector offset;
    private boolean implacable;
    private int setHeldNullTimer;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;
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
        if (boardMousePosition.x <= 0) return;
        if (!held.equals("wall") && !held.equals("null")) {
            selection.towerJustPlaced = true; //prevents selection click sounds from playing
            playSound(sounds.get("littleButtonOut"), 1, 1);
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
                held.equals("wall") &&
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
                !upgradable(tileTower) &&
                currentLevel > 0)
            errorText = "Max level!";
        else if (enemyNearby()) errorText = "Blocked!";
        popupTexts.add(new PopupText(p, monoMedium, new Color(255, 0, 0, 254),
                new Color(50, 0, 0, 200), new PVector(boardMousePosition.x, boardMousePosition.y),
                errorText));
    }

    private void clearHand() {
        if (!held.equals("null")) {
            inGameGui.wallBuyButton.timer = 0;
            playSound(sounds.get("littleButtonOut"), 1, 1);
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
        if (boardMousePosition.x <= 0 || held.equals("null") || heldSprite == null || !alive) return;
        //red if implacable
        if (implacable) p.tint(Color.RED.getRGB(), 180);
        else p.tint(new Color(0xFFDF8D).getRGB(), 180);
        PVector pos = new PVector(
                (roundTo(boardMousePosition.x, 50)) - (25f / 2) - offset.x + 13,
                roundTo(boardMousePosition.y, 50) - (25f / 2) - offset.y + 13);
        p.image(heldSprite, pos.x, pos.y);
        p.tint(255);
        displayCritterCircles();
        if (displayInfo != null) displayWallInfo();
    }

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
        if (!held.equals("null") && !held.equals("wall") && !held.endsWith("TL")) displayTurretInfo(p, heldClass);
    }

    private void displayWallInfo() {
        Wall wall = getWall();
        PVector pos = new PVector(
                (roundTo(boardMousePosition.x, 50)) - (25f / 2) - offset.x + 13 + 25,
                roundTo(boardMousePosition.y, 50) - (25f / 2) - offset.y + 13 + 25);
        // asymmetrical because of edge cases
        if (pos.x > 900 || boardMousePosition.x < 0) return;

        String priceText = null;
        String hpGainText = null;
        int hp = 0;
        String sellText = null;
        boolean canAffordTop = false;
        boolean topEnabled = true;
        boolean leftEnabled = true;
        boolean bottomEnabled = true;
        switch (displayInfo) {
            case PlaceWall -> {
                priceText = "$" + Wall.BUY_PRICE;
                hpGainText = "50 HP";
                canAffordTop = money >= Wall.BUY_PRICE;
                leftEnabled = false;
                bottomEnabled = false;
            } case UpgradeWall -> {
                if (wall == null) return;
                priceText = "$" + nfc(wall.upgradePrices[wall.nextLevelB]);
                hpGainText = "+" + wall.upgradeHp[wall.nextLevelB] + " HP";
                canAffordTop = money >= wall.upgradePrices[wall.nextLevelB];
                hp = wall.hp;
                sellText = "+$" + nfc((int) (0.8f * (float) wall.getValue()));
            } case MaxWallUpgrade -> {
                if (wall == null) return;
                topEnabled = false;
                hp = wall.hp;
                sellText = "+$" + nfc((int) (0.8f * (float) wall.getValue()));
            }
        }

        if (topEnabled) {
            Color textColor = new Color(0xFFC42C);
            Color highlightColor = new Color(0xBF8B4F00, true);
            if (!canAffordTop) {
                textColor = new Color(255, 0, 0, 254);
                highlightColor = new Color(50, 0, 0, 200);
            }
            highlightedText(p, priceText, pos.copy().add(0, -47), textColor, highlightColor,
                    monoMedium, CENTER);
            highlightedText(p, hpGainText, pos.copy().add(0, -30), textColor, highlightColor,
                    monoMedium, CENTER);
        }
        if (leftEnabled) {
            Color textColor = Color.WHITE;
            Color highlightColor = new Color(0xBF343434, true);
            highlightedText(p, hp + "", pos.copy().add(-29, -5), textColor, highlightColor,
                    monoMedium, RIGHT);
            highlightedText(p, "HP", pos.copy().add(-29, 12), textColor, highlightColor,
                    monoMedium, RIGHT);
        }
        if (bottomEnabled) {
            Color textColor = new Color(255, 0, 0, 254);
            Color highlightColor = new Color(50, 0, 0, 200);
            highlightedText(p, sellText, pos.copy().add(0, 40), textColor, highlightColor,
                    monoMedium, CENTER);
        }
    }

    private Wall getWall() {
        Tile tile = tiles.get(
                (roundTo(boardMousePosition.x, 50) / 50) + 1,
                (roundTo(boardMousePosition.y, 50) / 50) + 1);
        if (tile == null) return null;
        if (tile.tower == null) return null;
        if (!(tile.tower instanceof Wall)) return null;
        return (Wall) tile.tower;
    }

    private void universalWallInfo() {
        p.fill(0, 20);
        p.strokeWeight(2);
        p.stroke(0);
        p.rect(905, 700, PANEL_WIDTH, 130);
        p.fill(0, 254);
        p.textFont(pg);
        p.textAlign(LEFT);
        p.text("""
                        - LClick to place wall
                        - LClick on wall to upgrade
                        - RClick on wall to sell
                        - Press TAB or click sidebar to deselect""",
                905 + 5, 700 + 5, 190 - 10, 195 - 10 - 9);
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
                price = Slingshot.price;
            } case "crossbow" -> {
                heldSprite = staticSprites.get("crossbowFullTR");
                offset = new PVector(2, 2);
                price = Crossbow.price;
            } case "miscCannon" -> {
                heldSprite = staticSprites.get("miscCannonFullTR");
                offset = new PVector(0, 0);
                price = RandomCannon.price;
            } case "cannon" -> {
                heldSprite = staticSprites.get("cannonFullTR");
                offset = new PVector(5, 5);
                price = Cannon.price;
            } case "gluer" -> {
                heldSprite = staticSprites.get("gluerFullTR");
                offset = new PVector(0, 0);
                price = Gluer.price;
            } case "seismic" -> {
                heldSprite = staticSprites.get("seismicFullTR");
                offset = new PVector(4, 4);
                price = SeismicTower.price;
            } case "energyBlaster" -> {
                heldSprite = staticSprites.get("energyBlasterFullTR");
                offset = new PVector(13, 13);
                price = EnergyBlaster.price;
            } case "magicMissleer" -> {
                heldSprite = staticSprites.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = MagicMissileer.price;
            } case "tesla" -> {
                heldSprite = staticSprites.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = TeslaTower.price;
            } case "nightmare" -> {
                heldSprite = staticSprites.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = Nightmare.price;
            } case "flamethrower" -> {
                heldSprite = staticSprites.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = Flamethrower.price;
            } case "iceTower" -> {
                heldSprite = staticSprites.get("iceTowerFullTR");
                offset = new PVector(0, 0);
                price = IceTower.price;
            } case "booster" -> {
                heldSprite = staticSprites.get("boosterFullTR");
                offset = new PVector(0, 0);
                price = Booster.price;
            } case "railgun" -> {
                heldSprite = staticSprites.get("railgunFullTR");
                offset = new PVector(6, 6);
                price = Railgun.price;
            } case "waveMotion" -> {
                heldSprite = staticSprites.get("waveMotionFullTR");
                offset = new PVector(14, 14);
                price = WaveMotion.price;
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