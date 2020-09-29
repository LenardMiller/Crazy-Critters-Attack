package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.enemies;

public class Decay extends Buff {

    private int damage;

    public Decay(PApplet p, int enId, int damage, int duration, Turret turret) {
        super(p,enId,turret);
        effectDelay = 6; //frames
        effectTimer = p.frameCount + effectDelay;
        lifeDuration = duration; //frames
        lifeTimer = p.frameCount + lifeDuration;
        particle = "decay";
        name = "decay";
        this.damage = damage;
        this.enId = enId;
        this.turret = turret;
    }

    public void effect() {
        Enemy enemy = enemies.get(enId);
        enemy.barTrans = 255;
        enemy.damageSimple(damage,turret, "decay");
        effectTimer = p.frameCount + effectDelay;
    }
}