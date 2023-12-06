package main.projectiles.shockwaves;

import main.enemies.Enemy;
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

    protected final int maxRadius;
    protected final int DAMAGE;
    protected final int speed;
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
    protected Enemy.DamageType damageType;

    public Shockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle,
                     float width, int damage, Turret turret) {
        this.P = p;

        CENTER = new PVector(centerX, centerY);
        this.maxRadius = maxRadius;
        ANGLE = angle;
        WIDTH = radians(width); //from edge to center of AOE
        DAMAGE = damage;
        TURRET = turret;

        UNTOUCHED_ENEMIES = new ArrayList<>();
        UNTOUCHED_ENEMIES.addAll(enemies);
        speed = 400;
        radius = startingRadius;
    }

    public void update() {
        if (paused) return;
        radius += speed /FRAMERATE;
        spawnParticles();
        if (radius > maxRadius) shockwaves.remove(this);
        damageEnemies();
    }

    public abstract void display();

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
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                enemy.damageWithBuff(DAMAGE, buff, effectLevel, effectDuration, TURRET,
                        true, damageType, direction, -1);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
