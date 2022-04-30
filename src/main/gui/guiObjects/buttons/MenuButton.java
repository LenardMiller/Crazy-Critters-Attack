package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.inputHandler;
import static main.sound.SoundUtilities.playSound;
import static processing.core.PConstants.LEFT;

public class MenuButton extends Button {

    private boolean pressed;
    private Runnable action;

    /**
     * A cool little button for use in menus and whatnot.
     * @param p the PApplet
     * @param x x position
     * @param y y position
     * @param action what to do when pressed, optional, may use isPressed instead.
     */
    public MenuButton(PApplet p, float x, float y, Runnable action) {
        super(p, x, y, "null", true);
        this.action = action;
        position = new PVector(x, y);
        size = new PVector(200, 42);
        spriteIdle = animatedSprites.get("genericButtonBT")[0];
        spritePressed = animatedSprites.get("genericButtonBT")[1];
        spriteHover = animatedSprites.get("genericButtonBT")[2];
        sprite = spriteIdle;
    }

    /**
     * A cool little button for use in menus and whatnot.
     * @param p the PApplet
     * @param x x position
     * @param y y position
     */
    public MenuButton(PApplet p, float x, float y) {
        this(p, x, y, () -> {});
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
            if (holdable && p.mousePressed && p.mouseButton == LEFT) pressIn();
            else if (inputHandler.leftMouseReleasedPulse) {
                playSound(clickOut, 1, 1);
                pressIn();
                sprite = spritePressed;
            }
        } else sprite = spriteIdle;
    }

    @Override
    public void pressIn() {
        pressed = true;
        action.run();
    }

    /** @return whether the mouse was recently pressed, resets pressed */
    public boolean isPressed() {
        boolean wasPressed = pressed;
        pressed = false;
        return wasPressed;
    }
}
