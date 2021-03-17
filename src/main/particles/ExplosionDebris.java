package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class ExplosionDebris extends Particle{
    public ExplosionDebris(PApplet p, float x, float y, float angle, String type, float maxSpeed) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(9, 9);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 300; //degrees mode
        betweenFrames = down60ToFramerate(p.random(2,5));
        numFrames = 4;
        currentSprite = 0;
        sprites = animatedSprites.get(type + "ExDebrisPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}
