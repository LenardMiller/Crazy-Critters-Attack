package main.gui.guiObjects.buttons;

import main.gui.guiObjects.GuiObject;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;

public abstract class Button extends GuiObject {

    public int timer;
    PImage spriteIdle;
    PImage spritePressed;
    PImage spriteHover;
    String spriteLocation;
    boolean holdable;
    SoundFile clickIn;
    SoundFile clickOut;

    public Button(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        sprite = spriteIdle;
        holdable = false;
        clickIn = soundsH.get("clickIn");
        clickOut = soundsH.get("clickOut");
    }

    /**
     * If mouse over, push in.
     */
    public void hover(){
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
                p.mouseY > position.y-size.y/2 && alive && !paused) {
            sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse) {
                clickIn.stop();
                clickIn.play(1, volume);
            }
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (holdable && p.mousePressed && p.mouseButton == LEFT) action();
            else if (inputHandler.leftMouseReleasedPulse) {
                clickOut.stop();
                clickOut.play(1, volume);
                action();
                sprite = spritePressed;
            }
        } else sprite = spriteIdle;
    }

    public void action(){ //prints "Boink!"
        System.out.println("Boink!");
    }

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