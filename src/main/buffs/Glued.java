package main.buffs;

import main.enemies.Enemy;
import main.misc.CompressArray;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import static main.Main.*;

public class Glued extends Buff {

    private float speedMod;

    public Glued(PApplet p, int enId, float speedMod, int duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = 12; //frames
        System.out.println(duration);
        lifeDuration = duration;
        this.speedMod = speedMod;
        particle = "glue";
        name = "glued";
        this.enId = enId;
        slowAttacking();
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
        }
    }

    public void effect() { //slowing enemy movement done every frame
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.maxSpeed * speedMod;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            enemy.speed = newSpeed;
        }
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
}