package main.projectiles;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class RedArc extends Arc { //todo: make nicer looking

    public RedArc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance,
                  int priority) {
        super(p, startX, startY, turret, damage, maxLength, maxDistance, priority);
        lineColor = new Color(255, 0, 0);
        particleType = "energy";
    }

    protected void damageEnemy(Enemy enemy, int damage, Turret turret) {
        enemy.damageWithoutBuff(damage, turret, particleType, new PVector(0,0), false);
    }
}
