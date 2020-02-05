package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class ExplosionDebris extends Particle{
    public ExplosionDebris(PApplet p, float x, float y, float angle, String type, float maxSpeed) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(9, 9);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 5; //degrees mode
        lifespan = 0; //in frames, default: 0
        lifespan += (PApplet.round(p.random((-lifespan)+2,lifespan))); //injects 25% randomness so all don't die at once
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        numFrames = 4;
        currentSprite = 0;
        sprites = spritesAnimH.get(type + "ExDebrisPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}
