package main.gui.guiObjects.buttons;

import main.gui.guiObjects.GuiObject;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public abstract class Button extends GuiObject {

    PImage spriteOne;
    PImage spriteTwo;
    String spriteLocation;
    boolean holdable;

    public Button(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        sprite = spriteOne;
        holdable = false;
    }

    public void hover(){ //if mouse over, push in
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&p. mouseY > position.y-size.y/2 && alive){
            sprite = spriteTwo;
            if (holdable) {
                if (p.mousePressed && p.mouseButton == LEFT) action();
            }
            else if (inputHandler.leftMousePressedPulse) action();
        } else sprite = spriteOne;
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