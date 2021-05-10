package main.projectiles;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static processing.core.PConstants.HALF_PI;

public class MagicMissile extends Projectile {
    
    public int priority;
    private Enemy targetEnemy;

    private final PVector SPAWN_POSITION;
    
    public MagicMissile(PApplet p, float x, float y, float angle, Turret turret, int damage, int priority, PVector spawnPos) {
        super(p, x, y, angle, turret);
        this.SPAWN_POSITION = spawnPos;
        position = new PVector(x, y);
        size = new PVector(6, 20);
        radius = 14;
        maxSpeed = 300;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        sprite = staticSprites.get("magicMisslePj");
        hasTrail = true;
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
        if (priority == 0) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!enemy.stealthMode) {
                float x = abs(SPAWN_POSITION.x - enemy.position.x);
                float y = abs(SPAWN_POSITION.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900) {
                    if (priority == 0 && t < dist) { //close
                        e = enemy;
                        dist = t;
                    }
                    if (priority == 1 && t > dist) { //far
                        e = enemy;
                        dist = t;
                    }
                    if (priority == 2) if (enemy.maxHp > maxHp) { //strong
                        e = enemy;
                        maxHp = enemy.maxHp;
                    } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                        e = enemy;
                        dist = t;
                    }
                }
            }
        }
        targetEnemy = e;
    }

    private void aim(PVector target) {
        float targetAngle = clampAngle(findAngle(position, target));
        angle = clampAngle(angle);
        angle += angleDifference(targetAngle, angle) / 10;
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
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greenPuff"));
        projectiles.remove(this);
    }
}