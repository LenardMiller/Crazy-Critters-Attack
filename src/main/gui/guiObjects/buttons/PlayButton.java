package main.gui.guiObjects.buttons;

import main.Main;
import main.levelStructure.Level;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

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
        if (!isPlaying || levels[currentLevel].canBeSkipped()) hover();
        else sprite = SPRITE_GREY;
    }

    public void display() {
        Level currentLevel = levels[Main.currentLevel];

        if (isPlaying && !currentLevel.canBeSkipped()) {
            sprite = SPRITE_GREY;
            p.fill(100,100,100, 254);
        } else {
            p.fill(20,20,50, 254);
        }
        p.image(sprite,position.x - size.x / 2,position.y);
        p.textAlign(CENTER);
        p.textFont(h2);
        p.text(isPlaying ? "Send Early" : "Start Waves",position.x, position.y + 30);
        p.textFont(monoMedium);
        if (isPlaying && currentLevel.canBeSkipped())
            p.text("+$" + nfc(currentLevel.waves[currentLevel.currentWave].getReward()),
                    position.x, position.y + size.y / 2f - 15);
    }

    @Override
    public void pressIn() {
        if (p.frameCount <= timer) return;
        Level level = levels[currentLevel];
        if (!isPlaying) {
            isPlaying = true;
            level.currentWave = 0;
        } else {
            level.advance();
        }
    }
}