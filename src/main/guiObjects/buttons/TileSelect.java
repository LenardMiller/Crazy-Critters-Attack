package main.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class TileSelect extends Button {

    private String type;
    private PImage tileSprite;

    public TileSelect(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(50, 50);
        this.type = type;
        tileSprite = spritesH.get(type + "_TL");
        String sl = "";
        if (type.contains("BGA")) sl = "BGA";
        if (type.contains("BGW")) sl = "BGW";
        if (type.contains("BGB")) sl = "BGB";
        if (type.contains("BGC")) sl = "BGC";
        spriteLocation = "sprites/guiObjects/buttons/tileSelect/" + sl + "/"; //still uses old system because it is only created at beginning of game
        spriteOne = p.loadImage(spriteLocation + "000.png");
        spriteTwo = p.loadImage(spriteLocation + "001.png");
        sprite = spriteOne;
    }

    public void main() {
        if (active){
            hover();
            display();
        }
    }

    public void display() {
        p.image(tileSprite,position.x-size.x/2,position.y-size.y/2);
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }

    public void hover() { //below is if hovered or depressed
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 && p.mouseY > position.y-size.y/2 && alive && active){
            sprite = spriteTwo;
            if (inputHandler.leftMousePressedPulse) action();
        }
        else sprite = spriteOne;
    }

    public void action() {
        if (hand.held.equals(type)) hand.setHeld("null");
        hand.setHeld(type + "_TL");
    }
}