package main.gui;

import main.towers.Tower;
import processing.core.PApplet;

import static main.Main.*;

public class Selection { //what tower is selected

    private PApplet p;

    public String name;
    public int id;

    public Selection(PApplet p) {
        this.p = p;
        name = "null";
    }

    public void main() {
        clickoff();
        if (!name.equals("null")) { //don't display if nothing held
            display();
        }
    }

    public void swapSelected(int id) { //switches what is selected
        for (int i = tiles.size() - 1; i >= 0; i--) {
            if (tiles.get(i).tower != null) {
                Tower tower = tiles.get(i).tower;
                tower.visualize = false;
            }
        }
        this.id = id;
        Tower tower = tiles.get(id).tower;
        name = tower.name;
        sellButton.active = true;
        upgradeButtonOne.active = true;
        upgradeGuiObjectOne.active = true;
        if (tower.turret) {
            targetButton.active = true;
            repairButton.active = false;
            tower.visualize = true;
            upgradeButtonZero.active = true;
            upgradeButtonOne.position.y = 735;
            upgradeButtonZero.position.y = 585;
            upgradeGuiObjectZero.active = true;
            upgradeGuiObjectZero.position.y = 565;
            upgradeGuiObjectOne.position.y = 715;
            if (tower.nextLevelZero < tower.upgradeNames.length / 2) {
                upgradeGuiObjectZero.sprite = tower.upgradeIcons[tower.nextLevelZero];
            } else {
                upgradeGuiObjectZero.sprite = spritesAnimH.get("upgradeIC")[0];
            }
            if (tower.nextLevelOne < tower.upgradeNames.length) {
                upgradeGuiObjectOne.sprite = tower.upgradeIcons[tower.nextLevelOne];
            } else {
                upgradeGuiObjectOne.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
            targetButton.active = false;
            upgradeButtonOne.position.y += 45;
            upgradeButtonZero.position.y += 45;
            upgradeGuiObjectZero.position.y += 45;
            upgradeGuiObjectOne.position.y += 45;
        }
        if (!tower.turret) {
            targetButton.active = false;
            repairButton.active = true;
            upgradeButtonZero.active = false;
            upgradeButtonOne.position.y = 630;
            upgradeGuiObjectZero.active = false;
            upgradeGuiObjectOne.position.y = 610;
            if (tower.nextLevelOne < tower.upgradeNames.length) {
                upgradeGuiObjectOne.sprite = tower.upgradeIcons[tower.nextLevelOne];
            } else {
                upgradeGuiObjectOne.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
    }

    private void clickoff() { //desselect, hide stuff
        Tower tower = tiles.get(id).tower;
        if (tower != null) {
            if (p.mousePressed && p.mouseX < 700 && (p.mouseX > tower.tile.position.x || p.mouseX < tower.tile.position.x - tower.size.x || p.mouseY > tower.tile.position.y || p.mouseY < tower.tile.position.y - tower.size.y) && alive) {
                name = "null";
                sellButton.active = false;
                targetButton.active = false;
                repairButton.active = false;
                upgradeButtonZero.active = false;
                upgradeButtonOne.active = false;
                upgradeGuiObjectZero.active = false;
                upgradeGuiObjectOne.active = false;
                tower.visualize = false;
            }
        }
    }

    private void display() {
        Tower tower = tiles.get(id).tower;
        int speed = 0;
        int x = 0;
        String priority = "first";

        //bg
        p.fill(235);
        p.noStroke();
        if (tower.turret) { //different size bg so buttons fit
            p.rect(700, 210, 200, 300);
        }
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
            p.rect(700, 210, 200, 345);
        }
        if (!tower.turret) {
            p.rect(700, 210, 200, 345);
        }

        //name and special features
        p.textAlign(CENTER);
        p.fill(0);
        p.textFont(largeFont);
        switch (tower.name) {
            case "slingshot":
                p.text("Slingshot", 800, 241);
                speed = 12;
                break;
            case "crossbow":
                p.text("Crossbow", 800, 241);
                speed = 24;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Piercing", 710, 376 + x);
                p.fill(0);
                break;
            case "miscCannon":
                p.text("Random", 800, 241);
                p.text("Cannon", 800, 266);
                x = 25;
                speed = 12;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(0);
                break;
        }
        if (tower.name.equals("energyBlaster")) {
            p.text("Energy Blaster", 800, 241);
            speed = 16;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Splash damage", 710, 376 + x);
            p.fill(0);
        }
        if (tower.name.equals("magicMissleer")) {
            p.text("Magic Missileer", 800, 241);
            speed = 5;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Three homing missiles", 710, 376 + x);
            p.text("First, last and strong", 710, 396 + x);
        }
        switch (tower.name) {
            case "magicMissleerFour":
                p.text("Magic Missileer", 800, 241);
                speed = 5;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Four homing missiles", 710, 376 + x);
                p.text("First, last, strong", 710, 396 + x);
                p.text("and random", 710, 416 + x);
                break;
            case "woodWall":
                p.text("Wooden", 800, 241);
                p.text("Wall", 800, 266);
                x = 25;
                break;
            case "stoneWall":
                p.text("Stone", 800, 241);
                p.text("Wall", 800, 266);
                x = 25;
                break;
            case "metalWall":
                p.text("Metal", 800, 241);
                p.text("Wall", 800, 266);
                x = 25;
                break;
            case "crystalWall":
                p.text("Crystal", 800, 241);
                p.text("Wall", 800, 266);
                x = 25;
                break;
            case "ultimateWall":  //placeholder name?
                p.text("Titanium", 800, 241);
                p.text("Wall", 800, 266);
                x = 25;
                break;
            case "devWall":
                p.text("Developer", 800, 241);
                p.text("Wall", 800, 266);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Invulnerable", 710, 296 + 25);
                p.fill(0);
                break;
        }

        //put box around selected
        p.fill(255, 25);
        p.stroke(255);
        p.rect(tower.tile.position.x - tower.size.x, tower.tile.position.y - tower.size.y, tower.size.x, tower.size.y);

        //priority
        p.textFont(largeFont);
        p.textAlign(CENTER);
        if (tower.priority == 0) {
            priority = "first";
        } else if (tower.priority == 1) {
            priority = "last";
        } else if (tower.priority == 2) {
            priority = "strong";
        } else if (tower.priority == 3) {
            priority = "close";
        }
        if (tower.turret && !tower.name.equals("magicMissleer") && !tower.name.equals("magicMissleerFour")) {
            p.fill(75, 45, 0);
            p.text("Priority: " + priority, 800, 843);
        }

        //repair
        if (!tower.turret) {
            if (tower.twHp < tower.maxHp) {
                if (money >= ceil((float) (tower.price) - (float) (tower.value))) {
                    p.fill(11, 56, 0);
                } else {
                    p.fill(75, 0, 0);
                }
                p.text("$" + ceil((float) (tower.price) - (float) (tower.value)), 800, 843);
            } else {
                p.fill(15);
            }
            p.text("Repair", 800, 735);
        }

        //upgrade Zero
        int y;
        if (tower.turret) { //only display if turret
            y = -45;
            if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
                y += 45;
            }
            if (tower.nextLevelZero < tower.upgradeNames.length / 2) {
                if (money >= tower.upgradePrices[tower.nextLevelZero]) {
                    p.fill(11, 56, 0);
                } else {
                    p.fill(75, 0, 0);
                }
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelZero], 800, 585 + y);
                p.text("$" + tower.upgradePrices[tower.nextLevelZero], 800, 693 + y);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text(tower.upgradeDescOne[tower.nextLevelZero], 715, 615 + y);
                p.text(tower.upgradeDescTwo[tower.nextLevelZero], 715, 635 + y);
                p.text(tower.upgradeDescThree[tower.nextLevelZero], 715, 655 + y);
            } else {
                p.fill(15);
                p.textFont(largeFont);
                p.text("N/A", 800, 585 + y);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text("No more", 715, 615 + y);
                p.text("upgrades", 715, 635 + y);
            }
        }
        //upgrade One
        y = 0;
        if (tower.turret) {
            y = 105;
        }
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
            y += 45;
        }
        if (tower.nextLevelOne < tower.upgradeNames.length) {
            if (money >= tower.upgradePrices[tower.nextLevelOne]) {
                p.fill(11, 56, 0);
            } else {
                p.fill(75, 0, 0);
            }
            p.textFont(largeFont);
            p.textAlign(CENTER);
            p.text(tower.upgradeTitles[tower.nextLevelOne], 800, 585 + y);
            p.text("$" + tower.upgradePrices[tower.nextLevelOne], 800, 693 + y);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text(tower.upgradeDescOne[tower.nextLevelOne], 715, 615 + y);
            p.text(tower.upgradeDescTwo[tower.nextLevelOne], 715, 635 + y);
            p.text(tower.upgradeDescThree[tower.nextLevelOne], 715, 655 + y);
        } else {
            p.fill(15);
            p.textFont(largeFont);
            p.text("N/A", 800, 585 + y);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text("No more", 715, 615 + y);
            p.text("upgrades", 715, 635 + y);
        }

        //sell
        p.fill(75, 0, 0);
        p.textFont(largeFont);
        p.textAlign(CENTER);
        p.text("Sell for: $" + floor(tower.value * .8f), 800, 888);

        //health
        p.fill(0);
        p.textFont(mediumFont);
        p.textAlign(LEFT);
        p.text("Health: " + tower.twHp + "/" + tower.maxHp, 710, 276 + x);

        //stats
        if (tower.turret) {
            //damage
            p.text("Damage: " + tower.damage, 710, 296 + x);
            //firerate (delay)
            p.text("Load time: " + (float) (round((float) (tower.delay) / 6)) / 10 + "s", 710, 316 + x);
            //accuracy (error)
            if (tower.error == 0) {
                p.text("Perfect accuracy", 710, 336 + x);
            } else if (tower.error > 0 && tower.error < 1) {
                p.text("High accuracy", 710, 336 + x);
            } else if (tower.error >= 1 && tower.error <= 3) {
                p.text("Medium accuracy", 710, 336 + x);
            } else if (tower.error > 3) {
                p.text("Low accuracy", 710, 336 + x);
            }
            //velocity
            if (speed < 8) {
                p.text("Low velocity", 710, 356 + x);
            } else if (speed <= 18) {
                p.text("Medium velocity", 710, 356 + x);
            } else {
                p.text("High velocity", 710, 356 + x);
            }
        }
    }
}