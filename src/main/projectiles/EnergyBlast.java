package main.projectiles;

import main.enemies.Enemy;
import main.misc.MiscMethods;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.MediumExplosion;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PApplet.abs;

public class EnergyBlast extends Projectile {

    private final boolean BIG_EXPLOSION;

    public EnergyBlast(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectRadius, boolean bigExplosion) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 18);
        radius = 14;
        maxSpeed = 16;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = spritesH.get("energyPj");
        hasTrail = true;
        this.effectRadius = effectRadius;
        trail = "energy";
        this.BIG_EXPLOSION = bigExplosion;
    }

    public void collideEn() {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            boolean hitAlready = false;
            for (Enemy hitEnemy : hitEnemies)
                if (hitEnemy == enemy) {
                    hitAlready = true;
                    break;
                }
            if (hitAlready) continue;
            if (abs(enemy.position.x - position.x) <= (radius + enemy.radius) && abs(enemy.position.y - position.y) <= (radius + enemy.radius) && pierce > 0) { //if touching enemy, and has pierce
                enemy.damagePj(damage, buff, effectLevel, effectDuration, turret, splashEn, "burning", velocity, i);
                if (!BIG_EXPLOSION) {
                    int num = (int) (p.random(10, 16));
                    for (int j = num; j >= 0; j--) {
                        particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "energy", maxSpeed = p.random(0.5f, 2.5f)));
                    }
                    particles.add(new MediumExplosion(p, position.x, position.y, p.random(0, 360), "fire"));
                } else {
                    int num = (int) (p.random(16, 42));
                    for (int j = num; j >= 0; j--) {
                        particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "energy", maxSpeed = p.random(1.5f, 4.5f)));
                    }
                    particles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "fire"));
                }
                pierce--;
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy erEnemy = enemies.get(j);
                    if (abs(erEnemy.position.x - position.x) <= (effectRadius + erEnemy.radius) && abs(erEnemy.position.y - position.y) <= (effectRadius + erEnemy.radius)) { //if near enemy
                        hitAlready = false;
                        for (Enemy hitEnemy : hitEnemies)
                            if (hitEnemy == enemy) {
                                hitAlready = true;
                                break;
                            }
                        if (hitAlready) continue;
                        erEnemy.damagePj(3 * (damage / 4), buff, effectLevel, effectDuration, turret, splashEn, erEnemy.lastDamageType, PVector.fromAngle(MiscMethods.findAngle(erEnemy.position, position) + HALF_PI), j);
                    }
                }
            }
            if (pierce == -1) {
                dead = true;
            }
        }
    }
}
