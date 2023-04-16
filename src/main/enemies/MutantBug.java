package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class MutantBug extends Enemy {

    public MutantBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(100,100);
        pfSize = 4;
        radius = 40;
        speed = 25;
        moneyDrop = 5000;
        damage = 100;
        maxHp = 750000;
        hp = maxHp;
        hitParticle = HitParticle.glowOuch;
        name = "mutantBug";
        betweenWalkFrames = down60ToFramerate(20);
        attackDmgFrames = new int[]{15};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        betweenCorpseFrames = 5;
        corpseLifespan = 12;
        corpseSize = size;
        partSize = new PVector(43,43);
        dieSound = sounds.get("bigCrunchRoar");
        overkillSound = sounds.get("squashRoar");
        attackSound = sounds.get("whipCrack");
        moveSoundLoop = moveSoundLoops.get("bigBugLoop");
        loadStuff();
    }
}