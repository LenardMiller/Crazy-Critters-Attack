package main.gui;

import main.guiObjects.buttons.TowerBuy;
import main.towers.Tile;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.util.MiscMethods.roundTo;

public class Hand { //what is selected, eg: slingshot

    private PApplet p;

    public String held;
    private PImage heldSprite;
    private PVector offset;
    private boolean implacable;
    private String displayInfo;
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
        checkPlaceable();
        if (inputHandler.leftMousePressedPulse && !implacable) place();
        if (inputHandler.rightMousePressedPulse) remove();
        if ((inputHandler.leftMousePressedPulse || inputHandler.rightMousePressedPulse) && p.mouseX > BOARD_WIDTH) {
            held = "null";
            for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.depressed = false;
        }
        checkDisplay();
        displayHeld();
    }

    private void checkPlaceable() {
        implacable = false;
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (tile == null) implacable = true;
        else if (!held.equals("wall")) implacable = (tile.tower != null);
        else implacable = (tile.tower != null && tile.tower.turret);
        if (!implacable) implacable = (price > money);
    }

    private void checkDisplay() {
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile != null && tile.tower != null && !tile.tower.turret) { //if wall
                if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length) { //if upgradeable
                    heldSprite = spritesH.get("upgradeTW");
                    implacable = money < tile.tower.upgradePrices[tile.tower.nextLevelB];
                    displayInfo = "upgradeWall";
                } else {
                    heldSprite = spritesH.get("upgradeTW");
                    implacable = true;
                    displayInfo = "maxWallUpgrade";
                }
                if (tile.tower.hp < tile.tower.maxHp) { //if low hp
                    heldSprite = spritesH.get("repairTW");
                    implacable = money < ceil((float)(tile.tower.price) - (float)(tile.tower.value));
                    displayInfo = "repairWall";
                }
            } else {
                heldSprite = spritesH.get("woodWallTW"); //reset wall sprite
                displayInfo = "placeWall";
            }
        } else {
            setHeld(held); //reset sprite
            displayInfo = "null";
        }
    }

    private void displayHeld() { //shows whats held at ~1/2 opacity
        if (!held.equals("null") && alive) {
            //red if implacable
            if (implacable) p.tint(255, 0, 0, 150);
            else p.tint(255, 150);
            p.image(heldSprite, (roundTo(p.mouseX, 50)) - (25f / 2) - offset.x + 13, roundTo(p.mouseY, 50) - (25f / 2) - offset.y + 13);
            p.tint(255);
        }
    }

    public void displayHeldInfo() {
        if (tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1) != null) {
            if (displayInfo.equals("placeWall")) {
                p.fill(235);
                p.noStroke();
                p.rect(700, 211, 200, 707);
                if (money >= 25) p.fill(195, 232, 188);
                else p.fill(230, 181, 181);
                p.rect(705,247,190,119);
                p.textAlign(CENTER);
                p.fill(0);
                p.textFont(mediumLargeFont);
                p.text("Placing:", 800, 241);
                p.textFont(largeFont);
                p.text("Wooden", 800, 276);
                p.text("Wall", 800, 301);
                p.textFont(mediumFont);
                p.text("50 HP", 800, 331);
                p.text("$25", 800, 356);
            }
            if (displayInfo.equals("upgradeWall")) {
                Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
                p.fill(235);
                p.noStroke();
                p.rect(700, 211, 200, 707);
                p.textAlign(CENTER);
                //tower info
                p.fill(231, 232, 190);
                p.rect(705,247,190,119);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Selected:", 800, 241);
                p.textFont(largeFont);
                if (tower.nextLevelB > 0) {
                    p.text(tower.upgradeTitles[tower.nextLevelB - 1], 800, 276); //if not base level
                } else p.text("Wooden", 800, 276);
                p.text("Wall", 800, 301);
                p.textFont(mediumFont);
                p.text(tower.hp + " hp", 800, 331);
                p.text("Sell for: $" + (int)(0.8f*(float)tower.value),800,356);
                //upgrade info
                if (money >= tower.upgradePrices[tower.nextLevelB]) p.fill(195, 232, 188);
                else p.fill(230, 181, 181);
                p.rect(705,401,190,119);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Upgrade:", 800, 395);
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelB], 800, 430);
                p.text("Wall", 800, 455);
                p.textFont(mediumFont);
                p.text("+" + tower.upgradeHealth[tower.nextLevelB] + " HP", 800, 485);
                p.text("$" + tower.upgradePrices[tower.nextLevelB], 800, 510);
            }
            if (displayInfo.equals("maxWallUpgrade")) {
                Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
                p.fill(235);
                p.noStroke();
                p.rect(700, 211, 200, 707);
                p.textAlign(CENTER);
                //tower info
                p.fill(231, 232, 190);
                p.rect(705,247,190,119);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Selected:", 800, 241);
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.upgradeTitles.length-1], 800, 276);
                p.text("Wall", 800, 301);
                p.textFont(mediumFont);
                p.text(tower.hp + " hp", 800, 331);
                p.text("Sell for: $" + (int)(0.8f*(float)tower.value),800,356);
            }
            if (displayInfo.equals("repairWall")) {
                Tower tower = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1).tower; //should be a wall I hope
                p.fill(235);
                p.noStroke();
                p.rect(700,211,200,707);
                p.textAlign(CENTER);
                //tower info
                if (money >= ceil((float)(tower.price) - (float)(tower.value))) p.fill(195, 232, 188);
                else p.fill(230, 181, 181);
                p.rect(705,247,190,144);
                p.textFont(mediumLargeFont);
                p.fill(0);
                p.text("Selected:", 800, 241);
                p.textFont(largeFont);
                if (tower.nextLevelB > 0) {
                    p.text(tower.upgradeTitles[tower.nextLevelB - 1], 800, 276); //if not base level
                } else p.text("Wooden", 800, 276);
                p.text("Wall", 800, 301);
                p.textFont(mediumFont);
                p.text(tower.hp + " hp", 800, 331);
                p.text("Sell for: $" + (int)(0.8f*(float)tower.value),800,356);
                p.text("Repair for: $" + ceil((float)(tower.price) - (float)(tower.value)),800,381);
            }
            if (!displayInfo.equals("null")) {
                //universal info
                p.fill(200);
                p.rect(705,700,190,195);
                p.fill(0);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text("LClick to place", 710, 720);
                p.text("tower", 710, 740);
                p.text("LClick on tower to", 710, 770);
                p.text("upgrade or repair", 710, 790);
                p.text("RClick on tower", 710, 820);
                p.text("to Sell", 710, 840);
                p.text("Click on sidebar", 710, 870);
                p.text("to deselect",710,890);
            }
        }
    }

    public void setHeld(String heldSet) { //swaps whats held
        if (!heldSet.equals("null")) {
            switch (heldSet) {
                case "slingshot":
                    heldSprite = spritesH.get("slingshotFullTR");
                    offset = new PVector(0, 0);
                    price = 50;
                    break;
                case "crossbow":
                    heldSprite = spritesH.get("crossbowFullTR");
                    offset = new PVector(2, 2);
                    price = 100;
                    break;
                case "miscCannon":
                    heldSprite = spritesH.get("miscCannonFullTR");
                    offset = new PVector(0, 0);
                    price = 100;
                    break;
                case "energyBlaster":
                    heldSprite = spritesH.get("energyBlasterFullTR");
                    offset = new PVector(11, 11);
                    price = 150;
                    break;
                case "magicMissleer":
                    heldSprite = spritesH.get("magicMissleerFullTR");
                    offset = new PVector(0, 0);
                    price = 150;
                    break;
                case "wall":
                    heldSprite = spritesH.get("woodWallTW");
                    offset = new PVector(0, 0);
                    price = 25;
                    break;
            }
            held = heldSet;
        }
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
        else if (held.equals("energyBlaster") && alive) tile.tower = new EnergyBlaster(p, tile);
        else if (held.equals("magicMissleer") && alive) tile.tower = new MagicMissileer(p, tile);
        else if (held.equals("wall") && alive) {
            if (tile.tower != null && !tile.tower.turret) { //upgrade or repair
                if (tile.tower.hp < tile.tower.maxHp && money >= ceil((float)(tile.tower.price) - (float)(tile.tower.value))) {
                    tile.tower.repair();
                } else if (tile.tower.nextLevelB < tile.tower.upgradeIcons.length && money >= tile.tower.price) { //upgrade
                    money -= tile.tower.price;
                    tile.tower.upgrade(0);
                }
                money += price; //cancel out price change later
            } else tile.tower = new Wall(p, tile);
            changeHeld = false;
        }
        money -= price;
        if (changeHeld) held = "null";
//        path.nodeCheckObs();
    }
}