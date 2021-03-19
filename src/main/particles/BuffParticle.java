package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.particles;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class BuffParticle extends Particle {
    public BuffParticle(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(7, 7);
        maxSpeed = 15;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 300; //degrees mode
        betweenFrames = down60ToFramerate(p.random(3,6));
        numFrames = 8;
        currentSprite = 0;
        if (animatedSprites.get(type + "BuffPT") != null) sprites = animatedSprites.get(type + "BuffPT");
        else particles.remove(this);
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}