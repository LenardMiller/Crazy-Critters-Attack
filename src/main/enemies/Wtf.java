package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Wtf extends Enemy {

    public Wtf(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(100,100);
        pfSize = 4;
        radius = 40;
        speed = 18;
        moneyDrop = 2500;
        damage = 40;
        maxHp = 120000;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "wtf";
        betweenWalkFrames = down60ToFramerate(20);
        attackDmgFrames = new int[]{11};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(8);
        corpseLifespan = 12;
        corpseSize = size;
        partSize = new PVector(100,100);
        dieSound = sounds.get("bigCrunchRoar");
        overkillSound = sounds.get("squashRoar");
        attackSound = sounds.get("bugGrowlSlow");
        moveSoundLoop = moveSoundLoops.get("bigBugLoop");
        loadStuff();
    }
}