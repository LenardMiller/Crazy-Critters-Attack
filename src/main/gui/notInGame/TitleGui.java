package main.gui.notInGame;

import main.Main;
import main.gui.SettingsGui;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
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
        playButton = new MenuButton(p, p.width / 2f, p.height * 0.5f, "Play", () -> {
            transition(Screen.PlayOrLevelSelect, new PVector(1, 0));
        });
        settingsButton = new MenuButton(p, p.width /2f, p.height * 0.6f, "Settings", () -> {
            SettingsGui.delay = 1;
            if (settings) closeSettingsMenu();
            else settings = true;
        });
        exitButton = new MenuButton(p, p.width /2f, p.height * 0.7f, "Quit", () -> {
            transition(Screen.Exit, new PVector(0, 1));
        });
    }

    public void main() {
        playButton.main();
        exitButton.main();
        settingsButton.main();
    }
}
