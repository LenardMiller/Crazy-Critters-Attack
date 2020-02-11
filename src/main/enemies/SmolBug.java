package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class SmolBug extends Enemy{
    public SmolBug(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(12,12);
        pfSize = 1;
        radius = 6;
        maxSpeed = .4f;
        speed = maxSpeed;
        moneyDrop = 1;
        twDamage = 1;
        maxHp = 10; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "smolBug";
        numAttackFrames = 34;
        numMoveFrames = 8;
        betweenWalkFrames = 3;
        startFrame = 15; //attack start
        attackFrame = startFrame;
        loadSprites();
    }
}