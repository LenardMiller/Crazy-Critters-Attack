package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;

public class OpenMenu extends Button {
    public OpenMenu(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = spritesAnimH.get("towerTabSwitchBT")[0];
        spritePressed = spritesAnimH.get("towerTabSwitchBT")[1];
        spriteHover = spritesAnimH.get("towerTabSwitchBT")[2];
        sprite = spriteIdle;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void action(){
        System.out.println("Not yet implemented!");
    }
}
