package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.soundsH;

public class Sidewinder extends Enemy {

    public Sidewinder(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 0.7f;
        speed = maxSpeed;
        moneyDrop = 20;
        damage = 3;
        maxHp = 60; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "sidewinder";
        numAttackFrames = 15;
        numMoveFrames = 10;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{4};
        betweenAttackFrames = 5;
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(9,9);
        overkillSound = soundsH.get("snakeSquish");
        dieSound = soundsH.get("snakeCrunch");
        loadSprites();
    }
}