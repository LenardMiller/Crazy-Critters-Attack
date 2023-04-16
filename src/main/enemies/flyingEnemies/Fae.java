package main.enemies.flyingEnemies;

import main.Main;
import main.buffs.Buff;
import main.enemies.Enemy;
import main.gui.guiObjects.PopupText;
import main.particles.Floaty;
import main.particles.MiscParticle;
import main.particles.Ouch;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.findAngleBetween;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Fae extends Frost {

    private int hitTimer;
    private final String color;

    public Fae(PApplet p, float x, float y) {
        super(p, x, y);
        String oppositeColor;
        if ((int) p.random(2) == 1) {
            color = "green";
            oppositeColor = "blue";
        } else {
            color = "blue";
            oppositeColor = "green";
        }
        pfSize = 2;
        size = new PVector(25, 25);
        radius = 15;
        speed = 55;
        moneyDrop = 200;
        damage = 6;
        maxHp = 20000;
        hp = maxHp;
        hitParticle = HitParticle.valueOf((oppositeColor + "Puff"));
        name = "fae";
        betweenAttackFrames = 8;
        dieSound = sounds.get("faeDie");
        overkillSound = sounds.get("faeDie");
        attackFrames = animatedSprites.get("wolf" + "AttackEN"); //these exist solely because of glue
        moveFrames = animatedSprites.get("wolf" + "MoveEN");
        attackDmgFrames = new int[]{attackFrames.length - 1};
        attackSound = sounds.get("faeMagic");
        moveSoundLoop = moveSoundLoops.get("fae");
    }

    /**
     * Adds money with a popup.
     * Plays death sound.
     * Clear buffs.
     * Remove from array.
     * @param i id for buff stuff
     */
    @Override
    protected void die(int i) {
        Main.money += moneyDrop;
        popupTexts.add(new PopupText(p, new PVector(position.x, position.y), moneyDrop));
        if (overkill) {
            playSoundRandomSpeed(p, overkillSound, 1);
            for (int j = 0; j < 25; j++) {
                topParticles.add(new Floaty(p, position.x, position.y, p.random(100, 250), color + "Magic"));
            }
        }
        else playSoundRandomSpeed(p, dieSound, 1);
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

    /**
     * Angles towards target.
     * Damages target turret or machine.
     * Messes with state a bit.
     * Prevents attacking multiple times at once.
     */
    @Override
    protected void attack() {
        boolean dmg = false;
        for (int frame : tempAttackDmgFrames) {
            if (attackFrame == frame) {
                if (betweenAttackFrames > 1) attackCount++;
                dmg = true;
                break;
            }
        }
        //all attackCount stuff prevents attacking multiple times
        if (!dmg) attackCount = 0;
        if (attackCount > 1) dmg = false;
        if (targetTower != null && targetTower.alive) {
            if (pfSize > 2) { //angle towards tower correctly
                PVector t = new PVector(targetTower.tile.position.x - 25, targetTower.tile.position.y - 25);
                targetAngle = findAngleBetween(t, position);
            }
            moveFrame = 0;
            if (dmg) {
                targetTower.damage(damage);
                playSoundRandomSpeed(p, attackSound, 1);
                spawnAttackParticles();
            }
        } else if (!targetMachine) state = State.Moving;
        if (targetMachine) {
            moveFrame = 0;
            if (dmg) {
                machine.damage(damage);
                playSoundRandomSpeed(p, attackSound, 1);
                spawnAttackParticles();
            }
        }
        if (!attackCue && attackFrame == 0) state = State.Moving;
    }

    private void spawnAttackParticles() {
        for (int i = 0; i < 20; i++) {
            PVector partPosition = new PVector(position.x, position.y);
            partPosition.add(PVector.fromAngle(p.random(TWO_PI)).setMag(p.random(radius)));
            topParticles.add(new MiscParticle(p, partPosition.x, partPosition.y, p.random(360), "blueGreenFire"));
        }
    }

    /** handle animation states, animation disabled */
    @Override
    protected void animate() {
        if (!immobilized) {
            if (state == State.Attacking) {
                if (attackFrame >= attackFrames.length) attackFrame = 0;
                idleTime++;
                if (attackFrame < attackFrames.length - 1) {
                    if (idleTime >= betweenAttackFrames) {
                        attackFrame += 1;
                        idleTime = 0;
                    }
                } else attackFrame = 0;
            }
        }
        hitTimer++;
    }

    /** Calls to animate sprite. */
    @Override
    public void displayShadow() {
        if (!paused) animate();
    }

    /** Displays a bunch of particles. */
    @Override
    public void display() {
        if (debug) for (int i = trail.size() - 1; i > 0; i--) {
            trail.get(i).display();
        }
        if (!paused) {
            if (hitTimer < 10) {
                for (int i = 0; i < 3; i++) {
                    PVector partPosition = new PVector(position.x, position.y);
                    partPosition.add(PVector.fromAngle(p.random(TWO_PI)).setMag(p.random(radius * 2)));
                    topParticles.add(new MiscParticle(p, partPosition.x, partPosition.y, p.random(360), color + "Magic"));
                    topParticles.add(new Floaty(p, position.x, position.y, p.random(40, 70), color + "Magic"));
                }
            } else {
                PVector partPosition = new PVector(position.x, position.y);
                partPosition.add(PVector.fromAngle(p.random(TWO_PI)).setMag(p.random(radius)));
                topParticles.add(new MiscParticle(p, partPosition.x, partPosition.y, p.random(360), color + "Magic"));
                topParticles.add(new Floaty(p, position.x, position.y, p.random(25, 35), color + "Magic"));
            }
        }
        if (debug) {
            PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
            p.stroke(0, 0, 255);
            p.line(pfPosition.x - 10, pfPosition.y, pfPosition.x + 10, pfPosition.y);
            p.stroke(255, 0, 0);
            p.line(pfPosition.x, pfPosition.y - 10, pfPosition.x, pfPosition.y + 10);
            p.noFill();
            p.stroke(255, 0, 255);
            p.rect(pfPosition.x - 12.5f, pfPosition.y - 12.5f, pfSize * 25, pfSize * 25);
        }
    }

    /**
     * Display hp bar.
     * @param particles whether to display hurt particles
     */
    @Override
    protected void damageEffect(boolean particles) {
        if (hp == maxHp) return;
        showBar = true;
        if (particles) {
            int num = (int) p.random(pfSize, pfSize * pfSize);
            for (int j = num; j >= 0; j--) { //sprays ouch
                topParticles.add(new Ouch(p, position.x + p.random((size.x / 2) * -1, size.x / 2),
                  position.y + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), hitParticle.name()));
            }
            hitTimer = 0;
        }
    }
}
