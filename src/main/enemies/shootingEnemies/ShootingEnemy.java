package main.enemies.shootingEnemies;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.angleDifference;
import static main.misc.Utilities.clampAngle;

public abstract class ShootingEnemy extends Enemy {

    private Turret target;

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

            if (state == 0) move();
            else if (state == 1) attack();

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

    protected abstract void fire();
}
