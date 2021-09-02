package main.particles;

import main.misc.Animator;
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
        size = new PVector(5, 5);
        maxSpeed = p.random(100,200);
        speed = maxSpeed;
        angularVelocity = p.random(-900,900); //degrees mode
        animation = new Animator(
                staticSprites.get(type + "Pt"),
                secondsToFrames(p.random(0.25f, 0.5f)),
                false);
    }

    @Override
    protected void display() {
        if (!paused) {
            animation.update();
            displayAngle += radians(secondsToFrames(angularVelocity));
        }
        if (animation.ended()) dead = true;
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(displayAngle);
        float scale = abs(((animation.getBetweenFramesCounter() - 1) / (float) animation.getBetweenFrames()) - 1);
        p.scale(scale);
        p.image(animation.getCurrentFrame(), -size.x / 2, -size.y / 2);
        p.popMatrix();
    }
}