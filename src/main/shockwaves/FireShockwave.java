package main.shockwaves;

import main.particles.BuffParticle;
import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.particles;

public class FireShockwave extends Shockwave {

    public FireShockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage,
                         Turret turret, float effectLevel, float effectDuration) {
        super(p, centerX, centerY, maxRadius, angle, width, damage, turret);

        damageType = "burning";
        buff = "burning";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
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
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "fire"));
        for (int i = 0; i < P.random(3, 6); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100,200)));
        }
    }
}
