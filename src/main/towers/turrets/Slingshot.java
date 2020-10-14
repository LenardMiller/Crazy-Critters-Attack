package main.towers.turrets;

import main.projectiles.Pebble;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Slingshot extends Turret {

    public Slingshot(PApplet p, Tile tile) {
        super(p,tile);
        name = "slingshot";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 100;
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 12;
        range = 200;
        numFireFrames = 34;
        numLoadFrames = 59;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        damage = 15; //15
        loadSprites();
        debrisType = "wood";
        price = SLINGSHOT_PRICE;
        value = price;
        priority = 0; //first
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new Pebble(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 75;
        upgradePrices[2] = 75;
        upgradePrices[3] = 100;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Super Range";
        upgradeTitles[2] = "Damage Up";
        upgradeTitles[3] = "Faster Firing";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "range";

        upgradeDescA[2] = "+10";
        upgradeDescB[2] = "damage";
        upgradeDescC[2] = "";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[6];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[7];
    }

    public void upgradeSpecial(int id) {
        System.out.println("A: " + nextLevelA);
        System.out.println("B: " + nextLevelB);
        if (id == 0) {
            if (nextLevelA == 0) range += 30;
            if (nextLevelA == 1) range += 40;
        } if (id == 1) {
            if (nextLevelB == 2) damage += 10;
            if (nextLevelB == 3) delay -= 20;
        }
    }

    public void updateSprite() {}
}
