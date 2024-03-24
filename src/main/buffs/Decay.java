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

    public Decay(PApplet p, Enemy target, float damage, float duration, Turret turret) {
        super(p, target, turret);
        effectDelay = secondsToFrames(0.1f); //frames
        lifeDuration = secondsToFrames(duration); //frames
        particle = "decay";
        name = "decay";
        particleChance = 4;
        effectLevel = damage;
    }

    @Override
    public void effect() {
        target.showBar = true;
        target.damageWithoutBuff((int) effectLevel, turret, Enemy.DamageType.decay,
                new PVector(0,0), false);
        effectTimer = p.frameCount + effectDelay;
    }

    @Override
    protected void spawnParticles() {
        if (p.random(particleChance) < 1) {
            PVector pos = getRandomPointInRange(p, target.position, target.size.mag() * 0.4f);
            topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), particle));
        } if (p.random(particleChance * 2) < 1) {
            PVector pos = getRandomPointInRange(p, target.position, target.size.mag() * 0.2f);
            topParticles.add(new Floaty(p, pos.x, pos.y, p.random(25, 35), "smokeCloud"));
        }
    }
}