package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Mantis extends Enemy {

    public Mantis(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2; //2
        radius = 25;
        speed = 30;
        moneyDrop = 300;
        damage = 15;
        maxHp = 7500;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "mantis";
        betweenWalkFrames = down60ToFramerate(13);
        attackDmgFrames = new int[]{12};
        betweenAttackFrames = 1;
        corpseSize = size;
        partSize = new PVector(32,32);
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        loadStuff();
    }
}