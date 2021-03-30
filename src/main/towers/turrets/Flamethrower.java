package main.towers.turrets;

import main.misc.FadeSoundLoop;
import main.misc.Tile;
import main.projectiles.Flame;
import main.shockwaves.FireShockwave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.incrementByTo;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Flamethrower extends Turret {

    private final FadeSoundLoop FIRE_SOUND_LOOP;
    private final PImage[] CORE_SPRITES;
    private final int BETWEEN_CORE_FRAMES;

    private float rotationSpeed;
    private int count;
    private int coreFrame;
    private int coreFrameTimer;

    public Flamethrower(PApplet p, Tile tile) {
        super(p, tile);
        name = "flamethrower";
        size = new PVector(50, 50);
        offset = 7;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 0;
        pjSpeed = 300;
        range = 200;
        effectLevel = 6;
        effectDuration = 5;
        betweenIdleFrames = 0;
        state = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 2;
        loadSprites();
        debrisType = "metal";
        price = FLAMETHROWER_PRICE;
        value = price;
        priority = 0; //close
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        CORE_SPRITES = animatedSprites.get("flamewheelCoreTR");
        BETWEEN_CORE_FRAMES = 4;
        fireParticle = null;
        barrelLength = 24;
        count = 1;
        FIRE_SOUND_LOOP = fadeSoundLoops.get("flamethrower");
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (enemies.size() > 0 && alive && !paused) checkTarget();
        if (!paused && count > 1) rotateWheel();
        if (p.mousePressed && p.mouseX < tile.position.x && p.mouseX > tile.position.x - size.x && p.mouseY < tile.position.y
          && p.mouseY > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    private void rotateWheel() {
        float maxSpeed = 0.5f;
        float spoolSpeed = 0.001f;
        if (targetEnemy != null && enemies.size() > 0 && alive) {
            rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, maxSpeed);
        } else rotationSpeed = incrementByTo(rotationSpeed, spoolSpeed, 0);
        angle += rotationSpeed;
        delay = 5 * abs(rotationSpeed - maxSpeed);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && count == 1) aim(targetEnemy);
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    protected void displayPassB2() {
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
        if (count > 1) {
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

    protected void fire(float barrelLength, String particleType) {
        if (count == 1) {
            FIRE_SOUND_LOOP.setTargetVolume(1);
            PVector projectileSpawn = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            PVector angleVector = PVector.fromAngle(angle - HALF_PI);
            angleVector.setMag(barrelLength);
            projectileSpawn.add(angleVector);
            projectiles.add(new Flame(p, projectileSpawn.x, projectileSpawn.y, angle, this, damage, effectLevel, effectDuration,
              (int) (range - barrelLength - 100), false));
        }
        else {
            playSoundRandomSpeed(p, sounds.get("fireImpact"), 1);
            shockwaves.add(new FireShockwave(p, tile.position.x - size.x / 2, tile.position.y - size.y / 2,
              (int) barrelLength, range, 0, 720, damage, this, effectLevel, effectDuration));
        }
    }

    private void setUpgrades() {
        //prices
        upgradePrices[0] = 400;
        upgradePrices[1] = 500;
        upgradePrices[2] = 1500;

        upgradePrices[3] = 500;
        upgradePrices[4] = 600;
        upgradePrices[5] = 2500;
        //titles
        upgradeTitles[0] = "Better range";
        upgradeTitles[1] = "Betterer Range";
        upgradeTitles[2] = "Flame Wheel";

        upgradeTitles[3] = "More Damage";
        upgradeTitles[4] = "Fire Strength";
        upgradeTitles[5] = "Sapphire Fire";
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
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[23];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[11];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[14];
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
                    count = 8;
                    damage *= 10;
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
                    damage = 35;
                    effectDuration += 5;
                    effectLevel = 100;
                    break;
            }
        }
    }

    public void updateSprite() {
    }
}