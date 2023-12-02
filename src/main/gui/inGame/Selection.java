package main.gui.inGame;

import main.gui.guiObjects.buttons.UpgradeTower;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.strikethroughText;
import static main.sound.SoundUtilities.playSound;

public class Selection {

    private final PApplet P;

    public String name;
    public Turret turret;
    public boolean towerJustPlaced;
    private int purpleCount;
    private final SoundFile CLICK_IN;
    private final SoundFile CLICK_OUT;

    public static final Color STAT_TEXT_COLOR = new Color(255, 0, 0);
    public static final Color SPECIAL_TEXT_COLOR = new Color(100, 0, 200);
    public static final Color EFFECT_TEXT_COLOR = new Color(0, 200, 50);

    /** what tower is selected */
    public Selection(PApplet p) {
        this.P = p;
        name = "null";
        CLICK_IN = sounds.get("clickIn");
        CLICK_OUT = sounds.get("clickOut");
    }

    public void update() {
        clickOff();
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

    /**
     * Switches what is selected.
     * @param turret turret to select to
     */
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

    /** deselect, hide stuff */
    private void clickOff() {
        if (turret == null) return;
        if (inputHandler.leftMousePressedPulse &&
                mouseOnBoard() && alive && !paused) {
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

    private boolean mouseOnBoard() {
        boolean inCenter = boardMousePosition.x < BOARD_WIDTH && boardMousePosition.x > 0;
        boolean onTurret = (boardMousePosition.x > turret.tile.position.x ||
                        boardMousePosition.x < turret.tile.position.x - turret.size.x ||
                        boardMousePosition.y > turret.tile.position.y ||
                        boardMousePosition.y < turret.tile.position.y - turret.size.y);
        return inCenter && onTurret;
    }

    /**
     * Displays big circle around selected tower
     */
    public void turretOverlay() {
        if (name.equals("null") || turret == null) {
            return;
        }
        //display range and square
//        P.fill(100, 25);
//        P.stroke(255);
        float x = turret.tile.position.x - turret.size.x;
        float y = turret.tile.position.y - turret.size.y;
        if (turret instanceof Booster) {
            if (turret.range == 1) {
                P.rect(x, y - 50, 50, 150);
                P.rect(x - 50, y, 150, 50);
            } else {
                P.rect(x, y, turret.size.y, turret.size.y);
                P.rect(x - 50, y - 50, 150, 150);
            }
            P.noStroke();
            return;
        }
        if (turret instanceof SeismicTower) {
            float width = radians(((SeismicTower) turret).shockwaveWidth / 2);
            if (width < 6) {
                float startX = x + turret.size.x / 2;
                float startY = y + turret.size.y / 2;
                float angleA = turret.angle - HALF_PI + width;
                float angleB = turret.angle - HALF_PI - width;
                P.arc(startX, startY, turret.range * 2, turret.range * 2, angleB, angleA, PIE);
            }
        }
        P.rect(x, y, turret.size.y, turret.size.y);
        if (turret.getRange() > 1000) { //prevents lag
            P.noStroke();
            P.rect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            return;
        }
        if (turret.boostedRange() > 0) P.stroke(InGameGui.BOOSTED_TEXT_COLOR.getRGB());
        P.circle(turret.tile.position.x - (turret.size.x / 2), turret.tile.position.y - (turret.size.y / 2), turret.getRange() * 2);
        P.noStroke();
    }

    public void display() {
        if (name.equals("null") || turret == null) {
            return;
        }

        int speed = turret.pjSpeed;
        int offset;
        purpleCount = 0;

        background();
        offset = nameAndSpecial();
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
        P.rect(900, 212, 200, 299);
        if (!turret.hasPriority) P.rect(900, 212, 200, 344);
    }

    public static int displayTitleAndGetOffset(PApplet p, String[] titleLines) {
        for (int i = 0; i < titleLines.length; i++) {
            p.text(titleLines[i], 1000, 241 + 25 * i);
        }
        if (titleLines.length > 1) return 25;
        else return 0;
    }

    private int nameAndSpecial() {
        P.textAlign(CENTER);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB(), 254);
        P.textFont(h2);

        int offset = displayTitleAndGetOffset(P, turret.titleLines);
        turret.infoDisplay.accept(offset);

        return offset;
    }

    private void displayInfo(int offset, int speed) {
        //health
        P.textFont(h4);
        P.textAlign(LEFT);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB(), 254);
        if (turret.boostedMaxHp() > 0) P.fill(InGameGui.BOOSTED_TEXT_COLOR.getRGB(), 254);
        P.text("Health: " + turret.hp + "/" + turret.getMaxHp(), 910, 276 + offset);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB(), 254);

        //booster (I can't think of any other way to do this :(
        if (turret instanceof Booster) return;

        //damage
        if (turret.boostedDamage() > 0) P.fill(InGameGui.BOOSTED_TEXT_COLOR.getRGB(), 254);
        if (turret.getDamage() <= 0) P.text("No damage", 910, 296 + offset);
        else P.text("Damage: " + nfc(turret.getDamage()), 910, 296 + offset);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB(), 254);

        //firerate (delay)
        if (turret.boostedFirerate() > 0) P.fill(InGameGui.BOOSTED_TEXT_COLOR.getRGB(), 254);
        if (turret.getDelay() <= 0) P.text("Instant reload", 910, 316 + offset);
        else P.text("Reload time: " + nf(turret.getDelay(), 1, 1) + "s", 910, 316 + offset);
        P.fill(InGameGui.MAIN_TEXT_COLOR.getRGB(), 254);

        //velocity
        if (speed < 0) P.text("Instant travel", 910, 336 + offset);
        else if (speed < 500) P.text("Low velocity", 910, 336 + offset);
        else if (speed < 1000) P.text("Medium velocity", 910, 336 + offset);
        else P.text("High velocity", 910, 336 + offset);
        if (turret.pierce > 0) {
            P.fill(SPECIAL_TEXT_COLOR.getRGB(), 254);
            P.text("Pierce: " + turret.pierce, 910, 356 + offset + 20 * purpleCount);
            offset += 20;
        }

        //effects
        if (turret.effectLevel != 0 || turret.effectDuration != 0) {
            P.fill(EFFECT_TEXT_COLOR.getRGB(), 254);
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
                P.text("Effect Duration: " + effectDuration + "s", 910, 376 - x + 20 * purpleCount + offset);
            }
        }
    }

    private void displayStats() {
        int offset = 0;
        if (!turret.hasPriority) offset = 45;
        P.fill(STAT_TEXT_COLOR.getRGB(), 254);
        P.textAlign(LEFT);
        P.textFont(h4);

        turret.statsDisplay.accept(offset);
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
            if (canAfford) fillColor = new Color(11, 56, 0, 254);
            else fillColor = new Color(75, 0, 0, 254);
            P.fill(fillColor.getRGB(), fillColor.getAlpha());
            P.textFont(h2);
            P.text(turret.upgradeTitles[nextLevel], 1000, 585 + offsetC);
            P.textAlign(RIGHT);
            if (canAfford) P.text("$" + nfc(turret.upgradePrices[nextLevel]), BOARD_WIDTH + 200 - 20, 693 + offsetC);
            else {
                strikethroughText(P, "$" + nfc(turret.upgradePrices[nextLevel]), new PVector(BOARD_WIDTH + 200 - 20, 693 + offsetC),
                        fillColor, h2.getSize(), RIGHT);
            }
            P.textFont(h4);
            P.textAlign(LEFT);
            P.text(turret.upgradeDescA[nextLevel], 910, 615 + offsetC);
            P.text(turret.upgradeDescB[nextLevel], 910, 635 + offsetC);
            P.text(turret.upgradeDescC[nextLevel], 910, 655 + offsetC);
        } else {
            fillColor = new Color(15, 15, 15, 254);
            P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha());
            P.textFont(h2);
            P.text("N/A", 1000, 585 + offsetC);
            P.textFont(h4);
            P.textAlign(LEFT);
            P.text("No more", 910, 615 + offsetC);
            P.text("upgrades", 910, 635 + offsetC);
        }
        P.stroke(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha());
        if (turret.nextLevelB > 5 || turret.nextLevelA > 2) {
            //little x
            P.line(950, 685 + offsetC, 960, 685 + offsetC + 10);
            P.line(960, 685 + offsetC, 950, 685 + offsetC + 10);
        }
        if (upgradeButton == inGameGui.upgradeButtonA) { //A
            for (int i = 0; i < 3; i++) {
                if (nextLevel <= i) P.noFill();
                else P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha());
                P.rect(910 + (20 * i), 685 + offsetC, 10, 10);
            }
        } else { //B
            for (int i = 3; i < 6; i++) {
                if (nextLevel <= i) P.noFill();
                else P.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha());
                P.rect(910 + (20 * (i-3)), 685 + offsetC, 10, 10);
            }
        }
    }

    private void priorityButton() {
        P.textFont(h2);
        P.textAlign(CENTER);
        if (turret.hasPriority) {
            P.fill(75, 45, 0, 254);
            P.text("Target: " + turret.priority.text, 1000, 843);
        }
    }

    private void sellButton() {
        P.fill(75, 0, 0, 254);
        P.textFont(h2);
        P.textAlign(CENTER);
        P.text("Sell for: $" + nfc(floor(turret.getValue() * .8f)), 1000, 888);
    }

    public void setTextPurple(String s, int offset) {
        P.textFont(h4);
        P.textAlign(LEFT);
        P.fill(SPECIAL_TEXT_COLOR.getRGB(), 254);
        P.text(s, 910, 356 + offset + 20 * purpleCount);
        purpleCount++;
    }

    public void changePriority() {
        if (name.equals("null") || turret == null) return;
        playSound(selection.CLICK_OUT, 1, 1);
        if (turret.priority == Turret.Priority.Weak) turret.priority = Turret.Priority.Close;
        else turret.priority = Turret.Priority.values()[turret.priority.ordinal() + 1];
    }

    public void upgradeBottom() {
        if (name.equals("null") || turret == null) return;
        turret.upgrade(1, false);
    }

    public void upgradeTop() {
        if (name.equals("null") || turret == null) return;
        turret.upgrade(0, false);
    }

    public void sell() {
        if (name.equals("null") || turret == null) return;
        selection.turret.die(true);
        inGameGui.priorityButton.active = false;
        inGameGui.upgradeButtonA.active = false;
        inGameGui.upgradeButtonB.active = false;
        inGameGui.upgradeIconA.active = false;
        inGameGui.upgradeIconB.active = false;
        selection.name = "null";
        inGameGui.flashA = 255;
    }
}