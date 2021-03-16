package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.from60ToFramerate;

public class Golem extends Enemy {

    public Golem(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 60;
        damage = 8;
        maxHp = 800; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "golem";
        attackStartFrame = 22;
        betweenAttackFrames = from60ToFramerate(2);
        attackFrame = attackStartFrame;
        corpseSize = new PVector(84,84);
        partSize = new PVector(36,36);
        betweenCorpseFrames = from60ToFramerate(6);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        loadSprites();
    }
}