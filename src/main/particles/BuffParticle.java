package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class BuffParticle extends Particle{
    public BuffParticle(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(7, 7);
        maxSpeed = 0.25f;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 5; //degrees mode
        lifespan = 2; //in frames, default: 2
        lifespan += (PApplet.round(p.random((-lifespan)+2,lifespan))); //injects 25% randomness so all don't die at once
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        numFrames = 8;
        currentSprite = 0;
        sprites = spritesAnimH.get(type + "BuffPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}