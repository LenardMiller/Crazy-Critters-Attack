package main.shockwaves;

import main.particles.*;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.particles;

public class NuclearShockwave extends Shockwave {

    public NuclearShockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage,
                            Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, angle, width, damage, turret);

        damageType = "burning";
    }

    protected void display() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        particles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "smoke"));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "nuclear"));
        for (int i = 0; i < P.random(2, 5); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            boolean small = radius < MAX_RADIUS / 4 || P.random(4) < 3;
            if (small) particles.add(new LargeExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
            else particles.add(new MediumExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
        }
        for (int i = 0; i < P.random(1, 4); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "nuclear", P.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100,200)));
        }
    }
}
