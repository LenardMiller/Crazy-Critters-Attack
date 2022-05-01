package main.projectiles.enemyProjeciles;

import main.particles.Ouch;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Snowball extends EnemyProjectile {

    public Snowball(PApplet p, int damage, float x, float y, float angle) {
        super(p, damage, x, y, angle);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 400;
        speed = maxSpeed;
        angularVelocity = down60ToFramerate(p.random(-15,15));
        sprite = staticSprites.get("snowballPj");
        hitSound = sounds.get("mediumImpact");
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"snowPuff"));
        projectiles.remove(this);
    }
}
