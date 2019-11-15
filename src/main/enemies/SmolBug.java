package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class SmolBug extends Enemy{
    public SmolBug(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(12,12);
        radius = 6;
        maxSpeed = .4f;
        speed = maxSpeed;
        dangerLevel = 1;
        twDamage = 1;
        maxHp = 10; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "redOuch";
        name = "smolBug";
        numAttackFrames = 34;
        numMoveFrames = 24;
        startFrame = 15; //attack start
        attackFrame = startFrame;
        loadSprites();
    }
}