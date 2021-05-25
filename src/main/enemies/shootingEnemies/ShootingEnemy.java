package main.enemies.shootingEnemies;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;

public abstract class ShootingEnemy extends Enemy {

    protected int range;
    protected int betweenShootFrames;
    protected int shootFireFrame;
    protected Turret target;
    protected PImage[] shootFrames;

    private int shootFrame;

    protected ShootingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
        //TEMP
        target = selection.turret;
    }

    @Override
    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            angle = clampAngle(angle);
            targetAngle = clampAngle(targetAngle);
            angle += angleDifference(targetAngle, angle) / 10;

            if (target != null) {
                if (findDistBetween(position, target.tile.position) < range) {
                    state = 2;
                }
            }

            if (state == 0) move();
            else if (state == 1) attack();
            else if (state == 2 && shootFrame == shootFireFrame) fire();

            //prevent wandering
            if (points.size() == 0 && state != 1) pathRequestWaitTimer++;
            if (pathRequestWaitTimer > FRAMERATE) {
                requestPath(i);
                pathRequestWaitTimer = 0;
            }
        }
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayMain();
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    @Override
    protected void animate() {
        if (!immobilized) {
            if (state == 1) {
                if (attackFrame >= attackFrames.length) attackFrame = 0;
                sprite = attackFrames[attackFrame];
                idleTime++;
                if (attackFrame < attackFrames.length - 1) {
                    if (idleTime >= betweenAttackFrames) {
                        attackFrame += 1;
                        idleTime = 0;
                    }
                } else attackFrame = 0;
            } else if (state == 0) {
                idleTime++;
                if (moveFrame < moveFrames.length - 1) {
                    if (idleTime >= betweenWalkFrames) {
                        moveFrame++;
                        idleTime = 0;
                    }
                } else moveFrame = 0;
                sprite = moveFrames[moveFrame];
            } else if (state == 2) {
                idleTime++;
                if (shootFrame < shootFrames.length - 1) {
                    if (idleTime >= betweenShootFrames) {
                        shootFrame++;
                        idleTime = 0;
                    }
                } else shootFrame = 0;
                sprite = shootFrames[shootFrame];
            }
        }
        //shift back to normal
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    protected abstract void fire();
}
