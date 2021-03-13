package main.gui;

import main.gui.guiObjects.buttons.UpgradeTower;
import main.misc.Utilities;
import main.towers.Tower;
import processing.core.PApplet;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;

public class Selection { //what tower is selected

    private final PApplet P;

    public String name;
    public int id;
    public Tower tower;
    public boolean towerJustPlaced;
    private int purpleCount;
    private final SoundFile CLICK_IN;
    private final SoundFile CLICK_OUT;

    public Selection(PApplet p) {
        this.P = p;
        name = "null";
        CLICK_IN = sounds.get("clickIn");
        CLICK_OUT = sounds.get("clickOut");
    }

    public void main() {
        clickOff();
        //don't display if nothing held
        if (!name.equals("null") && tiles.get(id).tower != null) display();
    }

    /**
     * Switches what is selected.
     * @param id tower id
     */
    public void swapSelected(int id) {
        if (!paused) {
            if (this.id != id || name.equals("null")) {
                if (!towerJustPlaced) {
                    CLICK_IN.stop();
                    CLICK_IN.play(1, volume);
                    inGameGui.flashA = 255;
                } else towerJustPlaced = false;
            }
            tower = tiles.get(id).tower;
            hand.held = "null";
            if (tower.turret) {
                for (int i = tiles.size() - 1; i >= 0; i--) {
                    if (tiles.get(i).tower != null) tiles.get(i).tower.visualize = false;
                }
                this.id = id;
                name = tower.name;
                inGameGui.sellButton.active = true;
                inGameGui.upgradeButtonB.active = true;
                inGameGui.upgradeIconB.active = true;
                if (tower.turret) {
                    inGameGui.targetButton.active = true;
                    tower.visualize = true;
                    inGameGui.upgradeButtonA.active = true;
                    inGameGui.upgradeButtonB.position.y = 735;
                    inGameGui.upgradeButtonA.position.y = 585;
                    inGameGui.upgradeIconA.active = true;
                    inGameGui.upgradeIconA.position.y = 565;
                    inGameGui.upgradeIconB.position.y = 715;
                }
                if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) {
                    inGameGui.targetButton.active = false;
                    inGameGui.upgradeButtonB.position.y += 45;
                    inGameGui.upgradeButtonA.position.y += 45;
                    inGameGui.upgradeIconA.position.y += 45;
                    inGameGui.upgradeIconB.position.y += 45;
                }
                if (!tower.turret) {
                    inGameGui.targetButton.active = false;
                    inGameGui.upgradeButtonA.active = false;
                    inGameGui.upgradeButtonB.position.y = 630;
                    inGameGui.upgradeIconA.active = false;
                    inGameGui.upgradeIconB.position.y = 610;
                    if (tower.nextLevelB < tower.upgradeTitles.length) {
                        inGameGui.upgradeIconB.sprite = tower.upgradeIcons[tower.nextLevelB];
                    } else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];
                }
            }
        }
    }

    private void clickOff() { //desselect, hide stuff
        Tower tower = tiles.get(id).tower;
        if (tower != null) {
            if (inputHandler.leftMousePressedPulse && P.mouseX < 900 && (P.mouseX > tower.tile.position.x ||
                    P.mouseX < tower.tile.position.x - tower.size.x || P.mouseY > tower.tile.position.y ||
                    P.mouseY < tower.tile.position.y - tower.size.y) && alive && !paused) {
                if (!name.equals("null") && !towerJustPlaced) {
                    inGameGui.flashA = 255;
                    CLICK_OUT.stop();
                    CLICK_OUT.play(1, volume);
                }
                name = "null";
                inGameGui.sellButton.active = false;
                inGameGui.targetButton.active = false;
                inGameGui.upgradeButtonA.active = false;
                inGameGui.upgradeButtonB.active = false;
                inGameGui.upgradeIconA.active = false;
                inGameGui.upgradeIconB.active = false;
                tower.visualize = false;
            }
        }
    }

    void turretOverlay() {
        if (!name.equals("null") && tiles.get(id).tower != null) {
            //display range and square
            P.fill(255, 25);
            P.stroke(255);
            P.rect(tower.tile.position.x - tower.size.x, tower.tile.position.y - tower.size.y, tower.size.y, tower.size.y);
            P.circle(tower.tile.position.x - (tower.size.x / 2), tower.tile.position.y - (tower.size.y / 2), tower.range * 2);
            P.noStroke();
        }
    }

    private void display() {
        Tower tower = tiles.get(id).tower;
        int speed = 0;
        int offset = 0;
        purpleCount = 0;
        String priority = "first";

        //bg
        P.fill(235);
        P.noStroke();
        //different size bg so buttons fit
        P.rect(900, 212, 200, 298);
        if (tower.name.equals("magicMissleer") || tower.name.equals("magicMissleerFour")) P.rect(900, 212, 200, 343);

        //name and special features
        P.textAlign(CENTER);
        P.fill(0);
        P.textFont(largeFont);
        switch (tower.name) {
            case "slingshot":
                P.text("Slingshot", 1000, 241);
                speed = 12;
                break;
            case "slingshotRock":
                P.text("Slingshot MKII", 1000, 241);
                speed = 12;
                setTextPurple("Bleeding", offset);
                break;
            case "slingshotGravel":
                P.text("Gravel Slinger", 1000, 241);
                speed = 12;
                setTextPurple("8 gravel bits", offset);
                break;
            case "miscCannon":
                P.text("Luggage", 1000, 241);
                P.text("Blaster", 1000, 266);
                offset = 25;
                speed = 12;
                break;
            case "miscCannonLaundry":
                P.text("Dirty Luggage", 1000, 241);
                P.text("Blaster", 1000, 266);
                offset = 25;
                speed = 12;
                setTextPurple("Toxic splatters", offset);
                break;
            case "miscCannonBarrel":
                P.text("Minibarrel", 1000, 241);
                speed = 12;
                break;
            case "crossbow":
                P.text("Crossbow", 1000, 241);
                speed = 24;
                break;
            case "crossbowReinforced":
                P.text("Reinforced", 1000, 241);
                P.text("Crossbow", 1000, 266);
                offset = 25;
                speed = 24;
                break;
            case "crossbowMultishot":
                P.text("Shotbow", 1000, 241);
                speed = 24;
                setTextPurple("Seven bolts", offset);
                break;
            case "cannon":
                P.text("Cannon", 1000, 241);
                speed = 14;
                setTextPurple("Small splash", offset);
                break;
            case "fragCannon":
                P.text("Frag Cannon", 1000, 241);
                speed = 14;
                setTextPurple("Small splash", offset);
                setTextPurple("Shrapnel", offset + 20);
                break;
            case "dynamiteLauncher":
                P.text("Dynamite", 1000, 241);
                P.text("Launcher", 1000, 266);
                offset = 25;
                speed = 10;
                setTextPurple("Large splash", offset);
                break;
            case "gluer":
                P.text("Gluer", 1000, 241);
                speed = 7;
                setTextPurple("Slows", offset);
                break;
            case "splashGluer":
                P.text("Gluer Splasher", 1000, 241);
                speed = 7;
                setTextPurple("Slows", offset);
                setTextPurple("Splatter", offset + 20);
                break;
            case "shatterGluer":
                P.text("Gluer Spiker", 1000, 241);
                speed = 7;
                setTextPurple("Slows", offset);
                setTextPurple("Releases spikes", offset + 20);
                break;
            case "seismic":
                P.text("Seismic Tower", 1000, 241);
                speed = 7;
                setTextPurple("Shockwave", offset);
                break;
            case "seismicSniper":
                P.text("Seismic Sniper", 1000, 241);
                speed = 7;
                setTextPurple("Shockwave", offset);
                setTextPurple("Stuns stealth", offset + 20);
                break;
            case "seismicSlammer":
                P.text("Seismic", 1000, 241);
                P.text("Slammer", 1000, 266);
                offset = 25;
                speed = 7;
                setTextPurple("Shockwave", offset);
                setTextPurple("360 degrees", offset + 20);
                break;
            case "energyBlaster":
                P.text("Energy Blaster", 1000, 241);
                speed = 16;
                setTextPurple("Splash", offset);
                break;
            case "magicMissleer":
                P.text("Magic Missileer", 1000, 241);
                speed = 5;
                setTextPurple("Three homing missiles", offset);
                break;
            case "magicMissleerFour":
                P.text("Magic Missileer", 1000, 241);
                speed = 5;
                setTextPurple("Four homing missiles", offset);
                break;
            case "tesla":
                P.text("Tesla Tower", 1000, 241);
                speed = -1;
                setTextPurple("Chain lightning", offset);
                break;
            case "nightmare":
                P.text("Nightmare", 1000, 241);
                P.text("Blaster", 1000, 266);
                offset = 25;
                speed = 18;
                setTextPurple("Shotgun", offset);
                setTextPurple("Decay", offset + 20);
                break;
            case "flamethrower":
                P.text("Flamethrower", 1000, 241);
                speed = 5;
                setTextPurple("Fire", offset);
                setTextPurple("Slow rotation", offset + 20);
                break;
            case "railgun":
                P.text("Railgun", 1000, 241);
                speed = -1;
                break;
            case "waveMotion":
                P.text("Death Beam", 1000, 241);
                speed = -1;
                setTextPurple("Infinite pierce", offset);
                break;
        }

        //stats todo: fix
        int offsetB = 0;
        if (tower.name.contains("magicMissleer")) offsetB = 45;
        P.fill(255, 0, 0);
        P.textAlign(LEFT);
        P.textFont(mediumFont);
        if (tower.killsTotal != 1) P.text(tower.killsTotal + " kills", 910, 475 + offsetB);
        else P.text(tower.killsTotal + " kill", 910, 475 + offsetB);
        P.text(tower.damageTotal + " total damage", 910, 500 + offsetB);

        //priority
        P.textFont(largeFont);
        P.textAlign(CENTER);
        if (tower.priority == 0) priority = "close";
        else if (tower.priority == 1) priority = "far";
        else if (tower.priority == 2) priority = "strong";
        if (tower.turret && !tower.name.contains("magicMissleer")) {
            P.fill(75, 45, 0);
            P.text("Priority: " + priority, 1000, 843);
        }

        //upgrade icons
        if (!inGameGui.upgradeButtonA.greyed) {
            inGameGui.upgradeIconA.sprite = tower.upgradeIcons[tower.nextLevelA];
        } else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (!inGameGui.upgradeButtonB.greyed && tower.nextLevelB < tower.upgradeIcons.length) {
            inGameGui.upgradeIconB.sprite = tower.upgradeIcons[tower.nextLevelB];
        } else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];

        displayUpgrade(-45, tower.nextLevelA, inGameGui.upgradeButtonA);
        displayUpgrade(105, tower.nextLevelB, inGameGui.upgradeButtonB);

        //sell
        P.fill(75, 0, 0);
        P.textFont(largeFont);
        P.textAlign(CENTER);
        P.text("Sell for: $" + floor(tower.value * .8f), 1000, 888);

        //health
        P.fill(0);
        P.textFont(mediumFont);
        P.textAlign(LEFT);
        P.text("Health: " + tower.hp + "/" + tower.maxHp, 910, 276 + offset);

        //data
        if (tower.turret) {
            //damage
            P.text("Damage: " + tower.damage, 910, 296 + offset);
            //firerate (delay)
            P.text("Load time: " + (float) (round((float) (tower.delay) / 6)) / 10 + "s", 910, 316 + offset);
            //velocity
            if (speed < 0) P.text("Instant", 910, 336 + offset);
            else if (speed < 8) P.text("Low velocity", 910, 336 + offset);
            else if (speed <= 15) P.text("Medium velocity", 910, 336 + offset);
            else P.text("High velocity", 910, 336 + offset);
            if (tower.pierce > 0) {
                P.fill(100, 0, 200);
                P.text("Pierce: " + tower.pierce, 910, 356 + offset + 20 * purpleCount);
                offset += 20;
            }
            //effects
            if (tower.effectLevel != 0 || tower.effectDuration != 0) {
                P.fill(0, 200, 50);
                if (tower.effectLevel % 1 == 0) {
                    P.text("Effect Level: " + (int) tower.effectLevel, 910, 356 + 20 * purpleCount + offset);
                } else {
                    P.text("Effect Level: " + tower.effectLevel, 910, 356 + 20 * purpleCount + offset);
                }
                float effectDuration = Utilities.roundTo(tower.effectDuration / (float) FRAMERATE, 0.1f);
                if (effectDuration % 1 == 0) {
                    P.text("Effect Duration: " + (int) effectDuration + "s", 910, 376 + 20 * purpleCount + offset);
                } else {
                    P.text("Effect Duration: " + effectDuration + "s", 910, 376 + 20 * purpleCount + offset);
                }
            }
        }
    }

    private void displayUpgrade(int offsetC, int nextLevel, UpgradeTower upgradeButton) {
        //upgrade Zero / A
        Color fillColor;
        P.textAlign(CENTER);
        if (tower.name.contains("magicMissleer")) offsetC += 45;
        if (!upgradeButton.greyed && nextLevel < tower.upgradePrices.length) {
            if (money >= tower.upgradePrices[nextLevel]) fillColor = new Color(11, 56, 0);
            else fillColor = new Color(75, 0, 0);
            P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
            P.textFont(largeFont);
            P.text(tower.upgradeTitles[nextLevel], 1000, 585 + offsetC);
            P.text("$" + tower.upgradePrices[nextLevel], 1000, 693 + offsetC);
            P.textFont(mediumFont);
            P.textAlign(LEFT);
            P.text(tower.upgradeDescA[nextLevel], 910, 615 + offsetC);
            P.text(tower.upgradeDescB[nextLevel], 910, 635 + offsetC);
            P.text(tower.upgradeDescC[nextLevel], 910, 655 + offsetC);
        } else {
            fillColor = new Color(15);
            P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
            P.textFont(largeFont);
            P.text("N/A", 1000, 585 + offsetC);
            P.textFont(mediumFont);
            P.textAlign(LEFT);
            P.text("No more", 910, 615 + offsetC);
            P.text("upgrades", 910, 635 + offsetC);
        }
        P.stroke(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
        if (tower.nextLevelB > 5 || tower.nextLevelA > 2) {
            //little x
            P.line(950, 685 + offsetC, 960, 685 + offsetC + 10);
            P.line(960, 685 + offsetC, 950, 685 + offsetC + 10);
        }
        if (upgradeButton == inGameGui.upgradeButtonA) { //A
            for (int i = 0; i < 3; i++) {
                if (nextLevel <= i) P.noFill();
                else P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
                P.rect(910 + (20 * i), 685 + offsetC, 10, 10);
            }
        } else { //B
            for (int i = 3; i < 6; i++) {
                if (nextLevel <= i) P.noFill();
                else P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
                P.rect(910 + (20 * (i-3)), 685 + offsetC, 10, 10);
            }
        }
    }

    private void setTextPurple(String s, int offset) {
        P.textFont(mediumFont);
        P.textAlign(LEFT);
        P.fill(100, 0, 200);
        P.text(s, 910, 356 + offset);
        purpleCount++;
    }
}