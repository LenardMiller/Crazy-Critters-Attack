package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.playSound;
import static processing.core.PConstants.LEFT;

public class OpenMenu extends Button {

    public OpenMenu(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = animatedSprites.get("towerTabSwitchBT")[0];
        spritePressed = animatedSprites.get("towerTabSwitchBT")[1];
        spriteHover = animatedSprites.get("towerTabSwitchBT")[2];
        sprite = spriteIdle;
    }

    @Override
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
    @Override
    public void hover() {
        if (matrixMousePosition.x < position.x+size.x/2 && matrixMousePosition.x > position.x-size.x/2 &&
          matrixMousePosition.y < position.y+size.y/2 && matrixMousePosition.y > position.y-size.y/2 && alive && !paused) {
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

    @Override
    public void action(){
        paused = !paused;
    }
}
