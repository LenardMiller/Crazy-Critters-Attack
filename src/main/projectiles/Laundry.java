package main.projectiles;

import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Laundry extends Projectile {

    public PImage[] sprites; //alternate sprites, passed in

    public Laundry(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 700;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angleTwo = angle;
        angularVelocity = down60ToFramerate(p.random(-15, 15)); //degrees mode
        sprite = staticSprites.get("laundryPj");
        trail = "poison";
        type = "poison";
        hasTrail = true;
        effectRadius = 60;
        buff = "poisoned";
        hitSound = sounds.get("squishImpact");
    }

    public void die() {
        particles.add(new Ouch(p, position.x, position.y, p.random(0, 360), "poisonPuff"));
        int num = (int) (p.random(16, 42));
        for (int j = num; j >= 0; j--) {
            particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "poison", p.random(100,200)));
        }
        particles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "poison"));
        projectiles.remove(this);
    }
}