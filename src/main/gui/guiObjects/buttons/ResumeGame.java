package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class ResumeGame extends Button {

    public ResumeGame(PApplet p, float x, float y, String type, boolean active) {
        super(p, x, y, type, active);
        position = new PVector(x, y);
        size = new PVector(200, 42);
        spriteIdle = animatedSprites.get("genericButtonBT")[0];
        spritePressed = animatedSprites.get("genericButtonBT")[1];
        spriteHover = animatedSprites.get("genericButtonBT")[2];
        sprite = spriteIdle;
    }

    public void main(){
        hover();
        display();
    }

    /**
     * If mouse over, push in.
     * Works if paused or dead.
     */
    public void hover(){
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
                p.mouseY > position.y-size.y/2) {
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

    public void action() {
        paused = false;
    }
}
