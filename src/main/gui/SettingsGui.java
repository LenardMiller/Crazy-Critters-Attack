package main.gui;

import main.gui.guiObjects.buttons.SettingsMenuScreen;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.misc.Utilities.shadowedText;
import static processing.core.PConstants.CENTER;

public class SettingsGui {

    private final PApplet P;

    private SettingsMenuScreen returnButton;

    public SettingsGui(PApplet p) {
        P = p;
        build();
    }

    public void display() {
        shadowedText(P, "Settings", new PVector(P.width / 2f, 100), new Color(255, 255, 255),
          new Color(50, 50, 50), 48, CENTER);
        returnButton.main();
    }

    private void build() {
        returnButton = new SettingsMenuScreen(P, P.width / 2f, P.height / 2f);
    }
}
