package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class GiantGolem extends Enemy {

    public GiantGolem(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(76,76);
        pfSize = 3;
        radius = 30;
        maxSpeed = .35f;
        speed = maxSpeed;
        moneyDrop = 500;
        damage = 14;
        maxHp = 10000; //Hp
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "giantGolem";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{28};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(152,152);
        partSize = new PVector(68,68);
        corpseLifespan = 750;
        dieSound = sounds.get("bigLeaves");
        overkillSound = sounds.get("bigLeavesImpact");
        loadSprites();
    }
}