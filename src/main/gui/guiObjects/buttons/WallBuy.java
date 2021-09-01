package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSound;

public class WallBuy extends Button {

    public boolean depressed;
    public int timer;

    public WallBuy(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = animatedSprites.get("wallBuyBT")[0];
        spritePressed = animatedSprites.get("wallBuyBT")[1];
        spriteHover = animatedSprites.get("wallBuyBT")[2];
        sprite = spriteIdle;
        depressed = false;
    }

    @Override
    public void main(){
        timer++;
        if (active){
            hover();
            display();
        }
    }

    /**
     * If hovered or depressed.
     */
    @Override
    public void hover() {
        if (matrixMousePosition.x < position.x+size.x/2 && matrixMousePosition.x > position.x-size.x/2 &&
          matrixMousePosition.y < position.y+size.y/2 && matrixMousePosition.y > position.y-size.y/2 && alive && !paused) {
            sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMouseReleasedPulse && timer > 20) {
                timer = 0;
                action();
                sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (!hand.displayInfo.equals("null")) sprite = spritePressed;
    }

    @Override
    public void action() {
        depressed = !depressed;
        selection.name = "null";
        //if already holding, stop
        if (hand.held.equals("wall")) hand.setHeld("null");
            //if not, do
        else hand.setHeld("wall");
    }
}