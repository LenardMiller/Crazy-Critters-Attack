package main.gui;

import main.gui.guiObjects.GuiObject;
import main.gui.guiObjects.UpgradeIcon;
import main.gui.guiObjects.buttons.*;
import processing.core.PApplet;

import static main.Main.*;

public class InGameGui {

    private final PApplet P;

    public int flashA;

    public InGameGui(PApplet p) {
        this.P = p;
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
        selection.turretOverlay();
        P.fill(235); //big white bg
        P.rect(900,212,200,688);
        levels[currentLevel].display();
        P.fill(200); //money bg
        P.rect(BOARD_WIDTH, 175, BOARD_WIDTH + 200, 37);
        openMenuButton.main();
        P.fill(130); //towerbuy bg
        P.rect(900,21,200,127);
        wallBuyButton.main();
//        addMoneyButton.main();
        moneyIcon.main();
        playButton.main(); //display is in Level
        if (!isTowers || selection.name.equals("null")) {
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
        P.fill(255, flashA); //flash
        P.noStroke();
        P.rect(900,212,200,688);
        if (!paused) flashA -= 25;
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
        p.fill(0);
        p.textAlign(RIGHT);
        p.text(nfc(money), p.width - x, 211-5);
//        if (!alive) { todo: game over screen
//            p.textAlign(CENTER);
//            p.textFont(veryLargeFont);
//            p.fill(75,0,0);
//            p.text("Game Over", p.width/2f, p.height/2f);
//        }
    }

    private void build() {
        openMenuButton = new OpenMenu(P,BOARD_WIDTH+100,12,"null",true);
        towerBuyButtons.add(new TowerBuy(P,towerBuyX(0), towerBuyY(0),"slingshot",true)); //row 1
        towerBuyButtons.add(new TowerBuy(P,towerBuyX(0), towerBuyY(1),"miscCannon",true));
        towerBuyButtons.add(new TowerBuy(P,towerBuyX(0), towerBuyY(2),"crossbow",true));
        boolean test = false;
        if (currentLevel > 0 || test) {
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(0), "cannon", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(1), "gluer", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(2), "seismic", true)); //row 2
        } else {
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(0), "null", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(1), "null", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(1), towerBuyY(2), "null", true)); //row 2
        } if (currentLevel > 1 || test) {
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(2), towerBuyY(0), "energyBlaster", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(2), towerBuyY(1), "flamethrower", true));
            towerBuyButtons.add(new TowerBuy(P, towerBuyX(2), towerBuyY(2), "tesla", true));
        } else {
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(2), towerBuyY(0),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(2), towerBuyY(1),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(2), towerBuyY(2),"null",true));
        } if (currentLevel > 2 || test) {
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(0),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(1),"null",true)); //row 3
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(2),"magicMissleer",true));
        } else {
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(0),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(1),"null",true)); //row 3
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(3), towerBuyY(2),"null",true));
        } if (currentLevel > 3 || test) {
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(0),"railgun",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(1),"nightmare",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(2),"waveMotion",true));
        } else {
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(0),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(1),"null",true));
            towerBuyButtons.add(new TowerBuy(P,towerBuyX(4), towerBuyY(2),"null",true));
        }
        wallBuyButton = new WallBuy(P,BOARD_WIDTH+100,172-12,"null",true);
//        addMoneyButton = new AddMoney(p,BOARD_WIDTH + 16.5f,211-16.5f,"null",true);
        moneyIcon = new GuiObject(P,BOARD_WIDTH, 211-29,"moneyIc",true);
        playButton = new Play(P,1000,274.5f,"null",true);
        upgradeButtonA = new UpgradeTower(P,1000,480,"null",false, 0);
        upgradeButtonB = new UpgradeTower(P,1000,630,"null",false, 1);
        upgradeIconA = new UpgradeIcon(P,1030,610,"null",false);
        upgradeIconB = new UpgradeIcon(P,1030,610,"null",false);
        targetButton = new TargetPriority(P,1000,832.5f,"null",false);
        sellButton = new SellTower(P,1000,877.5f,"null",false);
    }

    private float towerBuyY(int column) {
        return 45.5f + (40 * column);
    }

    private float towerBuyX(int row) {
        return BOARD_WIDTH + 22.5f + (39 * row);
    }
}
