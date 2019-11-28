package main.towers.turrets;

import main.projectiles.Pebble;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Slingshot extends Turret{

    public Slingshot(PApplet p, Tile tile) {
        super(p,tile);
        name = "slingshot";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 180; //default: 180 frames
        delay += (round(p.random(-(delay/10),delay/10))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 12;
        error = 5; //set to 360 for a fun time. default: 5 degrees
        numFireFrames = 34;
        numLoadFrames = 59;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        damage = 10;
        loadSprites();
        debrisType = "stone";
        price = 50;
        value = price;
        priority = 0; //first
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        angle += radians(p.random(-error,error));
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new Pebble(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, damage));
    }

    private void setUpgrades(){
        //special
        upgradeSpecial[0] = false;
        upgradeSpecial[1] = false;
        upgradeSpecial[2] = false;
        upgradeSpecial[3] = false;
        //damage
        upgradeDamage[0] = 0;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 5;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
        upgradeDelay[2] = -30;
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
        upgradeError[0] = -1;
        upgradeError[1] = -2;
        upgradeError[2] = 0;
        upgradeError[3] = 0;
        //names
        upgradeNames[0] = name;
        upgradeNames[1] = name;
        upgradeNames[2] = name;
        upgradeNames[3] = name;
        //debris
        upgradeDebris[0] = "stone";
        upgradeDebris[1] = "stone";
        upgradeDebris[2] = "stone";
        upgradeDebris[3] = "stone";
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
