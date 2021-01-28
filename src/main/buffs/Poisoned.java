package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.enemies;

public class Poisoned extends Buff{
    public Poisoned(PApplet p, int enId, Turret turret){
        super(p,enId,turret);
        particleChance = 16;
        effectDelay = 60; //frames
        lifeDuration = 360;
        lifeTimer = p.frameCount + lifeDuration;
        particle = "poison";
        name = "poisoned";
        this.enId = enId;
    }

    public void effect(){ //a bit of damage a second
        Enemy enemy = enemies.get(enId);
        enemy.tintColor = 0;
        enemy.barTrans = 255;
        enemy.damageSimple(25,turret, "poison", new PVector(0,0), false);
    }
}