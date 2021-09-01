package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.paused;
import static main.Main.staticSprites;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PApplet.abs;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.HALF_PI;

public class Debris extends Particle {

    public Debris(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        maxSpeed = p.random(100,200);
        speed = maxSpeed;
        displayAngle = angle;
        angularVelocity = p.random(-900,900); //degrees mode
        lifespan = secondsToFrames(p.random(0.25f, 0.5f));
        if (lifespan == 1) lifespan++;
        sprite = staticSprites.get(type + "Pt");
        velocity = PVector.fromAngle(angle-HALF_PI);
    }

    @Override
    protected void display() {
        if (!paused) {
            delayTime++;
            displayAngle += radians(secondsToFrames(angularVelocity));
        }
        if (delayTime >= lifespan - 1) dead = true;
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(displayAngle);
        float scale = abs(((delayTime - 1) / (float) lifespan) - 1);
        p.scale(scale);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }
}