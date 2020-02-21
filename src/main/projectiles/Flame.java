package main.projectiles;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Flame extends Projectile{

    private PImage[] sprites;
    private int currentSprite;
    private int delay;

    public Flame(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        radius = 5;
        maxSpeed = 5;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 900;
        this.angle = angle;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        sprites = spritesAnimH.get("flamePJ");
        buff = "burning";
        splashEn = false;
    }

    public void die(int i) {
        projectiles.remove(i);
    }

    public void displayPassA() {} //no shadow

    public void displayPassB() {
        delay++;
        sprite = sprites[currentSprite];
        if (delay > 4 && p.random(0,10) > 1) {
            currentSprite++;
            delay = 0;
        }
        if (currentSprite > 9 && speed > 0) speed /= 1.1;
        if (pierce < 900) speed = 0;
        if (currentSprite >= sprites.length) dead = true;

        angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }
}