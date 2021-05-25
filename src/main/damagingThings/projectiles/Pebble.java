package main.damagingThings.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Pebble extends Projectile{

    public Pebble(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 700;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = down60ToFramerate(p.random(-15,15));
        sprite = staticSprites.get("pebblePj");
        hitSound = sounds.get("mediumImpact");
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
        projectiles.remove(this);
    }
}