package main.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.hp;
import static main.Main.spritesAnimH;

public class AddHp extends Button {

    public AddHp(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        spriteOne = spritesAnimH.get("livesAddBT")[0];
        spriteTwo = spritesAnimH.get("livesAddBT")[1];
        sprite = spriteOne;
        holdable = true;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void action(){ //give lives
        hp++;
    }
}