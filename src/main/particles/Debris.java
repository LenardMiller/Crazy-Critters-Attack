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
        maxSpeed = p.random(1.5f,3.5f); //default: 2
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = p.random(-15,15); //degrees mode
        lifespan = (int) p.random(3, 15);
        numFrames = 1;
        delay = lifespan/numFrames;
        sprite = spritesH.get(type + "Pt");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}