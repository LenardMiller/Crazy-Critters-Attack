package main.enemies;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.misc.Utilities.down60ToFramerate;

public class BigAlbinoBug extends Enemy{

    public BigAlbinoBug(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(53,53);
        pfSize = 2; //2
        radius = 26;
        maxSpeed = 18;
        speed = maxSpeed;
        moneyDrop = 100;
        damage = 15;
        maxHp = 2500;
        hp = maxHp;
        hitParticle = "glowOuch";
        name = "bigAlbinoBug";
        betweenWalkFrames = down60ToFramerate(3);
        attackStartFrame = 48; //attack start
        corpseSize = size;
        partSize = new PVector(32,32);
        attackFrame = attackStartFrame;
        dieSound = sounds.get("bigCrunch");
        overkillSound = sounds.get("squash");
        loadStuff();
    }
}