package main.projectiles;

import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public class Needle extends Projectile {

    public int effectLevel;
    private int lifeTimer;

    public Needle(PApplet p, float x, float y, float angle, Tower tower, int damage, int effectLevel, int lifespan) {
        super(p, x, y, angle, tower);
        position = new PVector(x, y);
        size = new PVector(2, 17);
        radius = 10;
        maxSpeed = 18;
        speed = maxSpeed;
        this.damage = damage;
        this.effectLevel = effectLevel;
        lifeTimer = lifespan + p.frameCount;
        this.angle = angle;
        sprite = spritesH.get("needlePj");
    }

    public void main(ArrayList<Projectile> projectiles, int i) {
        trail();
        displayPassB();
        move();
        collideEn();
        if (p.frameCount > lifeTimer) dead = true;
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 || position.y + size.y < -100 || position.x + size.x < -100) {
            dead = true;
        }
        if (dead) die(i);
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,angleTwo,"greyPuff"));
    }
}