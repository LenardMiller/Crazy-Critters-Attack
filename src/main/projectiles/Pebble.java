package main.projectiles;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesH;

public class Pebble extends Projectile{

    public Pebble(PApplet p, float x, float y, float angle, int damage) {
        super(p, x, y, angle);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 12;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        sprite = spritesH.get("pebblePj");
    }
}