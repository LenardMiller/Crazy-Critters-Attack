package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class TileSelect extends Button {

    private final String type;
    private final PImage tileSprite;

    public TileSelect(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(50, 50);
        this.type = type;
        String sl = "";
        if (type.contains("Ba")) sl = "Ba";
        if (type.contains("Fl")) sl = "Fl";
        if (type.contains("De")) sl = "De";
        if (type.contains("Br")) sl = "Br";
        if (type.contains("Ob")) sl = "obstacle";
        if (type.contains("Ma")) {
            sl = "machine";
            tileSprite = p.loadImage("sprites/gui/buttons/tileSelect/machine/icon.png");
        } else if (type.contains("Na")) {
            sl = "erase";
            tileSprite = p.loadImage("sprites/gui/buttons/tileSelect/erase/icon.png");
        } else if (type.contains("invisible")){
            tileSprite = p.loadImage("sprites/gui/buttons/tileSelect/erase/icon.png");
        }
        else tileSprite = staticSprites.get(type + "_Tl");
        if (tileSprite == null) System.err.println("tile icon missing: " + type);
        //still uses old system because it is only created at beginning of game
        spriteLocation = "sprites/gui/buttons/tileSelect/" + sl + "/";
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        sprite = spriteIdle;
    }

    @Override
    public void display() {
        p.image(tileSprite,position.x-size.x/2,position.y-size.y/2);
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }

    /**
     * If hovered or depressed.
     */
    @Override
    public void hover() {
        if (boardMousePosition.x < position.x+size.x/2 && boardMousePosition.x > position.x-size.x/2 &&
                boardMousePosition.y < position.y+size.y/2 && boardMousePosition.y > position.y-size.y/2 && alive && active && !isPaused) {
            sprite = spritePressed;
            if (inputHandler.leftMousePressedPulse) pressIn();
        }
        else sprite = spriteIdle;
    }

    @Override
    public void pressIn() {
        if (hand.held.equals(type)) hand.setHeld("null");
        hand.setHeld(type + "_Tl");
    }
}