package main.enemies.flyingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;

public class Mantoid extends FlyingEnemy {

    public Mantoid(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50, 50);
        pfSize = 2;
        radius = 25;
        speed = 50;
        moneyDrop = 500;
        damage = 5;
        maxHp = 30000;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "mantoid";
        attackDmgFrames = new int[]{12};
        betweenWalkFrames = 1;
        betweenAttackFrames = 1;
        corpseSize = size.copy();
        partSize = new PVector(38, 38);
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        loadStuff();
    }
}
