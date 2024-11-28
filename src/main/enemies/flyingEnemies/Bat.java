package main.enemies.flyingEnemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Bat extends FlyingEnemy {

    public Bat(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 1;
        radius = 12.5f;
        speed = 45;
        moneyDrop = 50;
        damage = 3;
        maxHp = 500; //Hp
        hp = maxHp;
        hitParticle = HitParticle.redOuch;
        name = "bat";
        attackDmgFrames = new int[]{3};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackDelay = down60ToFramerate(10);
        corpseSize = new PVector(50,50);
        partSize = new PVector(23, 23);
        walkDelay = down60ToFramerate(7);
        betweenCorpseFrames = down60ToFramerate(4);
        overkillSound = sounds.get("squeakSquish");
        dieSound = sounds.get("squeak");
        attackSound = sounds.get("flap");
        moveSoundLoop = moveSoundLoops.get("wingbeats");
        loadStuff();
    }
}