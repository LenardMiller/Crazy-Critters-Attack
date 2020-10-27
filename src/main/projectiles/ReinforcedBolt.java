package main.projectiles;

import main.particles.BoltBreak;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class ReinforcedBolt extends Projectile {

    public ReinforcedBolt(PApplet p, float x, float y, float angle, Turret turret, int damage, int pierce) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(7, 32);
        radius = 10;
        maxSpeed = 24;
        speed = maxSpeed;
        this.damage = damage;
        this.pierce = pierce;
        this.angle = angle;
        sprite = spritesH.get("reinforcedBoltPj");
    }

    public void die(int i) {
        projectiles.remove(i);
        particles.add(new BoltBreak(p,position.x,position.y,angleTwo));
    }
}