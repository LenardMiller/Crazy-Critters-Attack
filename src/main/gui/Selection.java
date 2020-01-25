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
        Tower tower = tiles.get(id).tower;
        hand.held = "null";
        if (tower.turret) {
            for (int i = tiles.size() - 1; i >= 0; i--) {
                if (tiles.get(i).tower != null) {
                    tiles.get(i).tower.visualize = false;
                }
            }
            this.id = id;
            name = tower.name;
            sellButton.active = true;
            upgradeButtonB.active = true;
            upgradeIconB.active = true;
            if (tower.turret) {
                targetButton.active = true;
                tower.visualize = true;
                upgradeButtonA.active = true;
                upgradeButtonB.position.y = 735;
                upgradeButtonA.position.y = 585;
                upgradeIconA.active = true;
                upgradeIconA.position.y = 565;
                upgradeIconB.position.y = 715;
                if (tower.nextLevelA < tower.upgradeNames.length / 2) {
                    upgradeIconA.sprite = tower.upgradeIcons[tower.nextLevelA];
                } else {
                    upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
                }
                if (tower.nextLevelB < tower.upgradeNames.length) {
                    upgradeIconB.sprite = tower.upgradeIcons[tower.nextLevelB];
                } else {
                    upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
                }
            }
            if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
                targetButton.active = false;
                upgradeButtonB.position.y += 45;
                upgradeButtonA.position.y += 45;
                upgradeIconA.position.y += 45;
                upgradeIconB.position.y += 45;
            }
            if (!tower.turret) {
                targetButton.active = false;
                upgradeButtonA.active = false;
                upgradeButtonB.position.y = 630;
                upgradeIconA.active = false;
                upgradeIconB.position.y = 610;
                if (tower.nextLevelB < tower.upgradeNames.length) {
                    upgradeIconB.sprite = tower.upgradeIcons[tower.nextLevelB];
                } else {
                    upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
                }
            }
        }
    }

    private void clickoff() { //desselect, hide stuff
        Tower tower = tiles.get(id).tower;
        if (tower != null) {
            if (inputHandler.leftMousePressedPulse && p.mouseX < 700 && (p.mouseX > tower.tile.position.x || p.mouseX < tower.tile.position.x - tower.size.x || p.mouseY > tower.tile.position.y || p.mouseY < tower.tile.position.y - tower.size.y) && alive) {
                name = "null";
                sellButton.active = false;
                targetButton.active = false;
                upgradeButtonA.active = false;
                upgradeButtonB.active = false;
                upgradeIconA.active = false;
                upgradeIconB.active = false;
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
        if ("magicMissleerFour".equals(tower.name)) {
            p.text("Magic Missileer", 800, 241);
            speed = 5;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Four homing missiles", 710, 376 + x);
            p.text("First, last, strong", 710, 396 + x);
            p.text("and random", 710, 416 + x);
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

        //upgrade Zero
        int y;
        if (tower.turret) { //only display if turret
            y = -45;
            if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
                y += 45;
            }
            if (tower.nextLevelA < tower.upgradeNames.length / 2) {
                if (money >= tower.upgradePrices[tower.nextLevelA]) {
                    p.fill(11, 56, 0);
                } else {
                    p.fill(75, 0, 0);
                }
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelA], 800, 585 + y);
                p.text("$" + tower.upgradePrices[tower.nextLevelA], 800, 693 + y);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text(tower.upgradeDescA[tower.nextLevelA], 715, 615 + y);
                p.text(tower.upgradeDescB[tower.nextLevelA], 715, 635 + y);
                p.text(tower.upgradeDescC[tower.nextLevelA], 715, 655 + y);
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
        if (tower.nextLevelB < tower.upgradeNames.length) {
            if (money >= tower.upgradePrices[tower.nextLevelB]) {
                p.fill(11, 56, 0);
            } else {
                p.fill(75, 0, 0);
            }
            p.textFont(largeFont);
            p.textAlign(CENTER);
            p.text(tower.upgradeTitles[tower.nextLevelB], 800, 585 + y);
            p.text("$" + tower.upgradePrices[tower.nextLevelB], 800, 693 + y);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text(tower.upgradeDescA[tower.nextLevelB], 715, 615 + y);
            p.text(tower.upgradeDescB[tower.nextLevelB], 715, 635 + y);
            p.text(tower.upgradeDescC[tower.nextLevelB], 715, 655 + y);
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
        p.text("Health: " + tower.hp + "/" + tower.maxHp, 710, 276 + x);

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