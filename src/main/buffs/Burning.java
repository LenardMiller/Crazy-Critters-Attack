package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import processing.core.PApplet;

import static main.Main.enemies;
import static main.Main.particles;
import static processing.core.PApplet.round;

public class Burning extends Buff{
    public Burning(PApplet p, int enId){
        super(p,enId);
        effectDelay = 12; //frames
        lifeDuration = round(p.random(2500,15000)); //milliseconds
        particle = "fire";
        this.enId = enId;
    }

    private void effect(){ //small damage fast
        Enemy enemy = enemies.get(enId);
        if (enemy.tintColor > 100){
            enemy.tintColor = 100;
        }
        enemy.barTrans = 255;
        enemy.hp--;
        int num = (int)(p.random(0,2));
        if (num == 0){ //small red splatter
            particles.add(new Ouch(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), "redOuch"));
        }
    }
}