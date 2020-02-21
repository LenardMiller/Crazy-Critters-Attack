package main.towers.turrets;

import main.projectiles.Bolt;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Crossbow extends Turret {

    private int pierce;

    public Crossbow(PApplet p, Tile tile) {
        super(p,tile);
        offset = 2;
        name = "crossbow";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 210; //default: 210 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 24;
        error = 2; //set to 360 for a fun time. default: 2 degrees
        numFireFrames = 13;
        numLoadFrames = 81;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 15;
        pierce = 2;
        loadSprites();
        debrisType = "wood";
        price = 100;
        value = price;
        priority = 1; //last
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        float angleB = angle;
        angleB += radians(p.random(-error,error));
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new Bolt(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angleB, this, damage, pierce));
    }

    private void setUpgrades(){
        //damage
        upgradeDamage[0] = 10;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 0;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
        upgradeDelay[2] = -25;
        upgradeDelay[3] = -35;
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
        upgradeError[2] = 0;
        upgradeError[3] = 0;
        //names
        upgradeNames[0] = name;
        upgradeNames[1] = name;
        upgradeNames[2] = name;
        upgradeNames[3] = name;
        //debris
        upgradeDebris[0] = "wood";
        upgradeDebris[1] = "wood";
        upgradeDebris[2] = "wood";
        upgradeDebris[3] = "wood";
        //titles
        upgradeTitles[0] = "+Sharpness";
        upgradeTitles[1] = "+Piercing";
        upgradeTitles[2] = "Faster Firing";
        upgradeTitles[3] = "Yet Faster Firing";
        //desc line one
        upgradeDescA[0] = "+10";
        upgradeDescA[1] = "Increase";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "Further";
        //desc line two
        upgradeDescB[0] = "Damage";
        upgradeDescB[1] = "Piercing";
        upgradeDescB[2] = "firerate";
        upgradeDescB[3] = "Increase";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "firerate";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[9];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[10];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgradeSpecial() {
        if (nextLevelA == 1) pierce += 2;
    }

    public void updateSprite() {};
}