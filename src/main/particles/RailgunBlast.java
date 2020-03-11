package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class RailgunBlast extends Particle {
    public RailgunBlast(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        maxSpeed = 0;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        lifespan = 1; //in frames
        lifespan += (PApplet.round(p.random(-(lifespan/4f),lifespan/4f))); //injects 25% randomness so all don't die at once
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        currentSprite = 0;
        sprites = spritesAnimH.get("railgunBlastPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
        numFrames = sprites.length;
    }
}