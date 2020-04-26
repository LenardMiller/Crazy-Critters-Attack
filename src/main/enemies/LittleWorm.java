package main.enemies;

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
}