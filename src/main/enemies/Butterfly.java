package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class Butterfly extends Enemy {

    public Butterfly(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .8f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 20; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "butterfly";
        numAttackFrames = 16;
        numMoveFrames = 8;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        betweenAttackFrames = 5;
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(18,18);
        betweenCorpseFrames = 4;
        loadSprites();
    }
}