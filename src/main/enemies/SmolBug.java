package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class SmolBug extends Enemy {
    public SmolBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 10;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "smolBug";
        betweenWalkFrames = down60ToFramerate(6);
        betweenAttackFrames = down60ToFramerate(3);
        attackStartFrame = 1; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{17};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(11,11);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}