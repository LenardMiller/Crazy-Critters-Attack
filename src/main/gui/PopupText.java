package main.gui;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static main.Main.paused;
import static main.Main.popupTexts;
import static main.misc.Utilities.incrementByTo;

public class PopupText {

    private final PApplet P;
    private final int SIZE;
    private final Color COLOR;
    private final PVector POSITION;
    private final String TEXT;

    private boolean firstPhase;
    private int alpha;
    private float displacement;
    private float movementSpeed;

    /**
     * A small piece of text that moves up to a stop and slowly disappears.
     * @param p the PApplet
     * @param size size of text
     * @param color color of text, RGB
     * @param position starting position of text
     * @param text what to display
     */
    public PopupText(PApplet p, int size, Color color, PVector position, String text) {
        P = p;
        SIZE = size;
        COLOR = color;
        POSITION = position;
        TEXT = text;

        firstPhase = true;
        alpha = 255;
        movementSpeed = 1;
    }

    public void main() {
        display();
        if (!paused) update();
    }

    private void display() {
        P.fill(COLOR.getRGB(), alpha);
        P.textAlign(PConstants.CENTER);
        P.textSize(SIZE);
        P.text(TEXT, POSITION.x, POSITION.y - (SIZE / 2f) - displacement);
    }

    private void update() {
        float delta = 0.05f;
        float targetSpeed = -0.25f;
        displacement += movementSpeed;
        if (firstPhase) {
            movementSpeed = incrementByTo(movementSpeed, delta, targetSpeed);
            if (movementSpeed == targetSpeed) firstPhase = false;
        } else {
            movementSpeed = incrementByTo(movementSpeed, delta / 2f, 0);
            alpha = (int) incrementByTo(alpha, 6, 0);
        }
        if (alpha == 0) popupTexts.remove(this);
    }
}
