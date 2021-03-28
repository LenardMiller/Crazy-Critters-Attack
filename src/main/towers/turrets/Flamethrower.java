package main.towers.turrets;

import main.misc.FadeSoundLoop;
import main.misc.Tile;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Flamethrower extends Turret {

    private final FadeSoundLoop FIRE_SOUND_LOOP = fadeSoundLoops.get("flamethrower");

    private int count;

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
        effectLevel = 5;
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
        fireParticle = null;
        barrelLength = 24;
        count = 1;
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null) aim(targetEnemy);
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    protected void fire(float barrelLength, String particleType) {
        FIRE_SOUND_LOOP.setTargetVolume(1);
        for (int i = 0; i < count; i++) {
            float fireAngle = angle + i*(TWO_PI/count);
            PVector projectileSpawn = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            PVector angleVector = PVector.fromAngle(fireAngle - HALF_PI);
            angleVector.setMag(barrelLength);
            projectileSpawn.add(angleVector);
            projectiles.add(new Flame(p, projectileSpawn.x, projectileSpawn.y, fireAngle, this, damage, effectLevel, effectDuration,
              (int) (range - barrelLength - 100), false));
        }
    }

    private void setUpgrades() {
        //prices
        upgradePrices[0] = 400;
        upgradePrices[1] = 500;
        upgradePrices[2] = 1750;

        upgradePrices[3] = 500;
        upgradePrices[4] = 600;
        upgradePrices[5] = 2250;
        //titles
        upgradeTitles[0] = "Better range";
        upgradeTitles[1] = "Betterer Range";
        upgradeTitles[2] = "Multifire";

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

        upgradeDescA[2] = "Multifire";
        upgradeDescB[2] = "";
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
                    count = 6;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 1;
                    break;
                case 4:
                    effectDuration += 2;
                    effectLevel += 5;
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