package main.guiObjects;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;

public class UpgradeGuiObject extends GuiObject {

    public UpgradeGuiObject(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        this.active = active;
        position = new PVector(x, y);
        size = new PVector(25, 25);
        //this variable is the only reason this gets its own class
        sprite = spritesAnimH.get("upgradeIC")[0];
    }
}