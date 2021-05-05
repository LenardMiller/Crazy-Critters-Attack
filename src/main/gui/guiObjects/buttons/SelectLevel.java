package main.gui.guiObjects.buttons;

import main.Main;
import main.gui.LevelSelectGui;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.playSound;

public class SelectLevel extends Button {

    private final int LEVEL;

    public SelectLevel(PApplet p, float x, float y, int level) {
        super(p, x, y, "null", true);
        position = new PVector(x, y);
        size = new PVector(200, 42);
        spriteIdle = animatedSprites.get("genericButtonBT")[0];
        spritePressed = animatedSprites.get("genericButtonBT")[1];
        spriteHover = animatedSprites.get("genericButtonBT")[2];
        sprite = spriteIdle;
        this.LEVEL = level;
    }

    @Override
    public void main(){
        hover();
        display();
    }

    /**
     * If mouse over, push in.
     * Works if paused or dead.
     */
    @Override
    public void hover(){
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 &&
                p.mouseY > position.y-size.y/2 && LevelSelectGui.delay < 0) {
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
    public void action() {
        currentLevel = LEVEL;
        Main.resetGame(p);
        paused = false;
        alive = true;
        screen = 0;
    }
}
