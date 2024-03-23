package main.misc;

import main.enemies.Enemy;
import main.particles.MiscParticle;
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

    public static final int CAP = 50;

    private final boolean ANIMATED;
    private final int MAX_LIFE;
    private final int BETWEEN_FRAMES;
    private final PVector POSITION;
    private final PVector SIZE;
    private final PVector VELOCITY;
    private final PImage[] SPRITES;
    private final Enemy.HitParticle BLOOD_PARTICLE;
    private final PApplet P;

    private int betweenTime;
    private int frame;
    private int lifespan;
    private float angularVelocity;
    private float angle;
    private Enemy.DamageType type;
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
                  int betweenFrames, int maxLife, Enemy.DamageType effectType, String name, Enemy.HitParticle bloodParticle, int frame,
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
        if (this.type == null) this.type = null;
        this.BLOOD_PARTICLE = bloodParticle;
        this.frame = frame;
        this.ANIMATED = animated;

        this.BETWEEN_FRAMES = betweenFrames;
        betweenTime = 0;

        this.MAX_LIFE = secondsToFrames(maxLife);
        lifespan = MAX_LIFE;
    }

    public void update(int i) {
        if (isPaused) return;
        move();
        bloodParticles();
        buffParticles();
        lifespan--;
        if (lifespan <= 0) corpses.remove(i);
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
        if (!isPaused) {
            if (ANIMATED && frame < SPRITES.length - 1) {
                betweenTime++;
                if (betweenTime >= BETWEEN_FRAMES) {
                    frame++;
                    betweenTime = 0;
                }
            }
        }

        if (type != null && type.finalTintColor != null) {
            drawSprites(tinting(sprite, tintedColor(type.finalTintColor)));
        } else if (type == Enemy.DamageType.bleeding) {
            drawSprites(tinting(sprite, tintedColor(BLOOD_PARTICLE.tintColor)));
        } else {
            drawSprites(tinting(sprite, null));
        }
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    private Color tintedColor(Color tint) {
        return new Color (
                getTintChannel(tint.getRed(), lifespan, MAX_LIFE),
                getTintChannel(tint.getGreen(), lifespan, MAX_LIFE),
                getTintChannel(tint.getBlue(), lifespan, MAX_LIFE)
        );
    }

    private void buffParticles() {
        if (isPaused || type == null || (type.particle == null && type != Enemy.DamageType.bleeding)) return;
        float chance = 0;
        //prevent divide by 0
        if (lifespan > 0) chance = sq(2 * ((float) MAX_LIFE / (float) lifespan));
        if (!ANIMATED) chance += 16;
        if (type == Enemy.DamageType.bleeding) chance *= 3;
        int num = (int) (P.random(0, chance));
        if (num == 0) {
            if (type == Enemy.DamageType.bleeding) {
                bottomParticles.add(new Pile(P, (float) (POSITION.x + 2.5 + P.random((SIZE.x / 2) * -1,
                        (SIZE.x / 2))), (float) (POSITION.y + 2.5 + P.random((SIZE.x / 2) * -1, (SIZE.x / 2))),
                        0, BLOOD_PARTICLE.name()));
            }
            else {
                towerParticles.add(new MiscParticle(P, (float) (POSITION.x + 2.5 + P.random((SIZE.x / 2) * -1,
                        (SIZE.x / 2))), (float) (POSITION.y + 2.5 + P.random((SIZE.x / 2) * -1, (SIZE.x / 2))),
                        P.random(360), type.particle));
            }
        }
    }

    private PImage tinting(PImage sprite, Color tintColor) {
        //for memory reasons
        PImage st = P.createImage(sprite.width, sprite.height, ARGB);
        sprite.loadPixels();
        arrayCopy(sprite.pixels, st.pixels);

        //tinting
        float transparency = ((float) lifespan) / ((float) MAX_LIFE);
        if (tintColor != null) {
            P.tint(currentTintColor.getRGB());
            superTint(st, new Color(tintColor.getRed(), tintColor.getGreen(), tintColor.getBlue(), 0), transparency);
        } else P.tint(currentTintColor.getRGB(), transparency * 255);
        return st;
    }

    private void bloodParticles() {
        if (isPaused || BLOOD_PARTICLE == null) return;
        for (int i = (int) ((SIZE.x / 25) * (SIZE.y / 25)) / 25; i >= 0; i--) {
            float speed = sqrt(sq(VELOCITY.x) + sq(VELOCITY.y));
            float chance = sq(1 / (speed + 0.01f));
            chance += 16;
            if (P.random(chance) < 1) {
                PVector pos = getRandomPointInRange(P, POSITION, SIZE.mag() * 0.4f);
                towerParticles.add(new Ouch(P, pos.x, pos.y, P.random(360), BLOOD_PARTICLE.name()));
            }
            chance += 10;
            if (P.random(chance) < 1) {
                PVector pos = getRandomPointInRange(P, POSITION, SIZE.mag() * 0.2f);
                bottomParticles.add(new Pile(P, pos.x, pos.y, 0, BLOOD_PARTICLE.name()));
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
