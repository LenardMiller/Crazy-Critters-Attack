package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Needle extends Projectile {

    public Needle(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectLevel, float effectDuration) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(2, 17);
        radius = 10;
        maxSpeed = 1100;
        speed = maxSpeed;
        this.damage = damage;
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        this.angle = angle;
        sprite = staticSprites.get("needlePj");
        buff = "decay";
    }

    @Override
    public void main() {
        trail();
        displayPassB();
        move();
        checkCollision();
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 || position.y + size.y < -100 || position.x + size.x < -100) {
            dead = true;
        }
        if (dead) die();
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,angleTwo,"greyPuff"));
        projectiles.remove(this);
    }
}