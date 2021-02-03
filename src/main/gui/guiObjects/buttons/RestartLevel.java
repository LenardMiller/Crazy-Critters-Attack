package main.gui.guiObjects.buttons;

import main.Main;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;

public class RestartLevel extends Button {

    public RestartLevel(PApplet p, float x, float y, String type, boolean active) {
        super(p, x, y, type, active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = spritesAnimH.get("towerTabSwitchBT")[0];
        spritePressed = spritesAnimH.get("towerTabSwitchBT")[1];
        spriteHover = spritesAnimH.get("towerTabSwitchBT")[2];
        sprite = spriteIdle;
    }

    public void action() {
        Main.resetGame(p);
    }
}
