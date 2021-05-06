package main.gui;

import main.gui.guiObjects.Slider;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;
import static main.misc.Utilities.shadowedText;
import static processing.core.PConstants.CENTER;

public class SettingsGui {

    private final PApplet P;

    private MenuButton returnButton;
    private Slider volumeSlider;
    private MenuButton resetSettings;

    public SettingsGui(PApplet p) {
        P = p;
        build();
    }

    private void build() {
        returnButton = new MenuButton(P, P.width/2f, (P.height/2f) + 175);
        volumeSlider = new Slider(P, "Global Volume", new PVector(P.width / 2f, P.height / 2f),
          globalVolume, 0.01f, 0.25f, 1);
        resetSettings = new MenuButton(P, P.width/2f, (P.height/2f) + 125);
    }

    public void main() {
        display();
        checkInputs();
    }

    private void checkInputs() {
        globalVolume = volumeSlider.main(globalVolume);
        if (returnButton.isPressed()) {
            if (settings) closeSettingsMenu();
            else settings = true;
        } if (resetSettings.isPressed()) {
            globalVolume = 0.25f;
            fullscreen = true;
        }
    }

    private void display() { //todo: keybinds page, todo: add fullscreen
        PVector position = new PVector(P.width / 2f, 300);
        shadowedText(P, "Settings", position, new Color(255, 255, 255, 254),
          new Color(50, 50, 50, 254), 48, CENTER);

        //buttons
        P.fill(200, 254);
        P.textFont(mediumFont);
        int offsetY = 7;
        returnButton.main();
        P.text("Return [ESC]", returnButton.position.x, returnButton.position.y + offsetY);
        resetSettings.main();
        P.text("Reset to Defaults", resetSettings.position.x, resetSettings.position.y + offsetY);
    }
}
