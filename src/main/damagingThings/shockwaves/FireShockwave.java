package main.damagingThings.shockwaves;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.topParticles;
import static main.particles.Particle.ParticleTypes.*;

public class FireShockwave extends Shockwave {

    public FireShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, int damage,
                         Turret turret, float effectLevel, float effectDuration) {
        super(p, centerX, centerY, startingRadius, maxRadius, 0, 720, damage, turret);

        damageType = "burning";
        buff = "burning";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }

    @Override
    protected void spawnParticles() {
        float a;
        PVector pos;
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(Smoke.create(P, pos.x, pos.y, a));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(Fire.create(P, pos.x, pos.y, a));
        for (int i = 0; i < P.random(5, 10); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(FireExplosionDebris.create(P, pos.x, pos.y, a, P.random(100,200)));
        }
    }
}
