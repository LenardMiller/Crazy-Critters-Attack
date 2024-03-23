package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.particles.MiscParticle;
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
    protected final int damage;
    protected final float angle;
    protected final float width;
    protected final PVector center;
    protected final ArrayList<Enemy> untouchedEnemies;
    protected final Turret turret;
    protected final PApplet p;

    protected int radius;
    protected int speed;
    protected float effectDuration;
    protected float effectLevel;
    protected String buff;
    protected Enemy.DamageType damageType;

    public Shockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle,
                     float width, int damage, Turret turret) {
        this.p = p;

        center = new PVector(centerX, centerY);
        this.maxRadius = maxRadius;
        this.angle = angle;
        this.width = radians(width); //from edge to center of AOE
        this.damage = damage;
        this.turret = turret;

        untouchedEnemies = new ArrayList<>();
        untouchedEnemies.addAll(enemies);
        speed = 400;
        radius = startingRadius;
    }

    public void update() {
        if (isPaused) return;
        radius += speed /FRAMERATE;
        spawnParticles();
        spawnBoostParticles();
        if (radius > maxRadius) shockwaves.remove(this);
        damageEnemies();
    }

    public abstract void display();

    protected abstract void spawnParticles();

    protected void spawnBoostParticles() {
        if (turret.boostedDamage() == 0) return;

        if (p.random(2) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new MiscParticle(p, pos.x, pos.y, a, "orangeMagic"));
        }
    }

    protected float randomAngle() {
        return p.random(angle - (width / 2), angle + (width / 2));
    }

    protected PVector randomPosition(float angle) {
        return new PVector((radius * sin(angle)) + center.x, (-(radius * cos(angle))) + center.y);
    }

    protected void damageEnemies() {
        for (int i = 0; i < untouchedEnemies.size(); i++) {
            Enemy enemy = untouchedEnemies.get(i);
            float a = findAngle(center, enemy.position);
            float angleDif = angle - a;
            float dist = findDistBetween(enemy.position, center);
            if (abs(angleDif) < width / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret,
                        true, damageType, direction);
                untouchedEnemies.remove(enemy);
            }
        }
    }
}
