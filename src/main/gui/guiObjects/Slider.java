package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Slider {

    private final PApplet P;
    private final String NAME;
    private final PVector POSITION;
    private final float MIN_OUTPUT;
    private final float MAX_OUTPUT;

    private static final int MAX_PROGRESS = 100;
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
        P.fill(50);
        P.strokeWeight(5);
        P.stroke(255);
        P.rectMode(PConstants.CENTER);
        P.line(POSITION.x - (MAX_PROGRESS / 2f), POSITION.y, POSITION.x + (MAX_PROGRESS / 2f), POSITION.y);
        P.rect(POSITION.x + MAX_PROGRESS - (MAX_PROGRESS / 2f), POSITION.y, 10, 10);
        P.rectMode(PConstants.CORNER);
        P.strokeWeight(1);
        return PApplet.map(progress, 0, MAX_PROGRESS, MIN_OUTPUT, MAX_OUTPUT);
    }
}
