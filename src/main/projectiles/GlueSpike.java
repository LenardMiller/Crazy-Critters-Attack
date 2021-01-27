package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public class GlueSpike extends Projectile {

    private int deathDate;

    public GlueSpike(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(7, 7);
        radius = 5;
        maxSpeed = 10 + p.random(-3, 3);
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        angularVelocity = 0;
        sprite = spritesH.get("glueSpikePj");
        hitSound = soundsH.get("smallImpact");
        int lifespan = 10;
        deathDate = p.frameCount + lifespan;
    }

    public void main(ArrayList<Projectile> projectiles, int i) {
        trail();
        displayPassB();
        move();
        collideEn();
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 ||
                position.y + size.y < -100 || position.x + size.x < -100 || dead) {
            die(i);
        }
        if (p.frameCount > deathDate) projectiles.remove(i);
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
    }
}