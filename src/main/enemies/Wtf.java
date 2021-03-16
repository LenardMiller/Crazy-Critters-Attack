package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class Wtf extends Enemy {

    public Wtf(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(100,100);
        pfSize = 4;
        radius = 40;
        maxSpeed = .2f;
        speed = maxSpeed;
        moneyDrop = 1000;
        damage = 25;
        maxHp = 10000;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "wtf";
        betweenWalkFrames = 3;
        attackStartFrame = 0; //attack start
        attackDmgFrames = new int[]{11};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = 10;
        corpseLifespan = 750;
        corpseSize = size;
        partSize = new PVector(100,100);
        attackFrame = attackStartFrame;
        dieSound = sounds.get("bigCrunchRoar");
        overkillSound = sounds.get("squashRoar");
        loadSprites();
    }
}