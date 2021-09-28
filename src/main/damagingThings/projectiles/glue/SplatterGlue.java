package main.damagingThings.projectiles.glue;

import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.particles.Particle.ParticleTypes.GlueExplosion;

public class SplatterGlue extends Glue {

    public SplatterGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration) {
        super(p, x, y, angle, turret, damage, effectLevel, effectDuration);
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        maxSpeed = 400;
        speed = maxSpeed;
        angularVelocity = 0;
        sprite = staticSprites.get("gluePj");
        trail = "glue";
        type = "glue";
        effectRadius = 60;
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(360),"gluePuff"));
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(360), "glue", p.random(100,200)));
        }
        topParticles.add(GlueExplosion.create(p, position.x, position.y, p.random(360)));
        projectiles.remove(this);
    }
}