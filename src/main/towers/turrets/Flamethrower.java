package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Flamethrower extends Turret {

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
        damage = 10;
        loadSprites();
        debrisType = "metal";
        price = FLAMETHROWER_PRICE;
        value = price;
        priority = 0; //close
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("fireImpact");
        fireParticle = null;
        barrelLength = 24;
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
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

    protected void spawnProjectile(PVector position, float angle) {
        projectiles.add(new Flame(p, position.x, position.y, angle, this, damage, effectLevel, effectDuration,
                (int) (range - barrelLength - 100), false));
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
        upgradeTitles[2] = "Fireball";

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

        upgradeDescA[2] = "explosive";
        upgradeDescB[2] = "fireball";
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
                    range += 25;
                    break;
                case 1:
                    range += 50;
                    break;
                case 2:
                    range += 125;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 10;
                    break;
                case 4:
                    effectDuration += 2;
                    effectLevel += 5;
                    break;
                case 5:
                    damage += 50;
                    effectDuration += 5;
                    effectLevel += 20;
                    break;
            }
        }
    }

    public void updateSprite() {
    }
}