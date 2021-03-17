package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class LittleWorm extends Enemy {

    public LittleWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 36;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 3;
        maxHp = 100; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "worm";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{18};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(2);
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(7, 7);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadSprites();
    }

    protected void move() {
        if (stealthMode && (int)p.random(0,15) == 0)
            particles.add(new Debris(p,position.x,position.y,p.random(0,360),levels[currentLevel].groundType));
        if (p.random(0,50) < 1)
            underParticles.add(new Pile(p, position.x, position.y, 0, levels[currentLevel].groundType));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }
}