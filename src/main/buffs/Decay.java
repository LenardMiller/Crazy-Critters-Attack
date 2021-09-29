package main.buffs;

import main.enemies.Enemy;
import main.particles.Floaty;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Decay extends Buff {

    private final int DAMAGE;

    public Decay(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        effectDelay = secondsToFrames(0.1f); //frames
        lifeDuration = secondsToFrames(duration); //frames
        particle = "decay";
        name = "decay";
        particleChance = 4;
        this.DAMAGE = (int) damage;
        this.enId = enId;
        this.turret = turret;
    }

    @Override
    public void effect() {
        Enemy enemy = enemies.get(enId);
        enemy.barAlpha = 255;
        enemy.damageWithoutBuff(DAMAGE,turret, "decay", new PVector(0,0), false);
        effectTimer = p.frameCount + effectDelay;
    }

    @Override
    protected void display() {
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            if (p.random(particleChance) < 1) {
                topParticles.add(new MiscParticle(p,
                        (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1,
                                (enemy.size.x / 2))), (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1,
                        (enemy.size.x / 2))), p.random(0, 360), particle));
            } if (p.random(particleChance * 2f) < 1) {
                topParticles.add(new Floaty(p,
                        (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))),
                        (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1,
                        (enemy.size.x / 2))), p.random(25, 35), "smokeCloud"));
            }
        }
    }
}