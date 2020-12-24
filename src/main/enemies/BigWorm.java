package main.enemies;

import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class BigWorm extends Enemy {

    public BigWorm(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(50, 50);
        pfSize = 2;
        radius = 25f;
        maxSpeed = .25f;
        speed = maxSpeed;
        moneyDrop = 250;
        damage = 10;
        maxHp = 300; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "bigWorm";
        attackStartFrame = 0;
        attackDmgFrames = new int[]{29};
        betweenAttackFrames = 4;
        attackFrame = attackStartFrame;
        stealthy = true;
        partSize = new PVector(31, 31);
        corpseSize = new PVector(50, 50);
        overkillSound = soundsH.get("squash");
        dieSound = soundsH.get("bigCrunch");
        loadSprites();
    }

    void move() {
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