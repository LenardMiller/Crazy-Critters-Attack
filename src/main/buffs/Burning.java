package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.enemies;

public class Burning extends Buff {

    private int damage;

    public Burning(PApplet p, int enId, int damage, int duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = 12; //frames
        lifeDuration = duration;
        this.damage = damage;
        particle = "fire";
        name = "burning";
        this.enId = enId;
    }

    public void effect() { //small damage fast
        Enemy enemy = enemies.get(enId);
        if (enemy.tintColor > 100){
            enemy.tintColor = 100;
        }
        enemy.barTrans = 255;
        enemy.effectDamage(damage,turret);
    }
}