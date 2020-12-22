package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class SmolBug extends Enemy {
    public SmolBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        maxSpeed = .4f;
        speed = maxSpeed;
        moneyDrop = 10;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "smolBug";
        betweenWalkFrames = 3;
        betweenAttackFrames = 3;
        attackStartFrame = 1; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{17};
        corpseSize = size;
        partSize = new PVector(11,11);
        overkillSound = soundsH.get("squish");
        dieSound = soundsH.get("crunch");
        loadSprites();
    }
}