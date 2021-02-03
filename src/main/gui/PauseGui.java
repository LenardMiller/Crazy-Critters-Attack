package main.gui;

import main.gui.guiObjects.buttons.ExitGame;
import main.gui.guiObjects.buttons.RestartLevel;
import main.gui.guiObjects.buttons.ResumeGame;
import processing.core.PApplet;

public class PauseGui {

    private final PApplet P;

    public static ResumeGame resumeGame;
    public static RestartLevel restartLevel;
    public static ExitGame exitGame;

    /**
     * Creates the pause menu.
     * @param p the PApplet
     */
    public PauseGui(PApplet p) {
        P = p;
        build();
    }

    public void display() {
        resumeGame.main();
        restartLevel.main();
        exitGame.main();
    }

    /**
     * Creates buttons
     */
    private void build() {
        resumeGame = new ResumeGame(P, P.width/2f, (P.height/2f) - 50, "null", true);
        restartLevel = new RestartLevel(P, P.width/2f, P.height/2f, "null", true);
        exitGame = new ExitGame(P, P.width/2f, (P.height/2f) + 50, "null", true);
    }
}
