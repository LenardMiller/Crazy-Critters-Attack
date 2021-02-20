package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class Butterfly extends Enemy {

    public Butterfly(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .8f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 60; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "butterfly";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = 5;
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(25,25);
        partSize = new PVector(18,18);
        betweenCorpseFrames = 4;
        overkillSound = soundsH.get("squish");
        dieSound = soundsH.get("crunch");
        loadSprites();
    }
}