package main.buffs;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.particles.Pile;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getRandomPointInRange;
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
        if (!gore) return;

        Enemy enemy = enemies.get(enId);

        if (p.random(particleChance) < 1) {
            PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
            topParticles.add(new Ouch(p, pos.x, pos.y, p.random(360), enemy.hitParticle));
        } if (p.random(particleChance * 4) < 1) {
            PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.2f);
            bottomParticles.add(new Pile(p, pos.x, pos.y, 0, enemy.hitParticle));
        }
    }
}