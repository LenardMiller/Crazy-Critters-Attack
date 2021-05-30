package main.enemies.shootingEnemies;

import main.enemies.Enemy;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;

public abstract class ShootingEnemy extends Enemy {

    protected int range;
    protected int betweenShootFrames;
    protected int shootFireFrame;
    protected int barrelLength;
    protected int shootDamage;
    protected Turret target;
    protected PImage[] shootFrames;
    protected SoundFile shootSound;

    private int shootFrame;

    protected ShootingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    protected void loadStuff() {
        attackFrames = animatedSprites.get(name + "AttackEN");
        moveFrames = animatedSprites.get(name + "MoveEN");
        shootFrames = animatedSprites.get(name + "ShootEN");
        maxTintColor = getTintColor();
        currentTintColor = new Color(255, 255, 255);
    }

    @Override
    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            if (target == null) {
                for (Tower tower : towers) {
                    if (tower instanceof Turret) {
                        if (findDistBetween(tower.getCenter(), position) < range) target = (Turret) tower;
                    }
                }
            }

            if (state != 2) {
                angle = clampAngle(angle);
                targetAngle = clampAngle(targetAngle);
                angle += angleDifference(targetAngle, angle) / 10;
            }

            if (canTargetTurret()) state = 2;
            else if (state == 2) state = 0;

            if (state == 0) move();
            else if (state == 1) attack();
            else if (state == 2) prepareToFire();

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

    private boolean canTargetTurret() {
        if (target == null) return false;
        return target.alive;
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
                sprite = shootFrames[shootFrame];
                if (shootFrame < shootFrames.length - 1) {
                    if (idleTime >= betweenShootFrames) {
                        shootFrame++;
                        idleTime = 0;
                    }
                } else shootFrame = 0;
            }
        }
        //shift back to normal
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    private void prepareToFire() {
        if (shootFrame != shootFireFrame || idleTime != 0) return;
        float projectileAngle = findAngle(position,
          new PVector(target.tile.position.x - 25, target.tile.position.y - 25));
        angle = (projectileAngle - HALF_PI);
        PVector projectilePosition = new PVector(position.x, position.y);
        PVector barrel = PVector.fromAngle(angle);
        barrel.setMag(barrelLength); //barrel length
        projectilePosition.add(barrel);
        playSoundRandomSpeed(p, shootSound, 1);
        fire(projectileAngle, projectilePosition);
    }

    protected abstract void fire(float projectileAngle, PVector projectilePosition);
}
