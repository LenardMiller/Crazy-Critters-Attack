package main.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.MediumExplosion;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class EnergyBlast extends Projectile {

    private final boolean BIG_EXPLOSION;

    public EnergyBlast(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius, boolean bigExplosion, int maxSpeed) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 18);
        radius = 22;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("energyPj");
        hitSound = sounds.get("energyImpact");
        this.effectRadius = effectRadius;
        type = Enemy.DamageType.energy;
        particleTrail = "energy";
        debrisTrail = damage > 800 ? particleTrail : null;
        trainChance = maxSpeed > 1200 ? 1 : 3;
        this.BIG_EXPLOSION = bigExplosion;
    }

    @Override
    protected void trail() { //leaves a trail of particles
        if (turret.boostedDamage() > 0 && p.random(trainChance) > 1) {
            topParticles.add(new MiscParticle(p, position.x, position.y,
                    p.random(TWO_PI), "orangeMagic"));
        }
        if (particleTrail != null) {
            if (p.random(3) > 1) {
                topParticles.add(new MiscParticle(p, position.x, position.y,
                        p.random(0, TWO_PI), particleTrail));
            }
            if (damage > 800) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y,
                        p.random(angle - 0.4f, angle + 0.4f), particleTrail,
                        p.random( maxSpeed / 2f, maxSpeed)));
            }
        }
    }

    @Override
    public void die() {
        if (!BIG_EXPLOSION) {
            int num = (int) (p.random(10, 16));
            for (int i = 0; i < num; i++) {
                PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), "energy"));
            }
            for (int j = num; j >= 0; j--) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(360), "energy", p.random(100,200)));
            }
            topParticles.add(new MediumExplosion(p, position.x, position.y, p.random(360), "energy"));
        } else {
            int num = (int) (p.random(16, 42));
            for (int j = num; j >= 0; j--) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(360), "energy", p.random(100,200)));
            }
            for (int i = 0; i < num; i++) {
                PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), "energy"));
            }
            topParticles.add(new LargeExplosion(p, position.x, position.y, p.random(360), "energy"));
            for (int i = 0; i < (num / 10) + 1; i++) {
                PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius / 2f);
                topParticles.add(new MediumExplosion(p, pos.x, pos.y, p.random(360), "energy"));
            }
        }
        projectiles.remove(this);
    }
}


