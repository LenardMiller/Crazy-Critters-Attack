package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Emperor extends Enemy {

    public Emperor(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2; //2
        radius = 25;
        speed = 27;
        moneyDrop = 200;
        damage = 18;
        maxHp = 4000;
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "emperor";
        betweenAttackFrames = down60ToFramerate(10);
        betweenWalkFrames = down60ToFramerate(12);
        attackDmgFrames = new int[]{7};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(20,20);
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        attackSound = sounds.get("whipCrack");
        moveSoundLoop = moveSoundLoops.get("bigBugLoop");
        loadStuff();
    }
}