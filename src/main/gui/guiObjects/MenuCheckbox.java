package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.inputHandler;
import static main.Main.sounds;
import static main.misc.Utilities.highlightedText;
import static main.misc.Utilities.playSound;
import static processing.core.PConstants.*;

public class MenuCheckbox {

    private static final int BOX_SIZE = 25;
    private static final SoundFile CLICK_IN = sounds.get("clickIn");
    private static final SoundFile CLICK_OUT = sounds.get("clickOut");

    private final PApplet P;
    private final PVector POSITION;
    private final String NAME;

    private Color borderColor;

    /**
     * A nice little check box for booleans.
     * @param p the PApplet
     * @param name text to display right of it
     * @param position where it is, from center of box
     */
    public MenuCheckbox(PApplet p, String name, PVector position) {
        P = p;
        POSITION = position;
        NAME = name;
    }

    /**
     * @param input what value to change
     * @return new value
     */
    public boolean main(boolean input) {
        displayText();
        if (clicked()) input = !input;
        displayBox(input);
        return input;
    }

    private boolean clicked() {
        boolean clicked = false;
        if (mouseNear()) {
            if (inputHandler.leftMousePressedPulse) playSound(CLICK_IN, 1, 1);
            if (inputHandler.leftMouseReleasedPulse) {
                playSound(CLICK_OUT, 1, 1);
                clicked = true;
            } if (P.mousePressed && P.mouseButton == LEFT) borderColor = new Color(50, 50, 50);
            else borderColor = new Color(200, 200, 200);
        } else borderColor = new Color(100, 100, 100);
        return clicked;
    }

    private boolean mouseNear() {
        boolean matchX = P.mouseX < POSITION.x+BOX_SIZE/2f && P.mouseX > POSITION.x-BOX_SIZE/2f;
        boolean matchY = P.mouseY < POSITION.y+BOX_SIZE/2f && P.mouseY > POSITION.y-BOX_SIZE/2f;
        return matchX && matchY;
    }

    public void displayText() {
        highlightedText(P, NAME, new PVector(POSITION.x + BOX_SIZE, POSITION.y + 8),
          new Color(200, 200, 200, 254), new Color(100, 100, 100, 200), 24, LEFT);
    }

    private void displayBox(boolean checked) {
        P.strokeWeight(4);
        if (checked) P.fill(175);
        else P.fill(50, 125);
        P.stroke(borderColor.getRGB());
        P.rectMode(CENTER);

        P.rect(POSITION.x, POSITION.y, BOX_SIZE, BOX_SIZE);

        P.strokeWeight(1);
        P.rectMode(CORNER);
    }
}
