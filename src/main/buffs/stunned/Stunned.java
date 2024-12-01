package main.buffs.stunned;

import main.buffs.Buff;
import main.enemies.Enemy;
import main.enemies.BurrowingEnemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Stunned extends Buff {

    public Stunned(PApplet p, Enemy target, Turret turret) {
        super(p, target, turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(2);
        particle = "stun";
        name = "stunned";
    }

    @Override
    public void effect() { //slowing enemy movement done every frame
        target.immobilized = true;
        if (target instanceof BurrowingEnemy) target.state = Enemy.State.Special;
    }

    @Override
    protected void updateTimer(){ //ends if at end of lifespan
        if (!isPaused) lifeTimer++;
        if (lifeTimer > lifeDuration) {
            target.immobilized = false;
            target.buffs.remove(this);
        }
    }
}