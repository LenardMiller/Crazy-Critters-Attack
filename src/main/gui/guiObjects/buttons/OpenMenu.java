package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.LEFT;

public class OpenMenu extends Button {

    public OpenMenu(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = spritesAnimH.get("towerTabSwitchBT")[0];
        spritePressed = spritesAnimH.get("towerTabSwitchBT")[1];
        spriteHover = spritesAnimH.get("towerTabSwitchBT")[2];
        sprite = spriteIdle;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
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

    public void action(){
        paused = !paused;
    }
}
