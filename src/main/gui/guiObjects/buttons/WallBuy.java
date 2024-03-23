package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSound;

public class WallBuy extends Button {

    public boolean depressed;
    public int timer;

    public WallBuy(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 24);
        spriteIdle = animatedSprites.get("wallBuyBT")[Math.min(currentLevel, 4) * 3];
        spritePressed = animatedSprites.get("wallBuyBT")[(Math.min(currentLevel, 4) * 3) + 1];
        spriteHover = animatedSprites.get("wallBuyBT")[(Math.min(currentLevel, 4) * 3) + 2];
        sprite = spriteIdle;
        depressed = false;
    }

    @Override
    public void update(){
        timer++;
        if (active) {
            hover();
        }
    }

    /**
     * If hovered or depressed.
     */
    @Override
    public void hover() {
        if (boardMousePosition.x < position.x+size.x/2 && boardMousePosition.x > position.x-size.x/2 &&
                boardMousePosition.y < position.y+size.y/2 && boardMousePosition.y > position.y-size.y/2 && alive && !isPaused) {
            sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMouseReleasedPulse && timer > 20) {
                timer = 0;
                pressIn();
                sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (hand.displayInfo != null) sprite = spritePressed;
    }

    @Override
    public void pressIn() {
        depressed = !depressed;
        selection.name = "null";
        //if already holding, stop
        if (hand.held.equals("wall")) hand.setHeld("null");
            //if not, do
        else hand.setHeld("wall");
    }
}