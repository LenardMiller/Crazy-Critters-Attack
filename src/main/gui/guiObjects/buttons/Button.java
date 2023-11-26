package main.gui.guiObjects.buttons;

import main.gui.guiObjects.GuiObject;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.sound.SoundUtilities.playSound;

public abstract class Button extends GuiObject {

    protected boolean holdable;
    protected PImage spriteIdle;
    protected PImage spritePressed;
    protected PImage spriteHover;
    protected String spriteLocation;
    protected SoundFile clickIn;
    protected SoundFile clickOut;

    protected Button(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        clickIn = sounds.get("clickIn");
        clickOut = sounds.get("clickOut");
    }

    public void hover() {
        if (boardMousePosition.x < position.x+size.x/2 &&
                boardMousePosition.x > position.x-size.x/2 &&
                boardMousePosition.y < position.y+size.y/2 &&
                boardMousePosition.y > position.y-size.y/2 &&
                alive && !paused) {
            sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (holdable && p.mousePressed && p.mouseButton == LEFT) pressIn();
            else if (inputHandler.leftMouseReleasedPulse) {
                playSound(clickOut, 1, 1);
                pressIn();
                sprite = spritePressed;
            }
        } else sprite = spriteIdle;
    }

    public abstract void pressIn();

    public void update() {
        if (!active) return;
        hover();
    }

    public void display() {
        if (!active) return;
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }
}