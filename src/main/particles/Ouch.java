package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class Ouch extends Particle{
    public Ouch(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        animated = true;
        position = new PVector(x, y);
        size = new PVector(5, 5);
        maxSpeed = 0.25f;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        lifespan = 1; //in frames
        lifespan += (PApplet.round(p.random(-(lifespan/4),lifespan/4))); //injects 25% randomness so all don't die at once
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        numFrames = 11;
        currentSprite = 0;
        sprites = spritesAnimH.get(type + "EnemyPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}