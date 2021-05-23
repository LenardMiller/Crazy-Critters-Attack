package main.enemies.burrowingEnemies;

import main.enemies.Enemy;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public abstract class BurrowingEnemy extends Enemy {

    protected BurrowingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    protected void move() {
        if (stealthMode) {
            if (p.random(10) < pfSize * pfSize) {
                PVector particlePosition = randomPosition();
                particles.add(new Debris(p, particlePosition.x, particlePosition.y, p.random(0, 360), levels[currentLevel].groundType));
            } if (p.random(25) < pfSize * pfSize) {
                PVector particalPosition = randomPosition();
                underParticles.add(new Pile(p, particalPosition.x, particalPosition.y, 0, levels[currentLevel].groundType));
            }
        }
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed/FRAMERATE);
        position.add(m);
        speed = maxSpeed;
    }

    private PVector randomPosition() {
        float x = p.random(position.x - (size.x / 2), position.x + (size.x / 2));
        float y = p.random(position.y - (size.y / 2), position.y + (size.y / 2));
        return new PVector(x, y);
    }

    //todo: reveal animation
}
