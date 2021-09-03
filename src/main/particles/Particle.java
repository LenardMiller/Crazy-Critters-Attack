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
        //Random
        BoltBreak(14, 40, () -> new Animator(
                animatedSprites.get("boltBreakPT"),
                 down60ToFramerate(p.random(5)),
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
        )),
        //Large Explosions
        ToxicExplosion(50, 50, () -> new Animator(
                animatedSprites.get("toxicLargeExplosionPT"),
                down60ToFramerate(1),
                false
        )),
        LargeEnergyExplosion(50, 50, () -> new Animator(
                animatedSprites.get("energyLargeExplosionPT"),
                down60ToFramerate(1),
                false
        )),
        GlueExplosion(50, 50, () -> new Animator(
                animatedSprites.get("glueLargeExplosionPT"),
                down60ToFramerate(1),
                false
        )),
        LargeFireExplosion(50, 50, () -> new Animator(
                animatedSprites.get("fireLargeExplosionPT"),
                down60ToFramerate(1),
                false
        )),
        //Medium Explosions
        MediumFireExplosion(30, 30, () -> new Animator(
                animatedSprites.get("fireMediumExplosionPT"),
                false
        )),
        MediumEnergyExplosion(30, 30, () -> new Animator(
                animatedSprites.get("energyMediumExplosionPT"),
                false
        )),
        MediumPuffExplosion(30, 30, () -> new Animator(
                animatedSprites.get("puffMediumExplosionPT"),
                false
        )),
        //Explosion Debris
        MetalExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("metalExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        FireExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("fireExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        EnergyExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("energyExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        PoisonExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("poisonExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        NuclearExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("nuclearExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        GlueExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("glueExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        )),
        ElectricityExplosionDebris(9, 9, () -> new Animator(
                animatedSprites.get("electricityExDebrisPT"),
                down60ToFramerate(p.random(2,5)),
                false
        ));

        private final PVector SIZE;
        private final AnimatorFactory ANIMATOR;

        ParticleTypes(float width, float height,AnimatorFactory animator) {
            SIZE = new PVector(width, height);
            ANIMATOR = animator;
        }

        public Particle create(PApplet p, float x, float y, float angle) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), ANIMATOR.get());
        }

        public Particle create(PApplet p, float x, float y, float angle, float speed) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), ANIMATOR.get(), speed);
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

    private Particle(PApplet p, PVector position, float angle, PVector size, Animator animation) {
        this(p, position.x, position.y, angle);
        this.size = size;
        this.animation = animation;
    }

    private Particle(PApplet p, PVector position, float angle, PVector size, Animator animation, float maxSpeed) {
        this(p, position, angle, size, animation);
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
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
