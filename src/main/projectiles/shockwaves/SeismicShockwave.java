package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.findAngle;
import static main.misc.Utilities.findDistBetween;

public class SeismicShockwave extends Shockwave {

    private final boolean SEISMIC_SENSE;

    public SeismicShockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage,
                            Turret turret, boolean seismicSense) {
        super(p, centerX, centerY, maxRadius, angle, width, damage, turret);

        SEISMIC_SENSE = seismicSense;
    }

    protected void display() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        particles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(0.5f, 2.5f)));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        particles.add(new BuffParticle(P, pos.x, pos.y, a, "smoke"));
        int debrisCount = (int) P.random(2, 5);
        for (int i = 0; i < debrisCount; i++) {
            a = randomAngle();
            pos = randomPosition(a);
            particles.add(new Debris(P, pos.x + CENTER.x, pos.y + CENTER.y, a, levels[currentLevel].groundType));
        }
    }

    protected void damageEnemies() {
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
