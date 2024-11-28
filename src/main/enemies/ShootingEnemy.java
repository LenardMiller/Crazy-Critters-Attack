package main.enemies;

import main.buffs.Buff;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;
import java.util.function.BiConsumer;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class ShootingEnemy extends Enemy {

    protected int range;
    protected int shootDelay;
    protected int shootFireFrame;
    protected int barrelLength;
    protected int shootDamage;
    protected SoundFile shootSound;
    protected Turret target;
    protected PImage[] shootFrames;

    private int shootFrame;
    private BiConsumer<Float, PVector> fireFun;

    protected ShootingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    ShootingEnemy(PApplet p, float x, float y,
           String name,
           PVector size,
           int pfSize,
           int radius,
           int speed,
           int moneyDrop,
           int damage,
           int hp,

           int range,
           int barrelLength,
           int shootDelay,
           int shootFireFrame,

           int[] attackDmgFrames,
           int walkDelay,
           int attackDelay,
           PVector corpseSize,
           PVector partSize,
           int corpseLifespan,
           int corpseDelay,
           HitParticle hitParticle,
           String dieSound,
           String overkillSound,
           String attackSound,
           String moveSoundLoop,

           String shootSound,
           BiConsumer<Float, PVector> fireFun
    ) {
        super(p, x, y, name, size, pfSize, radius, speed, moneyDrop, damage, hp, attackDmgFrames, walkDelay,
                attackDelay, corpseSize, partSize, corpseLifespan, corpseDelay, hitParticle, dieSound, overkillSound, attackSound,
                moveSoundLoop);
        this.range = range;
        this.barrelLength = barrelLength;
        this.shootDelay = shootDelay;
        this.shootFireFrame = shootFireFrame;
        this.shootSound = sounds.get(shootSound);
        this.fireFun = fireFun;
    }

    @Override
    protected void loadStuff() {
        attackFrames = animatedSprites.get(name + "AttackEN");
        moveFrames = animatedSprites.get(name + "MoveEN");
        shootFrames = animatedSprites.get(name + "ShootEN");
        currentTintColor = new Color(255, 255, 255);
    }

    @Override
    public void update(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!isPaused && !immobilized) {
            if (target == null) {
                for (Tower tower : towers) {
                    if (tower instanceof Turret) {
                        if (findDistBetween(tower.getCenter(), position) < range) target = (Turret) tower;
                    }
                }
            }

            if (state != State.Special) {
                rotation = normalizeAngle(rotation);
                targetAngle = normalizeAngle(targetAngle);
                rotation += getAngleDifference(targetAngle, rotation) / 10;
            }

            if (canTargetTurret()) state = State.Special;
            else if (state == State.Special) state = State.Moving;

            switch (state) {
                case Moving -> move();
                case Attacking -> attack();
                case Special -> prepareToFire();
            }

            //prevent wandering
            if (trail.isEmpty() && state != State.Attacking) pathRequestWaitTimer++;
            if (pathRequestWaitTimer > FRAMERATE) {
                requestPath(i);
                pathRequestWaitTimer = 0;
            }
        }
        if (!trail.isEmpty() && intersectTurnPoint()) swapPoints(true);
        //buffs
        for (int j = buffs.size() - 1; j >= 0; j--) {
            Buff buff = buffs.get(j);
            buff.update();
        }
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die();
    }

    private boolean canTargetTurret() {
        if (target == null) return false;
        return target.alive;
    }

    @Override
    protected void animate() {
        if (!immobilized) {
            switch (state) {
                case Attacking -> {
                    if (attackFrame >= attackFrames.length) attackFrame = 0;
                    sprite = attackFrames[attackFrame];
                    idleTime++;
                    if (attackFrame < attackFrames.length - 1) {
                        if (idleTime >= attackDelay) {
                            attackFrame += 1;
                            idleTime = 0;
                        }
                    } else attackFrame = 0;
                } case Moving -> {
                    idleTime++;
                    if (moveFrame < moveFrames.length - 1) {
                        if (idleTime >= walkDelay) {
                            moveFrame++;
                            idleTime = 0;
                        }
                    } else moveFrame = 0;
                    sprite = moveFrames[moveFrame];
                } case Special -> {
                    idleTime++;
                    sprite = shootFrames[shootFrame];
                    if (shootFrame < shootFrames.length - 1) {
                        if (idleTime >= shootDelay) {
                            shootFrame++;
                            idleTime = 0;
                        }
                    } else shootFrame = 0;
                }
            }
        }
        //shift back to normal
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    private void prepareToFire() {
        if (shootFrame != shootFireFrame || idleTime != 0) return;
        float projectileAngle = findAngle(position,
          new PVector(target.tile.position.x - 25, target.tile.position.y - 25));
        rotation = (projectileAngle - HALF_PI);
        PVector projectilePosition = new PVector(position.x, position.y);
        PVector barrel = PVector.fromAngle(rotation);
        barrel.setMag(barrelLength); //barrel length
        projectilePosition.add(barrel);
        playSoundRandomSpeed(p, shootSound, 1);
        fire(projectileAngle, projectilePosition);
    }

    protected void fire(float projectileAngle, PVector projectilePosition) {
        fireFun.accept(projectileAngle, projectilePosition);
    }
}
