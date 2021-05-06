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
    private final float DEFAULT;
    private final float MIN_OUTPUT;
    private final float MAX_OUTPUT;

    private boolean held;
    private int progress;
    private Color fillColor;
    private Color borderColor;

    /**
     * A nice little slider to control a value.
     * @param p the PApplet
     * @param name text to display above
     * @param position where is it?
     * @param start what the value starts as and where the middle is
     * @param min minimum output
     * @param max maximum output
     */
    public Slider(PApplet p, String name, PVector position, float start, float min, float max) {
        P = p;
        NAME = name;
        POSITION = position;
        DEFAULT = start;
        MIN_OUTPUT = min;
        MAX_OUTPUT = max;

        progress = MAX_PROGRESS / 2;
        fillColor = new Color(100, 100, 100);
        borderColor = new Color(0);
    }

    /**
     * @param input what value to change
     * @return new value
     */
    public float main(float input) {
        //braces have to be here, dunno why
        if (input == DEFAULT) {
            progress = MAX_PROGRESS / 2;
        } if (input < DEFAULT) {
            progress = (int) PApplet.map(input, MIN_OUTPUT, DEFAULT, 0, MAX_PROGRESS / 2f);
        } if (input > DEFAULT) {
            progress = (int) PApplet.map(input, DEFAULT, MAX_OUTPUT, MAX_PROGRESS / 2f, MAX_PROGRESS);
        }

        displaySlider();
        displayText();
        hover();

        if (held) {
            if (progress == MAX_PROGRESS / 2) return DEFAULT;
            if (progress < MAX_PROGRESS / 2) return PApplet.map(progress, 0, MAX_PROGRESS / 2f, MIN_OUTPUT, DEFAULT);
            return PApplet.map(progress, MAX_PROGRESS / 2f, MAX_PROGRESS, DEFAULT, MAX_OUTPUT);
        } return input;
    }

    private void hover() {
        if (mouseNear() || held) {
            if (inputHandler.leftMousePressedPulse) playSound(CLICK_IN, 1, 1);
            if (held && inputHandler.leftMouseReleasedPulse) playSound(CLICK_OUT, 1, 1);
            if (P.mousePressed && P.mouseButton == LEFT) {
                fillColor = new Color(50, 50, 50);
                progress = updateProgressBasedOnMouseX();
                held = true;
            } else {
                fillColor = new Color(100, 100, 100);
                held = false;
            } borderColor = new Color(200, 200, 200);
        } else {
            fillColor = new Color(100, 100, 100);
            borderColor = new Color(0, 0, 0);
        }
    }

    private int updateProgressBasedOnMouseX() {
        int scaledMouseX = (int) (P.mouseX - POSITION.x + MAX_PROGRESS/2);
        if (scaledMouseX < 0) return 0;
        return Math.min(scaledMouseX, MAX_PROGRESS);
    }

    private boolean mouseNear() {
        boolean matchX = P.mouseX < POSITION.x+BOX_SIZE/2f+boxX()+2 && P.mouseX > POSITION.x-BOX_SIZE/2f+boxX();
        boolean matchY = P.mouseY < POSITION.y+BOX_SIZE/2f+4 && P.mouseY > POSITION.y-BOX_SIZE/2f+2;
        return matchX && matchY;
    }

    private void displayText() {
        highlightedText(P, NAME, new PVector(POSITION.x, POSITION.y - 25), new Color(200, 200, 200, 254),
          new Color(100, 100, 100, 200), 24, CENTER);
    }

    private float boxX() {
        return progress - (MAX_PROGRESS / 2f);
    }

    private void displaySlider() {
        P.strokeWeight(5);
        P.rectMode(CENTER);
        P.fill(100, 200);
        P.rect(POSITION.x, POSITION.y, MAX_PROGRESS + BOX_SIZE, BOX_SIZE);
        P.stroke(0);
        P.line(POSITION.x - (MAX_PROGRESS / 2f), POSITION.y, POSITION.x + (MAX_PROGRESS / 2f), POSITION.y);
        P.strokeWeight(2);
        P.fill(fillColor.getRGB());
        P.stroke(borderColor.getRGB());
        P.rect(POSITION.x + boxX(), POSITION.y, BOX_SIZE, BOX_SIZE);
        P.strokeWeight(1);
        P.rectMode(PConstants.CORNER);
    }
}
