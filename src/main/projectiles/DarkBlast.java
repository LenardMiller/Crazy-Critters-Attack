package main.projectiles;

import main.particles.ExplosionDebris;
import main.particles.MediumExplosion;
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
        hitSound = sounds.get("energyImpact");
        hasTrail = true;
        this.effectRadius = effectRadius;
        type = "dark";
        trail = "dark";
    }

    public void die() {
        int num = (int) (p.random(10, 16));
        for (int j = num; j >= 0; j--) {
            particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "energy", maxSpeed = p.random(0.5f, 2.5f)));
        }
        particles.add(new MediumExplosion(p, position.x, position.y, p.random(0, 360), "energy"));
        projectiles.remove(this);
    }
}


