package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.from60ToFramerate;

public class Bat extends Enemy {

    public Bat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 33;
        speed = maxSpeed;
        moneyDrop = 50;
        damage = 5;
        maxHp = 400; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "bat";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = from60ToFramerate(10);
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(50,50);
        partSize = new PVector(23, 23);
        betweenWalkFrames = from60ToFramerate(4);
        betweenCorpseFrames = from60ToFramerate(4);
        overkillSound = sounds.get("squeakSquish");
        dieSound = sounds.get("squeak");
        loadSprites();
    }
}