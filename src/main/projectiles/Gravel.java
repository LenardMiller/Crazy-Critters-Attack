package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Gravel extends Projectile {

    public Gravel(PApplet p, float x, float y, float angle, Turret turret, int damage, int maxSpeed) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        radius = 5;
        this.maxSpeed = maxSpeed + p.random(-150, 15);
        speed = this.maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = down60ToFramerate(p.random(-15,15));
        sprite = staticSprites.get("stonePt");
        hitSound = sounds.get("smallImpact");
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
        projectiles.remove(this);
    }
}