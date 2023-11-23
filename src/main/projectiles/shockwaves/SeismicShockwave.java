package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
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

    private boolean isSeismic;
    private boolean is360;

    public SeismicShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle, float width, int damage,
                            Turret turret, boolean isSeismic, boolean is360) {
        super(p, centerX, centerY, startingRadius, maxRadius, angle, width, damage, turret);

        this.isSeismic = isSeismic;
        this.is360 = is360;
    }

    @Override
    public void display() {
        P.strokeWeight(3);
        float alpha = PApplet.map(radius, 0, MAX_RADIUS, is360 ? 100 : 255, 0);
        P.stroke(125, alpha);
        P.noFill();

        float angleA = ANGLE - HALF_PI + WIDTH / 2f;
        float angleB = ANGLE - HALF_PI - WIDTH / 2f;
        P.arc(CENTER.x, CENTER.y, radius * 2, radius * 2, angleB, angleA);
        if (isSeismic) {
            P.strokeWeight(2.5f);
            P.arc(CENTER.x, CENTER.y, radius * 1.8f, radius * 1.8f, angleB, angleA);
            P.strokeWeight(2);
            P.arc(CENTER.x, CENTER.y, radius * 1.6f, radius * 1.6f, angleB, angleA);
        }

        P.noStroke();
        P.strokeWeight(1);
    }

    @Override
    protected void spawnParticles() {
        float mult = is360 ? 0.5f : 1f;

        if (P.random(2 * (1 / mult)) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        }
        if (P.random(2 * (1 / mult)) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        }
        if (isSeismic && P.random(4) < 1f) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new MiscParticle(P, pos.x, pos.y, a, "stun"));
        }
        for (int i = 0; i < P.random(5 * mult, 12 * mult); i++) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new Debris(P, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < P.random(3 * mult, 6 * mult); i++) {
            float a = randomAngle();
            PVector pos = randomPosition(a);
            bottomParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(100,200)));
        }
    }


    @Override
    protected void damageEnemies() {
        for (int i = 0; i < UNTOUCHED_ENEMIES.size(); i++) {
            Enemy enemy = UNTOUCHED_ENEMIES.get(i);
            int damage = DAMAGE;
            if (enemy instanceof FlyingEnemy) continue;
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                enemy.damageWithBuff(damage, buff, effectLevel, effectDuration, TURRET,
                        true, damageType, direction, -1);
                if ((enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy)) {
                    enemy.damageWithBuff(damage, "stunned", 0, 30, TURRET,
                            true, damageType, direction, -1);
                }
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
