package main.gui;

import main.gui.guiObjects.buttons.ExitGame;
import main.gui.guiObjects.buttons.SelectLevel;
import processing.core.PApplet;

import static main.Main.*;

public class LevelSelectGui {

    private final PApplet P;

    private static SelectLevel[] levelSelectButtons;
    private static ExitGame exitGame;

    /**
     * Exists so LevelSelectScreen and SelectLevel aren't pressed at the same time
     */
    public static int delay;

    /**
     * Creates the level select screen.
     * @param p the PApplet
     */
    public LevelSelectGui(PApplet p) {
        P = p;
        build();
    }

    public void display() {
        delay--;
        //big text
        P.fill(255);
        P.textFont(veryLargeFont);
        P.textAlign(P.CENTER);
        P.text("Level Select [wip]", P.width/2f, 300);
        //buttons
        P.fill(200);
        P.textFont(mediumFont);
        int offsetY = 7;
        for (int i = 0; i < levelSelectButtons.length; i++) {
            levelSelectButtons[i].main();
            P.text(i, levelSelectButtons[i].position.x, levelSelectButtons[i].position.y + offsetY);
        }
        exitGame.main();
        P.text("Quit (esc)", exitGame.position.x, exitGame.position.y + offsetY);
    }

    private void build() {
        levelSelectButtons = new SelectLevel[levels.length];
        exitGame = new ExitGame(P, P.width/2f, P.height-100, "null", true);

        float factor = (levelSelectButtons.length/2f) - 0.5f;
        for (int i = 0; i < levelSelectButtons.length; i++) {
            levelSelectButtons[i] = new SelectLevel(P, P.width/2f, P.height/2f + (i-factor)*50, "null", true, i);
        }
    }
}
