package main.enemies.burrowingEnemies;

import main.Main;
import main.buffs.Buff;
import main.enemies.Enemy;
import main.gui.guiObjects.PopupText;
import main.misc.Corpse;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;

public abstract class BurrowingEnemy extends Enemy {

    protected BurrowingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            angle = clampAngle(angle);
            targetAngle = clampAngle(targetAngle);
            angle += angleDifference(targetAngle, angle) / 10;

            if (state == 0) move();
            else if (state == 1) attack();

            //prevent wandering
            if (points.size() == 0 && state != 1) pathRequestWaitTimer++;
            if (pathRequestWaitTimer > FRAMERATE) {
                requestPath(i);
                pathRequestWaitTimer = 0;
            }
        }
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayMain();
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    @Override
    protected void move() {
        if (p.random(10) < pfSize * pfSize) {
            PVector particlePosition = randomPosition();
            topParticles.add(new Debris(p, particlePosition.x, particlePosition.y, p.random(0, 360), levels[currentLevel].groundType));
        }
        if (p.random(25) < pfSize * pfSize) {
            PVector particalPosition = randomPosition();
            bottomParticles.add(new Pile(p, particalPosition.x, particalPosition.y, 0, levels[currentLevel].groundType));
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

    @Override
    protected void die(int i) {
        Main.money += moneyDrop;
        popupTexts.add(new PopupText(p, new PVector(position.x, position.y), moneyDrop));

        String type = lastDamageType;
        for (Buff buff : buffs) {
            if (buff.enId == i) type = buff.name;
        }
        if (overkill) playSoundRandomSpeed(p, overkillSound, 1);
        else playSoundRandomSpeed(p, dieSound, 1);
        if (state != 0) {
            if (overkill) {
                for (int j = 0; j < animatedSprites.get(name + "PartsEN").length; j++) {
                    float maxRotationSpeed = up60ToFramerate(200f / partSize.x);
                    corpses.add(new Corpse(p, position, partSize, angle, adjustPartVelocityToFramerate(partsDirection),
                      currentTintColor ,p.random(radians(-maxRotationSpeed), radians(maxRotationSpeed)),
                      0, corpseLifespan, type, name + "Parts", hitParticle, j, false));
                }
                for (int k = 0; k < sq(pfSize); k++) {
                    bottomParticles.add(new Pile(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1,
                      (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))),
                      0, hitParticle));
                }
            } else
                corpses.add(new Corpse(p, position, corpseSize,
                  angle + p.random(radians(-5), radians(5)), new PVector(0, 0),
                  currentTintColor, 0, betweenCorpseFrames, corpseLifespan, type, name + "Die",
                  "none", 0, true));
        }

        for (int j = buffs.size() - 1; j >= 0; j--) { //deals with buffs
            Buff buff = buffs.get(j);
            //if attached, remove
            if (buff.enId == i) {
                buffs.get(j).dieEffect();
                buffs.remove(j);
            } //shift ids to compensate for enemy removal
            else if (buff.enId > i) buff.enId -= 1;
        }

        enemies.remove(i);
    }

    //todo: reveal animation
}
