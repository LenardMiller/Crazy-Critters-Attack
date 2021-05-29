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
        maxSpeed = 75;
        speed = maxSpeed;
        moneyDrop = 500;
        damage = 20;
        maxHp = 5000; //Hp
        hp = maxHp;
        hitParticle = "redOuch";
        name = "velociraptor";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{8};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(8);
        betweenWalkFrames = down60ToFramerate(6);
        attackFrame = attackStartFrame;
        corpseSize = size;
        partSize = new PVector(45,45);
        overkillSound = sounds.get("dinoSquish");
        dieSound = sounds.get("dino");
        loadStuff();
    }
}