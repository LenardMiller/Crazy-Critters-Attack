package main.enemies;

import main.Main;
import main.buffs.Buff;
import main.misc.Corpse;
import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class LittleWorm extends Enemy {

    public LittleWorm(PApplet p, float x, float y) {
        super(p,x,y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 12.5f;
        maxSpeed = .3f;
        speed = maxSpeed;
        moneyDrop = 30;
        damage = 1;
        maxHp = 20; //Hp
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "worm";
        numAttackFrames = 34;
        numMoveFrames = 1;
        attackStartFrame = 0;
        attackDmgFrames = new int[]{18};
        betweenAttackFrames = 2;
        attackFrame = attackStartFrame;
        stealthMode = true;
        corpseSize = new PVector(5,5);
        loadSprites();
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);
        if (!attacking) {
            stealthMode = true;
            move();
            if ((int)p.random(0,15) == 0) particles.add(new Debris(p,position.x,position.y,p.random(0,360),"dirt"));
        } else {
            stealthMode = false;
            attack();
        }
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayPassB();
        //prevent from going offscreen
        if (position.x >= GRID_WIDTH-100 || position.x <= -100 || position.y >= GRID_HEIGHT-100 || position.y <= -100) dead = true;
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    void die(int i) {
        Main.money += moneyDrop;

        String type = lastDamageType;
        for (Buff buff : buffs) {
            if (buff.enId == i) {
                type = buff.name;
            }
        }
        if (stealthMode) {
            if (overkill) {
                for (int j = 0; j < spritesAnimH.get(name + "PartsEN").length; j++) {
                    float maxRv = 200f / partSize.x;
                    corpses.add(new Corpse(p, position, partSize, angle, partsDirection, p.random(radians(-maxRv), radians(maxRv)), 0, corpseLifespan, type, name + "Parts", hitParticle, j, false));
                }
            } else
                corpses.add(new Corpse(p, position, corpseSize, angle + p.random(radians(-5), radians(5)), new PVector(0, 0), 0, betweenCorpseFrames, corpseLifespan, type, name + "Die", "none", 0, true));

            for (int j = buffs.size() - 1; j >= 0; j--) { //deals with buffs
                Buff buff = buffs.get(j);
                //if attached, remove
                if (buff.enId == i) buffs.remove(j);
                    //shift ids to compensate for enemy removal
                else if (buff.enId > i) buff.enId -= 1;
            }
        }

        enemies.remove(i);
    }
}