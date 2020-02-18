package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.enemies;
import static main.Main.particles;

public class Poisoned extends Buff{
    public Poisoned(PApplet p, int enId, Turret turret){
        super(p,enId,turret);
        effectDelay = 60; //frames
        lifeDuration = 600;
        particle = "poison";
        name = "poisoned";
        this.enId = enId;
    }

    public void effect(){ //a bit of damage a second
        Enemy enemy = enemies.get(enId);
        enemy.tintColor = 0;
        enemy.barTrans = 255;
        enemy.effectDamage(3,turret);
        int num = PApplet.ceil(p.random(0,3));
        for (int j = num; j >= 0; j--){ //sprays green
            particles.add(new Ouch(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), "greenOuch"));
        }
    }
}