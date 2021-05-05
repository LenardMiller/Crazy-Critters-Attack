package main.gui.guiObjects.buttons;

import main.gui.guiObjects.GuiObject;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.misc.Utilities.playSound;

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
        sprite = spriteIdle;
        holdable = false;
        clickIn = sounds.get("clickIn");
        clickOut = sounds.get("clickOut");
    }

    public void hover(){
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
                p.mouseY > position.y-size.y/2 && alive && !paused) {
            sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (holdable && p.mousePressed && p.mouseButton == LEFT) action();
            else if (inputHandler.leftMouseReleasedPulse) {
                playSound(clickOut, 1, 1);
                action();
                sprite = spritePressed;
            }
        } else sprite = spriteIdle;
    }

    public abstract void action();

    @Override
    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void display(){
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
    }
}