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
            System.out.println(oldSize);
            int newSize = (int) (oldSize * (2 - speedMod));
            System.out.println(newSize);
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
                enemy.speed = enemy.maxSpeed; //set speed back to default
            }
            buffs.remove(i);
        }
    }
}