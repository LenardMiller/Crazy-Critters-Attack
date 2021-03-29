package main.shockwaves;

import main.particles.BuffParticle;
import main.particles.ExplosionDebris;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.particles;

public class FireShockwave extends Shockwave {

    public FireShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle, float width, int damage,
                         Turret turret, float effectLevel, float effectDuration) {
        super(p, centerX, centerY, startingRadius, maxRadius, angle, width, damage, turret);

        damageType = "burning";
        buff = "burning";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }

    protected void display() {
        float a;
        PVector pos;
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "smoke"));
        for (int i = 0; i < P.random(1,3); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new BuffParticle(P, pos.x, pos.y, a, "fire"));
        }
        for (int i = 0; i < P.random(10, 15); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100,200)));
        }
    }
}
