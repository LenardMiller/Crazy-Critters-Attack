package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

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
        maxHp = 500000; //Hp
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "dummy";
        betweenWalkFrames = 1;
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        corpseSize = size;
        dieSound = soundsH.get("woodBreak");
        overkillSound = soundsH.get("wodBreak");
        loadSprites();
    }
}