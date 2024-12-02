package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class RoboBug extends Enemy {

    public RoboBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 13;
        speed = 30;
        moneyDrop = 20;
        damage = 10;
        maxHp = 600;
        hp = maxHp;
        hitParticle = HitParticle.oilOuch;
        name = "roboBug";
        betweenWalkFrames = down60ToFramerate(6);
        betweenAttackFrames = down60ToFramerate(4);
        attackDmgFrames = new int[]{11};
        corpseSize = size;
        partSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        attackSound = sounds.get("bugGrowlVeryQuick");
        moveSoundLoop = moveSoundLoops.get("smallBugLoop");
        loadStuff();
    }
}