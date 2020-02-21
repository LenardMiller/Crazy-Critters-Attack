package main.towers.turrets;

import main.projectiles.MiscProjectile;
import main.towers.Tile;
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
        numFireFrames = 12;
        numLoadFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 3;
        error = 8; //default 8
        loadSprites();
        debrisType = "metal";
        price = 100;
        value = price;
        priority = 0; //close
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire() { //needed to change projectile fired
        float angleB = angle;
        angleB += PApplet.radians(p.random(-error,error));
        delayTime = p.frameCount + delay; //waits this time before firing
        int spriteType = (int)(p.random(0,5.99f));
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);
        spa.setMag(20);
        spp.add(spa);
        projectiles.add(new MiscProjectile(p,spp.x,spp.y, angleB, this, spriteType, damage));
    }

    private void setUpgrades() {
        //damage
        upgradeDamage[0] = 3;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 0;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = -10;
        upgradeDelay[2] = 0;
        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 100;
        //heath
        upgradeHealth[0] = 0;
        upgradeHealth[1] = 0;
        upgradeHealth[2] = 0;
        upgradeHealth[3] = 0;
        //error (accuracy)
        upgradeError[0] = 0;
        upgradeError[1] = 0;
        upgradeError[2] = -2;
        upgradeError[3] = -2;
        //names
        upgradeNames[0] = name;
        upgradeNames[1] = name;
        upgradeNames[2] = name;
        upgradeNames[3] = name;
        //debris
        upgradeDebris[0] = "metal";
        upgradeDebris[1] = "metal";
        upgradeDebris[2] = "metal";
        upgradeDebris[3] = "metal";
        //titles
        upgradeTitles[0] = "Damage Up";
        upgradeTitles[1] = "Faster Firing";
        upgradeTitles[2] = "Reduce Spread";
        upgradeTitles[3] = "Limited Spread";
        //desc line one
        upgradeDescA[0] = "+3";
        upgradeDescA[1] = "Increase";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "Further";
        //desc line two
        upgradeDescB[0] = "damage";
        upgradeDescB[1] = "firerate";
        upgradeDescB[2] = "accuracy";
        upgradeDescB[3] = "increase";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "accuracy";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[10];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[6];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void updateSprite() {}
}
