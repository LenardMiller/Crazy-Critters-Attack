package main.damagingThings.projectiles;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;
import static processing.core.PConstants.HALF_PI;

public abstract class Projectile {

    public int damage;
    public float radius;
    public float maxSpeed;
    public float speed;
    public float angle;
    public PImage sprite;
    public PApplet p;
    public PVector position;
    public PVector size;

    protected int pierce;
    protected int hitTime;
    protected int effectRadius;
    protected float effectDuration;
    protected float angleTwo;
    protected float angularVelocity;
    protected float effectLevel;
    protected boolean dead;
    protected boolean causeEnemyParticles;
    protected PVector velocity;
    protected String trail;
    protected String buff;
    protected String type;
    protected ArrayList<Enemy> hitEnemies;
    protected Turret turret;
    protected SoundFile hitSound;

    protected Projectile(PApplet p, float x, float y, float angle, Turret turret) {
        this.p = p;

        causeEnemyParticles = true;
        this.turret = turret;
        hitEnemies = new ArrayList<>();
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 10;
        speed = 100;
        damage = 1;
        pierce = 0;
        hitTime = 0;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        sprite = staticSprites.get("boltPj");
        velocity = PVector.fromAngle(angle - HALF_PI);
        trail = null;
        buff = "null";
        effectRadius = 0;
        effectLevel = 0;
        effectDuration = 0;
    }

    public void main() {
        displayPassB();
        if (!paused) {
            trail();
            move();
        }
        checkCollision();
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 ||
                position.y + size.y < -100 || position.x + size.x < -100) {
            projectiles.remove(this);
        }
        if (dead) die();
    }

    public abstract void die();

    protected void trail() { //leaves a trail of particles
        if (trail != null) {
            if (p.random(0, 3) > 1) topParticles.add(new MiscParticle(p, position.x, position.y,
                    p.random(0, 360), trail));
        }
    }

    public void displayPassA() {
        if (!paused) angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.tint(0,60);
        p.translate(position.x + 2, position.y + 2);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    public void displayPassB() {
        if (!paused) angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }

    public void move() {
        velocity.setMag(speed/FRAMERATE);
        position.add(velocity);
    }

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

    protected PVector fromExplosionCenter(Enemy enemy) {
        return PVector.fromAngle(Utilities.findAngle(enemy.position, position) + HALF_PI);
    }

    protected boolean enemyAlreadyHit(Enemy enemy) {
        for (Enemy hitEnemy : hitEnemies) {
            if (hitEnemy == enemy) {
                return true;
            }
        }
        return false;
    }

    protected boolean intersectingEnemy(Enemy enemy) {
        boolean touchingX = abs(enemy.position.x - position.x) < radius + enemy.radius;
        boolean touchingY = abs(enemy.position.y - position.y) < radius + enemy.radius;
        return touchingX && touchingY;
    }

    protected boolean nearEnemy(Enemy enemy) {
        boolean nearX = abs(enemy.position.x - position.x) <= effectRadius + enemy.radius;
        boolean nearY = abs(enemy.position.y - position.y) <= effectRadius + enemy.radius;
        return nearX && nearY;
    }
}
