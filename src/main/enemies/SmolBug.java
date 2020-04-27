package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class SmolBug extends Enemy {
    public SmolBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        maxSpeed = .4f;
        speed = maxSpeed;
        moneyDrop = 10;
        damage = 1;
        maxHp = 15; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "smolBug";
        numAttackFrames = 27;
        numMoveFrames = 8;
        betweenWalkFrames = 3;
        betweenAttackFrames = 3;
        attackStartFrame = 1; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{17};
        loadSprites();
    }
}