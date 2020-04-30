package main.towers.turrets;

import main.projectiles.Arc;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class TeslaTower extends Turret{

    private int arcLength;
    private int arcDistance;

    public TeslaTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "tesla";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 200; //200 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        damage = 15;
        arcDistance = 200;
        arcLength = 3;
        pjSpeed = -1;
        error = 0; //5 degrees
        numFireFrames = 6;
        numLoadFrames = 5;
        numIdleFrames = 18;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        betweenIdleFrames = 3;
        spriteType = 0;
        loadSprites();
        debrisType = "metal";
        price = 300;
        value = price;
        priority = 0; //close
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void checkTarget() {
        getTargetEnemy();
        if (spriteType == 0 && targetEnemy != null) { //if done animating
            spriteType = 1;
            frame = 0;
            fire();
        }
    }

    public void fire(){ //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        arcs.add(new Arc(p, tile.position.x - 25, tile.position.y - 25, this, damage, arcLength, arcDistance, priority));
    }

    public void displayPassB2() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(fireFrames[0],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void setUpgrades(){
        //damage
        upgradeDamage[0] = 0;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 10;
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
        upgradeDebris[0] = "metal";
        upgradeDebris[1] = "metal";
        upgradeDebris[2] = "metal";
        upgradeDebris[3] = "metal";
        //titles
        upgradeTitles[0] = "+Wattage";
        upgradeTitles[1] = "++Wattage";
        upgradeTitles[2] = "+Capacitance";
        upgradeTitles[3] = "+Amperage";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "Increase";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "+10";
        //desc line two
        upgradeDescB[0] = "arc";
        upgradeDescB[1] = "arc";
        upgradeDescB[2] = "recharge";
        upgradeDescB[3] = "damage";
        //desc line three
        upgradeDescC[0] = "distance";
        upgradeDescC[1] = "distance";
        upgradeDescC[2] = "rate";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[1];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[2];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[8];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgradeSpecial() {
        if (nextLevelA == 0 || nextLevelA == 1) {
            arcDistance += 100;
            arcLength++;
        }
    }

    public void updateSprite() {}
}