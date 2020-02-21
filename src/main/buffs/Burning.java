package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.enemies;
import static processing.core.PApplet.round;

public class Burning extends Buff {
    public Burning(PApplet p, int enId, Turret turret) {
        super(p,enId,turret);
        particleChance = 4;
        effectDelay = 12; //frames
        lifeDuration = round(p.random(120,600));
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
        enemy.effectDamage(1,turret);
    }
}