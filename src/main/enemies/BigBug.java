package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class BigBug extends Enemy{

    public BigBug(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(53,53);
        radius = 26;
        maxSpeed = .3f;
        speed = maxSpeed;
        dangerLevel = 1;
        twDamage = 15;
        maxHp = 80; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "redOuch";
        name = "bigBug";
        numAttackFrames = 100;
        numMoveFrames = 32;
        startFrame = 48; //attack start
        attackFrame = startFrame;
        loadSprites();
    }
}