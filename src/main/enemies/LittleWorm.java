package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class LittleWorm extends Enemy {

    public LittleWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 50; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "worm";
        numAttackFrames = 34;
        numMoveFrames = 1;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{18};
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(7, 7);
        corpseSize = new PVector(25,25);
        overkillSound = soundsH.get("squish");
        loadSprites();
    }

    void move() {
        if (stealthMode && (int)p.random(0,15) == 0) particles.add(new Debris(p,position.x,position.y,p.random(0,360),"dirt"));
        if (p.random(0,50) < 1) underParticles.add(new Pile(p, position.x, position.y, 0, "dirt"));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }
}