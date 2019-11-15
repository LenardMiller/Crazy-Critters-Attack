package main.guiObjects.buttons;

import main.guiObjects.Icon;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.alive;
import static main.Main.spritesAnimH;

public abstract class Button extends Icon {

    int actionTime;
    PImage spriteOne;
    PImage spriteTwo;
    String spriteLocation;

    public Button(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);

        spriteOne = spritesAnimH.get("nullBT")[0]; //popped out
        spriteTwo = spritesAnimH.get("nullBT")[1]; //pushed in
        sprite = spriteOne;
        actionTime = p.frameCount + 6;
    }

    public void hover(){ //if mouse over, push in
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&p. mouseY > position.y-size.y/2 && alive){
            sprite = spriteTwo;
            if (p.mousePressed && p.frameCount - actionTime >= 6){ //if hovering and click
                action();
            }
        } else{
            sprite = spriteOne;
        }
    }

    public void action(){ //prints "Boink!"
        System.out.println("Boink!");
        actionTime = p.frameCount + 6;
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