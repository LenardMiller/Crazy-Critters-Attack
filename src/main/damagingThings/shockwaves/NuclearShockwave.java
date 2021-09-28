package main.damagingThings.shockwaves;

import main.particles.*;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.topParticles;
import static main.particles.Particle.ParticleTypes.*;

public class NuclearShockwave extends Shockwave {

    public NuclearShockwave(PApplet p, float centerX, float centerY, int maxRadius, int damage, Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, 0, 720, damage, turret);

        damageType = "burning";
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        topParticles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "nuclear"));
        for (int i = 0; i < P.random(2, 5); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            boolean small = radius < MAX_RADIUS / 4 || P.random(4) < 3;
            if (small) topParticles.add(LargeFireExplosion.create(P, pos.x, pos.y, P.random(360)));
            else topParticles.add(MediumFireExplosion.create(P, pos.x, pos.y, P.random(360)));
        }
        for (int i = 0; i < P.random(1, 4); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(MetalExplosionDebris.create(P, pos.x, pos.y, a, P.random(100, 200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(NuclearExplosionDebris.create(P, pos.x, pos.y, a, P.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(FireExplosionDebris.create(P, pos.x, pos.y, a, P.random(100, 200)));
        }
    }
}
