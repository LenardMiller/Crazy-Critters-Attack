package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.misc.Utilities.secondsToFrames;

public class Poisoned extends Buff {

    public Poisoned(PApplet p, Enemy target, Turret turret){
        super(p, target, turret);
        particleChance = 16;
        effectDelay = secondsToFrames(1); //frames
        lifeDuration = secondsToFrames(6);
        particle = "poison";
        name = "poisoned";
    }

    @Override
    public void effect(){ //a bit of damage a second
        target.showBar = true;
        target.damageWithoutBuff(25,turret, Enemy.DamageType.poisoned, new PVector(0,0), false);
    }
}