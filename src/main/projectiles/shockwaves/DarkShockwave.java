package main.projectiles.shockwaves;

import main.misc.Utilities;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import java.awt.*;

import static main.Main.*;

public class DarkShockwave extends Shockwave {

    public DarkShockwave(PApplet p, float centerX, float centerY, int startingRadius, int maxRadius, Turret turret) {
        super(p, centerX, centerY, startingRadius, maxRadius, 0, 720, 0, turret);

        radius = maxRadius;
    }

    @Override
    public void display() {
        p.strokeWeight(3);
        float alval = (float) Math.pow(radius / (float) maxRadius, 3);
        float alpha = PApplet.map(alval, 0, 1, 255, 0);
        Color color = Utilities.mapColor(new Color(225, 60, 250), new Color(72, 16, 123), 0,
                maxRadius, radius);
        p.stroke(color.getRGB(), alpha);
        p.noFill();

        p.circle(center.x, center.y, radius * 2);

        p.noStroke();
        p.strokeWeight(1);
    }

    @Override
    public void update() {
        if (isPaused) return;
        radius -= speed * 0.5f / FRAMERATE;
        spawnParticles();
        spawnBoostParticles();
        if (radius <= 0) shockwaves.remove(this);
        damageEnemies();
    }

    @Override
    protected void spawnParticles() {}

    @Override
    protected void damageEnemies() {}
}
