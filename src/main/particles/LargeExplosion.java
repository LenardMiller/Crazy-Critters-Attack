package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static processing.core.PConstants.HALF_PI;

public class LargeExplosion extends Particle{
    public LargeExplosion(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(50,50);
        maxSpeed = 0;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        lifespan = 0; //in frames, default: 0
        delay = lifespan/numFrames;
        delayTime = p.frameCount + delay;
        numFrames = 18;
        currentSprite = 0;
        sprites = spritesAnimH.get("largeExplosionPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}