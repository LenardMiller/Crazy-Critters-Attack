package main.gui.notInGame;

import main.Game;
import main.gui.SettingsGui;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;

public class LevelSelectGui {

    private final PApplet P;

    private static MenuButton[] levelSelectButtons;
    private static MenuButton settingsButton;
    private static MenuButton goToTitle;

    /** Exists so LevelSelectScreen and SelectLevel aren't pressed at the same time */
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
        settingsButton = new MenuButton(P, P.width/2f, P.height-100 - 50, () -> {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        });
        goToTitle = new MenuButton(P, P.width/2f, P.height-100, () -> {
            screen = Screen.Title;
        });
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
        for (int i = 0; i < levelSelectButtons.length; i++) {
            if (levelSelectButtons[i].isPressed()) {
                currentLevel = i;
                Game.reset(P);
                paused = false;
                alive = true;
                screen = Screen.InGame;
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
            levelSelectButtons[i].display();
            if (delay < 0) levelSelectButtons[i].hover();
            P.text("Level " + (i+1), levelSelectButtons[i].position.x, levelSelectButtons[i].position.y + offsetY);
        }
        settingsButton.main();
        P.text("Settings", settingsButton.position.x, settingsButton.position.y + offsetY);
        goToTitle.main();
        P.text("Back to Title", goToTitle.position.x, goToTitle.position.y + offsetY);
    }
}
