package main.damagingThings.projectiles;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.particles.Particle.ParticleTypes.BoltBreak;

public class Bolt extends Projectile {

    public Bolt(PApplet p, float x, float y, float angle, Turret turret, int damage, int pierce) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(7, 32);
        radius = 10;
        maxSpeed = 1400;
        speed = maxSpeed;
        this.damage = damage;
        this.pierce = pierce;
        this.angle = angle;
        sprite = staticSprites.get("boltPj");
        hitSound = sounds.get("whooshImpact");
    }

    @Override
    public void die() {
        topParticles.add(BoltBreak.create(p,position.x,position.y,angleTwo));
        projectiles.remove(this);
    }
}