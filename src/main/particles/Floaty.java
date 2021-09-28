package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class Floaty extends Particle {

    public Floaty(PApplet p, float x, float y, float maxSpeed, String type) {
        super(p, x, y, 0);
        size = new PVector(20, 20);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        angle = p.random(360);
        velocity = PVector.fromAngle(angle-HALF_PI);
        animation = new Animator(
                animatedSprites.get(type + "FloatyPT"),
                down60ToFramerate(4),
                false);
    }
}