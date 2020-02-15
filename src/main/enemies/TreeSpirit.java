package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class TreeSpirit extends Enemy{

    public TreeSpirit(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        maxSpeed = .35f;
        speed = maxSpeed;
        moneyDrop = 50;
        twDamage = 4;
        maxHp = 50; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeSpirit";
        numAttackFrames = 42;
        numMoveFrames = 47;
        startFrame = 22;
        attackFrame = startFrame;
        loadSprites();
    }
}