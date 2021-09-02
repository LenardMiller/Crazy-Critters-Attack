package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class ExplosionDebris extends Particle {

    public ExplosionDebris(PApplet p, float x, float y, float angle, String type, float maxSpeed) {
        super(p, x, y, angle);
        size = new PVector(9, 9);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        angularVelocity = 300; //degrees mode
        animation = new Animator(
                animatedSprites.get(type + "ExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false);
    }
}
