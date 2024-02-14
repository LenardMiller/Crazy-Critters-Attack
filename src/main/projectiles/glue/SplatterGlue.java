package main.projectiles.glue;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.*;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SplatterGlue extends Glue {

    public SplatterGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration, int maxSpeed) {
        super(p, x, y, angle, turret, damage, effectLevel, effectDuration, maxSpeed);
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        angularVelocity = 0;
        sprite = staticSprites.get("gluePj");
        trail = "glue";
        type = Enemy.DamageType.glued;
        effectRadius = 100;
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
        int num = (int) (p.random(16, 42));
        for (int i = 0; i < num; i++) {
            PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius);
            topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), "glue"));
        }
        for (int j = num; j >= 0; j--) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "glue", p.random(100,200)));
        }
        topParticles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "glue"));
        for (int i = 0; i < (num / 10) + 1; i++) {
            PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius / 2f);
            topParticles.add(new LargeExplosion(p, pos.x, pos.y, p.random(360), "glue"));
        }
        projectiles.remove(this);
    }
}