package main.projectiles.glue;

import main.enemies.Enemy;
import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.projectiles.Projectile;
import main.towers.turrets.Gluer;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Glue extends Projectile {

    public Glue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration, int maxSpeed) {
        super(p, x, y, angle, turret);
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = 0;
        sprite = staticSprites.get("gluePj");
        particleTrail = "glue";
        debrisTrail = damage > 0 ? particleTrail : null;
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(TWO_PI),"gluePuff"));
        projectiles.remove(this);
        if (damage > 0) {
            for (int i = 0; i < 8; i++) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(TWO_PI),
                        particleTrail, p.random(100, 200)));
            }
        }
    }

    @Override
    public void checkCollision() {
        for (int enemyId = 0; enemyId < enemies.size(); enemyId++) {
            Enemy enemy = enemies.get(enemyId);
            if (enemyAlreadyHit(enemy)) continue;
            if (intersectingEnemy(enemy) && pierce > -1) {
                playSoundRandomSpeed(p, hitSound, 1);
                PVector applyVelocity = velocity;
                if (effectRadius > 0) applyVelocity = new PVector(0, 0);
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret,
                        causeEnemyParticles, type, applyVelocity);
                hitEnemies.add(enemy);
                Gluer gluer = (Gluer) turret;
                gluer.gluedTotal++;
                pierce--;
                //splash
                for (int splashEnemyId = enemies.size() - 1; splashEnemyId >= 0; splashEnemyId--) {
                    Enemy splashEnemy = enemies.get(splashEnemyId);
                    if (enemyAlreadyHit(splashEnemy)) continue;
                    if (nearEnemy(splashEnemy)) {
                        gluer.gluedTotal++;
                        applyVelocity = fromExplosionCenter(splashEnemy);
                        if (intersectingEnemy(splashEnemy)) applyVelocity = new PVector(0, 0);
                        splashEnemy.damageWithBuff(3 * (damage / 4), buff, effectLevel, effectDuration, turret,
                          causeEnemyParticles, type, applyVelocity);
                    }
                }
            }
            if (pierce < 0) dead = true;
        }
    }
}