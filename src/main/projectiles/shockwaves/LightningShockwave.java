package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;

public class LightningShockwave extends Shockwave {

    public LightningShockwave(PApplet p, float centerX, float centerY, int maxRadius, int damage, Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, 0, 720, damage, turret);

        damageType = Enemy.DamageType.electricity;
    }

    public void display() {
        P.strokeWeight(1);
        float alval = (float) Math.pow(radius / (float) MAX_RADIUS, 3);
        float alpha = PApplet.map(alval, 0, 1, 255, 0);
        P.stroke(new Color(0xD7F2F8).getRGB(), alpha);
        P.noFill();

        P.circle(CENTER.x, CENTER.y, radius * 2);

        P.noStroke();
        P.strokeWeight(1);
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "electricity"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        for (int i = 0; i < P.random(5, 12); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new Debris(P, pos.x, pos.y, a, levels[currentLevel].groundType));
        }
        for (int i = 0; i < P.random(5, 10); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "electricity", P.random(100,200)));
        }
    }
}
