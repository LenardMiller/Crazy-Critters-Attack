package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class BoltBreak extends Particle {
    public BoltBreak(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(14, 40);
        maxSpeed = 0;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        lifespan = 1; //in frames
        lifespan += (PApplet.round(p.random(-(lifespan/4f),lifespan/4f))); //injects 25% randomness so all don't die at once
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        currentSprite = 0;
        sprites = spritesAnimH.get("boltBreakPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
        numFrames = sprites.length;
    }
}