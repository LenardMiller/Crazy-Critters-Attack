package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class OrangeBubble extends Particle {

    public OrangeBubble(PApplet p, float x, float y, float maxSpeed) {
        super(p, x, y, 0);
        position = new PVector(x, y);
        size = new PVector(20, 20);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        displayAngle = angle;
        angle = p.random(360);
        angularVelocity = 0; //degrees mode
        betweenFrames = down60ToFramerate(4);
        currentSprite = 0;
        sprites = animatedSprites.get("orangeBubblePT");
        velocity = PVector.fromAngle(angle-HALF_PI);
        numFrames = sprites.length;
    }
}