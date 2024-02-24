package main.projectiles.arcs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.arcs;
import static main.Main.paused;
import static processing.core.PConstants.TWO_PI;

public class DemonArc extends Arc {

    public DemonArc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance,
                    Turret.Priority priority) {
        super(p, startX, startY, turret, damage, maxLength, maxDistance, priority);
        lineColor = new Color(0xE12E0E);
        particleType = Enemy.DamageType.energy;
        maxPoints = 30;
        variation = 15;
        weight = 3;
        particleChance = 30;
    }

    /** Creates points that define effect, damages enemies **/
    @Override
    protected void zap(Enemy blacklistedEnemy) {
        bigPoints.add(new StartEndPoints(p, startPosition));
        ArrayList<Enemy> hitEnemies = new ArrayList<>();
        if (blacklistedEnemy != null) hitEnemies.add(blacklistedEnemy);
        Enemy mainEnemy = getTargetEnemy(startPosition, priority, hitEnemies);
        if (mainEnemy != null) {
            damageEnemy(mainEnemy, damage, turret);
            hitEnemies.add(mainEnemy);
            bigPoints.add(new StartEndPoints(p, new PVector(mainEnemy.position.x, mainEnemy.position.y)));
            int x = 2; //no clue
            for (int i = 1; i < maxLength; i++) {
                Enemy jumpEnemy = getTargetEnemy(bigPoints.get(x - 1).position, Turret.Priority.Close, hitEnemies);
                if (jumpEnemy != null) {
                    bigPoints.add(new StartEndPoints(p, jumpEnemy.position));
                    damageEnemy(jumpEnemy, damage, turret);
                    hitEnemies.add(jumpEnemy);
                    x++;
                }
            }
            for (int i = 0; i < bigPoints.size() - 1; i++) bigPoints.get(i).getPoints(bigPoints.get(i + 1).position, particleChance, maxPoints);
        } else {
            float angle = p.random(TWO_PI);
            float mag = p.random(5, 20);
            PVector position = PVector.fromAngle(angle);
            position = position.setMag(mag);
            position.add(startPosition);
            bigPoints.add(new StartEndPoints(p, new PVector(position.x, position.y)));
            for (int i = 0; i < bigPoints.size() - 1; i++) bigPoints.get(i).getPoints(bigPoints.get(i + 1).position, particleChance, 3);
        }
    }

    @Override
    public void update(int j) {
        if (paused) return;
        if (alpha == 255) {
            zap(blacklistedEnemy);
            alpha = 254;
        } else if (alpha == 254) {
            alpha = 0;
        } else {
            arcs.remove(j);
        }
    }

    @Override
    protected void damageEnemy(Enemy enemy, int damage, Turret turret) {
        enemy.damageWithoutBuff(damage, turret, particleType, new PVector(0,0), false);
    }
}
