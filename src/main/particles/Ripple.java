package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;

public class Ripple extends Particle {

    public enum Type {
        water(10),
        dirtyWater(30),
        sludge(90);

        final int betweenFrames;

        Type(int betweenFrames) {
            this.betweenFrames = betweenFrames;
        }
    }

    public Ripple(PApplet p, float x, float y, Type type) {
        super(p, x, y, 0);
        size = new PVector(12, 12);
        animation = new Animator(
                animatedSprites.get(type + "PT"),
                down60ToFramerate(type.betweenFrames),
                false);
    }
}
