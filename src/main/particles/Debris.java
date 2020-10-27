package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesH;
import static processing.core.PConstants.HALF_PI;

public class Debris extends Particle {
    public Debris(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        maxSpeed = 2; //default: 2
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 8; //degrees mode
        lifespan = 6; //in frames, default: 6
        lifespan += (PApplet.round(p.random((-lifespan+2),lifespan))); //injects 100% randomness so all don't die at once
        numFrames = 1;
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        sprite = spritesH.get(type + "Pt");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}