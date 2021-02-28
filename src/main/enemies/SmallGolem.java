package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class SmallGolem extends Enemy{

    public SmallGolem(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        maxSpeed = .5f;
        speed = maxSpeed;
        moneyDrop = 45;
        damage = 6;
        maxHp = 120; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "smallGolem";
        betweenAttackFrames = 2;
        betweenWalkFrames = 1;
        attackStartFrame = 14;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(50,50);
        partSize = new PVector(24,24);
        betweenCorpseFrames = 5;
        dieSound = soundsH.get("leaves");
        overkillSound = soundsH.get("leavesImpact");
        loadSprites();
    }
}