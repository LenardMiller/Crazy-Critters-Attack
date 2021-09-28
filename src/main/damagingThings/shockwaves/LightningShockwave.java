package main.damagingThings.shockwaves;

import main.particles.Debris;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.particles.Particle.ParticleTypes.ElectricityExplosionDebris;

public class LightningShockwave extends Shockwave {

    public LightningShockwave(PApplet p, float centerX, float centerY, int maxRadius, int damage, Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, 0, 720, damage, turret);

        damageType = "electricity";
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "electricity"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        for (int i = 0; i < P.random(5, 12); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new Debris(P, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < P.random(5, 10); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(ElectricityExplosionDebris.create(P, pos.x, pos.y, a, P.random(100,200)));
        }
    }
}
