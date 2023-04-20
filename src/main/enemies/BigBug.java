package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class BigBug extends Enemy {

    public BigBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(53,53);
        pfSize = 2;
        radius = 26;
        speed = 18;
        moneyDrop = 100;
        damage = 10;
        maxHp = 1750;
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "bigBug";
        betweenWalkFrames = down60ToFramerate(13);
        attackDmgFrames = new int[]{9};
        corpseSize = size;
        partSize = new PVector(32,32);
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        attackSound = sounds.get("bugGrowlQuick");
        moveSoundLoop = moveSoundLoops.get("bigBugLoop");
        loadStuff();
    }
}