package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.enemies;

public class Burning extends Buff {

    private final int DAMAGE;

    public Burning(PApplet p, int enId, float damage, int duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = 12; //frames
        lifeDuration = duration;
        this.DAMAGE = (int) damage;
        particle = "fire";
        name = "burning";
        this.enId = enId;
    }

    public void effect() { //small damage fast
        Enemy enemy = enemies.get(enId);
        enemy.barTrans = 255;
        enemy.damageWithoutBuff(DAMAGE,turret, "fire", new PVector(0,0), false);
    }
}