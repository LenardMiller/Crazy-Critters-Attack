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

    @FunctionalInterface
    private interface AnimatorFactory {
        Animator get();
    }

    @FunctionalInterface
    private interface FloatFactory {
        float get();
    }

    @FunctionalInterface
    private interface IntFactory {
        int get();
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
        //Large Explosion
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
        //Medium Explosion
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
        )),
        //Floaty
        SmokeCloudFloaty(20, 20, () -> new Animator(
                animatedSprites.get("smokeCloudFloatyPT"),
                down60ToFramerate(4),
                false
        )),
        FrostCloudFloaty(20, 20, () -> new Animator(
                animatedSprites.get("frostCloudFloatyPT"),
                down60ToFramerate(4),
                false
        )),
        CoinFloaty(20, 20, () -> new Animator(
                animatedSprites.get("coinFloatyPT"),
                down60ToFramerate(4),
                false
        )),
        OrangeBubbleFloaty(20, 20, () -> new Animator(
                animatedSprites.get("orangeBubbleFloatyPT"),
                down60ToFramerate(4),
                false
        )),
        //Misc (buff)
        MiscParticle(7, 7, 15, "MiscPT",
                () -> p.random(-300, 300),
                () -> down60ToFramerate(p.random(3,6))
        ),
        Glue(7, 7, 15, () -> new Animator(
                animatedSprites.get("glueMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        BlueSmoke(7, 7, 15, () -> new Animator(
                animatedSprites.get("blueSmokeMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Fire(7, 7, 15, () -> new Animator(
                animatedSprites.get("fireMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Smoke(7, 7, 15, () -> new Animator(
                animatedSprites.get("smokeMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Electricity(7, 7, 15, () -> new Animator(
                animatedSprites.get("electricityMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Nuclear(7, 7, 15, () -> new Animator(
                animatedSprites.get("nuclearMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Stun(7, 7, 15, () -> new Animator(
                animatedSprites.get("stunMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        IceMagic(7, 7, 15, () -> new Animator(
                animatedSprites.get("iceMagicMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        OrangeMagic(7, 7, 15, () -> new Animator(
                animatedSprites.get("orangeMagicMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        GreenMagic(7, 7, 15, () -> new Animator(
                animatedSprites.get("greenMagicMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        YellowMagic(7, 7, 15, () -> new Animator(
                animatedSprites.get("yellowMagicMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Dark(7, 7, 15, () -> new Animator(
                animatedSprites.get("darkMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Energy(7, 7, 15, () -> new Animator(
                animatedSprites.get("energyMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        )),
        Decay(7, 7, 15, () -> new Animator(
                animatedSprites.get("decayMiscPT"),
                down60ToFramerate(p.random(3,6)),
                false
        ));

        private final float SPEED;
        private final FloatFactory ANGULAR_VELOCITY;
        private final PVector SIZE;

        private IntFactory betweenFrames;
        private String name;
        private AnimatorFactory animator;

        //0
        ParticleTypes(float width, float height, float speed, FloatFactory angularVelocity) {
            SIZE = new PVector(width, height);
            SPEED = speed;
            ANGULAR_VELOCITY = angularVelocity;
        }

        //1
        ParticleTypes(float width, float height, float speed, FloatFactory angularVelocity, AnimatorFactory animator) {
            this(width, height, speed, angularVelocity); //0
            this.animator = animator;
        }

        //2
        ParticleTypes(float width, float height, float speed, AnimatorFactory animator) {
            this(width, height, speed, () -> 0, animator); //1
        }

        //3
        ParticleTypes(float width, float height, AnimatorFactory animator) {
            this(width, height, 0, () -> 0, animator); //1
        }

        //4
        ParticleTypes(float width, float height, float speed, String name, FloatFactory angularVelocity, IntFactory betweenFrames) {
            this(width, height, speed, angularVelocity); //0
            this.name = name;
            this.betweenFrames = betweenFrames;
        }

        public Particle create(PApplet p, float x, float y, float angle, String type) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), new Animator(
                    animatedSprites.get(type + name),
                    betweenFrames.get(),
                    false
            ), SPEED, ANGULAR_VELOCITY.get());
        }

        public Particle create(PApplet p, float x, float y, float angle) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), animator.get(), SPEED, ANGULAR_VELOCITY.get());
        }

        public Particle create(PApplet p, float x, float y, float angle, float speed) {
            return new Particle(p, new PVector(x, y), angle, SIZE.copy(), animator.get(), speed, ANGULAR_VELOCITY.get());
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

    private Particle(PApplet p, PVector position, float angle, PVector size, Animator animation, float maxSpeed, float angularVelocity) {
        this(p, position, angle, size, animation);
        this.maxSpeed = maxSpeed;
        this.angularVelocity = angularVelocity;
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
