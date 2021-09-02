package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static processing.core.PConstants.HALF_PI;

public class MediumExplosion extends Particle {

    public MediumExplosion(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        size = new PVector(30,30);
        animation = new Animator(
                animatedSprites.get(type + "MediumExplosionPT"),
                0,
                false);
    }
}