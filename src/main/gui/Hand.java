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
import static main.misc.MiscMethods.*;

public class Hand {

    private PApplet p;

    public String held;
    private PImage heldSprite;
    private PVector offset;
    private boolean implacable;
    public String displayInfo;
    public int price;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;
        displayInfo = "null";
    }

    public void main() {
        if (!levelBuilder) checkPlaceable();
        else implacable = false;
        if (inputHandler.rightMousePressedPulse) remove();
        if ((inputHandler.leftMousePressedPulse || inputHandler.rightMousePressedPulse) && p.mouseX > BOARD_WIDTH) {
            if (!held.equals("null")) {
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
        Tile tileTower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        Tile tileObstacle = tiles.get((roundTo(p.mouseX, 50) / 50), (roundTo(p.mouseY, 50) / 50));
        if (tileTower == null) implacable = true;
        else if (!held.equals("wall")) implacable = (tileTower.tower != null);
        else implacable = (tileTower.tower != null && tileTower.tower.turret);
        if (tileObstacle != null && (tileObstacle.obstacle != null || tileObstacle.machine)) implacable = true;
        if (price > money) implacable = true;
    }

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
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
                if (tile.tower.hp < tile.tower.maxHp) { //if low hp
                    heldSprite = spritesH.get("repairTW");
                    implacable = money < ceil((float) (tile.tower.price) - (float) (tile.tower.value));
                    displayInfo = "repairWall";
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

    private void displayHeld() { //shows whats held at ~1/2 opacity
//        System.out.println(heldSprite);
        if (!held.equals("null") && heldSprite != null && alive) {
            //red if implacable
            if (implacable) p.tint(255, 0, 0, 150);
            else p.tint(255, 150);
            p.image(heldSprite, (roundTo(p.mouseX, 50)) - (25f / 2) - offset.x + 13, roundTo(p.mouseY, 50) - (25f / 2) - offset.y + 13);
            p.tint(255);
        }
    }

    public void displayHeldInfo() {
        if (displayInfo.equals("placeWall")) { //todo: can't place on enemies?
            p.fill(235);
            p.noStroke();
            p.rect(900, 212, 200, 707);
            if (money >= 25) p.fill(195, 232, 188);
            else p.fill(230, 181, 181);
            p.rect(905, 247, 190, 119);
            p.textAlign(CENTER);
            p.fill(0);
            p.textFont(mediumLargeFont);
            p.text("Placing:", 1000, 241);
            p.textFont(largeFont);
            p.text("Wooden", 1000, 276);
            p.text("Wall", 1000, 301);
            p.textFont(mediumFont);
            p.text("50 HP", 1000, 331);
            p.text("$25", 1000, 356);
        }
        if (displayInfo.equals("upgradeWall")) {
            Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            if (tower.nextLevelB > tower.upgradeTitles.length - 1) displayInfo = "maxWallUpgrade";
            else {
                p.fill(235);
                p.noStroke();
                p.rect(900, 212, 200, 707);
                p.textAlign(CENTER);
                //tower info
                p.fill(231, 232, 190);
                p.rect(905, 247, 190, 119);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Selected:", 1000, 241);
                p.textFont(largeFont);
                if (tower.nextLevelB > 0) {
                    p.text(tower.upgradeTitles[tower.nextLevelB - 1], 1000, 276); //if not base level
                } else p.text("Wooden", 1000, 276);
                p.text("Wall", 1000, 301);
                p.textFont(mediumFont);
                p.text(tower.hp + " hp", 1000, 331);
                p.text("Sell for: $" + (int) (0.8f * (float) tower.value), 1000, 356);
                //upgrade info
                if (money >= tower.upgradePrices[tower.nextLevelB]) p.fill(195, 232, 188);
                else p.fill(230, 181, 181);
                p.rect(905, 401, 190, 119);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Upgrade:", 1000, 395);
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelB], 1000, 430);
                p.text("Wall", 1000, 455);
                p.textFont(mediumFont);
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
                p.text("+" + hpDisplay + " HP", 1000, 485);
                p.text("$" + tower.upgradePrices[tower.nextLevelB], 1000, 510);
            }
        }
        if (displayInfo.equals("maxWallUpgrade")) {
            Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            p.fill(235);
            p.noStroke();
            p.rect(900, 212, 200, 707);
            p.textAlign(CENTER);
            //tower info
            p.fill(231, 232, 190);
            p.rect(905, 247, 190, 119);
            p.textFont(mediumLargeFont);
            p.fill(0);
            p.text("Selected:", 1000, 241);
            p.textFont(largeFont);
            if (currentLevel == 0) p.text("Wooden", 1000, 276);
            else p.text(tower.upgradeTitles[currentLevel - 1], 1000, 276);
            p.text("Wall", 1000, 301);
            p.textFont(mediumFont);
            p.text(tower.hp + " hp", 1000, 331);
            p.text("Sell for: $" + (int) (0.8f * (float) tower.value), 1000, 356);
        }
        if (displayInfo.equals("repairWall")) { //todo: repair progress bar?
            Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
            p.fill(235);
            p.noStroke();
            p.rect(900, 212, 200, 707);
            p.textAlign(CENTER);
            //tower info
            if (money >= ceil((float) (tower.price) - (float) (tower.value))) p.fill(195, 232, 188);
            else p.fill(230, 181, 181);
            p.rect(905, 247, 190, 144);
            p.textFont(mediumLargeFont);
            p.fill(0);
            p.text("Selected:", 1000, 241);
            p.textFont(largeFont);
            if (tower.nextLevelB > 0) {
                p.text(tower.upgradeTitles[tower.nextLevelB - 1], 1000, 276); //if not base level
            } else p.text("Wooden", 1000, 276);
            p.text("Wall", 1000, 301);
            p.textFont(mediumFont);
            p.text(tower.hp + " hp", 1000, 331);
            p.text("Sell for: $" + (int) (0.8f * (float) tower.value), 1000, 356);
            p.text("Repair for: $" + ceil((float) (tower.price) - (float) (tower.value)), 1000, 381);
        }
        if (!displayInfo.equals("null")) {
            //universal info
            p.fill(200);
            p.rect(905, 700, 190, 195);
            p.fill(0);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text("LClick to place", 910, 720);
            p.text("wall", 910, 740);
            p.text("LClick on wall to", 910, 770);
            p.text("upgrade or repair", 910, 790);
            p.text("RClick on wall", 910, 820);
            p.text("to Sell", 910, 840);
            p.text("Click on sidebar", 910, 870);
            p.text("to deselect", 910, 890);
        }
    }

    public void setHeld(String heldSet) { //swaps whats held
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
                price = 150;
                break;
            case "magicMissleer":
                heldSprite = spritesH.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = 150;
                break;
            case "tesla":
                heldSprite = spritesH.get("teslaFullTR");
                offset = new PVector(0, 0);
                price = 150;
                break;
            case "nightmare":
                heldSprite = spritesH.get("nightmareFullTR");
                offset = new PVector(0, 0);
                price = 200;
                break;
            case "flamethrower":
                heldSprite = spritesH.get("flamethrowerFullTR");
                offset = new PVector(7, 7);
                price = 200;
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
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower != null && !tile.tower.turret) tile.tower.sell();
        }
    }

    private void place() { //puts down tower and subtracts price
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        boolean changeHeld = true;
        if (held.equals("slingshot") && alive) tile.tower = new Slingshot(p, tile);
        else if (held.equals("crossbow") && alive) tile.tower = new Crossbow(p, tile);
        else if (held.equals("miscCannon") && alive) tile.tower = new RandomCannon(p, tile);
        else if (held.equals("cannon") && alive) tile.tower = new Cannon(p, tile);
        else if (held.equals("gluer") && alive) tile.tower = new Gluer(p, tile);
        else if (held.equals("seismic") && alive) tile.tower = new SeismicTower(p, tile);
        else if (held.equals("energyBlaster") && alive) tile.tower = new EnergyBlaster(p, tile);
        else if (held.equals("magicMissleer") && alive) tile.tower = new MagicMissileer(p, tile);
        else if (held.equals("tesla") && alive) tile.tower = new TeslaTower(p, tile);
        else if (held.equals("nightmare") && alive) tile.tower = new Nightmare(p, tile);
        else if (held.equals("flamethrower") && alive) tile.tower = new Flamethrower(p, tile);
        else if (held.equals("railgun") && alive) tile.tower = new Railgun(p, tile);
        else if (held.equals("waveMotion") && alive) tile.tower = new WaveMotion(p, tile);
        else if (held.equals("wall") && alive) {
            if (tile.tower != null && !tile.tower.turret) { //upgrade or repair
                if (tile.tower.hp < tile.tower.maxHp && money >= ceil((float) (tile.tower.price) - (float) (tile.tower.value))) {
                    tile.tower.repair();
                } else if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && money >= tile.tower.price) { //upgrade
                    money -= tile.tower.upgradePrices[tile.tower.nextLevelB];
                    tile.tower.upgrade(0);
                    connectWallQueues++;
                }
                money += price; //cancel out price change later
            } else {
                tile.tower = new Wall(p, tile);
                updateWallTiles();
                connectWallQueues++;
            }
            changeHeld = false;
        }
        if (held.contains("TL")) {
            tile = tiles.get((roundTo(p.mouseX, 50) / 50), (roundTo(p.mouseY, 50) / 50));
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