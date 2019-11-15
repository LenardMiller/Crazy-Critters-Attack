package main.guiObjects.buttons;

import main.guiObjects.Icon;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.spritesAnimH;
import static main.Main.icons;

public class TowerTab extends Button {
    public TowerTab(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteOne = spritesAnimH.get("towerTabSwitchBT")[0];
        spriteTwo = spritesAnimH.get("towerTabSwitchBT")[1];
        sprite = spriteOne;
        actionTime = p.frameCount + 6;
    }

    public void main(ArrayList<Icon> icons, int i){
        if (active){
            hover();
            display();
        }
    }

    public void action(){
        for (int i = 4; i <= 33; i++){ //iterates through all tower buttons and inverts active
            Icon button = icons.get(i);
            button.active = !button.active;
        }
        actionTime = p.frameCount + 6;
    }
}