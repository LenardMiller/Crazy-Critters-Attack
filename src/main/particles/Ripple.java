package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.down60ToFramerate;

public class Ripple extends Particle {

    public Ripple(PApplet p, float x, float y, String type) {
        super(p, x, y, 0);
        size = new PVector(12, 12);
        int betweenFrames;
        switch (type) {
            case "water":
                betweenFrames = 10;
                break;
            case "dirtyWater":
                betweenFrames = 30;
                break;
            default:
                betweenFrames = 0;
        }
        animation = new Animator(
                animatedSprites.get(type + "PT"),
                down60ToFramerate(betweenFrames),
                false);
    }
}
