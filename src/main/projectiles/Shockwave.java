package main.projectiles;

import main.enemies.Enemy;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Shockwave {

    private PApplet p;

    private PVector center;
    private int radius;
    private int maxRadius;
    private float angle;
    private float width;
    private int damage;
    private ArrayList<Enemy> untouchedEnemies;
    private int speed;

    public Shockwave(PApplet p, float centerX, float centerY, int maxRadius, float angle, float width, int damage) {
        this.p = p;

        center = new PVector(centerX, centerY);
        this.maxRadius = maxRadius;
        this.angle = angle;
        this.width = radians(width); //from edge to center of AOE
        this.damage = damage;

        untouchedEnemies = enemies;
        radius = 0;
        speed = 7;
    }

    public void main() {
        radius += speed;
        if (radius > maxRadius) shockwaves.remove(this);
        display();
    }

    private void display() {
        float a = p.random(angle - (width / 2), angle + (width / 2));
        float x = radius * sin(a);
        float y = -(radius * cos(a));
        underParticles.add(new Pile(p, x + center.x, y + center.y, 0, levels[currentLevel].groundType));

        int debrisCount = (int) p.random(2, 5);
        for (int i = 0; i < debrisCount; i++) {
            a = p.random(angle - (width / 2), angle + (width / 2));
            x = radius * sin(a);
            y = -(radius * cos(a));
            particles.add(new Debris(p, x + center.x, y + center.y, a, levels[currentLevel].groundType));
        }
    }
}
