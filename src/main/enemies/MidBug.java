package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class MidBug extends Enemy{
    public MidBug(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        maxSpeed = .5f;
        speed = maxSpeed;
        dangerLevel = 1;
        twDamage = 2;
        maxHp = 40; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "redOuch";
        name = "midBug";
        numAttackFrames = 42;
        numMoveFrames = 32;
        startFrame = 34; //attack start
        attackFrame = startFrame;
        loadSprites();
    }
}