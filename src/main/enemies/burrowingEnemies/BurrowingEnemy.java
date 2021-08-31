package main.enemies.burrowingEnemies;

import main.Main;
import main.buffs.Buff;
import main.enemies.Enemy;
import main.gui.guiObjects.PopupText;
import main.particles.Debris;
import main.particles.Pile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getAngleDifference;
import static main.misc.Utilities.normalizeAngle;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public abstract class BurrowingEnemy extends Enemy {

    protected BurrowingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    @Override
    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            angle = normalizeAngle(angle);
            targetAngle = normalizeAngle(targetAngle);
            angle += getAngleDifference(targetAngle, angle) / 10;

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
        float pixelsMoved = getActualSpeed() / FRAMERATE;
        m.setMag(pixelsMoved);
        //don't move if no path
        if (points.size() > 0) position.add(m);
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
            if (gore) goreyDeathEffect(type);
            else cleanDeathEffect();
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
