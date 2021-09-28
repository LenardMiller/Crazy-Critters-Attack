package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class BoltBreak extends Particle {

    public BoltBreak(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);

        size = new PVector(14, 40);
        animation = new Animator(
                animatedSprites.get("boltBreakPT"),
                down60ToFramerate(p.random(0,5)),
                false);
        velocity = PVector.fromAngle(angle-HALF_PI);
    }
}