package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class RailgunBlast extends Particle {

    public RailgunBlast(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);
        size = new PVector(25, 25);
        animation = new Animator(
                animatedSprites.get("railgunBlastPT"),
                down60ToFramerate(4),
                false);
    }
}