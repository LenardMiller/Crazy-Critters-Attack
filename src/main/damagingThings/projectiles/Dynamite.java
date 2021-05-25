package main.damagingThings.projectiles;

import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Dynamite extends Projectile {

    public Dynamite(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(9, 15);
        angularVelocity = down60ToFramerate(p.random(-15, 15)); //degrees mode
        radius = 10;
        maxSpeed = 600;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        type = "burning";
        sprite = staticSprites.get("dynamitePj");
        hitSound = sounds.get("smallExplosion");
        this.effectRadius = effectRadius;
    }

    @Override
    public void die() {
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "fire", p.random(100,200)));
        }
        topParticles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "fire"));
        projectiles.remove(this);
    }
}