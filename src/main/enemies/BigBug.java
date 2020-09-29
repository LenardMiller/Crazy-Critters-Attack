package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class BigBug extends Enemy{

    public BigBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(53,53);
        pfSize = 2; //2
        radius = 26;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 80;
        damage = 15;
        maxHp = 80; //80
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "bigBug";
        numAttackFrames = 100;
        numMoveFrames = 12;
        betweenWalkFrames = 3;
        attackStartFrame = 48; //attack start
        corpseSize = size;
        attackFrame = attackStartFrame;
        loadSprites();
    }
}