package main.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Laundry extends Projectile {

    public PImage[] sprites; //alternate sprites, passed in

    public Laundry(PApplet p, float x, float y, float angle, Turret turret, int damage, int maxSpeed, int maxRotation) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 10;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angleTwo = angle;
        angularVelocity = down60ToFramerate(p.random(-maxRotation, maxRotation)); //degrees mode
        sprite = staticSprites.get("laundryPj");
        trail = "poison";
        type = Enemy.DamageType.poisoned;
        effectRadius = 60;
        buff = "poisoned";
        hitSound = sounds.get("squishImpact");
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p, position.x, position.y, p.random(0, 360), "poisonPuff"));
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "poison", p.random(100,200)));
        }
        for (int i = 0; i < num; i++) {
            PVector pos = Utilities.getRandomPointInRange(p, position, effectRadius);
            topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), "poison"));
        }
        topParticles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "poison"));
        projectiles.remove(this);
    }
}