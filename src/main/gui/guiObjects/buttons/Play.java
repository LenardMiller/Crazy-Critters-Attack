package main.gui.guiObjects.buttons;

import main.levelStructure.Level;
import main.levelStructure.Wave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class Play extends Button {

    private PImage spriteGrey;
    private int timer;

    public Play(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 125);
        spritePressed = spritesAnimH.get("playBT")[0]; //in
        spriteIdle = spritesAnimH.get("playBT")[1]; //out
        spriteHover = spritesAnimH.get("playBT")[2]; //hover
        spriteGrey = spritesAnimH.get("playBT")[3]; //grey
        sprite = spriteIdle;
    }

    public void main() {
        if (active && selection.name.equals("null") && hand.held.equals("null")) {
            if (!playingLevel) hover();
            else sprite = spriteGrey;
        } else timer = p.frameCount + 10;
    }

    public void display(int y) {
        p.fill(20,20,50);
        y += (size.x/2) - 12.5;
        p.image(sprite,position.x-size.x/2,y);
        p.textAlign(CENTER);
        p.textFont(largeFont);
        p.text("Play",1000,y+30);
    }

    public void action() {
        if (p.frameCount > timer) {
            playingLevel = true;
            Level level = levels[currentLevel];
            level.currentWave = 0;
            Wave wave = level.waves[level.currentWave];
            wave.init();
        }
    }
}