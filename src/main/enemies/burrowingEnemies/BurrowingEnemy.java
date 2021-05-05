package main.enemies.burrowingEnemies;

import main.enemies.Enemy;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class BurrowingEnemy extends Enemy {

    protected BurrowingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    protected void move() {
        if (stealthMode && (int)p.random(0,15) == 0)
            particles.add(new Debris(p,position.x,position.y,p.random(0,360),levels[currentLevel].groundType));
        if (p.random(0,50) < 1)
            underParticles.add(new Pile(p, position.x, position.y, 0, levels[currentLevel].groundType));
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed/FRAMERATE);
        position.add(m);
        speed = maxSpeed;
    }
}
