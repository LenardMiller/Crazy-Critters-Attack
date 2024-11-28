package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;

public class Mammoth extends Enemy {

    public Mammoth(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(100, 100);
        pfSize = 4;
        radius = 50;
        speed = 30;
        damage = 50;
        maxHp = 200000;
        moneyDrop = 2500;
        hitParticle = HitParticle.redOuch;
        name = "mammoth";
        attackDmgFrames = new int[]{9};
        walkDelay = 5;
        attackDelay = 5;
        corpseSize = size;
        corpseLifespan = 12;
        partSize = new PVector(56, 56);
        dieSound = sounds.get("mammoth");
        overkillSound = sounds.get("mammothSquash");
        attackSound = sounds.get("biteGrowlSlow");
        moveSoundLoop = moveSoundLoops.get("bigFootsteps");
        loadStuff();
    }
}
