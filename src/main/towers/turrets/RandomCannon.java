package main.towers.turrets;

import main.particles.BuffParticle;
import main.projectiles.MiscProjectile;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class RandomCannon extends Turret {

    public RandomCannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "miscCannon";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 25; //default: 25 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 12;
        numFireFrames = 5;
        betweenFireFrames = 4;
        numLoadFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 10;
        range = 150;
        loadSprites();
        debrisType = "wood";
        price = RANDOMCANNON_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();
    }

    public void fire() {
        float angleB = angle;
        delayTime = p.frameCount + delay; //waits this time before firing
        int spriteType = (int)(p.random(0,5.99f));
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);
        spa.setMag(18);
        spp.add(spa);
        float iM = p.random(1,5);
        for (int i = 0; i < iM; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),"smoke"));
        }
        projectiles.add(new MiscProjectile(p,spp.x,spp.y, angleB, this, spriteType, damage));
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 125;
        upgradePrices[1] = 150;
        upgradePrices[2] = 75;
        upgradePrices[3] = 125;
        //titles
        upgradeTitles[0] = "Damage Up";
        upgradeTitles[1] = "Faster Firing";
        upgradeTitles[2] = "Longer range";
        upgradeTitles[3] = "Longest range";
        //description
        upgradeDescA[0] = "+15";
        upgradeDescB[0] = "damage";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "firerate";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Increase";
        upgradeDescB[2] = "range";
        upgradeDescC[2] = "";

        upgradeDescA[3] = "Further";
        upgradeDescB[3] = "increase";
        upgradeDescC[3] = "range";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[10];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[6];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            if (nextLevelA == 0) damage += 15;
            if (nextLevelA == 1) delay -= 10;
        } if (id == 1) {
            if (nextLevelB == 2) range += 20;
            if (nextLevelB == 3) range += 30;
        }
    }

    public void updateSprite() {}
}
