package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.staticSprites;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PConstants.HALF_PI;

public class Debris extends Particle {
    public Debris(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        maxSpeed = p.random(100,200);
        speed = maxSpeed;
        angle2 = angle;
        angularVelocity = p.random(-900,900); //degrees mode
        lifespan = secondsToFrames(p.random(0.05f, 0.25f));
        numFrames = 1;
        delay = lifespan/numFrames;
        sprite = staticSprites.get(type + "Pt");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}