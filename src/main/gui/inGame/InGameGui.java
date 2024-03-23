package main.gui.inGame;

import main.gui.guiObjects.GuiObject;
import main.gui.guiObjects.UpgradeIcon;
import main.gui.guiObjects.buttons.*;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.highlightedText;

public class InGameGui {

    public static final Color MAIN_PANEL_COLOR = new Color(235, 235, 235);
    public static final Color MONEY_PANEL_COLOR = new Color(200, 200, 200);
    public static final Color MAIN_TEXT_COLOR = new Color(0);
    public static final Color BOOSTED_TEXT_COLOR = new Color(255, 132, 0);
    public static final float SAVING_ALPHA_UPDATE = 0.3f;

    private final PApplet p;

    public OpenMenu openMenuButton;
    public WallBuy wallBuyButton;
    public SellTower sellButton;
    public TargetPriority priorityButton;
    public UpgradeTower upgradeButtonA, upgradeButtonB;
    public GuiObject upgradeIconA, upgradeIconB;

    private float savingAlpha;

    public InGameGui(PApplet p) {
        this.p = p;
        build();
    }

    public void update() {
        boolean isTowers = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).tower != null) {
                isTowers = true;
                break;
            }
        }

        openMenuButton.update();
        wallBuyButton.update();
        sellButton.update();
        priorityButton.update();
        upgradeButtonA.update();
        upgradeButtonB.update();
        if (isTowers) selection.update();
        for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.update();
        if (!isTowers || selection.name.equals("null")) {
            sellButton.active = false;
            priorityButton.active = false;
            upgradeButtonA.active = false;
            upgradeButtonB.active = false;
            upgradeIconA.active = false;
            upgradeIconB.active = false;
        }

        savingAlpha = (float) Math.max(0, savingAlpha - (Math.pow((0.8 * savingAlpha) - 1, 3) * -SAVING_ALPHA_UPDATE));
    }

    public void display() {
        boolean isTowers = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).tower != null) {
                isTowers = true;
                break;
            }
        }
        // Double range ring
        p.noFill();
        p.stroke(100, 254);
        p.strokeWeight(3);
        selection.turretOverlay();
        p.stroke(255);
        p.strokeWeight(1);
        selection.turretOverlay();

        // bg
        p.image(staticSprites.get("bgPn"), -200, 0);
        p.image(staticSprites.get("bgPn"), 900, 0);

        p.fill(MONEY_PANEL_COLOR.getRGB()); //money bg
        p.image(staticSprites.get("moneyPn"), BOARD_WIDTH, 176);

        openMenuButton.display();

        wallBuyButton.display();
        sellButton.display();
        priorityButton.display();
        upgradeButtonA.display();
        upgradeButtonB.display();
        upgradeIconA.display();
        upgradeIconB.display();

        if (isTowers) selection.display();
        for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.display();
    }

    public void displayDebugText(PApplet p, int padding) {
        highlightedText(p, "enemies: " + enemies.size(), new PVector(padding, 30), LEFT);
        highlightedText(p, "towers: " + towers.size(), new PVector(padding, 60), LEFT);
        highlightedText(p, "projectiles: " + projectiles.size(), new PVector(padding, 90), LEFT);
        int particleCount = topParticles.size() + towerParticles.size() + bottomParticles.size() + tileParticles.size();
        highlightedText(p, "particles: " + particleCount, new PVector(padding, 120), LEFT);
        highlightedText(p, "popups: " + popupTexts.size(), new PVector(padding, 150), LEFT);
        highlightedText(p, "path requests: " + pathFinder.requestQueue.size(), new PVector(padding, 180), LEFT);
        highlightedText(p, "MX: " + (int) boardMousePosition.x + ", MY: " + (int) boardMousePosition.y,
          new PVector(padding, BOARD_HEIGHT - padding - 30), LEFT);
        if (isFullscreen) highlightedText(p, "X: " + p.mouseX + ", Y: " + p.mouseY,
          new PVector(padding, BOARD_HEIGHT - padding), LEFT);
        highlightedText(p, round(p.frameRate) + " fps", new PVector(BOARD_WIDTH - padding, 30), RIGHT);
        int percentMemoryUsage = (int) ((Runtime.getRuntime().freeMemory() / (float) Runtime.getRuntime().totalMemory()) * 100);
        highlightedText(p, percentMemoryUsage + "% mem", new PVector(BOARD_WIDTH - padding, 60), RIGHT);
    }

    public void displayText(PApplet p, int x) {
        p.fill(MAIN_TEXT_COLOR.getRGB());
        p.textFont(h2);
        p.textAlign(LEFT);
        p.fill(200, 254);
        p.textFont(h4);
        p.textAlign(CENTER);
        p.text("Menu", 1000, 17);
        p.fill(0, 254);
        p.textFont(h3);
        p.fill(switch (currentLevel) {
            case 0 -> new Color(0xcba47d).getRGB();
            case 1 -> new Color(0xbcbcbc).getRGB();
            case 2 -> new Color(0x323232).getRGB();
            case 3 -> new Color(0x110019).getRGB();
            default -> new Color(0x000000).getRGB();
        }, 254);
        p.text("Place Walls", 1000, 170);
        p.textFont(monoHuge);
        p.fill(MAIN_TEXT_COLOR.getRGB(), 254);
        p.textAlign(RIGHT);
        p.text("$" + nfc(money), BOARD_WIDTH + 200 - x, 211-8);

        //saving heads-up
        highlightedText(p, "Progress Saved", new PVector(890, 890),
                new Color(255, 255, 255, (int) (254f * savingAlpha)),
                new Color(0, 0, 0, (int) (175f * savingAlpha)),
                monoMedium, RIGHT);
    }

    public void updateSave() {
        savingAlpha = 1;
    }

    private void build() {
        openMenuButton = new OpenMenu(p,BOARD_WIDTH+100,12,"null",true);

        towerBuyButtons.add(new TowerBuy(p,towerBuyX(0), towerBuyY(0),"slingshot", Slingshot.class, true));
        towerBuyButtons.add(new TowerBuy(p,towerBuyX(0), towerBuyY(1),"miscCannon", RandomCannon.class, true));
        towerBuyButtons.add(new TowerBuy(p,towerBuyX(0), towerBuyY(2),"crossbow", Crossbow.class, true));
        if (currentLevel > 0 || dev) {
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(0), "cannon", Cannon.class, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(1), "gluer", Gluer.class, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(2), "seismic", SeismicTower.class, true));
        } else {
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(0), "null", null, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(1), "null", null, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(1), towerBuyY(2), "null", null, true));
        } if (currentLevel > 1 || dev) {
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(2), towerBuyY(0), "energyBlaster", EnergyBlaster.class, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(2), towerBuyY(1), "flamethrower", Flamethrower.class, true));
            towerBuyButtons.add(new TowerBuy(p, towerBuyX(2), towerBuyY(2), "tesla", TeslaTower.class, true));
        } else {
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(2), towerBuyY(0),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(2), towerBuyY(1),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(2), towerBuyY(2),"null", null,true));
        } if (currentLevel > 2 || dev) {
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(0),"magicMissleer", MagicMissileer.class,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(1),"iceTower", IceTower.class,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(2),"booster", Booster.class,true));
        } else {
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(0),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(1),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(3), towerBuyY(2),"null", null,true));
        } if (currentLevel > 3 || dev) {
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(0),"railgun", Railgun.class,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(1),"nightmare", Nightmare.class,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(2),"waveMotion", WaveMotion.class,true));
        } else {
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(0),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(1),"null", null,true));
            towerBuyButtons.add(new TowerBuy(p,towerBuyX(4), towerBuyY(2),"null", null,true));
        }
        wallBuyButton = new WallBuy(p,BOARD_WIDTH+100,172-12,"null",true);
        upgradeButtonA = new UpgradeTower(p,1000,480,"null",false, 0);
        upgradeButtonB = new UpgradeTower(p,1000,630,"null",false, 1);
        upgradeIconA = new UpgradeIcon(p,1030,610,"null",false);
        upgradeIconB = new UpgradeIcon(p,1030,610,"null",false);
        priorityButton = new TargetPriority(p,1000,535,"null",false);
        sellButton = new SellTower(p,1000,877.5f,"null",false);
    }

    private static float towerBuyY(int column) {
        return 45.5f + (40 * column);
    }

    private static float towerBuyX(int row) {
        return BOARD_WIDTH + 22.5f + (39 * row);
    }
}
