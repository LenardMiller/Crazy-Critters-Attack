package main.damagingThings.projectiles.enemyProjeciles;

import main.particles.Debris;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class IceCrystal extends EnemyProjectile {

    public IceCrystal(PApplet p, int damage, float x, float y, float angle, Turret turret) {
        super(p, damage, x, y, angle, turret);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 600;
        speed = maxSpeed;
        angularVelocity = 0;
        trail = "iceMagic";
        sprite = staticSprites.get("iceCrystalPj");
        hitSound = sounds.get("mediumImpact");
    }

    @Override
    public void die() {
        for (int i = 0; i < 6; i++) {
            topParticles.add(new Debris(p, position.x, position.y, p.random(360), "ice"));
        }
        projectiles.remove(this);
    }
}
