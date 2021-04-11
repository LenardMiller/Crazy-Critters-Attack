package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class GiantBat extends Enemy {

    public GiantBat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(102,102);
        pfSize = 2;
        radius = 25;
        maxSpeed = 30;
        speed = maxSpeed;
        moneyDrop = 500;
        damage = 5;
        maxHp = 15000; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "giantBat";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(12);
        attackFrame = attackStartFrame;
        flying = true;
        corpseSize = new PVector(100,100);
        partSize = new PVector(50, 50);
        betweenWalkFrames = down60ToFramerate(11);
        betweenCorpseFrames = down60ToFramerate(4);
        overkillSound = sounds.get("bigSqueakSquash");
        dieSound = sounds.get("bigSqueak");
        loadStuff();
    }
}