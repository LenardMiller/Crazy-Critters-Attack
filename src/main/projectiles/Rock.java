package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Rock extends Projectile {

    public Rock(PApplet p, float x, float y, float angle, Turret turret, int damage) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(13, 13);
        radius = 6;
        maxSpeed = 12;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        angularVelocity = p.random(-15,15);
        sprite = spritesH.get("rockPj");
        hitSound = soundsH.get("rock");
        buff = "bleeding";
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"greyPuff"));
    }
}