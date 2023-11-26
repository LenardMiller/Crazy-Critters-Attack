package main.gui.guiObjects.buttons;

import main.levelStructure.Level;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class PlayButton extends Button {

    private final PImage SPRITE_GREY;

    private int timer;

    public PlayButton(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 250);
        spritePressed = animatedSprites.get("playBT")[0]; //in
        spriteIdle = animatedSprites.get("playBT")[1]; //out
        spriteHover = animatedSprites.get("playBT")[2]; //hover
        SPRITE_GREY = animatedSprites.get("playBT")[3]; //grey
        sprite = spriteIdle;
    }

    @Override
    public void update() {
        if (!playingLevel) hover();
        else sprite = SPRITE_GREY;
    }

    public void display() {
        p.fill(20,20,50, 254);
        p.image(sprite,position.x - size.x / 2,position.y);
        p.textAlign(CENTER);
        p.textFont(largeFont);
        p.text("Play",position.x, position.y + 30);
        p.textFont(mediumFont);
        p.text("[SPACE]", position.x, position.y + size.y / 2f - 12);
    }

    @Override
    public void pressIn() {
        if (p.frameCount > timer) {
            playingLevel = true;
            Level level = levels[currentLevel];
            level.currentWave = 0;
        }
    }
}