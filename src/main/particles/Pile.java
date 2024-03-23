package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.isPaused;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PApplet.radians;

public class Pile extends Particle {

    public Pile(PApplet p, float x, float y, float angle, String type) {
        super(p,x,y,angle);
        size = new PVector(10,10);
        animation = new Animator(
                animatedSprites.get(type + "PilePT")[(int) p.random(4)],
                secondsToFrames(8),
                false);
        velocity = new PVector(0,0);
    }

    @Override
    public void display() {
        if (animation.ended()) {
            dead = true;
            return;
        }
        p.tint(255,255 * (1 - animation.getBetweenFramesCounter() / (float) animation.getBetweenFrames()));
        if (!isPaused) {
            animation.update();
            displayAngle += radians(angularVelocity);
        }
        p.pushMatrix();
        p.translate(position.x,position.y);
        p.rotate(displayAngle);
        p.image(animation.getCurrentFrame(),-size.x/2,-size.y/2);
        p.tint(255);
        p.popMatrix();
    }
}
