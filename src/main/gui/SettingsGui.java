package main.gui;

import main.gui.guiObjects.MenuCheckbox;
import main.gui.guiObjects.MenuSlider;
import main.gui.guiObjects.buttons.MenuButton;
import main.misc.Settings;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static processing.core.PConstants.CENTER;

public class SettingsGui {

    private static final int buffer = 150;

    private final PApplet p;

    private MenuButton returnButton;
    private MenuSlider volumeSlider;
    private MenuSlider ringResolutionSlider;
    private MenuCheckbox fullscreenCheck;
    private MenuCheckbox rendererCheck;
    private MenuCheckbox goreCheck;
    private MenuButton resetSettings;

    public static int delay;

    public SettingsGui(PApplet p) {
        this.p = p;
        build();
    }

    private void build() {
        volumeSlider = new MenuSlider(p, "Global Volume", new PVector(p.width / 2f, buffer + 100),
          0.01f, 0.25f, 1);
        ringResolutionSlider = new MenuSlider(p, "Shockwave Quality", new PVector(p.width / 2f, buffer + 150),
                8, 30, 50);

        int checkX = (int) (p.width / 2f - 100);
        fullscreenCheck = new MenuCheckbox(p, "Fullscreen*", new PVector(checkX, buffer + 200));
        rendererCheck = new MenuCheckbox(p, "Anti-aliasing*", new PVector(checkX, buffer + 250));
        goreCheck = new MenuCheckbox(p, "Gore", new PVector(checkX, buffer + 300));

        resetSettings = new MenuButton(p, p.width/2f, p.height - buffer - 50, "Reset to Defaults",
                () -> settings = new Settings(settings));
        returnButton = new MenuButton(p, p.width/2f, p.height - buffer, "Return [ESC]", () -> {
            if (isSettings) closeSettingsMenu();
            else isSettings = true;
        });
    }

    public void update() {
        if (delay < 0) {
            checkInputs();
            returnButton.update();
            resetSettings.update();
        }
        delay--;
    }

    private void checkInputs() {
        settings.setVolume(volumeSlider.update(settings.getVolume()));
        settings.setRingResolution((int) ringResolutionSlider.update(settings.getRingResolution()));
        settings.setFullscreen(fullscreenCheck.update(settings.isFullscreen()));
        settings.setUseOpenGL(rendererCheck.update(settings.isUseOpenGL()));
        settings.setHasGore(goreCheck.update(settings.isHasGore()));
    }

    public void display() {
        if (delay >= 0) return;
        PVector position = new PVector(p.width / 2f, buffer);
        shadowedText(p, "Settings", position, new Color(255, 255, 255, 254),
          new Color(50, 50, 50, 254), 48, CENTER);

        //buttons
        int offsetY = 7;
        if (settings.restartRequired) highlightedText(p, "Restart required",
          new PVector(p.width / 2f, p.height - 250 + offsetY), new Color(255, 0, 0, 254),
          new Color(50, 0, 0, 200), h2.getSize(), CENTER);
        p.textFont(h4);
        p.fill(200, 254);
        returnButton.display();
        resetSettings.display();

        //other inputs
        volumeSlider.display();
        ringResolutionSlider.display();
        fullscreenCheck.display(settings.isFullscreen());
        rendererCheck.display(settings.isUseOpenGL());
        goreCheck.display(settings.isHasGore());
    }
}
