package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.from60ToFramerate;

public class SmallGolem extends Enemy{

    public SmallGolem(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 15;
        maxSpeed = 30;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 4;
        maxHp = 80; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "smallGolem";
        betweenAttackFrames = from60ToFramerate(2);
        betweenWalkFrames = from60ToFramerate(1);
        attackStartFrame = 14;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(50,50);
        partSize = new PVector(24,24);
        betweenCorpseFrames = from60ToFramerate(5);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        loadSprites();
    }
}