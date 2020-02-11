package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class TreeGiant extends Enemy {

    public TreeGiant(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(76,76);
        pfSize = 3;
        radius = 30;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 110;
        twDamage = 25;
        maxHp = 150; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "treeGiant";
        numAttackFrames = 63;
        numMoveFrames = 91;
        startFrame = 0;
        attackDmgFrames = new int[]{28};
        betweenAttackFrames = 2;
        attackFrame = startFrame;
        loadSprites();
    }
}