package main.damagingThings.arcs;

import main.towers.turrets.Turret;
import processing.core.PApplet;

import java.awt.*;

public class OrangeArc extends Arc {

    public OrangeArc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance,
                  Turret.Priority priority, int maxPoints) {
        super(p, startX, startY, turret, damage, maxLength, maxDistance, priority);
        lineColor = new Color(255, 117, 0);
        particleType = "orangeMagic";
        this.maxPoints = maxPoints;
        variation = 15;
        weight = 3;
    }
}
