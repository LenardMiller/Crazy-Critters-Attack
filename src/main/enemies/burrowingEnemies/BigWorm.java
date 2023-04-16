package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class BigWorm extends BurrowingEnemy {

    public BigWorm(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(50, 50);
        pfSize = 2;
        radius = 25f;
        speed = 24;
        moneyDrop = 250;
        damage = 15;
        maxHp = 5000; //Hp
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "bigWorm";
        attackDmgFrames = new int[]{29};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        partSize = new PVector(31, 31);
        corpseSize = new PVector(50, 50);
        overkillSound = sounds.get("squash");
        dieSound = sounds.get("bigCrunchRoar");
        attackSound = sounds.get("bugGrowlSlow");
        moveSoundLoop = moveSoundLoops.get("bigDig");
        loadStuff();
    }
}