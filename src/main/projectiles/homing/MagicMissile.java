package main.projectiles.homing;

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

    private final PVector SPAWN_POSITION;

    private float turningFraction = 10;
    private Enemy targetEnemy;

    public MagicMissile(PApplet p, float x, float y, float angle, Turret turret, int damage, Turret.Priority priority, PVector spawnPos) {
        super(p, x, y, angle, turret);
        this.SPAWN_POSITION = spawnPos;
        position = new PVector(x, y);
        size = new PVector(6, 20);
        radius = 14;
        maxSpeed = 300;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("magicMissilePj");
        trail = "greenMagic";
        this.priority = priority;
        hitSound = sounds.get("magicImpact");
    }

    private void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null) aim(targetEnemy.position);
    }

    private void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float dist;
        if (priority == Turret.Priority.Close) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy)) {
                float x = abs(SPAWN_POSITION.x - enemy.position.x);
                float y = abs(SPAWN_POSITION.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900) {
                    switch (priority) {
                        case Close:
                            if (t >= dist) break;
                            e = enemy;
                            dist = t;
                            break;
                        case Far:
                            if (t <= dist) break;
                            e = enemy;
                            dist = t;
                            break;
                        case Strong:
                            if (enemy.maxHp > maxHp) { //strong
                                e = enemy;
                                maxHp = enemy.maxHp;
                            } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                                e = enemy;
                                dist = t;
                            }
                            break;
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
        velocity.setMag(speed/FRAMERATE);
        position.add(velocity);
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greenPuff"));
        projectiles.remove(this);
    }
}