package main.projectiles.fragments;

import main.particles.Ouch;
import main.projectiles.Projectile;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Frag extends Projectile {

    protected int lifespan;
    protected int lifeTimer;

    public Frag(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        radius = 5;
        maxSpeed = 600 + p.random(-150, 150);
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = down60ToFramerate(p.random(-15,15));
        sprite = staticSprites.get("darkMetalPt");
        hitSound = sounds.get("smallImpact");
        lifespan = down60ToFramerate(15);
    }

    @Override
    public void main() {
        if (!paused) {
            lifeTimer++;
            trail();
            move();
        }
        displayPassB();
        checkCollision();
        if (lifeTimer > lifespan) dead = true;
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 ||
                position.y + size.y < -100 || position.x + size.x < -100 || dead) {
            die();
        }
    }

    @Override
    public void die() {
        projectiles.remove(this);
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
    }
}