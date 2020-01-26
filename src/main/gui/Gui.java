package main.gui;

import main.guiObjects.GuiObject;
import main.guiObjects.UpgradeIcon;
import main.guiObjects.buttons.*;
import processing.core.PApplet;

import static main.Main.*;

public class Gui {

    private PApplet p;

    public Gui(PApplet p) {
        this.p = p;
        build();
    }

    public void display() {
        boolean isTowers = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).tower != null) {
                isTowers = true;
                break;
            }
        }
        openMenuButton.main();
        addMoneyButton.main();
        moneyIcon.main();
        wallBuyButton.main();
        if (!isTowers) {
            sellButton.active = false;
            targetButton.active = false;
            upgradeButtonA.active = false;
            upgradeButtonB.active = false;
            upgradeIconA.active = false;
            upgradeIconB.active = false;
        }
        sellButton.main();
        targetButton.main();
        upgradeButtonA.main();
        upgradeButtonB.main();
        upgradeIconA.main();
        upgradeIconB.main();
        if (isTowers) selection.main();
        for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.main();
    }

    public void drawText(PApplet p, int x) {
        p.fill(255);
        p.textFont(largeFont);
        p.textAlign(LEFT);
        if (debug) {
            p.text("enemies: " + enemies.size(), x, 30);
            p.text("towers: " + towers.size(), x, 60);
            p.text("projectiles: " + projectiles.size(), x, 90);
            p.text("particles: " + particles.size(), x, 120);
            p.text("X: " + p.mouseX + " Y: " + p.mouseY, x, BOARD_HEIGHT - x);
            p.textAlign(RIGHT);
            p.text(round(p.frameRate) + " fps", BOARD_WIDTH - x, 30);
        }
        p.fill(200);
        p.textFont(mediumFont);
        p.textAlign(CENTER);
        p.text("Menu", 1000, 18);
        p.fill(0);
        p.text("Walls", 1000, 170);
        p.textFont(largeFont);
        p.textAlign(RIGHT);
        p.text(nfc(money), p.width - x, 211-4);
        if (!alive) {
            p.textAlign(CENTER);
            p.textFont(veryLargeFont);
            p.fill(75,0,0);
            p.text("Game Over", p.width/2f, p.height/2f);
        }
    }

    private void build() {
        openMenuButton = new OpenMenu(p,BOARD_WIDTH+100,12,"null",true);
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 21.5f, 45.5f,"slingshot",true)); //row 1
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 60.5f, 45.5f,"crossbow",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 101.5f, 45.5f,"miscCannon",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 140.5f, 45.5f,"energyBlaster",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 179.5f, 45.5f,"magicMissleer",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 21.5f, 85.5f,"null",true)); //row 2
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 60.5f, 85.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 101.5f, 85.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 140.5f, 85.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 179.5f, 85.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 21.5f, 124.5f,"null",true)); //row 3
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 60.5f, 124.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 101.5f, 124.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 140.5f, 124.5f,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + 179.5f, 124.5f,"null",true));
        wallBuyButton = new WallBuy(p,BOARD_WIDTH+100,172-12,"null",true);
        addMoneyButton = new AddMoney(p,BOARD_WIDTH + 16.5f,211-16.5f,"null",true);
        moneyIcon = new GuiObject(p,BOARD_WIDTH + 45, 211-16.5f,"moneyIc",true);
        upgradeButtonA = new UpgradeTower(p,1000,480,"null",false, 0);
        upgradeButtonB = new UpgradeTower(p,1000,630,"null",false, 1);
        upgradeIconA = new UpgradeIcon(p,1030,610,"null",false);
        upgradeIconB = new UpgradeIcon(p,1030,610,"null",false);
        targetButton = new TargetPriority(p,1000,832.5f,"null",false);
        sellButton = new SellTower(p,1000,877.5f,"null",false);
    }
}
