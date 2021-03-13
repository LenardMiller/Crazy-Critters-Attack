package main.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Laundry extends Projectile {

    public PImage[] sprites; //alternate sprites, passed in

    public Laundry(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 12;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angleTwo = angle;
        angularVelocity = p.random(-15, 15); //degrees mode
        sprite = staticSprites.get("laundryPj");
        trail = "poison";
        hasTrail = true;
        effectRadius = 60;
        buff = "poisoned";
        hitSound = sounds.get("squishImpact");
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p, position.x, position.y, p.random(0, 360), "poisonPuff"));
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
            if (abs(enemy.position.x - position.x) <= (radius + enemy.radius) && abs(enemy.position.y - position.y) <= (radius + enemy.radius) && pierce > -1) { //if touching enemy, and has pierce
                hitSound.stop();
                hitSound.play(p.random(0.8f, 1.2f), volume);
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret, splashEn, "poison", velocity, i);
                int num = (int) (p.random(16, 42));
                for (int j = num; j >= 0; j--) {
                    particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "poison", maxSpeed = p.random(1.5f, 4.5f)));
                }
                particles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "poison"));
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
                        erEnemy.damageWithBuff(3 * (damage / 4), buff, effectLevel, effectDuration, turret, splashEn, "poison", PVector.fromAngle(Utilities.findAngle(erEnemy.position, position) + HALF_PI), j);
                    }
                }
            }
            if (pierce == -1) {
                dead = true;
            }
        }
    }
}