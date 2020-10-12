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
        delay = 120;
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
        price = 100;
        value = price;
        priority = 0; //first
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new Pebble(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
    }

    private void setUpgrades(){
        //damage
        upgradeDamage[0] = 0;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 5;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
        upgradeDelay[2] = -20;
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
        //range
        upgradeRange[0] = 25;
        upgradeRange[1] = 40;
        upgradeRange[2] = 0;
        upgradeRange[3] = 0;
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
        upgradeTitles[0] = "More Precise";
        upgradeTitles[1] = "Super Precise";
        upgradeTitles[2] = "Faster Firing";
        upgradeTitles[3] = "Damage Up";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "further";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "+5";
        //desc line two
        upgradeDescB[0] = "accuracy";
        upgradeDescB[1] = "increase";
        upgradeDescB[2] = "firerate";
        upgradeDescB[3] = "damage";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "accuracy";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[6];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[8];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void updateSprite() {}
}
