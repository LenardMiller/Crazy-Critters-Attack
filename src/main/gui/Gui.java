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

    public void drawIcons() {
        boolean isTowers = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).tower != null) {
                isTowers = true;
                break;
            }

        }
        addMoney.main();
        moneyIcon.main();
        if (!isTowers) {
            sellButton.active = false;
            targetButton.active = false;
            upgradeButtonA.active = false;
            upgradeButtonB.active = false;
            upgradeIconA.active = false;
            upgradeIconB.active = false;
        }
        towerTabButton.main();
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
        p.textAlign(RIGHT);
        p.fill(0);
        p.text(nfc(money), p.width - x, 60);
        if (!alive) {
            p.textAlign(CENTER);
            p.textFont(veryLargeFont);
            p.fill(75,0,0);
            p.text("Game Over", p.width/2f, p.height/2f);
        }
        //textFont(TWFont); //replaced with healthbars
        //fill(255);
        //textAlign(CENTER);
        ////enemy health, jump to enemy.HpText
        //for (int i = enemies.size()-1; i >= 0; i--){
        //  Enemy enemy = enemies.get(i);
        //  enemy.HpText();
        //}
        ////tower health, jump to tower.HpText
        //for (int i = towers.size()-1; i >= 0; i--) {
        //  Tower tower = towers.get(i);
        //  tower.HpText();
        //}
    }

    private void build() {
        //add money button
        addMoney = (new AddMoney(p,BOARD_WIDTH + 22.5f,47.5f,"null",true));
        //money icon
        moneyIcon = (new GuiObject(p,BOARD_WIDTH + 57.5f, 47.5f,"moneyIc",true));
        //buy tower buttons tab 1 (4-18)
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 87,"slingshot",true)); //row 1
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 87,"crossbow",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 87,"miscCannon",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 87,"energyBlaster",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 87,"magicMissleer",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 127,"null",true)); //row 2 placeholders
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 127,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 127,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 127,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 127,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 167,"null",true)); //row 3
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 167,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 167,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 167,"null",true));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 167,"wall",true));
        //buy tower buttons tabp, 2 (19-33)
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 87,"null",false)); //row 1
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 87,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 87,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 87,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 87,"null",false)); //placeholders
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 127,"null",false)); //row 2
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 127,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 127,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 127,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 127,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 167,"null",false)); //row 3
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 167,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 167,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 167,"null",false));
        towerBuyButtons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 167,"null",false));
        //switch tower tab button
        towerTabButton = new TowerTab(p,1000,198,"null",true);
        //sell tower button
        sellButton = new SellTower(p,1000,877.5f,"null",false);
        //target priority button
        targetButton = new TargetPriority(p,1000,832.5f,"null",false);
        //upgrade wall button zero
        upgradeButtonA = new UpgradeTower(p,1000,480,"null",false, 0);
        //upgrade wall button one
        upgradeButtonB = new UpgradeTower(p,1000,630,"null",false, 1);
        //upgrade wall icon
        upgradeIconA = new UpgradeIcon(p,1030,610,"null",false);
        //upgrade wall icon
        upgradeIconB = new UpgradeIcon(p,1030,610,"null",false);
    }
}
