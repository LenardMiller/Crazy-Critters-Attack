package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Glue extends Projectile {

    public Glue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, int effectDuration) {
        super(p, x, y, angle, turret);
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        maxSpeed = 7;
        speed = maxSpeed;
        this.damage = damage;
        this.angle = angle;
        angularVelocity = 0;
        sprite = staticSprites.get("gluePj");
        hasTrail = true;
        trail = "glue";
        hitSound = sounds.get("squishImpact");
        buff = "glued";
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
    }
}