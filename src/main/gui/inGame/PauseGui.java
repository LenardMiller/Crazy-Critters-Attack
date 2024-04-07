package main.gui.inGame;

import main.gui.SettingsGui;
import main.gui.guiObjects.buttons.MenuButton;
import main.gui.notInGame.LevelSelectGui;
import main.misc.Saver;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;
import static main.misc.Utilities.shadowedText;

public class PauseGui {

    private final PApplet p;

    public static MenuButton resumeButton;
    public static MenuButton restartButton;
    public static MenuButton levelSelectButton;
    public static MenuButton settingsMenuButton;
    public static MenuButton exitButton;
    public static MenuButton goBack1;
    public static MenuButton goBack5;
    public static MenuButton goBack10;

    /**
     * Creates the pause menu.
     * @param p the PApplet
     */
    public PauseGui(PApplet p) {
        this.p = p;
        build();
    }

    /** Creates buttons */
    private void build() {
        resumeButton = new MenuButton(p, p.width/2f, (p.height/2f) - 75, () ->
                isPaused = !isPaused);
        levelSelectButton = new MenuButton(p, p.width/2f, (p.height/2f) + 25, "Level Select", () -> {
            alive = true;
            LevelSelectGui.delay = 1;
            transition(Screen.LevelSelect, new PVector(-1, 0));
        });
        settingsMenuButton = new MenuButton(p, p.width/2f, (p.height/2f) - 25, "Settings", () -> {
            SettingsGui.delay = 1;
            if (isSettings) closeSettingsMenu();
            else isSettings = true;
        });
        restartButton = new MenuButton(p, p.width/2f, p.height/2f + 75, "Restart", () ->
                transition(Screen.Restart, new PVector(-1, 0)));
        exitButton = new MenuButton(p, p.width/2f, (p.height/2f) + 175, "Quit", () ->
                transition(Screen.Exit, new PVector(0, 1)));

        goBack1 = new MenuButton(p, p.width/2f - 67, p.height/2f + 125, 67, 42, "-1", () -> {
            Saver.updateSave(currentLevel, max((levels[currentLevel].currentWave - 1), 0));
            transition(Screen.LoadGame, new PVector(-1, 0));
        });
        goBack5 = new MenuButton(p, p.width/2f, p.height/2f + 125, 67, 42, "-5", () -> {
            Saver.updateSave(currentLevel, max((levels[currentLevel].currentWave - 5), 0));
            transition(Screen.LoadGame, new PVector(-1, 0));
        });
        goBack10 = new MenuButton(p, p.width/2f + 67, p.height/2f + 125, 67, 42, "-10", () -> {
            Saver.updateSave(currentLevel, max((levels[currentLevel].currentWave - 10), 0));
            transition(Screen.LoadGame, new PVector(-1, 0));
        });
    }

    public void update() {
        restartButton.update();
        levelSelectButton.update();
        settingsMenuButton.update();
        exitButton.update();
        resumeButton.update();
        if (!hasWon) {
            goBack1.update();
            goBack5.update();
            goBack10.update();
        }
    }

    public void display() {
        //big text
        PVector position = new PVector(p.width / 2f, 300);
        if (!alive) shadowedText(p, "Game Over", position, new Color(255, 0, 0, 254),
                new Color(50, 0, 0, 254), title, CENTER);
        else if (hasWon) shadowedText(p, "You Win!", position, new Color(255, 255, 0, 254),
                new Color(125, 50, 0, 254), title, CENTER);
        else shadowedText(p, "Paused", position, new Color(255, 255, 255, 254),
                    new Color(50, 50, 50, 254), title, CENTER);
        //buttons
        p.fill(200, 254);
        p.textFont(h4);
        if (alive) {
            resumeButton.display();
            if (hasWon) p.text("Hide Menu [ESC]", resumeButton.position.x, resumeButton.position.y + MenuButton.TEXT_Y_OFFSET);
            else p.text("Resume [ESC]", resumeButton.position.x, resumeButton.position.y + MenuButton.TEXT_Y_OFFSET);
        }
        restartButton.display();
        levelSelectButton.display();
        settingsMenuButton.display();
        exitButton.display();
        if (!hasWon) {
            goBack1.display();
            goBack5.display();
            goBack10.display();
        }
    }
}
