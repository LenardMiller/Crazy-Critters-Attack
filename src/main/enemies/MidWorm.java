package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class MidWorm extends Enemy {

    public MidWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .35f;
        speed = maxSpeed;
        moneyDrop = 60;
        damage = 3;
        maxHp = 100; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "midWorm";
        numAttackFrames = 30;
        numMoveFrames = 1;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{15};
        betweenAttackFrames = 4;
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(9, 9);
        corpseSize = new PVector(25,25);
        overkillSound = soundsH.get("squish");
        dieSound = soundsH.get("crunch");
        loadSprites();
    }

    void move() {
        if (stealthMode && (int)p.random(0,12) == 0) particles.add(new Debris(p,position.x,position.y,p.random(0,360),levels[currentLevel].groundType));
        if (p.random(0,40) < 1) underParticles.add(new Pile(p, p.random(position.x - 5f, position.x + 5f), p.random(position.y - 12.5f, position.y + 12.5f), 0, levels[currentLevel].groundType));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }
}