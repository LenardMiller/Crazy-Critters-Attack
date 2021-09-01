package main.damagingThings.shockwaves;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.findAngle;
import static main.misc.Utilities.findDistBetween;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public abstract class Shockwave {

    protected final int MAX_RADIUS;
    protected final int DAMAGE;
    protected final int SPEED;
    protected final float ANGLE;
    protected final float WIDTH;
    protected final PVector CENTER;
    protected final ArrayList<Enemy> UNTOUCHED_ENEMIES;
    protected final Turret TURRET;
    protected final PApplet P;

    protected int radius;
    protected float effectDuration;
    protected float effectLevel;
    protected String buff;
    protected String damageType;

    public Shockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle,
                     float width, int damage, Turret turret) {
        this.P = p;

        CENTER = new PVector(centerX, centerY);
        MAX_RADIUS = maxRadius;
        ANGLE = angle;
        WIDTH = radians(width); //from edge to center of AOE
        DAMAGE = damage;
        TURRET = turret;

        UNTOUCHED_ENEMIES = new ArrayList<>();
        UNTOUCHED_ENEMIES.addAll(enemies);
        SPEED = 400;
        radius = startingRadius;
    }

    public void main() {
        if (!paused) {
            radius += SPEED/FRAMERATE;
            spawnParticles();
        }
        if (radius > MAX_RADIUS) shockwaves.remove(this);
        damageEnemies();
    }

    protected abstract void spawnParticles();

    protected float randomAngle() {
        return P.random(ANGLE - (WIDTH / 2), ANGLE + (WIDTH / 2));
    }

    protected PVector randomPosition(float angle) {
        return new PVector((radius * sin(angle)) + CENTER.x, (-(radius * cos(angle))) + CENTER.y);
    }

    protected void damageEnemies() {
        for (int i = 0; i < UNTOUCHED_ENEMIES.size(); i++) {
            Enemy enemy = UNTOUCHED_ENEMIES.get(i);
            if (enemy instanceof FlyingEnemy) continue;
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                if ((enemy.state == 0 && enemy instanceof BurrowingEnemy)) {
                    enemy.damageWithBuff(DAMAGE, "stunned", 0, 30, TURRET,
                      true, damageType, direction, -1);
                }
                else enemy.damageWithoutBuff(DAMAGE, TURRET, damageType, direction, true);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
