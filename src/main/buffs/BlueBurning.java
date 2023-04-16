package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.buffs;
import static main.Main.enemies;
import static main.misc.Utilities.secondsToFrames;

public class BlueBurning extends Buff {

    public BlueBurning(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = secondsToFrames(0.2f);
        lifeDuration = secondsToFrames(duration);
        effectLevel = damage;
        name = "blueBurning";
        particle = "blueGreenFire";
        this.enId = enId;
    }

    @Override
    public void effect() { //small damage fast
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            enemy.showBar = true;
            enemy.damageWithoutBuff((int) effectLevel, turret, Enemy.DamageType.blueBurning, new PVector(0, 0), false);
        }
    }
}