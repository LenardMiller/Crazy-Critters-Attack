package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.inputHandler;
import static main.Main.sounds;
import static main.misc.Utilities.highlightedText;
import static main.misc.Utilities.playSound;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

public class Slider {

    private static final int MAX_PROGRESS = 200;
    private static final int BOX_SIZE = 20;
    private static final SoundFile CLICK_IN = sounds.get("clickIn");
    private static final SoundFile CLICK_OUT = sounds.get("clickOut");

    private final PApplet P;
    private final String NAME;
    private final PVector POSITION;
    private final float MIN_OUTPUT;
    private final float MAX_OUTPUT;

    private int progress;
    private Color fillColor;
    private Color borderColor;

    public Slider(PApplet p, String name, PVector position, float start, float min, float max) {
        P = p;
        NAME = name;
        POSITION = position;
        MIN_OUTPUT = min;
        MAX_OUTPUT = max;

        progress = (int) PApplet.map(start, min, max, 0, MAX_PROGRESS);
        fillColor = new Color(100, 100, 100);
        borderColor = new Color(0);
    }

    public float main() {
        displaySlider();
        displayText();
        hover();

        return PApplet.map(progress, 0, MAX_PROGRESS, MIN_OUTPUT, MAX_OUTPUT);
    }

    private void hover() {
        if (mouseNear()) {
            if (inputHandler.leftMousePressedPulse) playSound(CLICK_IN, 1, 1);
            if (P.mousePressed && P.mouseButton == LEFT) {
                fillColor = new Color(50, 50, 50);
            } else fillColor = new Color(100, 100, 100);
            borderColor = new Color(200, 200, 200);
        } else {
            fillColor = new Color(100, 100, 100);
            borderColor = new Color(0, 0, 0);
        }
    }

    private boolean mouseNear() {
        boolean matchX = P.mouseX < POSITION.x+BOX_SIZE/2f+boxX()+2 && P.mouseX > POSITION.x-BOX_SIZE/2f+boxX();
        boolean matchY = P.mouseY < POSITION.y+BOX_SIZE/2f+4 && P.mouseY > POSITION.y-BOX_SIZE/2f+2;
        return matchX && matchY;
    }

    private void displayText() {
        highlightedText(P, NAME, new PVector(POSITION.x, POSITION.y - 25), new Color(200, 200, 200),
          new Color(100, 100, 100, 200), 24, CENTER);
    }

    private float boxX() {
        return progress - (MAX_PROGRESS / 2f);
    }

    private void displaySlider() {
        P.strokeWeight(5);
        P.stroke(0);
        P.rectMode(CENTER);
        P.line(POSITION.x - (MAX_PROGRESS / 2f), POSITION.y, POSITION.x + (MAX_PROGRESS / 2f), POSITION.y);
        P.strokeWeight(2);
        P.fill(fillColor.getRGB());
        P.stroke(borderColor.getRGB());
        P.rect(POSITION.x + boxX(), POSITION.y, BOX_SIZE, BOX_SIZE);
        P.strokeWeight(1);
        P.rectMode(PConstants.CORNER);
    }
}
