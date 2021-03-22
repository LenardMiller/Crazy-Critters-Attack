package main.projectiles;

import main.enemies.Enemy;
import main.particles.*;
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
    private final boolean NUKE_MODE;

    public Shockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage,
                     Turret turret, boolean seismicSense, boolean nukeMode) {
        this.P = p;

        CENTER = new PVector(centerX, centerY);
        MAX_RADIUS = maxRadius;
        ANGLE = angle;
        WIDTH = radians(width); //from edge to center of AOE
        DAMAGE = damage;
        TURRET = turret;
        SEISMIC_SENSE = seismicSense;
        NUKE_MODE = nukeMode;

        UNTOUCHED_ENEMIES = new ArrayList<>();
        UNTOUCHED_ENEMIES.addAll(enemies);
        radius = 0;
        SPEED = 400;
    }

    public void main() {
        if (!paused) {
            radius += SPEED/FRAMERATE;
            display();
        }
        if (radius > MAX_RADIUS) shockwaves.remove(this);
        damageEnemies();
    }

    private void display() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(0.5f, 2.5f)));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "smoke"));
        if (NUKE_MODE) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new BuffParticle(P, pos.x, pos.y, a, "nuclear"));
            for (int i = 0; i < P.random(2, 5); i++) {
                a = randomAngle();
                pos = randomPosition(a);
                boolean small = radius < MAX_RADIUS / 4 || P.random(4) < 3;
                int num = (int) P.random(16, 42);
                if (small) num = (int) P.random(8, 21);
                for (int j = 0; j < num; j++) {
                    particles.add(new ExplosionDebris(P, pos.x, pos.y, P.random(0, 360), "fire", P.random(1.5f, 4.5f)));
                }
                if (small) particles.add(new LargeExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
                else particles.add(new MediumExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
            }
        }

        int debrisCount = (int) P.random(2, 5);
        for (int i = 0; i < debrisCount; i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new Debris(P, pos.x + CENTER.x, pos.y + CENTER.y, a, levels[currentLevel].groundType));
        }
    }

    private float randomAngle() {
        return P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
    }

    private PVector randomPosition(float angle) {
        return new PVector((radius * sin(angle)) + CENTER.x, (-(radius * cos(angle))) + CENTER.y);
    }

    private void damageEnemies() {
        String damageType = null;
        if (NUKE_MODE) damageType = "burning";
        for (int i = 0; i < UNTOUCHED_ENEMIES.size(); i++) {
            Enemy enemy = UNTOUCHED_ENEMIES.get(i);
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                if (enemy.stealthMode && SEISMIC_SENSE) {
                    enemy.damageWithBuff(DAMAGE, "stunned", 0, 60, TURRET,
                            true, damageType, direction, -1);
                }
                else enemy.damageWithoutBuff(DAMAGE, TURRET, damageType, direction, true);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
