package main.gui;

import main.towers.Tower;
import processing.core.PApplet;

import static main.Main.*;

public class Selection { //what tower is selected

    private PApplet p;

    public String name;
    public int id;
    public Tower tower;

    public Selection(PApplet p) {
        this.p = p;
        name = "null";
    }

    public void main() {
        clickOff();
        //don't display if nothing held
        if (!name.equals("null") && tiles.get(id).tower != null) display();
    }

    public void swapSelected(int id) { //switches what is selected
        tower = tiles.get(id).tower;
        hand.held = "null";
        if (tower.turret) {
            for (int i = tiles.size() - 1; i >= 0; i--) {
                if (tiles.get(i).tower != null) tiles.get(i).tower.visualize = false;
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
                } else upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
                if (tower.nextLevelB < tower.upgradeNames.length) {
                    upgradeIconB.sprite = tower.upgradeIcons[tower.nextLevelB];
                } else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
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
                } else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
    }

    private void clickOff() { //desselect, hide stuff
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
        p.rect(900, 212, 200, 298);
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) p.rect(900, 212, 200, 343);

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
                p.text("Luggage", 1000, 241);
                p.text("Blaster", 1000, 266);
                offset = 25;
                speed = 12;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(0);
                break;
            case "energyBlaster":
                p.text("Energy Blaster", 1000, 241);
                speed = 16;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Splash damage", 910, 376 + offset);
                p.fill(0);
                break;
            case "magicMissleer":
                p.text("Magic Missileer", 1000, 241);
                speed = 5;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Three homing missiles", 910, 376 + offset);
                break;
            case "magicMissleerFour":
                p.text("Magic Missileer", 1000, 241);
                speed = 5;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100, 0, 200);
                p.text("Four homing missiles", 910, 376 + offset);
                break;
            case "tesla":
                p.text("Tesla Tower", 1000, 241);
                speed = -1;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100,0,200);
                p.text("Chain Lightning", 910, 376 + offset);
                break;
            case "nightmare":
                p.text("Nightmare", 1000, 241);
                p.text("Blaster", 1000, 266);
                offset = 25;
                speed = 18;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100,0,200);
                p.text("Shotgun, decay", 910, 376 + offset);
                break;
            case "flamethrower":
                p.text("Flamethrower", 1000, 241);
                speed = 5;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100,0,200);
                p.text("Fire, limited range", 910, 376 + offset);
                break;
            case "railgun":
                p.text("Railgun", 1000, 241);
                speed = -1;
                break;
            case "waveMotion":
                p.text("Wave Motion", 1000, 241);
                p.text("Tower",1000,266);
                offset = 25;
                speed = -1;
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.fill(100,0,200);
                p.text("Energy beam", 910, 376 + offset);
                break;
        }

        //stats
        int offsetB = 0;
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) offsetB = 45;
        p.fill(255, 0, 0);
        p.textAlign(LEFT);
        p.textFont(mediumFont);
        if (tower.killsTotal != 1) p.text(tower.killsTotal + " kills", 910, 475 + offsetB);
        else p.text(tower.killsTotal + " kill", 910, 475 + offsetB);
        p.text(tower.damageTotal + " damage", 910, 500 + offsetB);

        //put box around selected
        p.fill(255, 25);
        p.stroke(255);
        p.rect(tower.tile.position.x - tower.size.x, tower.tile.position.y - tower.size.y, tower.size.x, tower.size.y);

        //priority
        p.textFont(largeFont);
        p.textAlign(CENTER);
        if (tower.priority == 0) priority = "close";
        else if (tower.priority == 1) priority = "far";
        else if (tower.priority == 2) priority = "strong";
        if (tower.turret && !tower.name.equals("magicMissleer") && !tower.name.equals("magicMissleerFour")) {
            p.fill(75, 45, 0);
            p.text("Priority: " + priority, 1000, 843);
        }

        //upgrade Zero
        int offsetC;
        if (tower.turret) { //only display if turret
            offsetC = -45;
            if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) offsetC += 45;
            if (tower.nextLevelA < tower.upgradeNames.length / 2) {
                if (money >= tower.upgradePrices[tower.nextLevelA]) p.fill(11, 56, 0);
                else p.fill(75, 0, 0);
                p.textFont(largeFont);
                p.text(tower.upgradeTitles[tower.nextLevelA], 1000, 585 + offsetC);
                p.text("$" + tower.upgradePrices[tower.nextLevelA], 1000, 693 + offsetC);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text(tower.upgradeDescA[tower.nextLevelA], 910, 615 + offsetC);
                p.text(tower.upgradeDescB[tower.nextLevelA], 910, 635 + offsetC);
                p.text(tower.upgradeDescC[tower.nextLevelA], 910, 655 + offsetC);
            } else {
                p.fill(15);
                p.textFont(largeFont);
                p.text("N/A", 1000, 585 + offsetC);
                p.textFont(mediumFont);
                p.textAlign(LEFT);
                p.text("No more", 910, 615 + offsetC);
                p.text("upgrades", 910, 635 + offsetC);
            }
        }
        //upgrade One
        offsetC = 0;
        if (tower.turret) offsetC = 105;
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) offsetC += 45;
        if (tower.nextLevelB < tower.upgradeNames.length) {
            if (money >= tower.upgradePrices[tower.nextLevelB]) p.fill(11, 56, 0);
            else p.fill(75, 0, 0);
            p.textFont(largeFont);
            p.textAlign(CENTER);
            p.text(tower.upgradeTitles[tower.nextLevelB], 1000, 585 + offsetC);
            p.text("$" + tower.upgradePrices[tower.nextLevelB], 1000, 693 + offsetC);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text(tower.upgradeDescA[tower.nextLevelB], 910, 615 + offsetC);
            p.text(tower.upgradeDescB[tower.nextLevelB], 910, 635 + offsetC);
            p.text(tower.upgradeDescC[tower.nextLevelB], 910, 655 + offsetC);
        } else {
            p.fill(15);
            p.textFont(largeFont);
            p.text("N/A", 1000, 585 + offsetC);
            p.textFont(mediumFont);
            p.textAlign(LEFT);
            p.text("No more", 915, 615 + offsetC);
            p.text("upgrades", 915, 635 + offsetC);
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
            else if (tower.error > 0 && tower.error <= 2) p.text("High accuracy", 910, 336 + offset);
            else if (tower.error > 2 && tower.error <= 4) p.text("Medium accuracy", 910, 336 + offset);
            else if (tower.error > 4) p.text("Low accuracy", 910, 336 + offset);
            //velocity
            if (speed < 0) p.text("Instant", 910, 356 + offset);
            else if (speed < 8) p.text("Low velocity", 910, 356 + offset);
            else if (speed <= 18) p.text("Medium velocity", 910, 356 + offset);
            else p.text("High velocity", 910, 356 + offset);
        }
    }
}