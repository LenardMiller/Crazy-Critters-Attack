package main.gui;

import main.gui.guiObjects.buttons.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.shadowedText;

public class PauseGui {

    private final PApplet P;

    public static ResumeGame resumeGame;
    public static RestartLevel restartLevel;
    public static LevelSelectScreen levelSelect;
    public static SettingsMenuScreen settingsMenuScreen;
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
        PVector position = new PVector(P.width/2f, 300);
        if (!alive) shadowedText(P, "Game Over", position, new Color(255, 0, 0),
                new Color(50, 0, 0), 48, CENTER);
        else if (won) shadowedText(P, "You Win!", position, new Color(255, 255, 0),
                new Color(125, 50, 0), 48, CENTER);
        else shadowedText(P, "Paused", position, new Color(255, 255, 255),
                    new Color(50, 50, 50), 48, CENTER);
        //buttons
        P.fill(200);
        P.textFont(mediumFont);
        int offsetY = 7;
        if (alive) {
            resumeGame.main();
            if (won) P.text("Hide Menu (ESC)", resumeGame.position.x, resumeGame.position.y + offsetY);
            else P.text("Resume (ESC)", resumeGame.position.x, resumeGame.position.y + offsetY);
        }
        restartLevel.main();
        P.text("Restart", restartLevel.position.x, restartLevel.position.y + offsetY);
        levelSelect.main();
        P.text("Level Select", levelSelect.position.x, levelSelect.position.y + offsetY);
        settingsMenuScreen.main();
        P.text("Settings", settingsMenuScreen.position.x, settingsMenuScreen.position.y + offsetY);
        exitGame.main();
        P.text("Quit", exitGame.position.x, exitGame.position.y + offsetY);
    }

    /**
     * Creates buttons
     */
    private void build() {
        resumeGame = new ResumeGame(P, P.width/2f, (P.height/2f) - 75);
        restartLevel = new RestartLevel(P, P.width/2f, P.height/2f - 25);
        levelSelect = new LevelSelectScreen(P, P.width/2f, (P.height/2f) + 25);
        settingsMenuScreen = new SettingsMenuScreen(P, P.width/2f, (P.height/2f) + 75);
        exitGame = new ExitGame(P, P.width/2f, (P.height/2f) + 125);
    }
}
