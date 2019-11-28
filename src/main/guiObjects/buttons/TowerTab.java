package main.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static main.Main.towerBuyButtons;

public class TowerTab extends Button {
    public TowerTab(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteOne = spritesAnimH.get("towerTabSwitchBT")[0];
        spriteTwo = spritesAnimH.get("towerTabSwitchBT")[1];
        sprite = spriteOne;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void action(){
        System.out.println("test");
        for (TowerBuy towerBuyButton : towerBuyButtons) towerBuyButton.active = !towerBuyButton.active;
    }
}