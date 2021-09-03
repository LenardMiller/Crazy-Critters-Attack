package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.HALF_PI;

public class Particle {

    public interface AnimatorFactory {
        Animator get();
    }

    public enum ParticleTypes {
        BoltBreak(14, 40, () -> new Animator(
                animatedSprites.get("boltBreakPT"),
                 down60ToFramerate(p.random(0,5)),
                false
        )),
        RailgunBlast(25, 25, () -> new Animator(
                animatedSprites.get("railgunBlastPT"),
                down60ToFramerate(4),
                false
        )),
        Water(12, 12, () -> new Animator(
                animatedSprites.get("waterPT"),
                down60ToFramerate(10),
                false
        ));

        private final PVector SIZE;
        private final AnimatorFactory ANIMATOR;

        ParticleTypes(float width, float height, AnimatorFactory animator) {
            SIZE = new PVector(width, height);
            ANIMATOR = animator;
        }

        public Particle create(PApplet p, float x, float y, float angle) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), ANIMATOR.get());
        }
    }

    public static PApplet p;

    public float maxSpeed;
    public float speed;
    public float angle;
    public int delay;
    public PVector position;
    public PVector size;
    public Animator animation;

    protected float displayAngle;
    protected float angularVelocity;
    protected boolean dead;
    protected PVector velocity;

    protected Particle(PApplet p, float x, float y, float angle) {
        Particle.p = p;

        displayAngle = angle;
        position = new PVector(x, y);
        velocity = PVector.fromAngle(angle - HALF_PI);
    }

    protected Particle(PApplet p, PVector position, float angle, PVector size, Animator animation) {
        this(p, position.x, position.y, angle);
        this.size = size;
        this.animation = animation;
    }

    public void main(ArrayList<Particle> particles, int i) {
        if (crossedEdge()) dead = true;
        if (dead) particles.remove(i);
        display();
        if (!paused) move();
    }

    private boolean crossedEdge() {
        return position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 || position.y + size.y < -100 || position.x + size.x < -100;
    }

    protected void display() {
        animation.update();
        displayAngle += radians(secondsToFrames(angularVelocity));
        if (animation.ended()) dead = true;
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(displayAngle);
        p.image(animation.getCurrentFrame(), -size.x / 2, -size.y / 2);
        p.popMatrix();
    }

    protected void move() {
        velocity.setMag(speed/FRAMERATE);
        position.add(velocity);
    }
}
