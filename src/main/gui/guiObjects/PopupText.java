package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.highlightedText;
import static main.misc.Utilities.incrementByTo;
import static processing.core.PApplet.nfc;
import static processing.core.PConstants.CENTER;

public class PopupText {

    private final PApplet p;
    private final PFont font;
    private final Color textColor;
    private final Color highlightColor;
    private final PVector position;
    private final String text;

    private boolean firstPhase;
    private int alpha;
    private float displacement;
    private float movementSpeed;

    /**
     * A small piece of text that moves up to a stop and slowly disappears.
     * @param p the PApplet
     * @param font text font
     * @param textColor color of text, RGB
     * @param highlightColor color of highlight
     * @param position starting position of text
     * @param text what to display
     */
    public PopupText(PApplet p, PFont font, Color textColor, Color highlightColor, PVector position, String text) {
        this.p = p;
        this.font = font;
        this.textColor = textColor;
        this.highlightColor = highlightColor;
        this.position = position;
        this.text = text;

        firstPhase = true;
        alpha = 254;
        movementSpeed = 1;
    }

    /**
     * Money popup, 12 size, yellow, +$amount
     * @param p the PApplet
     * @param position where to spawn
     * @param amount what amount of money gained
     */
    public PopupText(PApplet p, PVector position, int amount) {
        this(p, monoMedium,
                new Color(255, 255, 0, 254),
                new Color(100, 50, 0, 150),
                position, "+$" + nfc(amount));
    }

    public void display() {
        int a = alpha;
        Color textColor = new Color(this.textColor.getRed(), this.textColor.getGreen(), this.textColor.getBlue(), a);
        if (alpha > highlightColor.getAlpha()) a = highlightColor.getAlpha();
        Color highlightColor = new Color(this.highlightColor.getRed(), this.highlightColor.getGreen(), this.highlightColor.getBlue(), a);
        highlightedText(p,
                text,
                new PVector((int) position.x, (int) (position.y - (font.getSize() / 2f) - displacement)),
                textColor, highlightColor, font, CENTER);
    }

    public void update() {
        if (isPaused) return;
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
