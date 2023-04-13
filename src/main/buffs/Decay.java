package main.buffs;

import main.enemies.Enemy;
import main.particles.Floaty;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getRandomPointInRange;
import static main.misc.Utilities.secondsToFrames;

public class Decay extends Buff {

    public Decay(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        effectDelay = secondsToFrames(0.1f); //frames
        lifeDuration = secondsToFrames(duration); //frames
        particle = "decay";
        name = "decay";
        particleChance = 4;
        effectLevel = damage;
        this.enId = enId;
        this.turret = turret;
    }

    @Override
    public void effect() {
        Enemy enemy = enemies.get(enId);
        enemy.showBar = true;
        enemy.damageWithoutBuff((int) effectLevel, turret, "decay", new PVector(0,0), false);
        effectTimer = p.frameCount + effectDelay;
    }

    @Override
    protected void spawnParticles() {
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            if (p.random(particleChance) < 1) {
                PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), particle));
            } if (p.random(particleChance * 2) < 1) {
                PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.2f);
                topParticles.add(new Floaty(p, pos.x, pos.y, p.random(25, 35), "smokeCloud"));
            }
        }
    }
}