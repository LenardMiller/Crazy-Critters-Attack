package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Worm extends BurrowingEnemy {

    public Worm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        speed = 36;
        moneyDrop = 30;
        damage = 3;
        maxHp = 100; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "worm";
        attackDmgFrames = new int[]{18};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(2);
        partSize = new PVector(7, 7);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}