package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class LargeExplosion extends Particle {

    public LargeExplosion(PApplet p, float x, float y, float angle, String type) {
        super(p, x, y, angle);
        size = new PVector(50,50);
        animation = new Animator(
                animatedSprites.get(type + "LargeExplosionPT"),
                down60ToFramerate(1),
                false);
    }
}