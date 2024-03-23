package main.projectiles.shockwaves;

import main.enemies.Enemy;
import main.misc.Utilities;
import main.particles.*;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.settings;
import static main.Main.topParticles;

public class NuclearShockwave extends Shockwave {

    public NuclearShockwave(PApplet p, float centerX, float centerY, int maxRadius, int damage, Turret turret) {
        super(p, centerX, centerY, 0, maxRadius, 0, 720, damage, turret);

        damageType = Enemy.DamageType.nuclear;
    }

    public void display() {
        float alval = (float) Math.pow(radius / (float) maxRadius, 3);
        float alphaCube = PApplet.map(alval, 0, 1, 255, 0);
        float alphaLinear = PApplet.map(radius, 0, maxRadius, 255, 0);
        Color color = Utilities.mapColor(Color.yellow, Color.red, 0, maxRadius, radius);
        Utilities.fastCircle(p, settings.getRingResolution(), center, radius, 5, color, alphaCube);
        Utilities.fastCircle(p, settings.getRingResolution(), center, (int) (radius * 0.9f), 4,
                color, alphaCube);
        Utilities.fastCircle(p, settings.getRingResolution(), center, (int) (radius * 0.8f), 3,
                color, alphaCube);
        Utilities.fastCircle(p, settings.getRingResolution(), center, radius * 2, 2,
                Color.WHITE, alphaLinear * 0.5f);
        Utilities.fastCircle(p, settings.getRingResolution(), center, (int) (radius * 0.4), 2,
                Color.YELLOW, alphaLinear * 0.5f);
        Utilities.fastCircle(p, settings.getRingResolution(), center, (int) (radius * 0.35f), 2,
                Color.YELLOW, alphaLinear * 0.5f);
    }

    @Override
    protected void spawnParticles() {
        float a = randomAngle();
        PVector pos = randomPosition(a);
        topParticles.add(new Ouch(p, pos.x, pos.y, a, "greyPuff"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(p, pos.x, pos.y, a, "smoke"));
        a = randomAngle();
        pos = randomPosition(a);
        topParticles.add(new MiscParticle(p, pos.x, pos.y, a, "nuclear"));
        for (int i = 0; i < p.random(2, 5); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            boolean small = radius < maxRadius / 4 || p.random(4) < 3;
            if (small) topParticles.add(new LargeExplosion(p, pos.x, pos.y, p.random(0, 360), "fire"));
            else topParticles.add(new MediumExplosion(p, pos.x, pos.y, p.random(0, 360), "fire"));
        }
        for (int i = 0; i < p.random(1, 4); i++) {
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(p, pos.x, pos.y, a, "metal", p.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(p, pos.x, pos.y, a, "nuclear", p.random(100,200)));
            a = randomAngle();
            pos = randomPosition(a);
            topParticles.add(new ExplosionDebris(p, pos.x, pos.y, a, "fire", p.random(100,200)));
        }
    }
}
