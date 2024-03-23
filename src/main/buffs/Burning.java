package main.buffs;

import main.enemies.Enemy;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.TWO_PI;
import static main.Main.topParticles;
import static main.misc.Utilities.getRandomPointInRange;
import static main.misc.Utilities.secondsToFrames;

public class Burning extends Buff {

    public Burning(PApplet p, Enemy target, float damage, float duration, Turret turret) {
        super(p, target, turret);
        particleChance = 4;
        effectDelay = secondsToFrames(0.2f);
        lifeDuration = secondsToFrames(duration);
        effectLevel = damage;
        particle = "fire";
        name = "burning";
    }

    @Override
    public void effect() { //small damage fast
        target.showBar = true;
        target.damageWithoutBuff((int) effectLevel, turret, Enemy.DamageType.burning,
                new PVector(0, 0), false);
    }

    @Override
    protected void spawnParticles() {
        if (particle != null) {
            if (p.random(particleChance) < 1) {
                PVector pos = getRandomPointInRange(p, target.position, target.size.mag() * 0.4f);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(TWO_PI), particle));
            }
            if (effectLevel > 15 && p.random(3) < 1) {
                PVector pos = getRandomPointInRange(p, target.position, target.size.mag() * 0.4f);
                topParticles.add(new ExplosionDebris(p, pos.x, pos.y, p.random(TWO_PI),
                        particle, p.random(100, 200)));
            }
        }
    }
}