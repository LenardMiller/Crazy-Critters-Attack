package main.projectiles;

import main.enemies.Enemy;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.particles.Vortex;
import main.projectiles.shockwaves.DarkShockwave;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class DarkBlast extends Projectile {

    public DarkBlast(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius, int maxSpeed) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 18);
        radius = 22;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("darkPj");
        hitSound = sounds.get("darkImpact");
        this.effectRadius = effectRadius;
        type = Enemy.DamageType.dark;
        particleTrail = "dark";
        debrisTrail = damage > 800 ? particleTrail : null;
        trainChance = maxSpeed > 1200 ? 1 : 3;
    }

    protected void trail() { //leaves a trail of particles
        topParticles.add(new MiscParticle(p, position.x, position.y,
                p.random(TWO_PI), particleTrail));
        topParticles.add(new ExplosionDebris(p, position.x, position.y,
                p.random(angle - 0.4f, angle + 0.4f), particleTrail,
                p.random( maxSpeed / 2f, maxSpeed)));
    }

    @Override
    public void die() {
        int numRings = effectRadius / 5;
        for (int i = 0; i < numRings; i++) {
            for (int j = 0; j < p.random(10, 16); j++) {
                float radius = (effectRadius/ (float) numRings) * i;
                PVector displacement = PVector.fromAngle(p.random(TWO_PI));
                displacement.setMag(radius);
                topParticles.add(new Vortex(p, new PVector(position.x, position.y), displacement, radius));
            }
        }
        projectiles.remove(this);
        shockwaves.add(new DarkShockwave(p, position.x, position.y, 0,
                effectRadius, turret));
    }
}


