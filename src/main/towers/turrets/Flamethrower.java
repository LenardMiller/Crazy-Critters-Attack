package main.towers.turrets;

import main.damagingThings.projectiles.BlueFlame;
import main.damagingThings.projectiles.Flame;
import main.damagingThings.shockwaves.FireShockwave;
import main.misc.Tile;
import main.sound.FadeSoundLoop;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.incrementByTo;
import static main.misc.Utilities.playSoundRandomSpeed;

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
        damage = 10;
        debrisType = "metal";
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
        if (enemies.size() > 0 && alive && !paused) checkTarget();
        if (!paused && wheel) rotateWheel();
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
          && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    private void rotateWheel() {
        float maxSpeed = 0.5f;
        float spoolSpeed = 0.002f;
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
              effectLevel, effectDuration, (int) (getRange() - barrelLength - 100), false));
        } else {
            projectiles.add(new Flame(p, position.x, position.y, angle, this, getDamage(),
              effectLevel, effectDuration, (int) (getRange() - barrelLength - 100), false));
        }
    }

    @Override
    protected void setUpgrades() {
        //prices
        upgradePrices[0] = 400;
        upgradePrices[1] = 500;
        upgradePrices[2] = 3500;

        upgradePrices[3] = 500;
        upgradePrices[4] = 600;
        upgradePrices[5] = 5000;
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

    protected void upgradeSpecial(int id) {
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
                    damage *= 50;
                    name = "flamewheel";
                    hasPriority = false;
                    effectLevel += 7;
                    selection.swapSelected(this);
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += damage / 2;
                    break;
                case 4:
                    effectDuration += 2;
                    effectLevel += 7;
                    break;
                case 5:
                    name = "magicFlamethrower";
                    magic = true;
                    debrisType = "darkMetal";
                    range += 30;
                    pjSpeed = 150;
                    damage = 100;
                    effectDuration += 5;
                    effectLevel = 100;
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {
    }
}