package main.gui;

import main.gui.guiObjects.buttons.UpgradeTower;
import main.towers.turrets.Gluer;
import main.towers.turrets.TeslaTower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.playSound;
import static main.misc.Utilities.strikethroughText;

public class Selection { //what tower is selected

    private final PApplet P;

    public String name;
    public Turret turret;
    public boolean towerJustPlaced;
    private int purpleCount;
    private final SoundFile CLICK_IN;
    private final SoundFile CLICK_OUT;

    private final Color STAT_TEXT_COLOR = new Color(255, 0, 0);
    private final Color SPECIAL_TEXT_COLOR = new Color(100, 0, 200);
    private final Color EFFECT_TEXT_COLOR = new Color(0, 200, 50);

    public Selection(PApplet p) {
        this.P = p;
        name = "null";
        CLICK_IN = sounds.get("clickIn");
        CLICK_OUT = sounds.get("clickOut");
    }

    public void main() {
        clickOff();
        //don't display if nothing held
        if (!name.equals("null") && turret != null) display();
    }

    /**
     * Switches what is selected.
     * @param id tower id
     */
    public void swapSelected(int id) {
        if (!paused) {
            Turret turret = (Turret) tiles.get(id).tower;
            if (this.turret != turret || name.equals("null")) {
                if (!towerJustPlaced) {
                    playSound(CLICK_IN, 1, 1);
                    inGameGui.flashA = 255;
                } else towerJustPlaced = false;
            }
            swapSelected(turret);
        }
    }

    public void swapSelected(Turret turret) {
        if (!paused) {
            hand.held = "null";
            if (turret != null) {
                if (this.turret != null) this.turret.visualize = false;
                this.turret = turret;
                name = turret.name;
                turret.visualize = true;
                inGameGui.sellButton.active = true;
                inGameGui.upgradeButtonB.active = true;
                inGameGui.upgradeIconB.active = true;
                inGameGui.priorityButton.active = true;
                inGameGui.upgradeButtonA.active = true;
                inGameGui.upgradeButtonB.position.y = 735;
                inGameGui.upgradeButtonA.position.y = 585;
                inGameGui.upgradeIconA.active = true;
                inGameGui.upgradeIconA.position.y = 565;
                inGameGui.upgradeIconB.position.y = 715;
                if (!turret.hasPriority) {
                    inGameGui.priorityButton.active = false;
                    inGameGui.upgradeButtonB.position.y += 45;
                    inGameGui.upgradeButtonA.position.y += 45;
                    inGameGui.upgradeIconA.position.y += 45;
                    inGameGui.upgradeIconB.position.y += 45;
                }
            }
        }
    }

    private void clickOff() { //desselect, hide stuff
        if (turret != null) {
            if (inputHandler.leftMousePressedPulse && P.mouseX < 900 && (P.mouseX > turret.tile.position.x ||
                    P.mouseX < turret.tile.position.x - turret.size.x || P.mouseY > turret.tile.position.y ||
                    P.mouseY < turret.tile.position.y - turret.size.y) && alive && !paused) {
                if (!name.equals("null") && !towerJustPlaced) {
                    inGameGui.flashA = 255;
                    playSound(CLICK_OUT, 1, 1);
                }
                name = "null";
                inGameGui.sellButton.active = false;
                inGameGui.priorityButton.active = false;
                inGameGui.upgradeButtonA.active = false;
                inGameGui.upgradeButtonB.active = false;
                inGameGui.upgradeIconA.active = false;
                inGameGui.upgradeIconB.active = false;
                turret.visualize = false;
            }
        }
    }

    public void turretOverlay() {
        if (!name.equals("null") && turret != null) {
            //display range and square
            P.fill(255, 25);
            P.stroke(255);
            P.rect(turret.tile.position.x - turret.size.x, turret.tile.position.y - turret.size.y, turret.size.y, turret.size.y);
            P.circle(turret.tile.position.x - (turret.size.x / 2), turret.tile.position.y - (turret.size.y / 2), turret.range * 2);
            P.noStroke();
        }
    }

    private void display() {
        int speed = turret.pjSpeed;
        int offset = 0;
        purpleCount = 0;

        background();
        offset = nameAndSpecial(offset);
        displayInfo(offset, speed);
        displayStats();
        upgradeIcons();
        upgradeButton(-45, turret.nextLevelA, inGameGui.upgradeButtonA);
        upgradeButton(105, turret.nextLevelB, inGameGui.upgradeButtonB);
        priorityButton();
        sellButton();
    }

    private void background() {
        //bg
        P.fill(InGameGui.MAIN_PANEL_COLOR.getRGB());
        P.noStroke();
        //different size bg so buttons fit
        P.rect(900, 212, 200, 298);
        if (!turret.hasPriority) P.rect(900, 212, 200, 343);
    }

    private int nameAndSpecial(int offset) {
        P.textAlign(CENTER);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB());
        P.textFont(largeFont);
        switch (turret.name) {
            case "slingshot":
                P.text("Slingshot", 1000, 241);
                break;
            case "slingshotRock":
                P.text("Slingshot MKII", 1000, 241);
                setTextPurple("Bleeding", offset);
                break;
            case "slingshotGravel":
                P.text("Gravel Slinger", 1000, 241);
                setTextPurple("8 gravel bits", offset);
                break;
            case "miscCannon":
                P.text("Luggage", 1000, 241);
                P.text("Launcher", 1000, 266);
                offset = 25;
                break;
            case "miscCannonLaundry":
                P.text("Dirty Luggage", 1000, 241);
                P.text("Launcher", 1000, 266);
                offset = 25;
                setTextPurple("Toxic splatters", offset);
                break;
            case "miscCannonBarrel":
                P.text("Minibarrel", 1000, 241);
                break;
            case "crossbow":
                P.text("Crossbow", 1000, 241);
                break;
            case "crossbowReinforced":
                P.text("Reinforced", 1000, 241);
                P.text("Crossbow", 1000, 266);
                offset = 25;
                break;
            case "crossbowMultishot":
                P.text("Shotbow", 1000, 241);
                setTextPurple("Seven bolts", offset);
                break;
            case "cannon":
                P.text("Cannon", 1000, 241);
                setTextPurple("Small splash", offset);
                break;
            case "fragCannon":
                P.text("Frag Cannon", 1000, 241);
                setTextPurple("Small splash", offset);
                setTextPurple("Shrapnel", offset);
                break;
            case "dynamiteLauncher":
                P.text("Dynamite", 1000, 241);
                P.text("Flinger", 1000, 266);
                offset = 25;
                setTextPurple("Large splash", offset);
                break;
            case "gluer":
                P.text("Gluer", 1000, 241);
                setTextPurple("Slows", offset);
                break;
            case "splashGluer":
                P.text("Gluer Splasher", 1000, 241);
                setTextPurple("Slows", offset);
                setTextPurple("Splatter", offset);
                break;
            case "shatterGluer":
                P.text("Gluer Spiker", 1000, 241);
                setTextPurple("Slows", offset);
                setTextPurple("Releases spikes", offset);
                break;
            case "seismic":
                P.text("Seismic Tower", 1000, 241);
                setTextPurple("Shockwave", offset);
                break;
            case "seismicSniper":
                P.text("Seismic Sniper", 1000, 241);
                setTextPurple("Shockwave", offset);
                setTextPurple("Stuns stealth", offset);
                break;
            case "seismicSlammer":
                P.text("Seismic", 1000, 241);
                P.text("Slammer", 1000, 266);
                offset = 25;
                setTextPurple("Shockwave", offset);
                setTextPurple("360 degrees", offset);
                break;
            case "energyBlaster":
                P.text("Energy Blaster", 1000, 241);
                setTextPurple("Splash", offset);
                break;
            case "nuclearBlaster":
                P.text("Nuclear Blaster", 1000, 241);
                setTextPurple("Huge splash", offset);
                break;
            case "darkBlaster":
                P.text("Dark Blaster", 1000, 241);
                setTextPurple("Splash", offset);
                break;
            case "magicMissleer":
                P.text("Magic Missileer", 1000, 241);
                setTextPurple("Three homing missiles", offset);
                break;
            case "magicMissleerFour":
                P.text("Magic Missileer", 1000, 241);
                setTextPurple("Four homing missiles", offset);
                break;
            case "tesla":
                P.text("Tesla Tower", 1000, 241);
                setTextPurple("Jumping electricity", offset);
                break;
            case "lightning":
                P.text("Lighting Caller", 1000, 241);
                setTextPurple("Jumping electricity", offset);
                setTextPurple("Multiple arcs", offset);
                setTextPurple("Splash", offset);
                break;
            case "highPowerTesla":
                P.text("High Power", 1000, 241);
                P.text("Tesla Tower", 1000, 266);
                offset += 25;
                setTextPurple("Jumping electricity", offset);
                break;
            case "nightmare":
                P.text("Nightmare", 1000, 241);
                P.text("Shotgun", 1000, 266);
                offset = 25;
                setTextPurple("Shotgun", offset);
                setTextPurple("Decay", offset);
                break;
            case "flamethrower":
                P.text("Flamethrower", 1000, 241);
                setTextPurple("Fire", offset);
                break;
            case "flamewheel":
                P.text("Flame Wheel", 1000, 241);
                setTextPurple("Fire waves", offset);
                setTextPurple("Spools up", offset);
                break;
            case "magicFlamethrower":
                P.text("Flame Conjurer", 1000, 241);
                setTextPurple("Blue fire", offset);
                setTextPurple("Infinite pierce", offset);
                break;
            case "railgun":
                P.text("Railgun", 1000, 241);
                break;
            case "waveMotion":
                P.text("Death Beam", 1000, 241);
                setTextPurple("Infinite pierce", offset);
                break;
        }
        return offset;
    }

    private void displayInfo(int offset, int speed) {
        //health
        turret.refreshHpBar();
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB());
        P.textFont(mediumFont);
        P.textAlign(LEFT);
        P.text("Health: " + turret.hp + "/" + turret.maxHp, 910, 276 + offset);
        //damage
        if (turret.damage <= 0) P.text("No damage", 910, 296 + offset);
        else P.text("Damage: " + nfc(turret.damage), 910, 296 + offset);
        //firerate (delay)
        if (turret.delay <= 0) P.text("Instant reload", 910, 316 + offset);
        else P.text("Reload time: " + nf(turret.delay, 1, 1) + "s", 910, 316 + offset);
        //velocity
        if (speed < 0) P.text("Instant travel", 910, 336 + offset);
        else if (speed < 500) P.text("Low velocity", 910, 336 + offset);
        else if (speed < 1000) P.text("Medium velocity", 910, 336 + offset);
        else P.text("High velocity", 910, 336 + offset);
        if (turret.pierce > 0) {
            P.fill(SPECIAL_TEXT_COLOR.getRGB());
            P.text("Pierce: " + turret.pierce, 910, 356 + offset + 20 * purpleCount);
            offset += 20;
        }
        //effects
        if (turret.effectLevel != 0 || turret.effectDuration != 0) {
            P.fill(EFFECT_TEXT_COLOR.getRGB());
            int x = 0;
            if (turret.effectLevel == 0) x = 20;
            else {
                if (turret.effectLevel % 1 == 0) {
                    P.text("Effect Level: " + (int) turret.effectLevel, 910, 356 + 20 * purpleCount + offset);
                } else {
                    P.text("Effect Level: " + turret.effectLevel, 910, 356 + 20 * purpleCount + offset);
                }
            }
            float effectDuration = turret.effectDuration;
            if (effectDuration % 1 == 0) {
                P.text("Effect Duration: " + (int) effectDuration + "s", 910, 376 - x + 20 * purpleCount + offset);
            } else {
                P.text("Effect Duration: " + effectDuration + "s", 910, 376 + - x + 20 * purpleCount + offset);
            }
        }
        //electricity
        if (turret instanceof TeslaTower) {
            TeslaTower tesla = (TeslaTower) turret;
            P.fill(new Color(100, 150, 255).getRGB());
            P.text("Jumps: " + tesla.arcLength, 910, 356 + 20 * purpleCount + offset);
        }
    }

    private void displayStats() { //todo: fix
        int offsetB = 0;
        if (!turret.hasPriority) offsetB = 45;
        P.fill(STAT_TEXT_COLOR.getRGB());
        P.textAlign(LEFT);
        P.textFont(mediumFont);
        if (turret instanceof Gluer) {
            Gluer gluer = (Gluer) turret;
            P.text(nfc(gluer.gluedTotal) + " glued", 910, 450 + offsetB);
        }
        if (turret.killsTotal != 1) P.text(nfc(turret.killsTotal) + " kills", 910, 475 + offsetB);
        else P.text(nfc(turret.killsTotal) + " kill", 910, 475 + offsetB);
        P.text(nfc(turret.damageTotal) + " dmg total", 910, 500 + offsetB);
    }

    private void upgradeIcons() {
        if (!inGameGui.upgradeButtonA.greyed) {
            inGameGui.upgradeIconA.sprite = turret.upgradeIcons[turret.nextLevelA];
        } else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (!inGameGui.upgradeButtonB.greyed && turret.nextLevelB < turret.upgradeIcons.length) {
            inGameGui.upgradeIconB.sprite = turret.upgradeIcons[turret.nextLevelB];
        } else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];
    }

    private void upgradeButton(int offsetC, int nextLevel, UpgradeTower upgradeButton) {
        Color fillColor;
        P.textAlign(CENTER);
        if (!turret.hasPriority) offsetC += 45;
        if (!upgradeButton.greyed && nextLevel < turret.upgradePrices.length) {
            boolean canAfford = money >= turret.upgradePrices[nextLevel];
            if (canAfford) fillColor = new Color(11, 56, 0);
            else fillColor = new Color(75, 0, 0);
            P.fill(fillColor.getRGB());
            P.textFont(largeFont);
            P.text(turret.upgradeTitles[nextLevel], 1000, 585 + offsetC);
            P.textAlign(RIGHT);
            if (canAfford) P.text("$" + nfc(turret.upgradePrices[nextLevel]), P.width - 20, 693 + offsetC);
            else {
                strikethroughText(P, "$" + nfc(turret.upgradePrices[nextLevel]), new PVector(P.width - 20, 693 + offsetC),
                        fillColor, largeFont.getSize(), RIGHT);
            }
            P.textFont(mediumFont);
            P.textAlign(LEFT);
            P.text(turret.upgradeDescA[nextLevel], 910, 615 + offsetC);
            P.text(turret.upgradeDescB[nextLevel], 910, 635 + offsetC);
            P.text(turret.upgradeDescC[nextLevel], 910, 655 + offsetC);
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
        if (turret.nextLevelB > 5 || turret.nextLevelA > 2) {
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

    private void priorityButton() {
        String priority = "close";
        P.textFont(largeFont);
        P.textAlign(CENTER);
        if (turret.priority == 0) priority = "close";
        else if (turret.priority == 1) priority = "far";
        else if (turret.priority == 2) priority = "max HP";
        if (turret.hasPriority) {
            P.fill(75, 45, 0);
            P.text("Priority: " + priority, 1000, 843);
        }
    }

    private void sellButton() {
        P.fill(75, 0, 0);
        P.textFont(largeFont);
        P.textAlign(CENTER);
        P.text("Sell for: $" + nfc(floor(turret.value * .8f)), 1000, 888);
    }

    private void setTextPurple(String s, int offset) {
        P.textFont(mediumFont);
        P.textAlign(LEFT);
        P.fill(SPECIAL_TEXT_COLOR.getRGB());
        P.text(s, 910, 356 + offset + 20 * purpleCount);
        purpleCount++;
    }
}