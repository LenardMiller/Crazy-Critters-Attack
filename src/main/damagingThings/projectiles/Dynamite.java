package main.damagingThings.projectiles;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.particles.Particle.ParticleTypes.FireExplosionDebris;
import static main.particles.Particle.ParticleTypes.LargeFireExplosion;

public class Dynamite extends Projectile {

    public Dynamite(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(9, 15);
        angularVelocity = down60ToFramerate(p.random(-15, 15)); //degrees mode
        radius = 10;
        maxSpeed = 600;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        type = "burning";
        sprite = staticSprites.get("dynamitePj");
        hitSound = sounds.get("smallExplosion");
        this.effectRadius = effectRadius;
    }

    @Override
    public void die() {
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            topParticles.add(FireExplosionDebris.create(p, position.x, position.y, p.random(0, 360), p.random(300, 500)));
        }
        topParticles.add(LargeFireExplosion.create(p, position.x, position.y, p.random(0, 360)));
        projectiles.remove(this);
    }
}