package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class LittleWorm extends Enemy {

    public LittleWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 20; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "worm";
        numAttackFrames = 34;
        numMoveFrames = 1;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{18};
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        stealthy = true;
        corpseSize = new PVector(5,5);
        loadSprites();
    }
}