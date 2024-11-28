package main.buffs.glued;

import main.buffs.Buff;
import main.enemies.Enemy;
import main.enemies.FlyingEnemy;
import main.enemies.Frost;
import main.misc.CompressArray;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class Glued extends Buff {

    public Glued(PApplet p, Enemy target, float speedMod, float duration, Turret turret) {
        super(p, target, turret);
        particleChance = 8;
        effectDelay = secondsToFrames(0.2f); //frames
        lifeDuration = secondsToFrames(duration);
        if (target instanceof FlyingEnemy) speedMod /= 2; //more strongly effects flying enemies
        this.effectLevel = speedMod;
        particle = "glue";
        name = "glued";
        slowAttacking();
    }

    protected void slowAttacking() { //slowing enemy attacking done once
        reset();
        float newSpeed = target.speed * effectLevel;
        if (target.speed > newSpeed) { //prevent speeding up enemy
            //setup
            target.speedModifier = effectLevel;
            int oldSize = target.attackFrames.length;
            int newSize = (int) (1f / (effectLevel * (1f / (float) oldSize)));
            ArrayList<Integer> expandedInts = new ArrayList<>();

            //run expansion algorithm
            compress = new CompressArray(oldSize - 1, newSize, expandedInts);
            compress.update();
            expandedInts = compress.compArray;

            //create expanded image array
            PImage[] expandedPImages = new PImage[expandedInts.size()];
            for (int i = 0; i < expandedInts.size(); i++) {
                expandedPImages[i] = target.attackFrames[expandedInts.get(i)];
            }

            //profit
            for (int i = 0; i < target.tempAttackDmgFrames.length; i++) {
                target.tempAttackDmgFrames[i] /= (int) effectLevel;
            }
            target.attackFrame = Math.round((float) target.attackFrame * (1 / effectLevel));
            target.attackFrames = expandedPImages;
        }
    }

    @Override
    public void effect() { //slowing enemy movement done every frame
        float newSpeed = target.speed * effectLevel;
        if (target.speed > newSpeed) { //prevent speeding up enemy
            target.speedModifier = effectLevel;
        }
    }

    @Override
    protected void updateTimer() { //ends if at end of lifespan
        if (!isPaused) lifeTimer++;
        if (lifeTimer > lifeDuration) {
            reset();
            target.buffs.remove(this);
        }
    }

    private void reset() {
        float newSpeed = target.speed;
        if (target.speed == newSpeed) { //prevent speeding up enemy
            target.speedModifier = 1; //set movement speed back to default
            //set attack speed back to default
            float frameProportion = (target.attackFrame / (float) target.attackFrames.length);
            target.attackFrames = animatedSprites.get(target.name + "AttackEN");
            if (target instanceof Frost) {
                target.attackFrames = animatedSprites.get("wolf" + "AttackEN");
            }
            target.attackFrame = Math.round(target.attackFrames.length * frameProportion);
            //set damage frames back to default
            System.arraycopy(target.attackDmgFrames, 0,
                    target.tempAttackDmgFrames, 0,
                    target.tempAttackDmgFrames.length);
        }
    }
}