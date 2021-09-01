package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;

public class Water extends Particle {

    public Water(PApplet p, float x, float y) {
        super(p, x, y, 0);
        position = new PVector(x, y);
        size = new PVector(12, 12);
        maxSpeed = 0;
        speed = maxSpeed;
        betweenFrames = down60ToFramerate(10);
        sprites = animatedSprites.get("waterPT");
        numFrames = sprites.length;
    }
}
