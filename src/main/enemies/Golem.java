package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class Golem extends Enemy{

    public Golem(PApplet p, float x, float y){
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        maxSpeed = .4f;
        speed = maxSpeed;
        moneyDrop = 80;
        damage = 15;
        maxHp = 1200; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "lichenOuch";
        name = "golem";
        attackStartFrame = 22;
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(84,84);
        partSize = new PVector(36,36);
        betweenCorpseFrames = 6;
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        loadSprites();
    }
}