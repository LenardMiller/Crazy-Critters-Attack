package main.buffs;

import main.enemies.Enemy;
import main.particles.ExplosionDebris;
import main.particles.MediumExplosion;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getRandomPointInRange;
import static main.misc.Utilities.secondsToFrames;

public class Burning extends Buff {

    public Burning(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = secondsToFrames(0.2f);
        lifeDuration = secondsToFrames(duration);
        effectLevel = damage;
        particle = "fire";
        name = "burning";
        this.enId = enId;
    }

    @Override
    public void effect() { //small damage fast
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            enemy.showBar = true;
            enemy.damageWithoutBuff((int) effectLevel, turret, Enemy.DamageType.burning, new PVector(0, 0), false);
        }
    }

    @Override
    protected void spawnParticles() {
        if (particle != null) {
            if (enId < 0) buffs.remove(this);
            else {
                Enemy enemy = enemies.get(enId);
                if (p.random(particleChance) < 1) {
                    PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
                    topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(TWO_PI), particle));
                }
                if (effectLevel > 15 && p.random(3) < 1) {
                    PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
                    topParticles.add(new ExplosionDebris(p, pos.x, pos.y, p.random(TWO_PI),
                            particle, p.random(100, 200)));
                }
            }
        }
    }
}