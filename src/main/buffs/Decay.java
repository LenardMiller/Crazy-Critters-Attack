package main.buffs;

import main.enemies.Enemy;
import processing.core.PApplet;

import static main.Main.enemies;

public class Decay extends Buff {

    private int damage;

    public Decay(PApplet p, int enId, int damage, int duration) {
        super(p,enId);
        effectDelay = 6; //frames
        effectTimer = p.frameCount + effectDelay;
        lifeDuration = duration; //frames
        lifeTimer = p.frameCount + lifeDuration;
        particle = "fire";
        name = "decay";
        this.damage = damage;
        this.enId = enId;
    }

    public void effect() {
        Enemy enemy = enemies.get(enId);
        enemy.barTrans = 255;
        enemy.hp -= damage;
        effectTimer = p.frameCount + effectDelay;
    }
}