package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.particles.Pile;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Bleeding extends Buff {

    public Bleeding(PApplet p, int enId, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = 12; //frames
        lifeDuration = 360;
        particle = null;
        name = "bleeding";
        this.enId = enId;
    }

    public void effect() { //small damage fast
        Enemy enemy = enemies.get(enId);
        enemy.barTrans = 255;
        enemy.damageWithoutBuff(15, turret, "none", new PVector(0,0), false);
    }

    void display() { //particles around enemy
        Enemy enemy = enemies.get(enId);
        int num = (int)(p.random(0, particleChance));
        if (num == 0) {
            particles.add(new Ouch(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), enemy.hitParticle));
        } if (p.random(0, particleChance * 4) < 1) {
            underParticles.add(new Pile(p, (float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), 0, enemy.hitParticle));
        }
    }
}