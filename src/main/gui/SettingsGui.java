package main.gui;

import main.gui.guiObjects.MenuCheckbox;
import main.gui.guiObjects.MenuSlider;
import main.gui.guiObjects.buttons.MenuButton;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;
import static main.misc.Utilities.shadowedText;
import static processing.core.PConstants.CENTER;

public class SettingsGui {

    private static final int buffer = 150;

    private final PApplet P;
    private final boolean fullscreenWas;

    private MenuButton returnButton;
    private MenuSlider volumeSlider;
    private MenuCheckbox fullscreenCheck;
    private MenuCheckbox goreCheck;
    private MenuButton resetSettings;

    public static int delay;

    public SettingsGui(PApplet p) {
        P = p;
        fullscreenWas = fullscreen;
        build();
    }

    private void build() {
        volumeSlider = new MenuSlider(P, "Global Volume", new PVector(P.width / 2f, buffer + 100),
          0.01f, 0.25f, 1);
        fullscreenCheck = new MenuCheckbox(P, "Fullscreen*", new PVector((P.width / 2f - 100), buffer + 150));
        goreCheck = new MenuCheckbox(P, "Gore", new PVector((P.width / 2f - 100), buffer + 200));
        resetSettings = new MenuButton(P, P.width/2f, P.height - buffer - 50);
        returnButton = new MenuButton(P, P.width/2f, P.height - buffer);
    }

    public void main() {
        if (delay < 0) {
            display();
            checkInputs();
        }
        delay--;
    }

    private void checkInputs() {
        globalVolume = volumeSlider.main(globalVolume);
        fullscreen = fullscreenCheck.main(fullscreen);
        gore = goreCheck.main(gore);
        if (returnButton.isPressed()) {
            if (settings) closeSettingsMenu();
            else settings = true;
        } if (resetSettings.isPressed()) {
            globalVolume = 0.25f;
            fullscreen = true;
            gore = true;
        }
    }

    private void display() { //todo: keybinds page
        PVector position = new PVector(P.width / 2f, buffer);
        shadowedText(P, "Settings", position, new Color(255, 255, 255, 254),
          new Color(50, 50, 50, 254), 48, CENTER);

        //buttons
        P.fill(200, 0, 0, 254);
        P.textFont(largeFont);
        int offsetY = 7;
        if (fullscreenWas != fullscreen) P.text("Restart required", P.width / 2f, P.height - 250 + offsetY);
        P.textFont(mediumFont);
        P.fill(200, 254);
        returnButton.main();
        P.text("Return [ESC]", returnButton.position.x, returnButton.position.y + offsetY);
        resetSettings.main();
        P.text("Reset to Defaults", resetSettings.position.x, resetSettings.position.y + offsetY);
    }
}
