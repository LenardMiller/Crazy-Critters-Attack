package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class TreeSprite extends Enemy {

    public TreeSprite(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        maxSpeed = .4f;
        speed = maxSpeed;
        moneyDrop = 15;
        damage = 2;
        maxHp = 40; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeSprite";
        numAttackFrames = 50;
        numMoveFrames = 30;
        betweenWalkFrames = 1;
        attackStartFrame = 28;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(50,50);
        partSize = new PVector(21,21);
        betweenCorpseFrames = 5;
        dieSound = "leaves";
        overkillSound = "leavesImpact";
        loadSprites();
    }
}