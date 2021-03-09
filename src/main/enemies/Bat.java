package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class Bat extends Enemy {

    public Bat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .5f;
        speed = maxSpeed;
        moneyDrop = 25;
        damage = 2;
        maxHp = 250; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "bat";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = 10;
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(50,50);
        partSize = new PVector(23, 23);
        betweenWalkFrames = 4;
        betweenCorpseFrames = 4;
        overkillSound = soundsH.get("squeakSquish");
        dieSound = soundsH.get("squeak");
        loadSprites();
    }
}