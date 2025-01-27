package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.selection;

public class SellTower extends Button {

    public SellTower(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 45);
        spriteIdle = animatedSprites.get("sellTowerBT")[0];
        spritePressed = animatedSprites.get("sellTowerBT")[1];
        spriteHover = animatedSprites.get("sellTowerBT")[2];
        sprite = spriteIdle;
    }

    @Override
    public void pressIn() { //kills tower and gives value
        active = false;
        selection.sell();
    }
}