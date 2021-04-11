package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class TreeSprite extends Enemy{

    public TreeSprite(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 15;
        damage = 2;
        maxHp = 40; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeSprite";
        betweenAttackFrames = down60ToFramerate(2);
        betweenWalkFrames = down60ToFramerate(3);
        attackStartFrame = 28;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(50,50);
        partSize = new PVector(21,21);
        betweenCorpseFrames = down60ToFramerate(5);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        loadStuff();
    }
}