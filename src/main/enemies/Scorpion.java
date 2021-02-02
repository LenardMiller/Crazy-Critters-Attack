package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class Scorpion extends Enemy {
    public Scorpion(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 13;
        maxSpeed = .65f;
        speed = maxSpeed;
        moneyDrop = 35;
        damage = 4;
        maxHp = 75;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "scorpion";
        attackDmgFrames = new int[]{5};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenWalkFrames = 5;
        betweenAttackFrames = 6;
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(13,13);
        overkillSound = soundsH.get("squish");
        dieSound = soundsH.get("crunch");
        loadSprites();
    }
}