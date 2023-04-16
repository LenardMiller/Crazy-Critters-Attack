package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Roach extends Enemy {

    public Roach(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12;
        speed = 65;
        moneyDrop = 100;
        damage = 10;
        maxHp = 5000;
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "roach";
        betweenWalkFrames = down60ToFramerate(6);
        betweenAttackFrames = down60ToFramerate(4);
        attackDmgFrames = new int[]{3};
        corpseSize = size;
        partSize = new PVector(14,14);
        dieSound = sounds.get("squish");
        overkillSound = sounds.get("crunch");
        attackSound = sounds.get("bugGrowlVeryQuick");
        moveSoundLoop = moveSoundLoops.get("smallBugLoop");
        loadStuff();
    }
}
