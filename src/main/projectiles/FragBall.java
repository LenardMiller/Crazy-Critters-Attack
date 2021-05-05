package main.projectiles;

import main.particles.ExplosionDebris;
import main.particles.MediumExplosion;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class FragBall extends Projectile {

    public FragBall(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 10;
        maxSpeed = 850;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("cannonBallPj");
        hitSound = sounds.get("largeImpact");
        this.effectRadius = effectRadius;
    }

    @Override
    public void die() {
        int num = (int) (p.random(10, 16));
        for (int j = num; j >= 0; j--) {
            particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "metal", p.random(100,200)));
        }
        particles.add(new MediumExplosion(p, position.x, position.y, p.random(0, 360), "puff"));
        int numFrags = 24;
        for (int j = 0; j < numFrags; j++)
            projectiles.add(new Frag(p, position.x, position.y, p.random(0, 360), turret, damage / 4));
        projectiles.remove(this);
    }
}
