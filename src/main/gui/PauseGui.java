package main.gui;

import main.gui.guiObjects.buttons.RestartLevel;
import processing.core.PApplet;

public class PauseGui {

    private final PApplet P;

    public static RestartLevel restartLevel;

    /**
     * Creates the pause menu.
     * @param p the PApplet
     */
    public PauseGui(PApplet p) {
        P = p;
        build();
    }

    public void display() {
        restartLevel.main();
    }

    /**
     * Creates buttons
     */
    private void build() {
        restartLevel = new RestartLevel(P, P.width/2f, P.height/2f, "null", true);
    }
}
