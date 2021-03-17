package main.buffs;

import main.enemies.Enemy;
import main.misc.CompressArray;
import main.particles.BuffParticle;
import main.projectiles.GlueSpike;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class SpikeyGlued extends Buff {

    private final float SPEED_MODIFIER;

    private final Spike[] SPIKES;

    public SpikeyGlued(PApplet p, int enId, float speedMod, float duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(duration);
        this.SPEED_MODIFIER = speedMod;
        particle = "glue";
        name = "glued";
        this.enId = enId;
        slowAttacking();

        SPIKES = new Spike[enemies.get(enId).pfSize * 3];
        for (int i = 0; i < SPIKES.length; i++) {
            Enemy enemy = enemies.get(enId);
            float x = p.random(-enemy.size.x/3, enemy.size.x/3);
            float y = p.random(-enemy.size.y/3, enemy.size.y/3);
            SPIKES[i] = new Spike(x, y, p.random(0,360));
        }
    }

    /**
     * slows enemy attacking, done once
    */
    private void slowAttacking() {
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * ((1 + SPEED_MODIFIER) / 2);
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            //setup
            enemy.speed = newSpeed;
            int oldSize = enemy.attackFrames.length;
            int newSize = (int) (1f / (SPEED_MODIFIER * (1f / (float) oldSize)));
            ArrayList<Integer> expandedInts = new ArrayList<>();
            //run expansion algorithm
            compress = new CompressArray(oldSize - 1, newSize, expandedInts);
            compress.main();
            expandedInts = compress.compArray;
            //change damage frames
            System.arraycopy(enemy.attackDmgFrames, 0, enemy.tempAttackDmgFrames, 0, enemy.tempAttackDmgFrames.length);
            for (int i = 0; i < enemy.tempAttackDmgFrames.length; i++) {
                for (int j = 0; j < expandedInts.size(); j++) {
                    if (expandedInts.get(j) == enemy.attackDmgFrames[i]) enemy.tempAttackDmgFrames[i] = j;
                }
            }
            //create expanded image array
            PImage[] expandedPImages = new PImage[expandedInts.size()];
            for (int i = 0; i < expandedInts.size(); i++) {
                expandedPImages[i] = enemy.attackFrames[expandedInts.get(i)];
            }
            //profit
            enemy.attackFrames = expandedPImages;
        }
    }

    /**
     * slows enemy movement, done every frame
     */
    public void effect() {
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * SPEED_MODIFIER;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            enemy.speed = newSpeed;
        }
    }

    /**
     * particles around enemy
     */
    void display() {
        if (particle != null) {
            Enemy enemy = enemies.get(enId);
            int num = (int) (p.random(0, particleChance));
            if (num == 0) {
                particles.add(new BuffParticle(p, (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))),
                        (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), p.random(0, 360),
                        particle));
            }
        }
        for (Spike spike : SPIKES) spike.display(enemies.get(enId).position);
    }

    /**
     * ends if at end of lifespan
     * @param i buff id
     */
    void end(int i) {
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * SPEED_MODIFIER;
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) {
            if (enemy.speed == newSpeed) { //prevent speeding up enemy
                enemy.speed = enemy.maxSpeed; //set movement speed back to default
                //set attack speed back to default
                enemy.attackFrames = animatedSprites.get(enemy.name + "AttackEN");
                if (enemy.attackFrame > enemy.attackFrames.length) enemy.attackFrame = 0;
                //set damage frames back to default
                System.arraycopy(enemy.attackDmgFrames, 0, enemy.tempAttackDmgFrames, 0, enemy.tempAttackDmgFrames.length);
            }
            buffs.remove(i);
        }
    }

    /**
     * shoots a bunch of glue spikes
     */
    public void dieEffect() {
        int numSpikes = 24;
        int spikeDamage = 100;
        Enemy enemy = enemies.get(enId);
        for (int i = 0; i < numSpikes; i++) {
            projectiles.add(new GlueSpike(p, enemy.position.x, enemy.position.y, p.random(0,360), turret, spikeDamage));
        }
    }

    private class Spike {

        private final PVector POSITION;
        private final float ANGLE;
        private final PImage SPRITE;
        private final PVector SIZE;

        /**
         * Little glue spikes that stick to enemy
         * @param x x pos relative to enemy
         * @param y y pos relative to enemy
         * @param angle rotation relative to screen
         */
        Spike(float x, float y, float angle) {
            POSITION = new PVector(x,y);
            this.ANGLE = radians(angle);
            SPRITE = staticSprites.get("glueSpikePj");
            SIZE = new PVector(7,7);
        }

        void display(PVector absPosition) {
            p.pushMatrix();
            p.translate(absPosition.x + POSITION.x, absPosition.y + POSITION.y);
            p.rotate(ANGLE);
            p.image(SPRITE, -SIZE.x / 2, -SIZE.y / 2);
            p.popMatrix();
        }
    }
}