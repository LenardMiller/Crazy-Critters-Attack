package main.gui;

import main.gui.guiObjects.buttons.ExitGame;
import main.gui.guiObjects.buttons.RestartLevel;
import main.gui.guiObjects.buttons.ResumeGame;
import processing.core.PApplet;

import static main.Main.*;

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
        //dark
        if (!alive) P.fill(50, 0, 0, 50);
        else P.fill(0, 0, 0, 50);
        P.rect(0, 0, P.width, P.height);
        //big text
        if (!alive) P.fill(255, 0, 0);
        else P.fill(255);
        P.textFont(veryLargeFont);
        P.textAlign(P.CENTER);
        if (!alive) P.text("Game Over", P.width/2f, 300);
        else P.text("Paused", P.width/2f, 300);
        //buttons
        P.fill(200);
        P.textFont(mediumFont);
        int offsetY = 7;
        if (alive) {
            resumeGame.main();
            P.text("Resume (space)", resumeGame.position.x, resumeGame.position.y + offsetY);
        }
        restartLevel.main();
        P.text("Restart", restartLevel.position.x, restartLevel.position.y + offsetY);
        exitGame.main();
        P.text("Quit (esc)", exitGame.position.x, exitGame.position.y + offsetY);
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
