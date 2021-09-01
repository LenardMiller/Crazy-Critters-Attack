package main.buffs.stunned;

import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.misc.Utilities.secondsToFrames;

public class Frozen extends Stunned {

    public Frozen(PApplet p, int enId, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(0.5f);
        particle = null;
        name = "frozen";
        this.enId = enId;
    }
}