package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class MidBug extends Enemy {

    public MidBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 13;
        speed = 30;
        moneyDrop = 20;
        damage = 3;
        maxHp = 40;
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "midBug";
        betweenWalkFrames = down60ToFramerate(6);
        betweenAttackFrames = down60ToFramerate(2);
        attackDmgFrames = new int[]{7};
        corpseSize = size;
        partSize = new PVector(14,14);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        attackSound = sounds.get("bugGrowlVeryQuick");
        moveSoundLoop = moveSoundLoops.get("smallBugLoop");
        loadStuff();
    }
}