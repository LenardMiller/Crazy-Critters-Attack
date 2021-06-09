package main.buffs.glued;

import main.buffs.Buff;
import main.enemies.Enemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.enemies.flyingEnemies.Frost;
import main.misc.CompressArray;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Glued extends Buff {

    private final float SPEED_MODIFIER;

    public Glued(PApplet p, int enId, float speedMod, float duration, Turret turret) {
        super(p,enId,turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(duration);
        if (enemies.get(enId) instanceof FlyingEnemy) speedMod /= 2; //more strongly effects flying enemies
        this.SPEED_MODIFIER = speedMod;
        particle = "glue";
        name = "glued";
        this.enId = enId;
        slowAttacking();
    }

    protected void slowAttacking() { //slowing enemy attacking done once
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.speed * SPEED_MODIFIER;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            //setup
            enemy.speedModifier = SPEED_MODIFIER;
            int oldSize = enemy.attackFrames.length;
            int newSize = (int) (1f / (SPEED_MODIFIER * (1f / (float) oldSize)));
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

    @Override
    public void effect() { //slowing enemy movement done every frame
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.speed * SPEED_MODIFIER;
        if (enemy.speed > newSpeed) { //prevent speeding up enemy
            enemy.speedModifier = SPEED_MODIFIER;
        }
    }

    @Override
    protected void end(int i){ //ends if at end of lifespan
        Enemy enemy = enemies.get(enId);
        float newSpeed = enemy.speed;
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) {
            if (enemy.speed == newSpeed) { //prevent speeding up enemy
                enemy.speedModifier = 1; //set movement speed back to default
                //set attack speed back to default
                enemy.attackFrames = animatedSprites.get(enemy.name + "AttackEN");
                if (enemy instanceof Frost) enemy.attackFrames = animatedSprites.get("wolf" + "AttackEN");
                if (enemy.attackFrame > enemy.attackFrames.length) enemy.attackFrame = 0;
                //set damage frames back to default
                System.arraycopy(enemy.attackDmgFrames, 0, enemy.tempAttackDmgFrames, 0, enemy.tempAttackDmgFrames.length);
            }
            buffs.remove(i);
        }
    }
}