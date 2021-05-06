package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.inputHandler;
import static main.misc.Utilities.playSound;
import static processing.core.PConstants.LEFT;

public class MenuButton extends Button {

    private boolean pressed;

    public MenuButton(PApplet p, float x, float y) {
        super(p, x, y, "null", true);
        position = new PVector(x, y);
        size = new PVector(200, 42);
        spriteIdle = animatedSprites.get("genericButtonBT")[0];
        spritePressed = animatedSprites.get("genericButtonBT")[1];
        spriteHover = animatedSprites.get("genericButtonBT")[2];
        sprite = spriteIdle;
    }

    /**
     * If mouse over, push in.
     * Works if paused or dead.
     */
    @Override
    public void hover(){
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
          p.mouseY > position.y-size.y/2) {
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

    /**
     * This makes sense for some reason, but I forgot why
     */
    @Override
    public void action() {
        pressed = true;
    }

    public boolean isPressed() {
        boolean wasPressed = pressed;
        pressed = false;
        return wasPressed;
    }
}
