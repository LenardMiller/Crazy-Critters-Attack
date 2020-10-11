package main.misc;

import main.particles.BuffParticle;
import main.particles.Ouch;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PApplet.radians;

public class Corpse {

    PApplet p;

    private PVector position;
    private PVector size;
    private float angle;
    private PVector velocity;
    private float angularVelocity;
    private PImage[] sprites;
    private String type;
    private String bloodParticle;

    private int betweenFrames;
    private int betweenTime;
    private int frame;
    private boolean animated;

    private int maxLife;
    private int lifespan;

    public Corpse(PApplet p, PVector position, PVector size, float angle, PVector velocity, float angularVelocity, int betweenFrames, int maxLife, String type, String name, String bloodParticle, int frame, boolean animated) {
        this.p = p;

        this.position = new PVector(position.x, position.y);
        this.size = size;
        this.angle = angle;
        if (!animated) {
            float speed = 3.5f;
            speed *= p.random(1, 2);
            float a = p.random(radians(0), radians(360));
            if (!(velocity.x == 0 && velocity.y == 0)) a = MiscMethods.findAngle(velocity);
            a -= HALF_PI;
            a += p.random(radians(-40), radians(40));
            velocity = PVector.fromAngle(a);
            this.velocity = velocity.setMag(speed);
        } else this.velocity = velocity;
        this.angularVelocity = angularVelocity;
        sprites = spritesAnimH.get(name + "EN");
        this.type = type;
        if (this.type == null) this.type = "normal";
        this.bloodParticle = bloodParticle;
        this.frame = frame;
        this.animated = animated;

        this.betweenFrames = betweenFrames;
        betweenTime = 0;

        this.maxLife = maxLife;
        lifespan = maxLife;
    }

    public void main(int i) {
        move();
        display();
        lifespan--;
        if (lifespan <= 0) corpses.remove(i);
    }

    private void move() {
        velocity.x *= (float)lifespan / (float)maxLife;
        velocity.y *= (float)lifespan / (float)maxLife;
        angularVelocity *= (float)lifespan / (float)maxLife;
        angle += angularVelocity;
        position.add(velocity);
    }

    private void display() {
        PImage sprite = sprites[frame];
        if (animated && frame < sprites.length - 1) {
            betweenTime++;
            if (betweenTime >= betweenFrames) {
                frame++;
                betweenTime = 0;
            }
        }

        float gTint = 255;
        if (type.equals("burning") || type.equals("decay")) {
            float g = 0;
            String part = "";
            if (type.equals("burning")) {
                g = 60;
                part = "fire";
            }
            if (type.equals("decay")) {
                g = 0;
                part = "decay";
            }

            gTint = (pow((float)lifespan / (float)maxLife, 3) * (255 - g)) + g;
            for (int i = (int) ((size.x / 25) * (size.y / 25)) / 25; i >= 0; i--) {
                float chance = sq(2 * ((float)maxLife / (float)lifespan));
                if (!animated) chance += 16;
                int num = (int)(p.random(0, chance));
                if (num == 0) {
                    particles.add(new BuffParticle(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), p.random(0, 360), part));
                }
            }
        }

        if (!bloodParticle.equals("none")) {
            for (int i = (int) ((size.x / 25) * (size.y / 25)) / 25; i >= 0; i--) {
                float speed = sqrt(sq(velocity.x) + sq(velocity.y));
                float chance = sq(1 / (speed + 0.01f));
                chance += 16;
                if (!type.equals("burning") && !type.equals("decay")) {
                    int num = (int) (p.random(0, chance));
                    if (num == 0) {
                        particles.add(new Ouch(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), p.random(0, 360), bloodParticle));
                    }
                }
                chance += 10;
                int num = (int) (p.random(0,chance));
                if (num == 0) {
                    particles.add(new Pile(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), 0, bloodParticle));
                }
            }
        }

        p.tint(gTint, ((float)lifespan / (float)maxLife) * 255f);
        angle += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        p.tint(255);
    }
}
