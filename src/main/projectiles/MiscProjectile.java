package main.projectiles;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.spritesAnimH;

public class MiscProjectile extends Projectile{

    public PImage[] sprites = new PImage[6]; //alternate sprites, passed in

    public MiscProjectile(PApplet p, float x, float y, float angle, Tower tower, int spriteType, int damage) {
        super(p, x, y, angle, tower);
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 5;
        maxSpeed = 12;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        angleTwo = angle;
        angularVelocity = 15; //degrees mode
        sprites = spritesAnimH.get("miscPJ");
        sprite = sprites[spriteType];
    }
}