package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

public class Mammoth extends Enemy {

    public Mammoth(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(100, 100);
        pfSize = 4;
        radius = 50;
        maxSpeed = 20;
        damage = 50;
        maxHp = 100000;
        moneyDrop = 2000;
        hitParticle = "redOuch";
        name = "mammoth";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{9};
        betweenWalkFrames = 5;
        betweenAttackFrames = 8;
        loadStuff();
    }
}
