package main.towers.turrets;

import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.EnergyBlast;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class EnergyBlaster extends Turret{

    private int effectRadius;
    private boolean bigExplosion;

    public EnergyBlaster(PApplet p, Tile tile) {
        super(p,tile);
        offset = 13;
        name = "energyBlaster";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 250;
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        damage = 60;
        pjSpeed = 16;
        range = 300;
        numFireFrames = 14; //14
        betweenFireFrames = 2;
        numLoadFrames = 42; //42
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        effectRadius = 35;
        bigExplosion = false;
        damageSound = soundsH.get("stoneDamage");
        breakSound = soundsH.get("stoneBreak");
        placeSound = soundsH.get("stonePlace");
        fireSound = soundsH.get("smallExplosion");
        loadSprites();
        debrisType = "darkMetal";
        price = ENERGYBLASTER_PRICE;
        value = price;
        priority = 2; //strong
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void fire() { //needed to change projectile fired
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);
        spa.setMag(40);
        spp.add(spa);
        projectiles.add(new EnergyBlast(p,spp.x,spp.y, angleB, this, damage, effectRadius, bigExplosion));
        for (int i = 0; i < 5; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-2);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),"energy"));
        }
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 300;
        upgradePrices[1] = 400;
        upgradePrices[2] = 1200;

        upgradePrices[3] = 250;
        upgradePrices[4] = 400;
        upgradePrices[5] = 1000;
        //titles
        upgradeTitles[0] = "Better Reload";
        upgradeTitles[1] = "Big Blasts";
        upgradeTitles[2] = "NUKE";

        upgradeTitles[3] = "Better Range";
        upgradeTitles[4] = "Fantastic Range";
        upgradeTitles[5] = "Big Succ";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "explosion";
        upgradeDescC[1] = "radius";

        upgradeDescA[2] = "big";
        upgradeDescB[2] = "boomers";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Further";
        upgradeDescB[4] = "increase";
        upgradeDescC[4] = "range";

        upgradeDescA[5] = "idk, black";
        upgradeDescB[5] = "hole or";
        upgradeDescC[5] = "sumthin";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[21];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[23];

        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[6];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[3];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 35;
                    break;
                case 1:
                    effectRadius += 25;
                    bigExplosion = true;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    effectRadius += 75;
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 35;
                    break;
                case 4:
                    range += 35;
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    damage += 500;
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {}
}