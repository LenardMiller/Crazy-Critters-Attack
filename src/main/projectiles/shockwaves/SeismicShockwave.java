package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.misc.Utilities;
import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.findAngle;
import static main.misc.Utilities.findDistBetween;

public class SeismicShockwave extends Shockwave {

    private final boolean isSeismic;
    private final boolean is360;

    public SeismicShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle, float width, int damage,
                            Turret turret, boolean isSeismic, boolean is360, int speed) {
        super(p, centerX, centerY, startingRadius, maxRadius, angle, width, damage, turret);

        this.isSeismic = isSeismic;
        this.is360 = is360;
        this.speed = speed;
    }

    @Override
    public void display() {
        p.strokeWeight(3);

        float alval = (float) Math.pow(radius / (float) maxRadius, 3);
        float alpha = PApplet.map(alval, 0, 1, is360 ? 100 : 255, 0);

        if (is360) {
            Utilities.fastCircle(p, settings.getRingResolution(), center, radius, 3, new Color(0x7D7D7D), alpha);
            p.strokeWeight(1);
            return;
        }

        p.stroke(125, alpha);
        p.noFill();

        float angleA = angle - HALF_PI + width / 2f;
        float angleB = angle - HALF_PI - width / 2f;
        p.arc(center.x, center.y, radius * 2, radius * 2, angleB, angleA);
        if (isSeismic) {
            p.strokeWeight(2.5f);
            p.arc(center.x, center.y, radius * 1.8f, radius * 1.8f, angleB, angleA);
            p.strokeWeight(2);
            p.arc(center.x, center.y, radius * 1.6f, radius * 1.6f, angleB, angleA);
        }

        p.noStroke();
        p.strokeWeight(1);
    }

    @Override
    protected void spawnParticles() {
        float mult = is360 ? 0.5f : 1f;

        if (p.random(2 * (1 / mult)) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new Ouch(p, pos.x, pos.y, a, "greyPuff"));
        }
        if (p.random(2 * (1 / mult)) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new MiscParticle(p, pos.x, pos.y, a, "smoke"));
        }
        if (isSeismic && p.random(4) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new MiscParticle(p, pos.x, pos.y, a, "stun"));
        }
        for (int i = 0; i < p.random(5 * mult, 12 * mult); i++) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new Debris(p, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < p.random(3 * mult, 6 * mult); i++) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new ExplosionDebris(p, pos.x, pos.y, a, "metal", p.random(100,200)));
        }
    }


    @Override
    protected void damageEnemies() {
        for (int i = 0; i < untouchedEnemies.size(); i++) {
            Enemy enemy = untouchedEnemies.get(i);
            int damage = this.damage;
            if (enemy instanceof FlyingEnemy) continue;
            float a = findAngle(center, enemy.position);
            float angleDif = angle - a;
            float dist = findDistBetween(enemy.position, center);
            if (abs(angleDif) < width / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, turret,
                        true, damageType, direction, -1);
                if ((enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy)) {
                    enemy.damageWithBuff(damage, "stunned", 0, 30, turret,
                            true, damageType, direction, -1);
                }
                untouchedEnemies.remove(enemy);
            }
        }
    }
}
