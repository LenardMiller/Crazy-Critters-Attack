package main.projectiles;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.findAngle;
import static main.misc.Utilities.findDistBetween;
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
    private final boolean SEISMIC_SENSE;

    public Shockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage,
                     Turret turret, boolean seismicSense) {
        this.P = p;

        CENTER = new PVector(centerX, centerY);
        this.MAX_RADIUS = maxRadius;
        this.ANGLE = angle;
        this.WIDTH = radians(width); //from edge to center of AOE
        this.DAMAGE = damage;
        this.TURRET = turret;
        this.SEISMIC_SENSE = seismicSense;

        UNTOUCHED_ENEMIES = new ArrayList<>();
        UNTOUCHED_ENEMIES.addAll(enemies);
        radius = 0;
        SPEED = 7;
    }

    public void main() {
        if (!paused) {
            radius += SPEED;
            display();
        }
        if (radius > MAX_RADIUS) shockwaves.remove(this);
        damageEnemies();
    }

    private void display() {
        float a = P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
        float x = (radius * sin(a)) + CENTER.x;
        float y = (-(radius * cos(a))) + CENTER.y;
        particles.add(new ExplosionDebris(P, x, y, a, "metal", P.random(0.5f, 2.5f)));
        a = P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
        x = (radius * sin(a)) + CENTER.x;
        y = (-(radius * cos(a))) + CENTER.y;
        particles.add(new Ouch(P, x, y, a, "greyPuff"));
        a = P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
        x = (radius * sin(a)) + CENTER.x;
        y = (-(radius * cos(a))) + CENTER.y;
        particles.add(new BuffParticle(P, x, y, a, "smoke"));

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
                PVector direction = PVector.fromAngle(a - HALF_PI);
                if (enemy.stealthMode && SEISMIC_SENSE) {
                    enemy.damageWithBuff(DAMAGE, "stunned", 0, 60, TURRET,
                            true, "normal", direction, -1);
                }
                else enemy.damageWithoutBuff(DAMAGE, TURRET, "normal", direction, true);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
