package main.projectiles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesH;

public class Bolt extends Projectile{

    public Bolt(PApplet p, float x, float y, float angle, int damage, int pierce) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(7, 32);
        radius = 10;
        maxSpeed = 24;
        speed = maxSpeed;
        this.damage = damage;
        this.pierce = pierce;
        this.angle = angle;
        hitDelay = 2;
        sprite = spritesH.get("boltPj");
    }
}