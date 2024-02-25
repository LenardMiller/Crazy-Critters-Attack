package main.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.MediumExplosion;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Dynamite extends Projectile {

    public Dynamite(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius, int maxSpeed) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(9, 15);
        angularVelocity = down60ToFramerate(p.random(-15, 15)); //degrees mode
        radius = 10;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        particleTrail = "fire";
        type = Enemy.DamageType.burning;
        sprite = staticSprites.get("dynamitePj");
        hitSound = sounds.get("mediumExplosion");
        this.effectRadius = effectRadius;
    }

    @Override
    public void die() {
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(360), "fire", p.random(100,200)));
        }
        for (int i = 0; i < num; i++) {
            PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius);
            topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), "fire"));
        }
        topParticles.add(new LargeExplosion(p, position.x, position.y, p.random(360), "fire"));
        for (int i = 0; i < (num / 10) + 1; i++) {
            PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius / 2f);
            topParticles.add(new MediumExplosion(p, pos.x, pos.y, p.random(360), "fire"));
        }
        projectiles.remove(this);
    }
}