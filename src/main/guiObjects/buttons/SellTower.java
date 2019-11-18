package main.guiObjects.buttons;

import main.guiObjects.GuiObject;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public class SellTower extends Button {

    public SellTower(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 45);
        spriteOne = spritesAnimH.get("sellTowerBT")[0];
        spriteTwo = spritesAnimH.get("sellTowerBT")[1];
        sprite = spriteOne;
        actionTime = p.frameCount + 30;
    }

    public void main(ArrayList<GuiObject> guiObjects, int i){
        if (active){
            hover();
            display();
        }
    }

    public void action(){ //kills tower and gives value
        Tower tower = towers.get(selection.id);
        money += (int) (tower.value * .8);
        tower.twHp = 0; //creates particles (may need to change later)
        active = false;
        targetButton.active = false;
        repairButton.active = false;
        upgradeButtonZero.active = false;
        upgradeButtonOne.active = false;
        upgradeGuiObjectZero.active = false;
        upgradeGuiObjectOne.active = false;
        selection.name = "null";
    }
}