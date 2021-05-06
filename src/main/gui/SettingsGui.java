package main.gui;

import main.gui.guiObjects.Slider;
import main.gui.guiObjects.buttons.ResetSettings;
import main.gui.guiObjects.buttons.SettingsMenuScreen;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.globalVolume;
import static main.Main.mediumFont;
import static main.misc.Utilities.shadowedText;
import static processing.core.PConstants.CENTER;

public class SettingsGui {

    private final PApplet P;

    private SettingsMenuScreen returnButton;
    private Slider volumeSlider;
    private ResetSettings resetSettings;

    public SettingsGui(PApplet p) {
        P = p;
        build();
    }

    public void display() { //todo: keybinds page
        PVector position = new PVector(P.width/2f, 300);
        shadowedText(P, "Settings", position, new Color(255, 255, 255, 254),
          new Color(50, 50, 50, 254), 48, CENTER);

        //buttons
        globalVolume = volumeSlider.main(globalVolume);
        P.fill(200, 254);
        P.textFont(mediumFont);
        int offsetY = 7;
        returnButton.main();
        P.text("Return [ESC]", returnButton.position.x, returnButton.position.y + offsetY);
        resetSettings.main();
        P.text("Reset to Defaults", resetSettings.position.x, resetSettings.position.y + offsetY);
    }

    private void build() {
        returnButton = new SettingsMenuScreen(P, P.width/2f, (P.height/2f) + 175);
        volumeSlider = new Slider(P, "Global Volume", new PVector(P.width / 2f, P.height / 2f), globalVolume, 0.01f, 1);
        resetSettings = new ResetSettings(P, P.width/2f, (P.height/2f) + 125);
    }
}
