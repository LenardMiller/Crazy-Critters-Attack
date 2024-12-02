package main.towers.turrets;

import main.projectiles.BlueFlame;
import main.projectiles.Flame;
import main.projectiles.shockwaves.FireShockwave;
import main.enemies.Enemy;
import main.misc.Tile;
import main.sound.FadeSoundLoop;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Flamethrower extends Turret {

    private static final Color SPECIAL_COLOR = new Color(0xFA9B4D);

    public static String pid = "M2-200-15-0";
    public static String description =
            "Spews a column of fire at the nearest critter. " +
                    "Has very short range, but very high damage per second.";
    public static char shortcut = 'D';
    public static String title1 = "Flamethrower";
    public static String title2 = null;
    public static int price = 1000;

    private final FadeSoundLoop FIRE_SOUND_LOOP;
    private final PImage[] coreSprites;
    private final int BETWEEN_CORE_FRAMES;

    private float rotationSpeed;
    private int count;
    private int coreFrame;
    private int coreFrameTimer;
    private boolean magic;
    private boolean wheel;

    public Flamethrower(PApplet p, Tile tile) {
        super(p, tile);
        name = "flamethrower";
        offset = 7;
        pjSpeed = 300;
        range = 200;
        effectLevel = 10;
        effectDuration = 3.3f;
        damage = 15;
        delay = 0;
        material = Material.metal;
        basePrice = price;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        coreSprites = animatedSprites.get("flamethrowerWheelCoreTR");
        BETWEEN_CORE_FRAMES = 4;
        fireParticle = null;
        barrelLength = 24;
        count = 1;
        priority = Priority.Unbuffed;
        effect = "burning";
        FIRE_SOUND_LOOP = fadeSoundLoops.get("flamethrower");
        titleLines = new String[]{"Flamethrower"};
        extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Burning:", null));
        extraInfo.add((arg) -> selection.displayInfoLine(
                arg, SPECIAL_COLOR, "DPS", ((int) (effectLevel / 0.2f)) + ""));
        extraInfo.add((arg) -> selection.displayInfoLine(
                arg, SPECIAL_COLOR, "Duration", nf(effectDuration, 1, 1) + "s"));
    }

    @Override
    public void update() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (!enemies.isEmpty() && !machine.dead && !isPaused) checkTarget();
        if (!isPaused && wheel) rotateWheel();
        if (p.mousePressed && boardMousePosition.x < tile.position.x && boardMousePosition.x > tile.position.x - size.x && boardMousePosition.y < tile.position.y
          && boardMousePosition.y > tile.position.y - size.y && alive && !isPaused) {
            selection.swapSelected(tile.id);
        }
    }

    private void rotateWheel() {
        float maxSpeed = 0.3f;
        float spoolSpeed = 0.001f;
        if (targetEnemy != null && !enemies.isEmpty() && alive) {
            rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, maxSpeed);
        } else rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, 0);
        angle += rotationSpeed;
        delay = 10 * abs(rotationSpeed - maxSpeed);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && !wheel) aim(targetEnemy);
        if (state == State.Idle && targetEnemy != null) { //if done animating
            state = State.Fire;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    /**
     * Sets the target angle to match the target.
     * Leads shots if enemy moving.
     * @param enemy enemy to aim at
     */
    @Override
    protected void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / (pjSpeed * (boostedRange() > 0 ? 1.2f : 1f));
            if (magic) time = dist / (150 * (range / 200f));
            PVector enemyHeading = PVector.fromAngle(enemy.rotation);
            if (enemy.state == Enemy.State.Moving) enemyHeading.setMag(enemy.getActualSpeed() * time); //only lead if enemy moving
            else enemyHeading.setMag(0);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        targetAngle = normalizeAngle(findAngle(position, target));
        angle = normalizeAngle(angle);
        angle += getAngleDifference(targetAngle, angle) / (FRAMERATE/6f);

        if (abs(targetAngle - angle) < 0.05) angle = targetAngle; //snap to prevent getting stuck

        if (visualize && isDebug) { //cool lines
            p.stroke(255);
            p.line(position.x, position.y, target.x, target.y);
            p.stroke(255, 0, 0, 150);
            p.line(target.x, p.height, target.x, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y, 0, target.y);
        }
    }

    public void displayTop() {
        //shadow
        for (int i = 0; i < count; i++) {
            float rotateAngle = angle + i*(TWO_PI/count);
            p.pushMatrix();
            p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
            p.rotate(rotateAngle);
            p.tint(0, 60);
            if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            p.popMatrix();
        }
        //main
        for (int i = 0; i < count; i++) {
            float rotateAngle = angle + i*(TWO_PI/count);
            p.pushMatrix();
            p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            p.rotate(rotateAngle);
            p.tint(255, tintColor, tintColor);
            if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            p.popMatrix();
            p.tint(255);
        }
        if (wheel) {
            int coreOffset = -57;
            p.tint(0, 60);
            p.image(coreSprites[coreFrame], tile.position.x + 2 + coreOffset, tile.position.y + 2 + coreOffset);
            p.tint(255, tintColor, tintColor);
            p.image(coreSprites[coreFrame], tile.position.x + coreOffset, tile.position.y + coreOffset);
            p.tint(255);
            if (!isPaused) animateCore();
        }
    }

    private void animateCore() {
        coreFrameTimer++;
        if (coreFrameTimer >= BETWEEN_CORE_FRAMES) {
            coreFrameTimer = 0;
            if (coreFrame < coreSprites.length-1) coreFrame++;
            else coreFrame = 0;
        }
    }

    @Override
    protected void fire(float barrelLength, String particleType) {
        if (!wheel) {
            FIRE_SOUND_LOOP.setTargetVolume(1);
            PVector projectileSpawn = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            PVector angleVector = PVector.fromAngle(angle - HALF_PI);
            angleVector.setMag(barrelLength);
            projectileSpawn.add(angleVector);
            spawnProjectiles(projectileSpawn, angle);
        }
        else {
            playSoundRandomSpeed(p, sounds.get("fireImpact"), 1);
            shockwaves.add(new FireShockwave(p, tile.position.x - size.x / 2, tile.position.y - size.y / 2,
              (int) barrelLength, getRange(), getDamage(), this, effectLevel, effectDuration));
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (magic) {
            projectiles.add(new BlueFlame(p, position.x, position.y, angle, this, getDamage(),
              effectLevel, effectDuration, (int) (getRange() - barrelLength), false));
        } else {
            projectiles.add(new Flame(p, position.x, position.y, angle, this, getDamage(),
              effectLevel, effectDuration, (int) (getRange() - barrelLength - 100), false));
        }
    }

    @Override
    protected void setUpgrades() {
        //prices
        upgradePrices[0] = 400;
        upgradePrices[1] = 600;
        upgradePrices[2] = 4000;

        upgradePrices[3] = 600;
        upgradePrices[4] = 700;
        upgradePrices[5] = 6000;
        //titles
        upgradeTitles[0] = "Strong Jet";
        upgradeTitles[1] = "Fierce Jet";
        upgradeTitles[2] = "Flame Wheel";

        upgradeTitles[3] = "Hotter Flames";
        upgradeTitles[4] = "Sticky Fire";
        upgradeTitles[5] = "Blue Fire";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "range";

        upgradeDescA[2] = "Waves";
        upgradeDescB[2] = "of fire";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "damage";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Strengthen";
        upgradeDescB[4] = "burning";
        upgradeDescC[4] = "effect";

        upgradeDescA[5] = "Massive";
        upgradeDescB[5] = "damage";
        upgradeDescC[5] = "increase";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[57];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[58];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[32];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[66];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[11];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[31];
    }

    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> range += 20;
                case 1 -> range += 30;
                case 2 -> {
                    wheel = true;
                    count = 8;
                    damage *= 10;
                    name = "flamethrowerWheel";
                    hasPriority = false;
                    selection.swapSelected(this);
                    titleLines = new String[]{"Flame Wheel"};
                    extraInfo.remove(0);
                    extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                            SPECIAL_COLOR, "Fire Waves:", null));
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> damage += damage;
                case 4 -> {
                    effectDuration += 3.3f;
                    effectLevel += 15;
                } case 5 -> {
                    name = "flamethrowerMagic";
                    magic = true;
                    material = Material.darkMetal;
                    effect = "blueBurning";
                    range += 30;
                    pjSpeed = 150;
                    damage = 200;
                    effectDuration = 10;
                    effectLevel = 250;
                    titleLines = new String[]{"Flame Conjurer"};
                    extraInfo.remove(0);
                    Color specialColor = new Color(0x7DB5FF);
                    extraInfo.clear();
                    extraInfo.add((arg) -> selection.displayInfoLine(arg, specialColor, "Piercing", null));
                    extraInfo.add((arg) -> selection.displayInfoLine(arg, specialColor, "Blue Fire:", null));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, specialColor, "DPS", ((int) (effectLevel / 0.2f)) + ""));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, specialColor, "Duration", nf(effectDuration, 1, 1) + "s"));
                    loadSprites();
                }
            }
        }
    }
}