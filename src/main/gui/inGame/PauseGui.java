package main.gui.inGame;

import main.Main;
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

    public static MenuButton resumeGame;
    public static MenuButton restartLevel;
    public static MenuButton levelSelect;
    public static MenuButton settingsMenuScreen;
    public static MenuButton exitGame;

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
        resumeGame = new MenuButton(P, P.width/2f, (P.height/2f) - 75);
        restartLevel = new MenuButton(P, P.width/2f, P.height/2f - 25);
        levelSelect = new MenuButton(P, P.width/2f, (P.height/2f) + 25);
        settingsMenuScreen = new MenuButton(P, P.width/2f, (P.height/2f) + 75);
        exitGame = new MenuButton(P, P.width/2f, (P.height/2f) + 125);
    }

    public void main() {
        display();
        checkButtonsPressed();
    }

    private void display() {
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
            resumeGame.main();
            if (won) P.text("Hide Menu [ESC]", resumeGame.position.x, resumeGame.position.y + offsetY);
            else P.text("Resume [ESC]", resumeGame.position.x, resumeGame.position.y + offsetY);
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

    private void checkButtonsPressed() {
        if (resumeGame.isPressed()) {
            paused = !paused;
        } if (restartLevel.isPressed()) {
            paused = false;
            Main.resetGame(P);
        } if (levelSelect.isPressed()) {
            Main.resetGame(P);
            paused = false;
            alive = true;
            LevelSelectGui.delay = 1;
            screen = 1;
        } if (settingsMenuScreen.isPressed()) {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        } if (exitGame.isPressed()) {
            paused = false;
            P.exit();
        }
    }
}
