package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class MudCreature extends Enemy {

    public MudCreature(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(50,50);
        pfSize = 2;
        radius = 25;
        maxSpeed = 90;
        speed = maxSpeed;
        moneyDrop = 350;
        damage = 50;
        maxHp = 10000; //Hp
        hp = maxHp;
        hitParticle = "mudOuch";
        name = "mudCreature";
        betweenWalkFrames = down60ToFramerate(6);
        betweenAttackFrames = down60ToFramerate(6);
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{9};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(25,25);
        overkillSound = sounds.get("mudSquish");
        dieSound = sounds.get("mudDie");
        loadStuff();
    }
}