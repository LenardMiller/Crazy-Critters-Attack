package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class Emperor extends Enemy{

    public Emperor(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2; //2
        radius = 25;
        maxSpeed = .45f;
        speed = maxSpeed;
        moneyDrop = 200;
        damage = 12;
        maxHp = 2000;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "emperor";
        numAttackFrames = 15;
        betweenAttackFrames = 10;
        numMoveFrames = 8;
        betweenWalkFrames = 4;
        attackDmgFrames = new int[]{7};
        attackStartFrame = 0; //attack start
        corpseSize = size;
        partSize = new PVector(18,18);
        attackFrame = attackStartFrame;
        dieSound = soundsH.get("bigCrunch");
        overkillSound = soundsH.get("squash");
        loadSprites();
    }
}