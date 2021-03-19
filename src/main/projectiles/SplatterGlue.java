package main.projectiles;

import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SplatterGlue extends Projectile {

    public SplatterGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration) {
        super(p, x, y, angle, turret);
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        maxSpeed = 400;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = 0;
        sprite = staticSprites.get("gluePj");
        hasTrail = true;
        trail = "glue";
        type = "glue";
        effectRadius = 60;
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    public void die() {
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "glue", maxSpeed = p.random(1.5f, 4.5f)));
        }
        particles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "glue"));
        projectiles.remove(this);
    }
}