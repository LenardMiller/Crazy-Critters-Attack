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
        type = Enemy.DamageType.energy;
        trail = "energy";
        this.BIG_EXPLOSION = bigExplosion;
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


