package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class TileSelect extends Button {

    private final String TYPE;
    private final PImage TILE_SPRITE;

    public TileSelect(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(50, 50);
        this.TYPE = type;
        String sl = "";
        if (type.contains("BGA")) sl = "BGA";
        if (type.contains("BGW")) sl = "BGW";
        if (type.contains("BGB")) sl = "BGB";
        if (type.contains("BGC")) sl = "BGC";
        if (type.contains("Ob")) sl = "obstacle";
        if (type.contains("Ma")) {
            sl = "machine";
            TILE_SPRITE = p.loadImage("sprites/guiObjects/buttons/tileSelect/machine/icon.png");
        } else TILE_SPRITE = spritesH.get(type + "_TL");
        spriteLocation = "sprites/guiObjects/buttons/tileSelect/" + sl + "/"; //still uses old system because it is only created at beginning of game
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        sprite = spriteIdle;
    }

    public void main() {
        if (active){
            hover();
            display();
        }
    }

    public void display() {
        p.image(TILE_SPRITE,position.x-size.x/2,position.y-size.y/2);
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }

    /**
     * If hovered or depressed.
     */
    public void hover() {
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
                p.mouseY > position.y-size.y/2 && alive && active && !paused) {
            sprite = spritePressed;
            if (inputHandler.leftMousePressedPulse) action();
        }
        else sprite = spriteIdle;
    }

    public void action() {
        if (hand.held.equals(TYPE)) hand.setHeld("null");
        hand.setHeld(TYPE + "_TL");
    }
}