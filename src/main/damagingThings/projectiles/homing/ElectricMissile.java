package main.damagingThings.projectiles.homing;

import main.damagingThings.arcs.YellowArc;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class ElectricMissile extends MagicMissile {

    public ElectricMissile(PApplet p, float x, float y, float angle, Turret turret, int damage, int priority,
                           PVector spawnPos, float effectDuration, float effectLevel) {
        super(p, x, y, angle, turret, damage, priority, spawnPos);
        trail = "nuclear";
        type = "nuclear";
        buff = "electrified";
        sprite = staticSprites.get("electricMissilePj");
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"yellowPuff"));
        arcs.add(new YellowArc(p, position.x, position.y, turret, 0, 0, 75, -1));
        projectiles.remove(this);
    }
}
