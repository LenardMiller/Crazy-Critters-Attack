package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;

public class Water extends Particle {

    public Water(PApplet p, float x, float y) {
        super(p, x, y, 0);
        size = new PVector(12, 12);
        animation = new Animator(
                animatedSprites.get("waterPT"),
                down60ToFramerate(10),
                false);
    }
}
