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

    /**
     * Creates buttons
     */
    private void build() {
        resumeButton = new MenuButton(P, P.width/2f, (P.height/2f) - 75, () -> {
            paused = !paused;
        });
        restartButton = new MenuButton(P, P.width/2f, P.height/2f - 25, () -> {
            paused = false;
            Game.reset(P);
        });
        levelSelectButton = new MenuButton(P, P.width/2f, (P.height/2f) + 25, () -> {
            Game.reset(P);
            paused = false;
            alive = true;
            LevelSelectGui.delay = 1;
            screen = Screen.LevelSelect;
        });
        settingsMenuButton = new MenuButton(P, P.width/2f, (P.height/2f) + 75, () -> {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        });
        exitButton = new MenuButton(P, P.width/2f, (P.height/2f) + 125, () -> {
            paused = false;
            P.exit();
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
        int offsetY = 7;
        if (alive) {
            resumeButton.main();
            if (won) P.text("Hide Menu [ESC]", resumeButton.position.x, resumeButton.position.y + offsetY);
            else P.text("Resume [ESC]", resumeButton.position.x, resumeButton.position.y + offsetY);
        }
        restartButton.main();
        P.text("Restart", restartButton.position.x, restartButton.position.y + offsetY);
        levelSelectButton.main();
        P.text("Level Select", levelSelectButton.position.x, levelSelectButton.position.y + offsetY);
        settingsMenuButton.main();
        P.text("Settings", settingsMenuButton.position.x, settingsMenuButton.position.y + offsetY);
        exitButton.main();
        P.text("Quit", exitButton.position.x, exitButton.position.y + offsetY);
    }
}
