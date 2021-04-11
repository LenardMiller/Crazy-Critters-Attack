package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class MidWorm extends Enemy {

    public MidWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = 42;
        speed = maxSpeed;
        moneyDrop = 60;
        damage = 6;
        maxHp = 700; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "midWorm";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{15};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(9, 9);
        corpseSize = new PVector(25,25);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }

    protected void move() {
        if (stealthMode && (int)p.random(0,12) == 0)
            particles.add(new Debris(p, p.random(position.x - radius, position.x + radius), p.random(position.y - radius, position.y + radius), p.random(0, 360), levels[currentLevel].groundType));
        if (p.random(0,40) < 1)
            underParticles.add(new Pile(p, p.random(position.x - radius, position.x + radius), p.random(position.y - radius, position.y + radius), 0, levels[currentLevel].groundType));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed/FRAMERATE);
        position.add(m);
        speed = maxSpeed;
    }
}