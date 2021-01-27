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

public class SpikeyGlued extends Buff {

    private float speedMod;

    private Spike[] spikes;

    public SpikeyGlued(PApplet p, int enId, float speedMod, int duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = 12; //frames
        lifeDuration = duration;
        this.speedMod = speedMod;
        particle = "glue";
        name = "glued";
        this.enId = enId;
        slowAttacking();

        spikes = new Spike[enemies.get(enId).pfSize * 3];
        for (int i = 0; i < spikes.length; i++) {
            Enemy enemy = enemies.get(enId);
            float x = p.random(-enemy.size.x/3, enemy.size.x/3);
            float y = p.random(-enemy.size.y/3, enemy.size.y/3);
            spikes[i] = new Spike(x, y, p.random(0,360));
        }
    }

    private void slowAttacking() { //slowing enemy attacking done once
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * speedMod;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            //setup
            enemy.speed = newSpeed;
            int oldSize = enemy.attackFrames.length;
            int newSize = (int) (1f / (speedMod * (1f / (float) oldSize)));
            ArrayList<Integer> expandedInts = new ArrayList<>();
            //run expansion algorithm
            compress = new CompressArray(oldSize - 1, newSize, expandedInts);
            compress.main();
            expandedInts = compress.compArray;
            //create expanded image array
            PImage[] expandedPImages = new PImage[expandedInts.size()];
            for (int i = 0; i < expandedInts.size(); i++) {
                expandedPImages[i] = enemy.attackFrames[expandedInts.get(i)];
            }
            //profit
            enemy.attackFrames = expandedPImages;
            //todo: fix damage frames
        }
    }

    public void effect() { //slowing enemy movement done every frame
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * speedMod;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            enemy.speed = newSpeed;
        }
    }

    void display() { //particles around enemy
        if (particle != null) {
            Enemy enemy = enemies.get(enId);
            int num = (int) (p.random(0, particleChance));
            if (num == 0) {
                particles.add(new BuffParticle(p, (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))),
                        (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), p.random(0, 360),
                        particle));
            }
        }
        for (Spike spike : spikes) spike.display(enemies.get(enId).position);
    }

    void end(int i){ //ends if at end of lifespan
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * speedMod;
        if (p.frameCount > lifeTimer) {
            if (enemy.speed == newSpeed) { //prevent speeding up enemy
                enemy.speed = enemy.maxSpeed; //set movement speed back to default
                //set attack speed back to default
                enemy.attackFrames = spritesAnimH.get(enemy.name + "AttackEN");
                if (enemy.attackFrame > enemy.attackFrames.length) enemy.attackFrame = 0;
            }
            buffs.remove(i);
        }
    }

    public void dieEffect() {
        int numSpikes = 8;
        int spikeDamage = 50;
        Enemy enemy = enemies.get(enId);
        for (int i = 0; i < numSpikes; i++) {
            projectiles.add(new GlueSpike(p, enemy.position.x, enemy.position.y, p.random(0,360), turret, spikeDamage));
        }
    }

    private class Spike {

        private PVector position;
        private float angle;
        private PImage sprite;
        private PVector size;

        Spike(float x, float y, float angle) {
            position = new PVector(x,y);
            this.angle = radians(angle);
            sprite = spritesH.get("glueSpikePj");
            size = new PVector(7,7);
        }

        void display(PVector absPosition) {
            p.pushMatrix();
            p.translate(absPosition.x + position.x, absPosition.y + position.y);
            p.rotate(angle);
            p.image(sprite, -size.x / 2, -size.y / 2);
            p.popMatrix();
        }
    }
}