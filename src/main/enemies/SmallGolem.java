package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class SmallGolem extends Enemy{

    public SmallGolem(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        speed = 30;
        moneyDrop = 30;
        damage = 5;
        maxHp = 100; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "smallGolem";
        betweenAttackFrames = down60ToFramerate(2);
        betweenWalkFrames = down60ToFramerate(3);
        attackDmgFrames = new int[]{12};
        corpseSize = new PVector(50,50);
        partSize = new PVector(24,24);
        betweenCorpseFrames = down60ToFramerate(5);
        dieSound = sounds.get("gravelDie");
        overkillSound = sounds.get("gravelExplode");
        attackSound = sounds.get("swooshPunch");
        moveSoundLoop = moveSoundLoops.get("stonesMove");
        loadStuff();
    }
}