package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class BigWorm extends Enemy {

    public BigWorm(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(50, 50);
        pfSize = 2;
        radius = 25f;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 250;
        damage = 15;
        maxHp = 5000; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "bigWorm";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{29};
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        betweenAttackFrames = down60ToFramerate(4);
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(31, 31);
        corpseSize = new PVector(50, 50);
        overkillSound = sounds.get("squash");
        dieSound = sounds.get("bigCrunch");
        loadStuff();
    }

    protected void move() {
        if (stealthMode && (int) p.random(0, 8) == 0)
            particles.add(new Debris(p, p.random(position.x - radius, position.x + radius), p.random(position.y - radius, position.y + radius), p.random(0, 360), levels[currentLevel].groundType));
        if (p.random(0, 20) < 1)
            underParticles.add(new Pile(p, p.random(position.x - radius, position.x + radius), p.random(position.y - radius, position.y + radius), 0, levels[currentLevel].groundType));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }
}