package main.misc;

import main.particles.BuffParticle;
import main.particles.Ouch;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static processing.core.PApplet.radians;

public class Corpse {

    private final boolean ANIMATED;
    private final int MAX_LIFE;
    private final int BETWEEN_FRAMES;
    private final PVector POSITION;
    private final PVector SIZE;
    private final PVector VELOCITY;
    private final PImage[] SPRITES;
    private final String BLOOD_PARTICLE;
    private final PApplet P;

    private int betweenTime;
    private int frame;
    private int lifespan;
    private float angularVelocity;
    private float angle;
    private String type;
    private Color currentTintColor;

    /**
     * A dead enemy.
     * @param p the PApplet
     * @param position position of the corpse
     * @param size size of the corpse
     * @param angle rotation of the corpse
     * @param velocity movement of the corpse
     * @param currentTintColor initial tint color
     * @param angularVelocity rotational speed of the corpse
     * @param betweenFrames number of times to duplicate a frame
     * @param maxLife how long it should last
     * @param effectType what sort of visual effect to apply
     * @param name what enemy this was
     * @param bloodParticle what color the blood should be
     * @param frame what frame to start its animation on
     * @param animated should it be animated
     */
    public Corpse(PApplet p, PVector position, PVector size, float angle, PVector velocity, Color currentTintColor, float angularVelocity,
                  int betweenFrames, int maxLife, String effectType, String name, String bloodParticle, int frame,
                  boolean animated) {
        this.P = p;

        this.POSITION = new PVector(position.x, position.y);
        this.SIZE = size;
        this.angle = angle;
        this.currentTintColor = currentTintColor;
        if (!animated) {
            float speed = 3.5f;
            speed *= p.random(1, 2);
            float a = p.random(radians(0), radians(360));
            if (!(velocity.x == 0 && velocity.y == 0)) a = Utilities.findAngle(velocity);
            a -= HALF_PI;
            a += p.random(radians(-40), radians(40));
            velocity = PVector.fromAngle(a);
            this.VELOCITY = velocity.setMag(speed);
        } else this.VELOCITY = velocity;
        this.angularVelocity = angularVelocity * 2;
        SPRITES = animatedSprites.get(name + "EN");
        this.type = effectType;
        if (this.type == null) this.type = "normal";
        this.BLOOD_PARTICLE = bloodParticle;
        this.frame = frame;
        this.ANIMATED = animated;

        this.BETWEEN_FRAMES = betweenFrames;
        betweenTime = 0;

        this.MAX_LIFE = secondsToFrames(maxLife);
        lifespan = MAX_LIFE;
    }

    public void main(int i) {
        if (!paused) {
            move();
            lifespan--;
            if (lifespan <= 0) corpses.remove(i);
        }
    }

    private void move() {
        VELOCITY.x *= (float)lifespan / (float) MAX_LIFE;
        VELOCITY.y *= (float)lifespan / (float) MAX_LIFE;
        angularVelocity *= (float)lifespan / (float) MAX_LIFE;
        angle += radians(angularVelocity);
        POSITION.add(VELOCITY);
    }

    public void display() {
        PImage sprite = SPRITES[frame];
        if (!paused) {
            if (ANIMATED && frame < SPRITES.length - 1) {
                betweenTime++;
                if (betweenTime >= BETWEEN_FRAMES) {
                    frame++;
                    betweenTime = 0;
                }
            }
        }

        Color tint;
        boolean doSpecialEffects = true;
        Color tintFinal;
        String part;
        switch (type) {
            case "burning":
                tintFinal = new Color(60,60,60);
                part = "fire";
                break;
            case "blueBurning":
                tintFinal = new Color(0, 14, 64);
                part = "blueGreenFire";
                break;
            case "decay":
                tintFinal = new Color(0,0,0);
                part = "decay";
                break;
            case "poisoned":
                tintFinal = new Color(120, 180, 0);
                part = "poison";
                break;
            case "glued":
                tintFinal = new Color(234, 229, 203);
                part = "glue";
                break;
            case "energy":
                tintFinal = new Color(60, 60, 60);
                part = "energy";
                break;
            case "electricity":
                tintFinal = new Color(60, 60, 60);
                part = "electricity";
                break;
            case "nuclear":
                tintFinal = new Color(60, 60, 60);
                part = "nuclear";
                break;
            case "dark":
                tintFinal = new Color(79, 0, 128);
                part = "dark";
                break;
            case "frozen":
                tintFinal = new Color(150, 225, 255);
                part = null;
                break;
            default:
                tintFinal = new Color(255,255,255);
                doSpecialEffects = false;
                part = null;
                break;
        }
        tint = new Color (
                getTintChannel(tintFinal.getRed(), lifespan, MAX_LIFE),
                getTintChannel(tintFinal.getGreen(), lifespan, MAX_LIFE),
                getTintChannel(tintFinal.getBlue(), lifespan, MAX_LIFE)
        );
        if (doSpecialEffects) buffParticles(part);

        bloodParticles();
        PImage st = tinting(sprite, tint, doSpecialEffects);
        drawSprites(st);
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    private void buffParticles(String part) {
        if (!paused) {
            float chance = 0;
            //prevent divide by 0
            if (lifespan > 0) chance = sq(2 * ((float) MAX_LIFE / (float) lifespan));
            if (!ANIMATED) chance += 16;
            int num = (int) (P.random(0, chance));
            if (num == 0) {
                particles.add(new BuffParticle(P, (float) (POSITION.x + 2.5 + P.random((SIZE.x / 2) * -1,
                        (SIZE.x / 2))), (float) (POSITION.y + 2.5 + P.random((SIZE.x / 2) * -1, (SIZE.x / 2))),
                        P.random(0, 360), part));
            }
        }
    }

    private PImage tinting(PImage sprite, Color tint, boolean doTint) {
        //for memory reasons
        PImage st = P.createImage(sprite.width, sprite.height, ARGB);
        sprite.loadPixels();
        arrayCopy(sprite.pixels, st.pixels);

        //tinting
        float transparency = ((float) lifespan) / ((float) MAX_LIFE);
        if (doTint) {
            P.tint(currentTintColor.getRGB());
            superTint(st, new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 0), transparency);
        } else P.tint(currentTintColor.getRGB(), transparency * 255);
        return st;
    }

    private void bloodParticles() {
        if (!paused) {
            if (!BLOOD_PARTICLE.equals("none")) {
                for (int i = (int) ((SIZE.x / 25) * (SIZE.y / 25)) / 25; i >= 0; i--) {
                    float speed = sqrt(sq(VELOCITY.x) + sq(VELOCITY.y));
                    float chance = sq(1 / (speed + 0.01f));
                    chance += 16;
                    if (!type.equals("burning") && !type.equals("decay")) { //idk
                        int num = (int) (P.random(0, chance));
                        if (num == 0) {
                            particles.add(new Ouch(P, (float) (POSITION.x + 2.5 + P.random((SIZE.x / 2) * -1,
                                    (SIZE.x / 2))), (float) (POSITION.y + 2.5 + P.random((SIZE.x / 2) * -1,
                                    (SIZE.x / 2))), P.random(0, 360), BLOOD_PARTICLE));
                        }
                    }
                    chance += 10;
                    int num = (int) (P.random(0, chance));
                    if (num == 0) {
                        underParticles.add(new Pile(P, (float) (POSITION.x + 2.5 + P.random((SIZE.x / 2) * -1,
                                (SIZE.x / 2))), (float) (POSITION.y + 2.5 + P.random((SIZE.x / 2) * -1,
                                (SIZE.x / 2))), 0, BLOOD_PARTICLE));
                    }
                }
            }
        }
    }

    private void drawSprites(PImage sprite) {
        P.pushMatrix();
        P.translate(POSITION.x, POSITION.y);
        P.rotate(angle);
        P.image(sprite, -SIZE.x / 2, -SIZE.y / 2);
        P.popMatrix();
        P.tint(255);
    }

    private int getTintChannel(float channel, float lifespan, float maxLife) {
        return (int) ((pow(lifespan / maxLife, 3) * (255 - channel)) + channel);
    }
}
