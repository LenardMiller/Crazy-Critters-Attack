package main.misc;

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

    private int betweenFrames;
    private int betweenTime;
    private int frame;

    private int maxLife;
    private int lifespan;

    public Corpse(PApplet p, PVector position, PVector size, float angle, PVector velocity, float angularVelocity, int betweenFrames, String name) {
        this.p = p;

        this.position = position;
        this.size = size;
        this.angle = angle;
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
        sprites = spritesAnimH.get(name + "DieEN");

        this.betweenFrames = betweenFrames;
        betweenTime = 0;
        frame = 0;

        maxLife = 500;
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

        p.tint(255, ((float)lifespan / (float)maxLife) * 255f);
        angle += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        p.tint(255);
    }
}
