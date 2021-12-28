package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class TreeGiant extends Enemy {

    public TreeGiant(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(76,76);
        pfSize = 3;
        radius = 30;
        speed = 18;
        moneyDrop = 250;
        damage = 15;
        maxHp = 5000; //Hp
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeGiant";
        attackDmgFrames = new int[]{28};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        betweenWalkFrames = down60ToFramerate(3);
        corpseSize = new PVector(152,152);
        partSize = new PVector(68,68);
        corpseLifespan = 12;
        dieSound = sounds.get("bigLeaves");
        overkillSound = sounds.get("bigLeavesImpact");
        loadStuff();
    }
}