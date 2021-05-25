package main.damagingThings.shockwaves;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.findAngle;
import static main.misc.Utilities.findDistBetween;

public class SeismicShockwave extends Shockwave {

    private final boolean SEISMIC_SENSE;

    public SeismicShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, float angle, float width, int damage,
                            Turret turret, boolean seismicSense) {
        super(p, centerX, centerY, startingRadius, maxRadius, angle, width, damage, turret);

        SEISMIC_SENSE = seismicSense;
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        bottomParticles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        bottomParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        for (int i = 0; i < P.random(5, 12); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            bottomParticles.add(new Debris(P, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < P.random(3, 6); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            bottomParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(100,200)));
        }
    }

    @Override
    protected void damageEnemies() {
        for (int i = 0; i < UNTOUCHED_ENEMIES.size(); i++) {
            Enemy enemy = UNTOUCHED_ENEMIES.get(i);
            float a = findAngle(CENTER, enemy.position);
            float angleDif = ANGLE - a;
            float dist = findDistBetween(enemy.position, CENTER);
            if (abs(angleDif) < WIDTH / 2f && dist < radius) {
                PVector direction = PVector.fromAngle(a - HALF_PI);
                if ((enemy.state == 0 && enemy instanceof BurrowingEnemy) && SEISMIC_SENSE) {
                    enemy.damageWithBuff(DAMAGE, "stunned", 0, 60, TURRET,
                      true, damageType, direction, -1);
                }
                else enemy.damageWithoutBuff(DAMAGE, TURRET, damageType, direction, true);
                UNTOUCHED_ENEMIES.remove(enemy);
            }
        }
    }
}
