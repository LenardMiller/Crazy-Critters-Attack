package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Shark extends BurrowingEnemy {

    public Shark(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 36;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 3;
        maxHp = 100; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "shark";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{4};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(5);
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(20, 20);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}