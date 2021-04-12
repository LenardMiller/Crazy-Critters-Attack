package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static main.misc.Utilities.highlightedText;
import static processing.core.PConstants.CENTER;

public class Slider {

    private final PApplet P;
    private final String NAME;
    private final PVector POSITION;
    private final float MIN_OUTPUT;
    private final float MAX_OUTPUT;

    private static final int MAX_PROGRESS = 200;
    private int progress;

    public Slider(PApplet p, String name, PVector position, float start, float min, float max) {
        P = p;
        NAME = name;
        POSITION = position;
        MIN_OUTPUT = min;
        MAX_OUTPUT = max;

        progress = (int) PApplet.map(start, min, max, 0, 100);
    }

    public float main() {
        displaySlider();
        displayText();

        return PApplet.map(progress, 0, MAX_PROGRESS, MIN_OUTPUT, MAX_OUTPUT);
    }

    private void displayText() {
        highlightedText(P, NAME, new PVector(POSITION.x, POSITION.y - 25), new Color(200, 200, 200),
          new Color(100, 100, 100, 200), 24, CENTER);
    }

    private void displaySlider() {
        P.fill(100);
        P.strokeWeight(5);
        P.stroke(0);
        P.rectMode(CENTER);
        P.line(POSITION.x - (MAX_PROGRESS / 2f), POSITION.y, POSITION.x + (MAX_PROGRESS / 2f), POSITION.y);
        P.strokeWeight(2);
        P.rect(POSITION.x + MAX_PROGRESS - (MAX_PROGRESS / 2f), POSITION.y, 20, 20);
        P.strokeWeight(1);
        P.rectMode(PConstants.CORNER);
    }
}
