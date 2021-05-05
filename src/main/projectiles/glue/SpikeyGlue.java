package main.projectiles.glue;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.sounds;
import static main.Main.staticSprites;

public class SpikeyGlue extends Glue {

    public SpikeyGlue(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel, float effectDuration) {
        super(p, x, y, angle, turret, damage, effectLevel, effectDuration);
        position = new PVector(x, y);
        size = new PVector(10, 23);
        radius = 6;
        maxSpeed = 400;
        speed = maxSpeed;
        angularVelocity = 0;
        sprite = staticSprites.get("spikeyGluePj");
        hasTrail = true;
        trail = "glue";
        hitSound = sounds.get("squishImpact");
        buff = "spikeyGlued";
    }
}