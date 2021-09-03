package main.damagingThings.projectiles;

import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;

public class ReinforcedBolt extends Bolt {

    public ReinforcedBolt(PApplet p, float x, float y, float angle, Turret turret, int damage, int pierce) {
        super(p, x, y, angle, turret, damage, pierce);
        sprite = staticSprites.get("reinforcedBoltPj");
    }
}