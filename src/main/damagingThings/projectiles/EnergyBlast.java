package main.damagingThings.projectiles;

import main.particles.ExplosionDebris;
import main.particles.MediumExplosion;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.particles.Particle.ParticleTypes.LargeEnergyExplosion;

public class EnergyBlast extends Projectile {

    private final boolean BIG_EXPLOSION;

    public EnergyBlast(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius, boolean bigExplosion) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 18);
        radius = 22;
        maxSpeed = 1000;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("energyPj");
        hitSound = sounds.get("energyImpact");
        this.effectRadius = effectRadius;
        type = "energy";
        trail = "energy";
        this.BIG_EXPLOSION = bigExplosion;
    }

    @Override
    public void die() {
        if (!BIG_EXPLOSION) {
            int num = (int) (p.random(10, 16));
            for (int j = num; j >= 0; j--) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "energy", p.random(100,200)));
            }
            topParticles.add(new MediumExplosion(p, position.x, position.y, p.random(0, 360), "energy"));
        } else {
            int num = (int) (p.random(16, 42));
            for (int j = num; j >= 0; j--) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "energy", p.random(100,200)));
            }
            topParticles.add(LargeEnergyExplosion.create(p, position.x, position.y, p.random(0, 360)));
        }
        projectiles.remove(this);
    }
}


