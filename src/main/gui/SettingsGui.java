package main.gui;

import main.gui.guiObjects.buttons.SettingsMenuScreen;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.mediumFont;
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
        PVector position = new PVector(P.width/2f, 300);
        shadowedText(P, "Settings", position, new Color(255, 255, 255),
          new Color(50, 50, 50), 48, CENTER);

        //buttons
        P.fill(200);
        P.textFont(mediumFont);
        int offsetY = 7;
        returnButton.main();
        P.text("Return [ESC]", returnButton.position.x, returnButton.position.y + offsetY);
    }

    private void build() {
        returnButton = new SettingsMenuScreen(P, P.width/2f, (P.height/2f) + 175);
    }
}
