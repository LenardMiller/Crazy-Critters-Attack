package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Dummy extends Enemy {

    public Dummy(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(28,28);
        pfSize = 1; //1
        radius = 14;
        speed = 0;
        moneyDrop = 0;
        damage = 0;
        maxHp = 500000; //Hp
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "dummy";
        betweenWalkFrames = down60ToFramerate(1);
        corpseSize = size;
        dieSound = sounds.get("woodBreak");
        overkillSound = sounds.get("wodBreak");
        loadStuff();
    }
}