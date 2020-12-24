package main.towers.turrets;

import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Shockwave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class SeismicTower extends Turret {

    public SeismicTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "seismic";
        size = new PVector(50,50);
        offset = 4;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 200; //default: 200 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 7;
        numFireFrames = 14;
        betweenFireFrames = 1;
        numLoadFrames = 20;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 15;
        range = 250;
        damageSound = soundsH.get("woodDamage");
        breakSound = soundsH.get("woodBreak");
        placeSound = soundsH.get("woodPlace");
        fireSound = soundsH.get("smallExplosion");
        loadSprites();
        debrisType = "stone";
        price = CANNON_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && spriteType != 1) aim(targetEnemy);
        if (spriteType == 0 && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            spriteType = 1;
            frame = 0;
        }
        if (spriteType == 1 && frame == fireFrames.length - 1) fire();
    }

    public void fire() {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        delayTime = p.frameCount + delay; //waits this time before firing
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);
        float particleCount = p.random(1,5);
        spa.setMag(29); //barrel length
        spp.add(spa);
        String part = "smoke";
        shockwaves.add(new Shockwave(p, spp.x, spp.y, (int) range, angleB, 60, damage));
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),part));
        } particleCount = p.random(1,5);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,p.random(0, 360),part));
        }
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 125;
        upgradePrices[1] = 150;
        upgradePrices[2] = 500;
        upgradePrices[3] = 75;
        upgradePrices[4] = 125;
        upgradePrices[5] = 600;
        //titles
        upgradeTitles[0] = "NYI";
        upgradeTitles[1] = "NYI";
        upgradeTitles[2] = "NYI";
        upgradeTitles[3] = "NYI";
        upgradeTitles[4] = "NYI";
        upgradeTitles[5] = "NYI";
        //description
        upgradeDescA[0] = "";
        upgradeDescB[0] = "";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "";
        upgradeDescB[1] = "";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "";
        upgradeDescB[2] = "";
        upgradeDescC[2] = "";

        upgradeDescA[3] = "";
        upgradeDescB[3] = "";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "";
        upgradeDescB[4] = "";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "";
        upgradeDescB[5] = "";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[0];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    break;
                case 1:
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    break;
                case 4:
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {}
}