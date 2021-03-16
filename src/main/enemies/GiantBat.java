package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class GiantBat extends Enemy {

    public GiantBat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(102,102);
        pfSize = 2;
        radius = 25;
        maxSpeed = .5f;
        speed = maxSpeed;
        moneyDrop = 500;
        damage = 20;
        maxHp = 10000; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "giantBat";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = 12;
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(100,100);
        partSize = new PVector(50, 50);
        betweenWalkFrames = 5;
        betweenCorpseFrames = 4;
        overkillSound = sounds.get("bigSqueakSquash");
        dieSound = sounds.get("bigSqueak");
        loadSprites();
    }
}