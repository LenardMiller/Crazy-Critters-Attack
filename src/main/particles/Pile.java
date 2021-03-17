package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.paused;
import static main.misc.Utilities.secondsToFrames;
import static processing.core.PApplet.radians;

public class Pile extends Particle {

    public Pile(PApplet p, float x, float y, float angle, String type) {
        super(p,x,y,angle);
        size = new PVector(10,10);
        maxSpeed = 0;
        speed = 0;
        angleTwo = 0;
        lifespan = secondsToFrames(8);
        sprites = animatedSprites.get(type + "PilePT");
        currentSprite = (int) p.random(0,3.99f);
        numFrames = 4;
        velocity = new PVector(0,0);
        angularVelocity = 0;
    }

    protected void display() {
        if (lifespan <= 0) dead = true;
        p.tint(255,255*((float)lifespan / secondsToFrames(8)));
        if (!paused) {
            lifespan--;
            angleTwo += radians(angularVelocity);
        }
        p.pushMatrix();
        p.translate(position.x,position.y);
        p.rotate(angleTwo);
        p.image(sprites[currentSprite],-size.x/2,-size.y/2);
        p.tint(255);
        p.popMatrix();
    }
}
