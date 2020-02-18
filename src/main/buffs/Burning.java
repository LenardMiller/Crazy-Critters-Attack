package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.enemies;
import static main.Main.particles;
import static processing.core.PApplet.round;

public class Burning extends Buff{
    public Burning(PApplet p, int enId, Turret turret){
        super(p,enId,turret);
        effectDelay = 12; //frames
        lifeDuration = round(p.random(2500,15000)); //milliseconds
        particle = "fire";
        name = "burning";
        this.enId = enId;
    }

    public void effect(){ //small damage fast
        Enemy enemy = enemies.get(enId);
        if (enemy.tintColor > 100){
            enemy.tintColor = 100;
        }
        enemy.barTrans = 255;
        enemy.effectDamage(1,turret);
        int num = (int)(p.random(0,2));
        if (num == 0){ //small red splatter
            particles.add(new Ouch(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), "redOuch"));
        }
    }
}