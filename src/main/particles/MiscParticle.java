package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class MiscParticle extends Particle {

    public MiscParticle(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        size = new PVector(7, 7);
        maxSpeed = 15;
        speed = maxSpeed;
        angularVelocity = p.random(-300, 300); //degrees mode
        animation = new Animator(
                animatedSprites.get(type + "MiscPT"),
                down60ToFramerate(p.random(3,6)),
                false);
    }
}