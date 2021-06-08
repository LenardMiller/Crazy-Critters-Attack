package main.damagingThings.projectiles.glue;

import main.damagingThings.projectiles.Projectile;
import main.enemies.Enemy;
import main.particles.Ouch;
import main.towers.turrets.Gluer;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Glue extends Projectile {

    public Glue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration) {
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
        trail = "glue";
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
        projectiles.remove(this);
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
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret, causeEnemyParticles, type, applyVelocity, enemyId);
                hitEnemies.add(enemy);
                Gluer gluer = (Gluer) turret;
                gluer.gluedTotal++;
                pierce--;
                //splash
                for (int splashEnemyId = enemies.size() - 1; splashEnemyId >= 0; splashEnemyId--) {
                    Enemy splashEnemy = enemies.get(splashEnemyId);
                    if (enemyAlreadyHit(splashEnemy)) continue;
                    if (nearEnemy(splashEnemy)) {
                        applyVelocity = fromExplosionCenter(splashEnemy);
                        if (intersectingEnemy(splashEnemy)) applyVelocity = new PVector(0, 0);
                        splashEnemy.damageWithBuff(3 * (damage / 4), buff, effectLevel, effectDuration, turret,
                          causeEnemyParticles, type, applyVelocity, splashEnemyId);
                    }
                }
            }
            if (pierce < 0) dead = true;
        }
    }
}