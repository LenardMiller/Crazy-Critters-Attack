package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.buffs;
import static main.Main.enemies;

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
    }

    public void effect() { //slows enemies
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
            if (enemy.speed != newSpeed) { //prevent speeding up enemy
                enemy.speed = enemy.maxSpeed; //set speed back to default
            }
            buffs.remove(i);
        }
    }
}