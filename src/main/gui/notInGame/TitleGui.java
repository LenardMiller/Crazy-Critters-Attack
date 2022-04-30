package main.gui.notInGame;

import main.Main;
import main.gui.SettingsGui;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;

import static main.Main.paused;
import static main.Main.settings;
import static main.misc.Utilities.closeSettingsMenu;

public class TitleGui {

    private final PApplet p;

    private static MenuButton playButton;
    private static MenuButton exitButton;
    private static MenuButton settingsButton;

    public TitleGui(PApplet p) {
        this.p = p;
        build();
    }

    private void build() {
        playButton = new MenuButton(p, p.width / 2f, p.height * 0.5f);
        settingsButton = new MenuButton(p, p.width /2f, p.height * 0.6f);
        exitButton = new MenuButton(p, p.width /2f, p.height * 0.7f);
    }

    public void main() {
        display();
        checkButtonsPressed();
    }

    private void checkButtonsPressed() {
        if (settingsButton.isPressed()) {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        } if (exitButton.isPressed()) {
            paused = false;
            p.exit();
        } if (playButton.isPressed()) {
            Main.screen = Main.Screen.LevelSelect;
        }
    }

    private void display() {
        playButton.main();
        exitButton.main();
        settingsButton.main();
    }
}
