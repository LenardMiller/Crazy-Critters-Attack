package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.topParticles;
import static processing.core.PConstants.HALF_PI;

public class FireShockwave extends Shockwave {

    public FireShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, int damage,
                         Turret turret, float effectLevel, float effectDuration) {
        super(p, centerX, centerY, startingRadius, maxRadius, 0, 720, damage, turret);

        damageType = Enemy.DamageType.burning;
        buff = "burning";
        this.effectDuration = effectDuration;
        this.effectLevel = effectLevel;
    }

    public void display() {
        P.strokeWeight(3);
        float alval = (float) Math.pow(radius / (float) MAX_RADIUS, 3);
        float alpha = PApplet.map(alval, 0, 1, 255, 0);
        Color color = Utilities.mapColor(Color.yellow, Color.red, 0, MAX_RADIUS, radius);
        P.stroke(color.getRGB(), alpha);
        P.noFill();

        float angleA = ANGLE - HALF_PI + WIDTH / 2f;
        float angleB = ANGLE - HALF_PI - WIDTH / 2f;
        P.arc(CENTER.x, CENTER.y, radius * 2, radius * 2, angleB, angleA);

        P.noStroke();
        P.strokeWeight(1);
    }

    @Override
    protected void spawnParticles() {
        if (P.random(3) < 1) {
            float a;
            PVector pos;
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "fire"));
            for (int i = 0; i < P.random(5, 10); i++) {
                a = randomAngle();
                pos = randomPosition(a);
                topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100, 200)));
            }
        }
    }
}
