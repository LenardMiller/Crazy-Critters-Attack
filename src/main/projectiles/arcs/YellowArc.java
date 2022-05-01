package main.projectiles.arcs;

import main.towers.turrets.Turret;
import processing.core.PApplet;

import java.awt.*;

public class YellowArc extends Arc {

    public YellowArc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance, Turret.Priority priority) {
        super(p, startX, startY, turret, damage, maxLength, maxDistance, priority);
        lineColor = Color.YELLOW;
        particleType = "nuclear";
    }
}
