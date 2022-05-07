package main.projectiles;

import main.particles.Vortex;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class DarkBlast extends Projectile {

    public DarkBlast(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 18);
        radius = 22;
        maxSpeed = 1000;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("darkPj");
        hitSound = sounds.get("darkImpact");
        this.effectRadius = effectRadius;
        type = "dark";
        trail = "dark";
    }

    @Override
    public void die() {
        int numRings = effectRadius/5;
        for (int i = 0; i < numRings; i++) {
            for (int j = 0; j < p.random(10, 16); j++) {
                float radius = (effectRadius/ (float) numRings) * i;
                PVector displacement = PVector.fromAngle(p.random(TWO_PI));
                displacement.setMag(radius);
                topParticles.add(new Vortex(p, new PVector(position.x, position.y), displacement, radius));
            }
        }
        projectiles.remove(this);
    }
}


