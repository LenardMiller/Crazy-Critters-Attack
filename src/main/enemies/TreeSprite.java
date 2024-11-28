package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class TreeSprite extends Enemy{

    public TreeSprite(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        speed = 24;
        moneyDrop = 15;
        damage = 2;
        maxHp = 40; //Hp <---------------------------
        hp = maxHp;
        hitParticle = HitParticle.leafOuch;
        name = "treeSprite";
        attackDelay = down60ToFramerate(2);
        walkDelay = down60ToFramerate(3);
        attackDmgFrames = new int[]{25};
        corpseSize = new PVector(50,50);
        partSize = new PVector(21,21);
        betweenCorpseFrames = down60ToFramerate(5);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        attackSound = sounds.get("swooshPunch");
        moveSoundLoop = moveSoundLoops.get("leafyStepsLoop");
        loadStuff();
    }
}