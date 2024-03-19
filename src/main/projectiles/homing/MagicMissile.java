package main.projectiles.homing;

import main.particles.ExplosionDebris;
import main.projectiles.Projectile;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static processing.core.PConstants.HALF_PI;

public class MagicMissile extends Projectile {

    public Turret.Priority priority;

    private final PVector spawnPosition;

    private float turningFraction = 10;
    private Enemy targetEnemy;

    public MagicMissile(PApplet p, float x, float y, float angle, Turret turret, int damage, Turret.Priority priority,
                        PVector spawnPos, int maxSpeed) {
        super(p, x, y, angle, turret);
        this.spawnPosition = spawnPos;
        position = new PVector(x, y);
        size = new PVector(6, 20);
        radius = 14;
        this.maxSpeed = maxSpeed;
        speed = maxSpeed;
        type = Enemy.DamageType.greenMagic;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("magicMissilePj");
        particleTrail = "greenMagic";
        this.priority = priority;
        hitSound = sounds.get("magicImpact");
    }

    private void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null) {
            aim(targetEnemy.position);
            if (targetEnemy.hp <= 0) targetEnemy = null;
        }
    }

    private void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong

        //target random enemy
        if (priority.equals(Turret.Priority.None)) {
            if (targetEnemy == null) {
                targetEnemy = enemies.get((int) p.random(enemies.size()));
            }
            return;
        }

        float dist;
        if (priority == Turret.Priority.Close) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy)) {
                float x = abs(spawnPosition.x - enemy.position.x);
                float y = abs(spawnPosition.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900) {
                    switch (priority) {
                        case Close -> {
                            if (t >= dist) break;
                            e = enemy;
                            dist = t;
                        } case Far -> {
                            if (t <= dist) break;
                            e = enemy;
                            dist = t;
                        } case Strong -> {
                            if (enemy.maxHp > maxHp) { //strong
                                e = enemy;
                                maxHp = enemy.maxHp;
                            } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                                e = enemy;
                                dist = t;
                            }
                        }
                    }
                }
            }
        }
        targetEnemy = e;
    }

    private void aim(PVector target) {
        float targetAngle = normalizeAngle(findAngle(position, target));
        angle = normalizeAngle(angle);
        angle += getAngleDifference(targetAngle, angle) / turningFraction;
        turningFraction = Math.max(turningFraction - 0.1f, 0f);
        angleTwo = angle;
        velocity = PVector.fromAngle(angle-HALF_PI);
    }

    @Override
    public void move() {
        if (enemies.size() > 0) checkTarget();
        else die();
        velocity.setMag(getBoostedSpeed());
        position.add(velocity);
    }

    @Override
    public void die() {
        if (damage > 1000) {
            for (int i = 0; i < 8; i++) {
                topParticles.add(new ExplosionDebris(p, position.x, position.y, p.random(TWO_PI),
                        "greenMagic", p.random(100, 200)));
            }
        }
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greenPuff"));
        projectiles.remove(this);
    }
}