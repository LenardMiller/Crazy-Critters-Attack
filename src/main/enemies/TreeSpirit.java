package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.moveSoundLoops;
import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class TreeSpirit extends Enemy {

    public TreeSpirit(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(42,42);
        pfSize = 2; //2
        radius = 21;
        speed = 21;
        moneyDrop = 30;
        damage = 3;
        maxHp = 350; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "leafOuch";
        name = "treeSpirit";
        betweenAttackFrames = down60ToFramerate(2);
        betweenWalkFrames = down60ToFramerate(3);
        attackDmgFrames = new int[]{22};
        corpseSize = new PVector(84,84);
        partSize = new PVector(38,38);
        betweenCorpseFrames = down60ToFramerate(6);
        dieSound = sounds.get("leaves");
        overkillSound = sounds.get("leavesImpact");
        attackSound = sounds.get("swooshPunch");
        moveSoundLoop = moveSoundLoops.get("leafyStepsLoop");
        loadStuff();
    }
}