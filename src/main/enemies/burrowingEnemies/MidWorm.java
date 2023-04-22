package main.enemies.burrowingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class MidWorm extends BurrowingEnemy {

    public MidWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        speed = 42;
        moneyDrop = 60;
        damage = 7;
        maxHp = 1200; //Hp
        hp = maxHp;
        hitParticle = HitParticle.greenOuch;
        name = "midWorm";
        attackDmgFrames = new int[]{15};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        partSize = new PVector(9, 9);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        attackSound = sounds.get("bugGrowlVeryQuick");
        moveSoundLoop = moveSoundLoops.get("littleDig");
        loadStuff();
    }
}