package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class MidBug extends Enemy {
    public MidBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1; //1
        radius = 13;
        maxSpeed = .5f;
        speed = maxSpeed;
        moneyDrop = 20;
        damage = 3;
        maxHp = 40;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "midBug";
        betweenWalkFrames = 4;
        betweenAttackFrames = 2;
        attackStartFrame = 34; //attack start
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(14,14);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadSprites();
    }
}