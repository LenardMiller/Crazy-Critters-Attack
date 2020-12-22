package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class TreeGiant extends Enemy {

    public TreeGiant(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(76,76);
        pfSize = 3;
        radius = 30;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 250;
        damage = 7;
        maxHp = 5000; //Hp
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeGiant";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{28};
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(152,152);
        partSize = new PVector(68,68);
        corpseLifespan = 750;
        dieSound = soundsH.get("bigLeaves");
        overkillSound = soundsH.get("bigLeavesImpact");
        loadSprites();
    }
}