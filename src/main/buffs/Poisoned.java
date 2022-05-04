package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.enemies;
import static main.misc.Utilities.secondsToFrames;

public class Poisoned extends Buff {

    public Poisoned(PApplet p, int enId, Turret turret){
        super(p,enId,turret);
        particleChance = 16;
        effectDelay = secondsToFrames(1); //frames
        lifeDuration = secondsToFrames(6);
        particle = "poison";
        name = "poisoned";
        this.enId = enId;
    }

    @Override
    public void effect(){ //a bit of damage a second
        Enemy enemy = enemies.get(enId);
        enemy.showBar = true;
        enemy.damageWithoutBuff(25,turret, "poison", new PVector(0,0), false);
    }
}