package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.from60ToFramerate;

public class Sidewinder extends Enemy {

    public Sidewinder(PApplet p, float x, float y) { //todo: too small
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 42;
        speed = maxSpeed;
        moneyDrop = 20;
        damage = 3;
        maxHp = 60; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "sidewinder";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{4};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = from60ToFramerate(5);
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(9,9);
        overkillSound = sounds.get("hissSquish");
        dieSound = sounds.get("hiss");
        loadSprites();
    }
}