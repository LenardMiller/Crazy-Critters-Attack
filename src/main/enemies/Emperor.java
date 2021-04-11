package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Emperor extends Enemy{

    public Emperor(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2; //2
        radius = 25;
        maxSpeed = 27;
        speed = maxSpeed;
        moneyDrop = 200;
        damage = 18;
        maxHp = 3000;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "emperor";
        betweenAttackFrames = down60ToFramerate(10);
        betweenWalkFrames = down60ToFramerate(12);
        attackDmgFrames = new int[]{7};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackStartFrame = 0; //attack start
        corpseSize = size;
        partSize = new PVector(20,20);
        attackFrame = attackStartFrame;
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        loadStuff();
    }
}