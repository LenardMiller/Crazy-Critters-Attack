package main.projectiles;

import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Rock extends Projectile {

    public Rock(PApplet p, float x, float y, float angle, Turret turret, int damage, int maxSpeed, int effectLevel, int effectDuration) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(13, 13);
        radius = 6;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        angularVelocity = down60ToFramerate(p.random(-15,15));
        sprite = staticSprites.get("rockPj");
        hitSound = sounds.get("largeImpact");
        buff = "bleeding";
        particleTrail = "smoke";
    }

    @Override
    public void die() {
        for (int i = 0; i < 8; i++) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(TWO_PI),
                    "metal", p.random(100, 200)));
        }
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
        projectiles.remove(this);
    }
}