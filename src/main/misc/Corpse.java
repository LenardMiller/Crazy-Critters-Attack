package main.misc;

import main.particles.BuffParticle;
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

    private int betweenFrames;
    private int betweenTime;
    private int frame;

    private int maxLife;
    private int lifespan;

    public Corpse(PApplet p, PVector position, PVector size, float angle, PVector velocity, float angularVelocity, int betweenFrames, int maxLife, String type, String name) {
        this.p = p;

        this.position = position;
        this.size = size;
        this.angle = angle;
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
        sprites = spritesAnimH.get(name + "DieEN");
        this.type = type;

        this.betweenFrames = betweenFrames;
        betweenTime = 0;
        frame = 0;

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
        position.add(velocity);
    }

    private void display() {
        PImage sprite = sprites[frame];
        if (frame < sprites.length - 1) {
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
                int num = (int)(p.random(0, sq(2 * ((float)maxLife / (float)lifespan))));
                if (num == 0) {
                    particles.add(new BuffParticle(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), p.random(0, 360), part));
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
