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

    private final PApplet P;

    private final PVector CENTER;
    private int radius;
    private final int MAX_RADIUS;
    private final float ANGLE;
    private final float WIDTH;
    private final int DAMAGE;
    private final ArrayList<Enemy> UNTOUCHED_ENEMIES;
    private final int SPEED;
    private final Turret TURRET;

    public Shockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage, Turret turret) {
        this.P = p;

        CENTER = new PVector(centerX, centerY);
        this.MAX_RADIUS = maxRadius;
        this.ANGLE = angle;
        this.WIDTH = radians(width); //from edge to center of AOE
        this.DAMAGE = damage;
        this.TURRET = turret;

        UNTOUCHED_ENEMIES = new ArrayList<>();
        UNTOUCHED_ENEMIES.addAll(enemies);
        radius = 0;
        SPEED = 7;
    }

    public void main() {
        radius += SPEED;
        if (radius > MAX_RADIUS) shockwaves.remove(this);
        display();
        damageEnemies();
    }

    private void display() {
        float a = P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
        float x = radius * sin(a);
        float y = -(radius * cos(a));
        underParticles.add(new Pile(P, x + CENTER.x, y + CENTER.y, 0, levels[currentLevel].groundType));

        int debrisCount = (int) P.random(2, 5);
        for (int i = 0; i < debrisCount; i++) {
            a = P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
            x = radius * sin(a);
            y = -(radius * cos(a));
            particles.add(new Debris(P, x + CENTER.x, y + CENTER.y, a, levels[currentLevel].groundType));
        }
    }

    private void damageEnemies() {
        for (int i = 0; i < UNTOUCHED_ENEMIES.size(); i++) {
            Enemy enemy = UNTOUCHED_ENEMIES.get(i);
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                enemy.damageSimple(DAMAGE, TURRET, "normal", PVector.fromAngle(a - HALF_PI), true);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
