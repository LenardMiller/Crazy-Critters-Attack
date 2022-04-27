package main.enemies.flyingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class GiantBat extends FlyingEnemy {

    public GiantBat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(102,102);
        pfSize = 2;
        radius = 25;
        speed = 30;
        moneyDrop = 500;
        damage = 5;
        maxHp = 12500; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "giantBat";
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(12);
        corpseSize = new PVector(100,100);
        partSize = new PVector(50, 50);
        betweenWalkFrames = down60ToFramerate(11);
        betweenCorpseFrames = down60ToFramerate(4);
        overkillSound = sounds.get("bigSqueakSquash");
        dieSound = sounds.get("bigSqueak");
        attackSound = sounds.get("flap");
        loadStuff();
    }
}