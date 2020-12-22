package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class Dummy extends Enemy {
    public Dummy(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(28,28);
        pfSize = 1; //1
        radius = 14;
        maxSpeed = 0;
        speed = maxSpeed;
        moneyDrop = 0;
        damage = 0;
        maxHp = 50; //Hp
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "dummy";
        betweenWalkFrames = 1;
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        corpseSize = size;
        loadSprites();
    }
}