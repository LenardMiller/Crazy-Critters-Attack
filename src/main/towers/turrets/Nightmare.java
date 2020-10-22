package main.towers.turrets;

import main.particles.BuffParticle;
import main.projectiles.Needle;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Nightmare extends Turret {

    private int numProjectiles;
    private int effectLevel;

    public Nightmare(PApplet p, Tile tile) {
        super(p,tile);
        name = "nightmare";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 210; //210
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 18;
        range = 200;
        numFireFrames = 14;
        numLoadFrames = 22;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 100;
        numProjectiles = 3;
        effectLevel = 50;
        effectDuration = 220;
        loadSprites();
        debrisType = "darkMetal";
        price = 300;
        value = price;
        priority = 2; //strong
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        //todo: fix spread
        for (int i = 0; i < numProjectiles; i++) {
            float angleB = angle;
            PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
            PVector spa = PVector.fromAngle(angleB-HALF_PI);
            spa.setMag(20);
            spp.add(spa);
            projectiles.add(new Needle(p, spp.x, spp.y, angleB, this, damage, effectLevel, effectDuration));
            for (int j = 0; j < 3; j++) {
                PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
                spa2.setMag(-2);
                PVector spp2 = new PVector(spp.x,spp.y);
                spp2.add(spa2);
                particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),"decay"));
            }
        }

        delayTime = p.frameCount + delay; //waits this time before firing
    }

    private void setUpgrades(){
//        //delay (firerate)
//        upgradeDelay[0] = -45;
//        upgradeDelay[1] = 0;
//        upgradeDelay[2] = 0;
//        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 100;
//        //error (accuracy)
//        upgradeRange[0] = 0;
//        upgradeRange[1] = 0;
//        upgradeRange[2] = -4;
//        upgradeRange[3] = 0;
        //titles
        upgradeTitles[0] = "Firerate";
        upgradeTitles[1] = "More Needles";
        upgradeTitles[2] = "Reduce Spread";
        upgradeTitles[3] = "Effect Power";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "Fire more";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "Increase";
        //desc line two
        upgradeDescB[0] = "firerate";
        upgradeDescB[1] = "projectiles";
        upgradeDescB[2] = "accuracy";
        upgradeDescB[3] = "damage,";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "duration";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[4];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[3];
    }

    public void upgradeSpecial() {
        if (nextLevelA == 1) numProjectiles = 5;
        if (nextLevelB == 1) {
            effectDuration += 60;
            effectLevel += 3;
        }
    }

    public void updateSprite() {}
}