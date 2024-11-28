package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class GiantGolem extends Enemy {

    public GiantGolem(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(76,76);
        pfSize = 3;
        radius = 30;
        speed = 24;
        moneyDrop = 500;
        damage = 18;
        maxHp = 20000; //Hp
        hp = maxHp;
        hitParticle = HitParticle.lichenOuch;
        name = "giantGolem";
        attackDmgFrames = new int[]{28};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackDelay = down60ToFramerate(2);
        walkDelay = down60ToFramerate(3);
        corpseSize = new PVector(152,152);
        partSize = new PVector(68,68);
        corpseLifespan = 12;
        dieSound = sounds.get("rocksCrumble");
        overkillSound = sounds.get("rocksCrumble");
        attackSound = sounds.get("swooshPunchSlow");
        moveSoundLoop = moveSoundLoops.get("bigStonesMove");
        loadStuff();
    }
}