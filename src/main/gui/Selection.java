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
            if (inputHandler.leftMousePressedPulse && p.mouseX < 900 && (p.mouseX > tower.tile.position.x || p.mouseX < tower.tile.position.x - tower.size.x || p.mouseY > tower.tile.position.y || p.mouseY < tower.tile.position.y - tower.size.y) && alive) {
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
        int offset = 0;
        String priority = "first";

        //bg
        p.fill(235);
        p.noStroke();
        //different size bg so buttons fit
        if (tower.turret) p.rect(900, 210, 200, 300);
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) p.rect(900, 210, 200, 345);
        if (!tower.turret) p.rect(900, 210, 200, 345);

        //name and special features
        p.textAlign(CENTER);
        p.fill(0);
        p.textFont(largeFont);
        switch (tower.name) {
            case "slingshot":
                p.text("Slingshot", 1000, 241);
                speed = 12;
                break;
            case "crossbow":
                p.text("Crossbow", 1000, 241);
                speed = 24;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Piercing", 910, 376 + offset);
                p.fill(0);
                break;
            case "miscCannon":
                p.text("Random", 1000, 241);
                p.text("Cannon", 1000, 266);
                offset = 25;
                speed = 12;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(0);
                break;
        }
        if (tower.name.equals("energyBlaster")) {
            p.text("Energy Blaster", 1000, 241);
            speed = 16;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Splash damage", 910, 376 + offset);
            p.fill(0);
        }
        if (tower.name.equals("magicMissleer")) {
            p.text("Magic Missileer", 1000, 241);
            speed = 5;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Three homing missiles", 910, 376 + offset);
            p.text("First, last and strong", 910, 396 + offset);
        }
        if ("magicMissleerFour".equals(tower.name)) {
            p.text("Magic Missileer", 1000, 241);
            speed = 5;
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.fill(100, 0, 200);
            p.text("Four homing missiles", 910, 376 + offset);
            p.text("First, last, strong", 910, 396 + offset);
            p.text("and random", 910, 416 + offset);
        }

        //put box around selected
        p.fill(255, 25);
        p.stroke(255);
        p.rect(tower.tile.position.x - tower.size.x, tower.tile.position.y - tower.size.y, tower.size.x, tower.size.y);

        //priority
        p.textFont(largeFont);
        p.textAlign(CENTER);
        if (tower.priority == 0) priority = "first";
        else if (tower.priority == 1) priority = "last";
        else if (tower.priority == 2) priority = "strong";
        else if (tower.priority == 3) priority = "close";
        if (tower.turret && !tower.name.equals("magicMissleer") && !tower.name.equals("magicMissleerFour")) {
            p.fill(75, 45, 0);
            p.text("Priority: " + priority, 1000, 843);
        }

        //upgrade Zero
        int offsetB;
        if (tower.turret) { //only display if turret
            offsetB = -45;
            if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) offsetB += 45;
            if (tower.nextLevelA < tower.upgradeNames.length / 2) {
                if (money >= tower.upgradePrices[tower.nextLevelA]) p.fill(11, 56, 0);
                else p.fill(75, 0, 0);
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelA], 1000, 585 + offsetB);
                p.text("$" + tower.upgradePrices[tower.nextLevelA], 1000, 693 + offsetB);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text(tower.upgradeDescA[tower.nextLevelA], 915, 615 + offsetB);
                p.text(tower.upgradeDescB[tower.nextLevelA], 915, 635 + offsetB);
                p.text(tower.upgradeDescC[tower.nextLevelA], 915, 655 + offsetB);
            } else {
                p.fill(15);
                p.textFont(largeFont);
                p.text("N/A", 1000, 585 + offsetB);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text("No more", 915, 615 + offsetB);
                p.text("upgrades", 915, 635 + offsetB);
            }
        }
        //upgrade One
        offsetB = 0;
        if (tower.turret) offsetB = 105;
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) offsetB += 45;
        if (tower.nextLevelB < tower.upgradeNames.length) {
            if (money >= tower.upgradePrices[tower.nextLevelB]) p.fill(11, 56, 0);
            else p.fill(75, 0, 0);
            p.textFont(largeFont);
            p.textAlign(CENTER);
            p.text(tower.upgradeTitles[tower.nextLevelB], 1000, 585 + offsetB);
            p.text("$" + tower.upgradePrices[tower.nextLevelB], 1000, 693 + offsetB);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text(tower.upgradeDescA[tower.nextLevelB], 915, 615 + offsetB);
            p.text(tower.upgradeDescB[tower.nextLevelB], 915, 635 + offsetB);
            p.text(tower.upgradeDescC[tower.nextLevelB], 915, 655 + offsetB);
        } else {
            p.fill(15);
            p.textFont(largeFont);
            p.text("N/A", 1000, 585 + offsetB);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text("No more", 915, 615 + offsetB);
            p.text("upgrades", 915, 635 + offsetB);
        }

        //sell
        p.fill(75, 0, 0);
        p.textFont(largeFont);
        p.textAlign(CENTER);
        p.text("Sell for: $" + floor(tower.value * .8f), 1000, 888);

        //health
        p.fill(0);
        p.textFont(mediumFont);
        p.textAlign(LEFT);
        p.text("Health: " + tower.hp + "/" + tower.maxHp, 910, 276 + offset);

        //stats
        if (tower.turret) {
            //damage
            p.text("Damage: " + tower.damage, 910, 296 + offset);
            //firerate (delay)
            p.text("Load time: " + (float) (round((float) (tower.delay) / 6)) / 10 + "s", 910, 316 + offset);
            //accuracy (error)
            if (tower.error == 0) p.text("Perfect accuracy", 910, 336 + offset);
            else if (tower.error > 0 && tower.error < 1) p.text("High accuracy", 910, 336 + offset);
            else if (tower.error >= 1 && tower.error <= 3) p.text("Medium accuracy", 910, 336 + offset);
            else if (tower.error > 3) p.text("Low accuracy", 910, 336 + offset);
            //velocity
            if (speed < 8) p.text("Low velocity", 910, 356 + offset);
            else if (speed <= 18) p.text("Medium velocity", 910, 356 + offset);
            else p.text("High velocity", 910, 356 + offset);
        }
    }
}