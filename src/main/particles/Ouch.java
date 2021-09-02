package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class Ouch extends Particle {

    public Ouch(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        size = new PVector(5, 5);
        maxSpeed = 15;
        speed = maxSpeed;
        animation = new Animator(
                animatedSprites.get(type + "PT"),
                down60ToFramerate(p.random(0,3)),
                false);
    }
}