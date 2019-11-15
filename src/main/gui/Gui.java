package main.gui;

import main.guiObjects.Icon;
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
        if (towers.size() == 0){ //force deactivation if no towers
            sellButton.active = false;
            targetButton.active = false;
            repairButton.active = false;
            upgradeButtonZero.active = false;
            upgradeButtonOne.active = false;
            upgradeIconZero.active = false;
            upgradeIconOne.active = false;
        }
        towerTabButton.main(icons, 0);
        sellButton.main(icons, 0);
        targetButton.main(icons, 0);
        repairButton.main(icons, 0);
        upgradeButtonZero.main(icons, 0);
        upgradeButtonOne.main(icons, 0);
        upgradeIconZero.main(icons, 0);
        upgradeIconOne.main(icons, 0);
        if (towers.size() != 0){
            selection.main();
        }
        for (int i = icons.size()-1; i >= 0; i--){
            Icon icon = icons.get(i);
            icon.main(icons, i);
        }
    }

    public void drawText(PApplet p, int x) {
        p.fill(255);
        p.textFont(largeFont);
        p.textAlign(LEFT);
        //debug related stuff
        p.text("enemies: " + enemies.size(), x, 30);
        p.text("towers: " + towers.size(), x, 60);
        p.text("projectiles: " + projectiles.size(), x, 90);
        p.text("particles: " + particles.size(), x, 120);
        p.text("X: " + p.mouseX + " Y: " + p.mouseY, x, BOARD_HEIGHT -x);
        //gameplay related stuff
        //p.fill(255,0,0); //red
        //p.text("Hp", boardWidth + (x*2) + 25, 30); //replaced with heart icon
        //p.fill(255,225,0); //orangish-yellow
        //p.text("$", boardWidth + (x*2) + 25, 60); //replaced with $ icon
        p.textAlign(RIGHT);
        p.text(round(p.frameRate) + " fps", BOARD_WIDTH - x, 30);
        p.fill(0);
        p.text(hp, p.width - x, 30);
        p.text(nfc(money), p.width - x, 60);
        if (!alive){
            p.textAlign(CENTER);
            p.textFont(veryLargeFont);
            p.fill(75,0,0);
            p.text("Game Over", p.width/2, p.height/2);
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
        //add money & add lives buttons
        icons.add(new AddHp(p,BOARD_WIDTH + 22.5f,17.5f,"null",true));
        icons.add(new AddMoney(p,BOARD_WIDTH + 22.5f,47.5f,"null",true));
        //money and lives icons
        icons.add(new Icon(p,BOARD_WIDTH + 57.5f, 17.5f,"livesIc",true));
        icons.add(new Icon(p,BOARD_WIDTH + 57.5f, 47.5f,"moneyIc",true));
        //buy tower buttons tab 1 (4-18)
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 87,"slingshot",true)); //row 1
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 87,"crossbow",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 87,"miscCannon",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 87,"energyBlaster",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 87,"magicMissleer",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 127,"null",true)); //row 2 placeholders
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 127,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 127,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 127,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 127,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 167,"null",true)); //row 3
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 167,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 167,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 167,"null",true));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 167,"wall",true));
        //buy tower buttons tabp, 2 (19-33)
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 87,"null",false)); //row 1
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 87,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 87,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 87,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 87,"null",false)); //placeholders
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 127,"null",false)); //row 2
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 127,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 127,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 127,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 127,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)21.5, 167,"null",false)); //row 3
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)60.5, 167,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)101.5, 167,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)140.5, 167,"null",false));
        icons.add(new TowerBuy(p,BOARD_WIDTH + (float)179.5, 167,"null",false));
        //switch tower tab button
        towerTabButton = new TowerTab(p,800,198,"null",true);
        //sell tower button
        sellButton = new SellTower(p,800,877.5f,"null",false);
        //target priority button
        targetButton = new TargetPriority(p,800,832.5f,"null",false);
        //repair wall button
        repairButton = new RepairWall(p,800,780,"null",false);
        //upgrade wall button zero
        upgradeButtonZero = new UpgradeTower(p,800,480,"null",false, 0);
        //upgrade wall button one
        upgradeButtonOne = new UpgradeTower(p,800,630,"null",false, 1);
        //upgrade wall icon
        upgradeIconZero = new UpgradeIcon(p,830,610,"null",false);
        //upgrade wall icon
        upgradeIconOne = new UpgradeIcon(p,830,610,"null",false);
    }
}
