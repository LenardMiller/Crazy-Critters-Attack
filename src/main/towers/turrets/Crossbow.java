package main.towers.turrets;

import main.projectiles.Bolt;
import main.misc.Tile;
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
        delay = 310; //default: 310 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 24;
        range = 300;
        numFireFrames = 13;
        numLoadFrames = 81;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 30;
        pierce = 2;
        loadSprites();
        debrisType = "wood";
        price = CROSSBOW_PRICE;
        value = price;
        priority = 1; //last
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new Bolt(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage, pierce));
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 75;
        upgradePrices[1] = 175;
        upgradePrices[2] = 100;
        upgradePrices[3] = 150;
        //titles
        upgradeTitles[0] = "Pointy";
        upgradeTitles[1] = "Sharp";
        upgradeTitles[2] = "Increase range";
        upgradeTitles[3] = "Faster Firing";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "piercing";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "+20";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Increase";
        upgradeDescB[2] = "range";
        upgradeDescC[2] = "";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[9];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[10];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            if (nextLevelA == 0) pierce += 2;
            if (nextLevelA == 1) damage += 20;
        } if (id == 1) {
            if (nextLevelB == 2) range += 30;
            if (nextLevelB == 3) delay -= 70;
        }
    }

    public void updateSprite() {};
}