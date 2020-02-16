package main.guiObjects.buttons;

import main.levelStructure.Wave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class Play extends Button {

    private PImage spriteGrey;

    public Play(PApplet p, float x, float y, String type, boolean active) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 125);
        spriteOne = spritesAnimH.get("playBT")[1]; //out
        spriteTwo = spritesAnimH.get("playBT")[0]; //in
        spriteGrey = spritesAnimH.get("playBT")[2]; //grey
        sprite = spriteOne;
    }

    public void main() {
        if (active && selection.name.equals("null")) {
            if (!playingLevel) hover();
            else sprite = spriteGrey;
        }
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
        playingLevel = true;
        forest.currentWave = 0;
        Wave wave = forest.waves[forest.currentWave];
        wave.init();
    }
}