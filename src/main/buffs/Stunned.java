package main.buffs;

import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Stunned extends Buff {

    public Stunned(PApplet p, int enId, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(2);
        particle = "stun";
        name = "stunned";
        this.enId = enId;
    }

    public void effect() { //slowing enemy movement done every frame
        enemies.get(enId).immobilized = true;
    }

    protected void end(int i){ //ends if at end of lifespan
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) {
            enemies.get(enId).immobilized = false;
            buffs.remove(i);
        }
    }
}