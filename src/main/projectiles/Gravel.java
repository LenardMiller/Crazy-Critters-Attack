package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Gravel extends Projectile {

    public Gravel(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(5, 5);
        radius = 5;
        maxSpeed = 12 + p.random(-3, 3);
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = p.random(-15,15);
        sprite = spritesH.get("stonePt");
        hitSound = soundsH.get("smallImpact");
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
    }
}