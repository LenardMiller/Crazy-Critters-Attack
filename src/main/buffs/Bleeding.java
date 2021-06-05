package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.particles.Pile;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Bleeding extends Buff {

    public Bleeding(PApplet p, int enId, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(6);
        particle = null;
        name = "bleeding";
        this.enId = enId;
    }

    @Override
    public void effect() { //small damage fast
        Enemy enemy = enemies.get(enId);
        enemy.barAlpha = 255;
        enemy.damageWithoutBuff(15, turret, "none", new PVector(0,0), false);
    }

    @Override
    protected void display() { //particles around enemy
        Enemy enemy = enemies.get(enId);
        int num = (int)(p.random(0, particleChance));
        if (num == 0) {
            if (gore) topParticles.add(new Ouch(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))),
              (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), enemy.hitParticle));
        } if (p.random(0, particleChance * 4) < 1) {
            if (gore) bottomParticles.add(new Pile(p, (float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))),
              (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), 0, enemy.hitParticle));
        }
    }
}