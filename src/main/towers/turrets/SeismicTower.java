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

    private float shockwaveWidth;

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
        damage = 30;
        range = 225;
        shockwaveWidth = 60;
        damageSound = soundsH.get("stoneDamage");
        breakSound = soundsH.get("stoneBreak");
        placeSound = soundsH.get("stonePlace");
        fireSound = soundsH.get("seismicSlam");
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
        shockwaves.add(new Shockwave(p, spp.x, spp.y, (int) range, angleB, shockwaveWidth, damage, this));
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
        upgradePrices[0] = 200;
        upgradePrices[1] = 200;
        upgradePrices[2] = 800;
        upgradePrices[3] = 250;
        upgradePrices[4] = 300;
        upgradePrices[5] = 700;
        //titles
        upgradeTitles[0] = "Longer range";
        upgradeTitles[1] = "Larger AOE";
        upgradeTitles[2] = "360 Wave";
        upgradeTitles[3] = "Faster firing";
        upgradeTitles[4] = "Damage boost";
        upgradeTitles[5] = "Seismic sense";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "area of";
        upgradeDescC[1] = "effect";

        upgradeDescA[2] = "Shockwave";
        upgradeDescB[2] = "encircles";
        upgradeDescC[2] = "tower";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "+30";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Detect";
        upgradeDescB[5] = "stealthy";
        upgradeDescC[5] = "enemies";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[0];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 50;
                    break;
                case 1:
                    shockwaveWidth += 30;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    shockwaveWidth = 360;
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    delay -= 50;
                    break;
                case 4:
                    damage += 30;
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
