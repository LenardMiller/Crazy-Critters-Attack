package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class TreeSpirit extends Enemy {

    public TreeSpirit(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        maxSpeed = .35f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 5;
        maxHp = 500; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeSpirit";
        attackStartFrame = 22;
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        corpseSize = new PVector(84,84);
        partSize = new PVector(38,38);
        betweenCorpseFrames = 6;
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        loadSprites();
    }
}