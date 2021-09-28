package main.damagingThings.shockwaves;

import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SeismicShockwave extends Shockwave {


    public SeismicShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle, float width, int damage,
                            Turret turret) {
        super(p, centerX, centerY, startingRadius, maxRadius, angle, width, damage, turret);
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        bottomParticles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        bottomParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        for (int i = 0; i < P.random(5, 12); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            bottomParticles.add(new Debris(P, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < P.random(3, 6); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            bottomParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(100,200)));
        }
    }
}
