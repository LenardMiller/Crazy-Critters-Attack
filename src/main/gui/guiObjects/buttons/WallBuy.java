package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class WallBuy extends Button {

    public boolean depressed;

    public WallBuy(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = spritesAnimH.get("wallBuyBT")[0];
        spritePressed = spritesAnimH.get("wallBuyBT")[1];
        spriteHover = spritesAnimH.get("wallBuyBT")[2];
        sprite = spriteIdle;
        depressed = false;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void hover(){ //below is if hovered or depressed
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 && p.mouseY > position.y-size.y/2 && alive && active) {
            sprite = spriteHover;
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMouseReleasedPulse) {
                action();
                sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (!hand.displayInfo.equals("null")) sprite = spritePressed;
    }

    public void action() {
        depressed = !depressed;
        selection.name = "null";
        //if already holding, stop
        if (hand.held.equals("wall")) hand.setHeld("null");
        //if not, do
        else hand.setHeld("wall");
    }
}
