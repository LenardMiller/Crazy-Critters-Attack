package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.*;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.topParticles;

public class NuclearShockwave extends Shockwave {

    public NuclearShockwave(PApplet p, float centerX, float centerY, int maxRadius, int damage, Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, 0, 720, damage, turret);

        damageType = Enemy.DamageType.nuclear;
    }

    public void display() {
        P.strokeWeight(5);
        float alval = (float) Math.pow(radius / (float) maxRadius, 3);
        float alphaCube = PApplet.map(alval, 0, 1, 255, 0);
        float alphaLinear = PApplet.map(radius, 0, maxRadius, 255, 0);
        Color color = Utilities.mapColor(Color.yellow, Color.red, 0, maxRadius, radius);
        P.stroke(color.getRGB(), alphaCube);
        P.noFill();
        P.circle(CENTER.x, CENTER.y, radius * 2);
        P.strokeWeight(4);
        P.circle(CENTER.x, CENTER.y, radius * 1.8f);
        P.strokeWeight(3);
        P.circle(CENTER.x, CENTER.y, radius * 1.6f);
        P.strokeWeight(2);
        P.stroke(255, alphaLinear * 0.5f);
        P.circle(CENTER.x, CENTER.y, radius * 4);
        P.stroke(Color.YELLOW.getRGB(), alphaLinear);
        P.circle(CENTER.x, CENTER.y, radius * 0.8f);
        P.circle(CENTER.x, CENTER.y, radius * 0.7f);

        P.noStroke();
        P.strokeWeight(1);
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        topParticles.add(new Ouch(P, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "smoke"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(P, pos.x, pos.y, a, "nuclear"));
        for (int i = 0; i < P.random(2, 5); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            boolean small = radius < maxRadius / 4 || P.random(4) < 3;
            if (small) topParticles.add(new LargeExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
            else topParticles.add(new MediumExplosion(P, pos.x, pos.y, P.random(0, 360), "fire"));
        }
        for (int i = 0; i < P.random(1, 4); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "metal", P.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "nuclear", P.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(P, pos.x, pos.y, a, "fire", P.random(100,200)));
        }
    }
}
