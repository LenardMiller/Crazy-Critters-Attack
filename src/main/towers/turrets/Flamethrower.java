package main.towers.turrets;

import main.damagingThings.projectiles.BlueFlame;
import main.damagingThings.projectiles.Flame;
import main.damagingThings.shockwaves.FireShockwave;
import main.enemies.Enemy;
import main.misc.Tile;
import main.sound.FadeSoundLoop;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Flamethrower extends Turret {

    private final FadeSoundLoop FIRE_SOUND_LOOP;
    private final PImage[] CORE_SPRITES;
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
        effectLevel = 6;
        effectDuration = 5;
        damage = 20;
        delay = 0;
        material = "metal";
        price = FLAMETHROWER_PRICE;
        value = price;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        CORE_SPRITES = animatedSprites.get("flamewheelCoreTR");
        BETWEEN_CORE_FRAMES = 4;
        fireParticle = null;
        barrelLength = 24;
        count = 1;
        FIRE_SOUND_LOOP = fadeSoundLoops.get("flamethrower");
        titleLines = new String[]{"Flamethrower"};
        infoDisplay = (o) -> selection.setTextPurple("Fire", o);

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (enemies.size() > 0 && !machine.dead && !paused) checkTarget();
        if (!paused && wheel) rotateWheel();
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
          && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    private void rotateWheel() {
        float maxSpeed = 0.3f;
        float spoolSpeed = 0.005f;
        if (targetEnemy != null && enemies.size() > 0 && alive) {
            rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, maxSpeed);
        } else rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, 0);
        angle += rotationSpeed;
        delay = 5 * abs(rotationSpeed - maxSpeed);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && !wheel) aim(targetEnemy);
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
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
            float time = dist / pjSpeed;
            if (magic) time = dist / (150 * (range / 200f));
            PVector enemyHeading = PVector.fromAngle(enemy.angle);
            if (enemy.state == Enemy.State.Moving) enemyHeading.setMag(enemy.getActualSpeed() * time); //only lead if enemy moving
            else enemyHeading.setMag(0);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        targetAngle = normalizeAngle(findAngle(position, target));
        angle = normalizeAngle(angle);
        angle += getAngleDifference(targetAngle, angle) / (FRAMERATE/6f);

        if (abs(targetAngle - angle) < 0.05) angle = targetAngle; //snap to prevent getting stuck

        if (visualize && debug) { //cool lines
            p.stroke(255);
            p.line(position.x, position.y, target.x, target.y);
            p.stroke(255, 0, 0, 150);
            p.line(target.x, p.height, target.x, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y, 0, target.y);
        }
    }

    protected void displayMain() {
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
            p.image(CORE_SPRITES[coreFrame], tile.position.x + 2 + coreOffset, tile.position.y + 2 + coreOffset);
            p.tint(255, tintColor, tintColor);
            p.image(CORE_SPRITES[coreFrame], tile.position.x + coreOffset, tile.position.y + coreOffset);
            p.tint(255);
            if (!paused) animateCore();
        }
    }

    private void animateCore() {
        coreFrameTimer++;
        if (coreFrameTimer >= BETWEEN_CORE_FRAMES) {
            coreFrameTimer = 0;
            if (coreFrame < CORE_SPRITES.length-1) coreFrame++;
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
        upgradePrices[2] = 6000;

        upgradePrices[3] = 600;
        upgradePrices[4] = 700;
        upgradePrices[5] = 8000;
        //titles
        upgradeTitles[0] = "Better range";
        upgradeTitles[1] = "Betterer Range";
        upgradeTitles[2] = "Flame Wheel";

        upgradeTitles[3] = "More Damage";
        upgradeTitles[4] = "Fire Strength";
        upgradeTitles[5] = "Blue Fire";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "range";
        upgradeDescC[1] = "again";

        upgradeDescA[2] = "Waves";
        upgradeDescB[2] = "of fire";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "base";
        upgradeDescC[3] = "damage";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "& duration";

        upgradeDescA[5] = "Massive";
        upgradeDescB[5] = "damage";
        upgradeDescC[5] = "increase";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[32];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[11];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[31];
    }

    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 20;
                    break;
                case 1:
                    range += 30;
                    break;
                case 2:
                    wheel = true;
                    count = 8;
                    damage *= 10;
                    name = "flamewheel";
                    hasPriority = false;
                    effectLevel += 7;
                    selection.swapSelected(this);
                    titleLines = new String[]{"Flame Wheel"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Fire waves", o);
                        selection.setTextPurple("Spools up", o);
                    };
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += damage;
                    break;
                case 4:
                    effectDuration += 5;
                    effectLevel += 10;
                    break;
                case 5:
                    name = "magicFlamethrower";
                    magic = true;
                    material = "darkMetal";
                    range += 30;
                    pjSpeed = 150;
                    damage = 200;
                    effectDuration = 25;
                    effectLevel = 250;
                    titleLines = new String[]{"Flame Conjurer"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Blue fire", o);
                        selection.setTextPurple("Piercing", o);
                    };
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {
    }
}