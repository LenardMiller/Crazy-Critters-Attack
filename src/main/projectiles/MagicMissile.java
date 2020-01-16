package main.projectiles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.util.MiscMethods.findAngleBetween;
import static processing.core.PConstants.HALF_PI;

public class MagicMissile extends Projectile {
    
    public int priority;
    
    public MagicMissile(PApplet p, float x, float y, float angle, int damage, int priority) {
        super(p, x, y, angle);
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
        if (priority == 0) { //first
            aim(enemyTracker.firstPos,position);
        } else if (priority == 1) { //last
            aim(enemyTracker.lastPos,position);
        } else if (priority == 2) { //strong
            aim(enemyTracker.strongPos,position);
        } else { //first, placeholder for close
            aim(enemyTracker.firstPos,position);
        }
    }

    private void aim(PVector target, PVector position) { //todo: cool slow turn
        angle = findAngleBetween(target,position);
//        PVector ratio = PVector.sub(target,position);
//        if (position.x == target.x){ //if on the same x
//            if (position.y >= target.y){ //if below target or on same y, angle right
//                angle = 0;
//            } else if (position.y < target.y){ //if above target, angle left
//                angle = PI;
//            }
//        } else if (position.y == target.y){ //if on same y
//            if (position.x > target.x){ //if  right of target, angle down
//                angle = 3*HALF_PI;
//            } else if (position.x < target.x){ //if left of target, angle up
//                angle = HALF_PI;
//            }
//        } else{
//            if (position.x < target.x && position.y > target.y){ //if to left and below NOT WORKING
//                angle = (atan(abs(ratio.x+15)/abs(ratio.y)));
//            } else if (position.x < target.x && position.y < target.y){ //if to left and above
//                angle = (atan(abs(ratio.y)/abs(ratio.x))) + HALF_PI;
//            } else if (position.x > target.x && position.y < target.y){ //if to right and above NOT WORKING
//                angle = (atan(abs(ratio.x+15)/abs(ratio.y))) + PI;
//            } else if (position.x > target.x && position.y > target.y){ //if to right and below
//                angle = (atan(abs(ratio.y)/abs(ratio.x))) + 3*HALF_PI;
//            }
//        }
        angleTwo = angle;
        velocity = PVector.fromAngle(angle-HALF_PI);
    }

    public void move(){
        if (enemies.size() > 0){
            checkTarget();
        }
        velocity.setMag(speed);
        position.add(velocity);
    }
}