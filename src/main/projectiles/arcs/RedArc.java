package main.projectiles.arcs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.arcs;
import static main.Main.paused;
import static main.misc.Utilities.up60ToFramerate;

public class RedArc extends Arc {

    public RedArc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance,
                  Turret.Priority priority) {
        super(p, startX, startY, turret, damage, maxLength, maxDistance, priority);
        lineColor = Color.red;
        particleType = Enemy.DamageType.energy;
        maxPoints = 30;
        variation = 15;
        weight = 3;
    }
}
