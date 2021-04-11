package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.money;

public class AddMoney extends Button {

    public AddMoney(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        spriteIdle = animatedSprites.get("moneyAddBT")[0];
        spritePressed = animatedSprites.get("moneyAddBT")[1];
        spriteHover = animatedSprites.get("moneyAddBT")[2];
        sprite = spriteIdle;
        holdable = true;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void action(){ //give money
        money += 1000;
    }
}