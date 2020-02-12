package main.projectiles;

import main.enemies.Enemy;
import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.HALF_PI;

public class MagicMissile extends Projectile {
    
    public int priority;
    private Enemy targetEnemy;
    private PVector spawnPos;
    
    public MagicMissile(PApplet p, float x, float y, float angle, Tower tower, int damage, int priority, PVector spawnPos) {
        super(p, x, y, angle, tower);
        this.spawnPos = spawnPos;
        position = new PVector(x, y);
        size = new PVector(6, 20);
        radius = 14;
        maxSpeed = 5;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        sprite = spritesH.get("magicMisslePj");
        hasTrail = true;
        trail = "greenMagic";
        this.priority = priority;
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
                float x = abs(spawnPos.x - enemy.position.x);
                float y = abs(spawnPos.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
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
        targetEnemy = e;
    }

    private void aim(PVector target) { //todo: cool slow turn
//        angle = findAngleBetween(target,position) + QUARTER_PI;
        PVector ratio = PVector.sub(target,position);
        if (position.x == target.x){ //if on the same x
            if (position.y >= target.y){ //if below target or on same y, angle right
                angle = 0;
            } else if (position.y < target.y){ //if above target, angle left
                angle = PI;
            }
        } else if (position.y == target.y){ //if on same y
            if (position.x > target.x){ //if  right of target, angle down
                angle = 3*HALF_PI;
            } else if (position.x < target.x){ //if left of target, angle up
                angle = HALF_PI;
            }
        } else{
            if (position.x < target.x && position.y > target.y){ //if to left and below NOT WORKING
                angle = (atan(abs(ratio.x+15)/abs(ratio.y)));
            } else if (position.x < target.x && position.y < target.y){ //if to left and above
                angle = (atan(abs(ratio.y)/abs(ratio.x))) + HALF_PI;
            } else if (position.x > target.x && position.y < target.y){ //if to right and above NOT WORKING
                angle = (atan(abs(ratio.x+15)/abs(ratio.y))) + PI;
            } else if (position.x > target.x && position.y > target.y){ //if to right and below
                angle = (atan(abs(ratio.y)/abs(ratio.x))) + 3*HALF_PI;
            }
        }
        angleTwo = angle;
        velocity = PVector.fromAngle(angle-HALF_PI);
    }

    public void move() {
        if (enemies.size() > 0) checkTarget();
        velocity.setMag(speed);
        position.add(velocity);
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greenPuff"));
    }
}