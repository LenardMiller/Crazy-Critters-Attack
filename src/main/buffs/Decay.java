package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.enemies;
import static main.misc.Utilities.secondsToFrames;

public class Decay extends Buff {

    private final int DAMAGE;

    public Decay(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        effectDelay = secondsToFrames(0.1f); //frames
        lifeDuration = secondsToFrames(duration); //frames
        particle = "decay";
        name = "decay";
        this.DAMAGE = (int) damage;
        this.enId = enId;
        this.turret = turret;
    }

    public void effect() {
        Enemy enemy = enemies.get(enId);
        enemy.barTrans = 255;
        enemy.damageWithoutBuff(DAMAGE,turret, "decay", new PVector(0,0), false);
        effectTimer = p.frameCount + effectDelay;
    }
}