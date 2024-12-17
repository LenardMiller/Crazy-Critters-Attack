package main.projectiles.enemyProjeciles;

import main.particles.ExplosionDebris;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Laser extends EnemyProjectile {

    public Laser(PApplet p, int damage, float x, float y, float angle) {
        super(p, damage, x, y, angle);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 600;
        speed = maxSpeed;
        angularVelocity = 0;
        particleTrail = "energy";
        sprite = staticSprites.get("laserPj");
        hitSound = sounds.get("mediumImpact");
    }

    @Override
    public void die() {
        for (int i = 0; i < 6; i++) {
            topParticles.add(new ExplosionDebris(p,
                    position.x, position.y, p.random(360),
                    "energy", p.random(5)));
        }
        projectiles.remove(this);
    }
}
