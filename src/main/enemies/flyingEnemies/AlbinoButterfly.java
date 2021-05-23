package main.enemies.flyingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class AlbinoButterfly extends FlyingEnemy {

    public AlbinoButterfly(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 48;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 60; //Hp
        hp = maxHp;
        hitParticle = "glowOuch";
        name = "albinoButterfly";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(5);
        attackFrame = attackStartFrame;
        corpseSize = new PVector(25,25);
        partSize = new PVector(18, 18);
        betweenCorpseFrames = down60ToFramerate(4);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }
}