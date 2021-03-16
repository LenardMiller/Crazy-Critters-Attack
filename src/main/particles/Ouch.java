package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.from60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class Ouch extends Particle {
    public Ouch(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        maxSpeed = 15;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        betweenFrames = from60ToFramerate(p.random(0,3));
        currentSprite = 0;
        sprites = animatedSprites.get(type + "EnemyPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
        numFrames = sprites.length;
    }
}