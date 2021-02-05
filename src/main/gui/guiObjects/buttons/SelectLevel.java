package main.gui.guiObjects.buttons;

import main.Main;
import main.gui.LevelSelectGui;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SelectLevel extends Button {

    private final int LEVEL;

    public SelectLevel(PApplet p, float x, float y, String type, boolean active, int level) {
        super(p, x, y, type, active);
        position = new PVector(x, y);
        size = new PVector(200, 42);
        spriteIdle = spritesAnimH.get("genericButtonBT")[0];
        spritePressed = spritesAnimH.get("genericButtonBT")[1];
        spriteHover = spritesAnimH.get("genericButtonBT")[2];
        sprite = spriteIdle;
        this.LEVEL = level;
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
                p.mouseY > position.y-size.y/2 && LevelSelectGui.delay < 0) {
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
        currentLevel = LEVEL;
        Main.resetGame(p);
        paused = false;
        alive = true;
        screen = 0;
    }
}
