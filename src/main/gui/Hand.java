package main.gui;

import main.gui.guiObjects.buttons.TowerBuy;
import main.misc.Tile;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.roundTo;
import static main.misc.MiscMethods.updateNodes;
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

    public Hand(PApplet p) {
        this.P = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;
        displayInfo = "null";
    }

    public void main() {
        if (paused && !held.equals("null")) setHeld("null");
        if (!levelBuilder) checkPlaceable();
        else implacable = false;
        if (inputHandler.rightMousePressedPulse) remove();
        if ((inputHandler.leftMousePressedPulse && P.mouseX > BOARD_WIDTH) || (inputHandler.rightMousePressedPulse && !held.equals("wall"))) {
            if (!held.equals("null")) {
                inGameGui.wallBuyButton.timer = 0;
                soundsH.get("clickOut").stop();
                soundsH.get("clickOut").play(1, volume);
            }
            held = "null";
            for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.depressed = false;
        }
        if (!levelBuilder) checkDisplay();
        displayHeld();
        if (inputHandler.leftMousePressedPulse) {
            if (!held.equals("wall") && !held.equals("null")) {
                selection.towerJustPlaced = true; //prevents selection click sounds from playing
                soundsH.get("clickOut").stop();
                soundsH.get("clickOut").play(1, volume);
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

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower != null && !tile.tower.turret) { //if wall
                if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && tile.tower.nextLevelB < currentLevel) { //if upgradeable
                    heldSprite = spritesH.get("upgradeTW");
                    implacable = money < tile.tower.upgradePrices[tile.tower.nextLevelB];
                    displayInfo = "upgradeWall";
                } else {
                    if (currentLevel > 0) heldSprite = spritesH.get("upgradeTW");
                    implacable = true;
                    displayInfo = "maxWallUpgrade";
                }
            } else {
                heldSprite = spritesH.get("placeTW"); //reset wall sprite
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
        if (displayInfo.equals("placeWall")) { //todo: can't place too close to enemy
            P.fill(235);
            P.noStroke();
            P.rect(900, 212, 200, 707);
            if (money >= 25) P.fill(195, 232, 188);
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
            P.text("$25", 1000, 356);
        }
        if (displayInfo.equals("upgradeWall")) {
            Tower tower = tiles.get((roundTo(P.mouseX, 50) / 50) + 1, (roundTo(P.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            if (tower.nextLevelB > tower.upgradeTitles.length - 1) displayInfo = "maxWallUpgrade";
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
                if (tower.nextLevelB > 0) {
                    P.text(tower.upgradeTitles[tower.nextLevelB - 1], 1000, 276); //if not base level
                } else P.text("Wooden", 1000, 276);
                P.text("Wall", 1000, 301);
                P.textFont(mediumFont);
                P.text(tower.hp + " hp", 1000, 331);
                P.text("Sell for: $" + (int) (0.8f * (float) tower.value), 1000, 356);
                //upgrade info
                if (money >= tower.upgradePrices[tower.nextLevelB]) P.fill(195, 232, 188);
                else P.fill(230, 181, 181);
                P.rect(905, 401, 190, 119);
                P.textFont(mediumLargeFont);
                P.fill(0);
                P.text("Upgrade:", 1000, 395);
                P.textFont(largeFont);
                P.text(tower.upgradeTitles[tower.nextLevelB], 1000, 430);
                P.text("Wall", 1000, 455);
                P.textFont(mediumFont);
                int hpDisplay = 0;
                switch (tower.name) {
                    case "woodWall":
                        hpDisplay = 75;
                        break;
                    case "stoneWall":
                        hpDisplay = 125;
                        break;
                    case "metalWall":
                        hpDisplay = 250;
                        break;
                    case "crystalWall":
                        hpDisplay = 500;
                        break;
                }
                P.text("+" + hpDisplay + " HP", 1000, 485);
                P.text("$" + tower.upgradePrices[tower.nextLevelB], 1000, 510);
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
            P.text("Click on sidebar", 910, 870);
            P.text("to deselect", 910, 890);
        }
    }

    /**
     * Swaps what's held.
     * @param heldSet name of what should be held
     */
    public void setHeld(String heldSet) {
        switch (heldSet) {
            case "slingshot":
                heldSprite = spritesH.get("slingshotFullTR");
                offset = new PVector(0, 0);
                price = SLINGSHOT_PRICE;
                break;
            case "crossbow":
                heldSprite = spritesH.get("crossbowFullTR");
                offset = new PVector(2, 2);
                price = CROSSBOW_PRICE;
                break;
            case "miscCannon":
                heldSprite = spritesH.get("miscCannonFullTR");
                offset = new PVector(0, 0);
                price = RANDOMCANNON_PRICE;
                break;
            case "cannon":
                heldSprite = spritesH.get("cannonFullTR");
                offset = new PVector(5, 5);
                price = CANNON_PRICE;
                break;
            case "gluer":
                heldSprite = spritesH.get("gluerFullTR");
                offset = new PVector(0, 0);
                price = GLUER_PRICE;
                break;
            case "seismic":
                heldSprite = spritesH.get("seismicFullTR");
                offset = new PVector(4, 4);
                price = SEISMIC_PRICE;
                break;
            case "energyBlaster":
                heldSprite = spritesH.get("energyBlasterFullTR");
                offset = new PVector(13, 13);
                price = ENERGYBLASTER_PRICE;
                break;
            case "magicMissleer":
                heldSprite = spritesH.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = 150;
                break;
            case "tesla":
                heldSprite = spritesH.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = TESLATOWER_PRICE;
                break;
            case "nightmare":
                heldSprite = spritesH.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = 200;
                break;
            case "flamethrower":
                heldSprite = spritesH.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = FLAMETHROWER_PRICE;
                break;
            case "railgun":
                heldSprite = spritesH.get("railgunFullTR");
                offset = new PVector(6, 6);
                price = 200;
                break;
            case "waveMotion":
                heldSprite = spritesH.get("waveMotionFullTR");
                offset = new PVector(0, 0);
                price = 250;
                break;
            case "wall":
                heldSprite = spritesH.get("woodWallTW");
                offset = new PVector(0, 0);
                price = 25;
                break;
            case "null":
                heldSprite = null;
                break;
        }
        if (heldSet.contains("TL")) {
            offset = new PVector(0,0);
            price = 0;
            heldSprite = spritesH.get(heldSet);
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