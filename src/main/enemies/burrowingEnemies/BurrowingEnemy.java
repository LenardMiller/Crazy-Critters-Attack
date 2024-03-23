package main.enemies.burrowingEnemies;

import main.Main;
import main.enemies.Enemy;
import main.gui.guiObjects.PopupText;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public abstract class BurrowingEnemy extends Enemy {

    protected BurrowingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    protected void move() {
        if (moveSoundLoop != null) moveSoundLoop.increment();
        if (p.random(10) < pfSize * pfSize) {
            PVector particlePosition = randomPosition();
            topParticles.add(new Debris(p, particlePosition.x, particlePosition.y, p.random(0, 360), levels[currentLevel].groundType));
        }
        if (p.random(25) < pfSize * pfSize) {
            PVector particlePosition = randomPosition();
            bottomParticles.add(new Pile(p, particlePosition.x, particlePosition.y, 0, levels[currentLevel].groundType));
        }
        PVector m = PVector.fromAngle(rotation);
        float pixelsMoved = getActualSpeed() / FRAMERATE;
        m.setMag(pixelsMoved);
        //don't move if no path
        if (trail.size() > 0) position.add(m);
    }

    @Override
    protected void animate() {
        super.animate();
        if (immobilized) {
            sprite = attackFrames[0];
        } else if (state == State.Special) {
            state = State.Moving;
        }
    }

    private PVector randomPosition() {
        float x = p.random(position.x - (size.x / 2), position.x + (size.x / 2));
        float y = p.random(position.y - (size.y / 2), position.y + (size.y / 2));
        return new PVector(x, y);
    }

    @Override
    protected void die() {
        Main.money += moneyDrop;
        popupTexts.add(new PopupText(p, new PVector(position.x, position.y), moneyDrop));

        DamageType type = lastDamageType;
        if (!buffs.isEmpty()) {
            type = DamageType.valueOf(buffs.get((int) p.random(buffs.size() - 1)).name);
        }
        if (overkill) playSoundRandomSpeed(p, overkillSound, 1);
        else playSoundRandomSpeed(p, dieSound, 1);
        if (state != State.Moving) {
            if (settings.isHasGore()) goreyDeathEffect(type);
            else cleanDeathEffect();
        }

        enemies.remove(this);
    }
}
