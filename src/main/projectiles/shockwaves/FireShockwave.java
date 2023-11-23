package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.topParticles;

public class FireShockwave extends Shockwave {

    public FireShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, int damage,
                         Turret turret, float effectLevel, float effectDuration) {
        super(p, centerX, centerY, startingRadius, maxRadius, 0, 720, damage, turret);

        damageType = Enemy.DamageType.burning;
        buff = "burning";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }

    public void display() {}

    @Override
    protected void spawnParticles() {
        if (P.random(3) < 1) {
            float a;
            PVector pos;
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "fire"));
            for (int i = 0; i < P.random(5, 10); i++) {
                a = randomAngle();
                pos = randomPosition(a);
                topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100, 200)));
            }
        }
    }
}
