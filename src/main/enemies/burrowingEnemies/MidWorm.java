package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class MidWorm extends BurrowingEnemy {

    public MidWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 42;
        speed = maxSpeed;
        moneyDrop = 60;
        damage = 6;
        maxHp = 700; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "midWorm";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{15};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        attackFrame = attackStartFrame;
        partSize = new PVector(9, 9);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}