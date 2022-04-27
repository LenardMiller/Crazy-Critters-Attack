package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class Velociraptor extends Enemy {

    public Velociraptor(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(100,100);
        pfSize = 3;
        radius = 25;
        speed = 75;
        moneyDrop = 300;
        damage = 20;
        maxHp = 4000; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "velociraptor";
        attackDmgFrames = new int[]{8};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(6);
        betweenWalkFrames = down60ToFramerate(6);
        corpseSize = size;
        partSize = new PVector(45,45);
        overkillSound = sounds.get("dinoSquish");
        dieSound = sounds.get("dino");
        attackSound = sounds.get("biteGrowl");
        loadStuff();
    }
}