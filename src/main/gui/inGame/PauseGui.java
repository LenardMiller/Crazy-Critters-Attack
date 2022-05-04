package main.gui.inGame;

import main.Game;
import main.gui.notInGame.LevelSelectGui;
import main.gui.SettingsGui;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;
import static main.misc.Utilities.shadowedText;

public class PauseGui {

    private final PApplet P;

    public static MenuButton resumeButton;
    public static MenuButton restartButton;
    public static MenuButton levelSelectButton;
    public static MenuButton settingsMenuButton;
    public static MenuButton exitButton;

    /**
     * Creates the pause menu.
     * @param p the PApplet
     */
    public PauseGui(PApplet p) {
        P = p;
        build();
    }

    /** Creates buttons */
    private void build() {
        resumeButton = new MenuButton(P, P.width/2f, (P.height/2f) - 75, () -> {
            paused = !paused;
        });
        restartButton = new MenuButton(P, P.width/2f, P.height/2f - 25, "Restart", () -> {
            transition(Screen.Restart, new PVector(-1, 0));
        });
        levelSelectButton = new MenuButton(P, P.width/2f, (P.height/2f) + 25, "Level Select", () -> {
            alive = true;
            LevelSelectGui.delay = 1;
            transition(Screen.LevelSelect, new PVector(-1, 0));
        });
        settingsMenuButton = new MenuButton(P, P.width/2f, (P.height/2f) + 75, "Settings", () -> {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        });
        exitButton = new MenuButton(P, P.width/2f, (P.height/2f) + 125, "Quit", () -> {
            transition(Screen.Exit, new PVector(0, 1));
        });
    }

    public void main() {
        //big text
        PVector position = new PVector(P.width / 2f, 300);
        if (!alive) shadowedText(P, "Game Over", position, new Color(255, 0, 0, 254),
                new Color(50, 0, 0, 254), veryLargeFont, CENTER);
        else if (won) shadowedText(P, "You Win!", position, new Color(255, 255, 0, 254),
                new Color(125, 50, 0, 254), veryLargeFont, CENTER);
        else shadowedText(P, "Paused", position, new Color(255, 255, 255, 254),
                    new Color(50, 50, 50, 254), veryLargeFont, CENTER);
        //buttons
        P.fill(200, 254);
        P.textFont(mediumFont);
        if (alive) {
            resumeButton.main();
            if (won) P.text("Hide Menu [ESC]", resumeButton.position.x, resumeButton.position.y + MenuButton.TEXT_Y_OFFSET);
            else P.text("Resume [ESC]", resumeButton.position.x, resumeButton.position.y + MenuButton.TEXT_Y_OFFSET);
        }
        restartButton.main();
        levelSelectButton.main();
        settingsMenuButton.main();
        exitButton.main();
    }
}
