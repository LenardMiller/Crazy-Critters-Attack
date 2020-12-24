package main.projectiles;

import main.enemies.Enemy;
import main.particles.Debris;
import main.particles.Pile;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.findAngle;
import static main.misc.MiscMethods.findDistBetween;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Shockwave {

    private PApplet p;

    private PVector center;
    private int radius;
    private int maxRadius;
    private float angle;
    private float width;
    private int damage;
    private ArrayList<Enemy> untouchedEnemies;
    private int speed;
    private Turret turret;

    public Shockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage, Turret turret) {
        this.p = p;

        center = new PVector(centerX, centerY);
        this.maxRadius = maxRadius;
        this.angle = angle;
        this.width = radians(width); //from edge to center of AOE
        this.damage = damage;

        untouchedEnemies = new ArrayList<>();
        untouchedEnemies.addAll(enemies);
        radius = 0;
        speed = 7;
    }

    public void main() {
        radius += speed;
        if (radius > maxRadius) shockwaves.remove(this);
        display();
        damageEnemies();
    }

    private void display() {
        float a = p.random(angle - (width / 2), angle + (width / 2));
        float x = radius * sin(a);
        float y = -(radius * cos(a));
        underParticles.add(new Pile(p, x + center.x, y + center.y, 0, levels[currentLevel].groundType));

        int debrisCount = (int) p.random(2, 5);
        for (int i = 0; i < debrisCount; i++) {
            a = p.random(angle - (width / 2), angle + (width / 2));
            x = radius * sin(a);
            y = -(radius * cos(a));
            particles.add(new Debris(p, x + center.x, y + center.y, a, levels[currentLevel].groundType));
        }
    }

    private void damageEnemies() {
        for (int i = 0; i < untouchedEnemies.size(); i++) {
            Enemy enemy = untouchedEnemies.get(i);
            float a = findAngle(center, enemy.position);
            float angleDif = angle - a;
            float dist = findDistBetween(enemy.position, center);
            if (abs(angleDif) < width && dist < radius) {
                enemy.damageSimple(damage, turret, "normal", PVector.fromAngle(a - HALF_PI), true);
                untouchedEnemies.remove(enemy);
            }
        }
    }
}
