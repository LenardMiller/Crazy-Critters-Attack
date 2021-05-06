package main.gui;

import main.Main;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;

public class LevelSelectGui {

    private final PApplet P;

    private static MenuButton[] levelSelectButtons;
    private static MenuButton settingsMenu;
    private static MenuButton exitGame;

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

    private void build() {
        levelSelectButtons = new MenuButton[levels.length];
        settingsMenu = new MenuButton(P, P.width/2f, P.height-100 - 50);
        exitGame = new MenuButton(P, P.width/2f, P.height-100);
        float factor = (levelSelectButtons.length/2f) - 0.5f;
        for (int i = 0; i < levelSelectButtons.length; i++) {
            levelSelectButtons[i] = new MenuButton(P, P.width/2f, P.height/2f + (i-factor)*50);
        }
    }

    public void main() {
        display();
        checkButtonsPressed();
    }

    private void checkButtonsPressed() {
        if (settingsMenu.isPressed()) {
            if (settings) closeSettingsMenu();
            else settings = true;
        } if (exitGame.isPressed()) {
            paused = false;
            P.exit();
        }
        for (int i = 0; i < levelSelectButtons.length; i++) {
            if (levelSelectButtons[i].isPressed()) {
                currentLevel = i;
                Main.resetGame(P);
                paused = false;
                alive = true;
                screen = 0;
            }
        }
    }

    private void display() {
        delay--;
        //big text
        P.fill(255, 254);
        P.textFont(veryLargeFont);
        P.textAlign(P.CENTER);
        P.text("Level Select [wip]", P.width/2f, 300);
        //buttons
        P.fill(200, 254);
        P.textFont(mediumFont);
        int offsetY = 7;
        for (int i = 0; i < levelSelectButtons.length; i++) {
            levelSelectButtons[i].main();
            P.text("Level " + (i+1), levelSelectButtons[i].position.x, levelSelectButtons[i].position.y + offsetY);
        }
        settingsMenu.main();
        P.text("Settings", settingsMenu.position.x, settingsMenu.position.y + offsetY);
        exitGame.main();
        P.text("Quit", exitGame.position.x, exitGame.position.y + offsetY);
    }
}
