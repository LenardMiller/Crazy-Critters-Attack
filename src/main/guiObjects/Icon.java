package main.guiObjects;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.spritesH;

public class Icon{

    public PApplet p;

    public PVector position;
    public PVector size;
    public PImage sprite;
    public boolean active;

    public Icon(PApplet p, float x, float y, String type, boolean active){
        this.p = p;

        this.active = active;
        position = new PVector(x, y);
        size = new PVector(25, 25);
        sprite = spritesH.get(type);
    }

    public void main(ArrayList<Icon> icons, int i){
        if (active){
            display();
        }
    }

    private void display(){
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }
}
