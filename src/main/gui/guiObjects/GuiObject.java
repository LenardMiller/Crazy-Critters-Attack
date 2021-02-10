package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.spritesH;

public class GuiObject {

    public PApplet p;

    public PVector position;
    public PVector size;
    public PImage sprite;
    public boolean active;

    public GuiObject(PApplet p, float x, float y, String type, boolean active){
        this.p = p;

        this.active = active;
        position = new PVector(x, y);
        size = new PVector(0, 0);
        sprite = spritesH.get(type);
    }

    public void main(){
        if (active){
            display();
        }
    }

    private void display() {
        if (sprite != null) p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }
}
