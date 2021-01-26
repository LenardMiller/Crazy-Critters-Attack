package main.projectiles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class SpikeyGlue extends Projectile {

    public SpikeyGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, int effectDuration) {
        super(p, x, y, angle, turret);
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        maxSpeed = 7;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 1;
        this.angle = angle;
        angularVelocity = 0;
        sprite = spritesH.get("gluePj");
        hasTrail = true;
        trail = "glue";
        hitSound = soundsH.get("squishImpact");
        buff = "spikeyGlued";
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new Ouch(p,position.x,position.y,p.random(0,360),"gluePuff"));
    }
}