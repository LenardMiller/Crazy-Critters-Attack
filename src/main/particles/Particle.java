package main.particles;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.HALF_PI;

public abstract class Particle {

    public float maxSpeed;
    public float speed;
    public float angle;
    public int delay;
    public PApplet p;
    public PImage sprite;
    public PImage[] sprites;
    public PVector position;
    public PVector size;

    protected float angleTwo;
    protected float angularVelocity;
    protected boolean dead;
    protected int lifespan;
    protected int numFrames;
    protected int currentSprite;
    protected int delayTime;
    protected int betweenFrames;
    protected int bfTimer;
    protected PVector velocity;

    protected Particle(PApplet p, float x, float y, float angle) {
        this.p = p;

        position = new PVector(x, y);
        size = new PVector(5, 5);
        speed = (float) 1;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        lifespan = 60; //in frames
        lifespan += (PApplet.round(p.random(-(lifespan / 4f), lifespan / 4f))); //injects 10% randomness so all don't die at once
        numFrames = 1;
        currentSprite = 0;
        delay = lifespan / numFrames;
        sprite = spritesH.get("nullPt");
        velocity = PVector.fromAngle(angle - HALF_PI);
        betweenFrames = 0;
    }

    public void main(ArrayList<Particle> particles, int i) {
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 || position.y + size.y < -100 || position.x + size.x < -100) { //if crossed edge, delete
            dead = true;
        }
        if (dead) particles.remove(i);
        display();
        if (!paused) move();
    }

    protected void display() {
        if (numFrames > 1) {
            if (!paused) {
                if (bfTimer >= betweenFrames) {
                    if (currentSprite == numFrames - 1) dead = true;
                    else {
                        currentSprite++;
                    }
                    bfTimer = 0;
                } else bfTimer++;
                angleTwo += radians(angularVelocity);
            }
            p.pushMatrix();
            p.translate(position.x, position.y);
            p.rotate(angleTwo);
            p.image(sprites[currentSprite], -size.x / 2, -size.y / 2);
        } else {
            if (!paused) {
                delayTime++;
                angleTwo += radians(angularVelocity);
            }
            if (delayTime > delay) dead = true;
            p.pushMatrix();
            p.translate(position.x, position.y);
            p.rotate(angleTwo);
            p.image(sprite, -size.x / 2, -size.y / 2);
        }
        p.popMatrix();
    }

    private void move() {
        velocity.setMag(speed);
        position.add(velocity);
    }
}
