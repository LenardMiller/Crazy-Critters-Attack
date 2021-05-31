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
        speed = maxSpeed;
        damage = 50;
        maxHp = 100000;
        hp = maxHp;
        moneyDrop = 2000;
        hitParticle = "redOuch";
        name = "mammoth";
        betweenWalkFrames = 5;
        loadStuff();
    }
}
