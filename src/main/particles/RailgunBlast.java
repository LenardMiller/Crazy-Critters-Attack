package main.particles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.misc.Utilities.from60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class RailgunBlast extends Particle {
    public RailgunBlast(PApplet p, float x, float y, float angle) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        maxSpeed = 0;
        speed = maxSpeed;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        betweenFrames = from60ToFramerate(4);
        currentSprite = 0;
        sprites = animatedSprites.get("railgunBlastPT");
        velocity = PVector.fromAngle(angle-HALF_PI);
        numFrames = sprites.length;
    }
}