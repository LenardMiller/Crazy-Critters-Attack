package main.particles;

import main.misc.Animator;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.HALF_PI;

public abstract class Particle {

    public float maxSpeed;
    public float speed;
    public float angle;
    public int delay;
    public PApplet p;
    public PVector position;
    public PVector size;
    public Animator animation;

    protected float displayAngle;
    protected float angularVelocity;
    protected boolean dead;
    protected PVector velocity;

    protected Particle(PApplet p, float x, float y, float angle) {
        this.p = p;

        displayAngle = angle;
        position = new PVector(x, y);
        velocity = PVector.fromAngle(angle - HALF_PI);
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
