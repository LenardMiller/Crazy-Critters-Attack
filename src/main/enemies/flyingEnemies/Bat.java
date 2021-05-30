package main.enemies.flyingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Bat extends FlyingEnemy {

    public Bat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 45;
        speed = maxSpeed;
        moneyDrop = 50;
        damage = 3;
        maxHp = 750; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "bat";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(10);
        attackFrame = attackStartFrame;
        corpseSize = new PVector(50,50);
        partSize = new PVector(23, 23);
        betweenWalkFrames = down60ToFramerate(7);
        betweenCorpseFrames = down60ToFramerate(4);
        overkillSound = sounds.get("squeakSquish");
        dieSound = sounds.get("squeak");
        loadStuff();
    }
}