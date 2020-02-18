package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class MiscProjectile extends Projectile{

    public PImage[] sprites = new PImage[6]; //alternate sprites, passed in

    public MiscProjectile(PApplet p, float x, float y, float angle, Turret turret, int spriteType, int damage) {
        super(p, x, y, angle, turret);
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

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
    }
}