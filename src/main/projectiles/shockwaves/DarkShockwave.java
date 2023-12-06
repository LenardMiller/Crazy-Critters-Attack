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
        P.strokeWeight(3);
        float alval = (float) Math.pow(radius / (float) maxRadius, 3);
        float alpha = PApplet.map(alval, 0, 1, 255, 0);
        Color color = Utilities.mapColor(new Color(225, 60, 250), new Color(72, 16, 123), 0,
                maxRadius, radius);
        P.stroke(color.getRGB(), alpha);
        P.noFill();

        P.circle(CENTER.x, CENTER.y, radius * 2);

        P.noStroke();
        P.strokeWeight(1);
    }

    @Override
    public void update() {
        if (paused) return;
        radius -= speed * 0.5f / FRAMERATE;
        spawnParticles();
        if (radius <= 0) shockwaves.remove(this);
        damageEnemies();
    }

    @Override
    protected void spawnParticles() {}

    @Override
    protected void damageEnemies() {}
}
