package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Twisted extends Enemy {

    public Twisted(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2; //2
        radius = 25;
        speed = 30;
        moneyDrop = 750;
        damage = 30;
        maxHp = 45000; //Hp
        hp = maxHp;
        hitParticle = HitParticle.brownLeafOuch;
        name = "twisted";
        attackDelay = down60ToFramerate(10);
        walkDelay = down60ToFramerate(20);
        attackDmgFrames = new int[]{4};
        corpseSize = new PVector(50,50);
        partSize = new PVector(26,26);
        betweenCorpseFrames = down60ToFramerate(6);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        attackSound = sounds.get("swooshPunch");
        moveSoundLoop = moveSoundLoops.get("leafyStepsLoop");
        loadStuff();
    }
}