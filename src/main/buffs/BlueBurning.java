package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.buffs;
import static main.Main.enemies;
import static main.misc.Utilities.secondsToFrames;

public class BlueBurning extends Buff {

    private final int DAMAGE;

    public BlueBurning(PApplet p, int enId, float damage, float duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = secondsToFrames(0.2f);
        lifeDuration = secondsToFrames(duration);
        this.DAMAGE = (int) damage;
        name = "blueBurning";
        particle = "blueGreenFire";
        this.enId = enId;
    }

    public void effect() { //small damage fast
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            enemy.barAlpha = 255;
            enemy.damageWithoutBuff(DAMAGE, turret, "blueBurning", new PVector(0, 0), false);
        }
    }
}