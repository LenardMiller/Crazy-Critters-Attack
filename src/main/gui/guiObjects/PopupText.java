package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.paused;
import static main.Main.popupTexts;
import static main.misc.Utilities.highlightedText;
import static main.misc.Utilities.incrementByTo;
import static processing.core.PApplet.nfc;
import static processing.core.PConstants.CENTER;

public class PopupText {

    private final PApplet P;
    private final int SIZE;
    private final Color TEXT_COLOR;
    private final Color HIGHLIGHT_COLOR;
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
     * @param textColor color of text, RGB
     * @param highlightColor color of highlight
     * @param position starting position of text
     * @param text what to display
     */
    public PopupText(PApplet p, int size, Color textColor, Color highlightColor, PVector position, String text) {
        P = p;
        SIZE = size;
        TEXT_COLOR = textColor;
        HIGHLIGHT_COLOR = highlightColor;
        POSITION = position;
        TEXT = text;

        firstPhase = true;
        alpha = 255;
        movementSpeed = 1;
    }

    /**
     * Money popup, 12 size, yellow, +$amount
     * @param p the PApplet
     * @param position where to spawn
     * @param amount what amount of money gained
     */
    public PopupText(PApplet p, PVector position, int amount) {
        this(p, 16, new Color(255, 255, 0, 254), new Color(100, 50, 0, 150), position, "+$" + nfc(amount));
    }

    public void main() {
        display();
        if (!paused) update();
    }

    private void display() {
        int a = alpha;
        Color textColor = new Color(TEXT_COLOR.getRed(), TEXT_COLOR.getGreen(), TEXT_COLOR.getBlue(), a);
        if (alpha > HIGHLIGHT_COLOR.getAlpha()) a = HIGHLIGHT_COLOR.getAlpha();
        Color highlightColor = new Color(HIGHLIGHT_COLOR.getRed(), HIGHLIGHT_COLOR.getGreen(), HIGHLIGHT_COLOR.getBlue(), a);
        highlightedText(P, TEXT, new PVector(POSITION.x, POSITION.y - (SIZE / 2f) - displacement), textColor, highlightColor, SIZE, CENTER);
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
