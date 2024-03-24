package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.misc.Utilities.secondsToFrames;

public class BlueBurning extends Buff {

    public BlueBurning(PApplet p, Enemy target, float damage, float duration, Turret turret) {
        super(p, target, turret);
        particleChance = 4;
        effectDelay = secondsToFrames(0.2f);
        lifeDuration = secondsToFrames(duration);
        effectLevel = damage;
        name = "blueBurning";
        particle = "blueGreenFire";
    }

    @Override
    public void effect() { //small damage fast
        target.showBar = true;
        target.damageWithoutBuff((int) effectLevel, turret, Enemy.DamageType.blueBurning,
                new PVector(0, 0), false);
    }
}