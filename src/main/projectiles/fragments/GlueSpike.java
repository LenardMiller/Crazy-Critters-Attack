package main.projectiles.fragments;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class GlueSpike extends Frag {

    public GlueSpike(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret, damage);
        position = new PVector(x, y);
        size = new PVector(7, 7);
        radius = 5;
        maxSpeed = 600 + p.random(-150, 150);
        speed = maxSpeed;
        angularVelocity = 0;
        sprite = staticSprites.get("glueSpikePj");
        hitSound = sounds.get("smallImpact");
        lifespan = down60ToFramerate(15);
    }

    @Override
    public void die() {
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
        projectiles.remove(this);
    }
}