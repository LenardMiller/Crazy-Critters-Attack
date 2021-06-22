package main.damagingThings.projectiles.homing;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

public class ElectricMissile extends MagicMissile {

    public ElectricMissile(PApplet p, float x, float y, float angle, Turret turret, int damage, int priority,
                           PVector spawnPos, float effectDuration, float effectLevel) {
        super(p, x, y, angle, turret, damage, priority, spawnPos);
        trail = "nuclear";
        type = "nuclear";
        buff = "electrified";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }
}
