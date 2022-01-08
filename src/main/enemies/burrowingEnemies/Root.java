package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Root extends BurrowingEnemy {

    public Root(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        speed = 40;
        moneyDrop = 100;
        damage = 20;
        maxHp = 35000; //Hp
        hp = maxHp;
        hitParticle = "sapOuch";
        name = "root";
        attackDmgFrames = new int[]{18};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(2);
        partSize = new PVector(8, 8);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}