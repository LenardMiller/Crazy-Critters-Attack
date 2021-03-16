package main.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.LargeExplosion;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SplatterGlue extends Projectile {

    public SplatterGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, int effectDuration) {
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
        effectRadius = 60;
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
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
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret, splashEn, "glue", velocity, i);
                int num = (int) (p.random(16, 42));
                for (int j = num; j >= 0; j--) {
                    particles.add(new ExplosionDebris(p, position.x, position.y, p.random(0, 360), "glue", maxSpeed = p.random(1.5f, 4.5f)));
                }
                particles.add(new LargeExplosion(p, position.x, position.y, p.random(0, 360), "glue"));
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
                        erEnemy.damageWithBuff(3 * (damage / 4), buff, effectLevel, effectDuration, turret, splashEn, "glue", PVector.fromAngle(Utilities.findAngle(erEnemy.position, position) + HALF_PI), j);
                    }
                }
            }
            if (pierce < 0) {
                dead = true;
            }
        }
    }
}