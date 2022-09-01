package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Golem extends Enemy {

    public Golem(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        speed = 24;
        moneyDrop = 60;
        damage = 8;
        maxHp = 1000; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "golem";
        betweenAttackFrames = down60ToFramerate(2);
        betweenWalkFrames = down60ToFramerate(3);
        attackDmgFrames = new int[]{22};
        corpseSize = new PVector(84,84);
        partSize = new PVector(36,36);
        betweenCorpseFrames = down60ToFramerate(6);
        dieSound = sounds.get("gravelDie");
        overkillSound = sounds.get("gravelExplode");
        attackSound = sounds.get("swooshPunch");
        moveSoundLoop = moveSoundLoops.get("stonesMove");
        loadStuff();
    }
}