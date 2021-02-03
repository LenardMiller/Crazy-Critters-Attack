package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.MagicMissile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class MagicMissileer extends Turret{

    public MagicMissileer(PApplet p, Tile tile) {
        super(p,tile);
        name = "magicMissleer";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 200; //200 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        damage = 15;
        pjSpeed = 5;
        range = 0; //0 degrees
        numFireFrames = 8;
        numLoadFrames = 26;
        numIdleFrames = 8;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        betweenIdleFrames = 8;
        spriteType = 0;
        loadSprites();
        debrisType = "crystal";
        price = 300;
        value = price;
        priority = 2; //strong
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

    public void fire() { //needed to change projectile fired
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 0,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 1,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 2,tile.position));
        if (name.equals("magicMissleerFour")) {
            projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, (int)(p.random(0,2.99f)),tile.position));
        }
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

    private void setUpgrades() {
//        //damage
//        upgradeDamage[0] = 10;
//        upgradeDamage[1] = 10;
//        upgradeDamage[2] = 0;
//        upgradeDamage[3] = 0;
//        //delay (firerate)
//        upgradeDelay[0] = 0;
//        upgradeDelay[1] = 0;
//        upgradeDelay[2] = -45;
//        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 200;
//        //names
//        upgradeNames[0] = name;
//        upgradeNames[1] = name;
//        upgradeNames[2] = name;
//        upgradeNames[3] = "magicMissleerFour";
        //titles
        upgradeTitles[0] = "Magic Power";
        upgradeTitles[1] = "Mega Magic";
        upgradeTitles[2] = "Faster Firing";
        upgradeTitles[3] = "More Missiles";
        //desc line one
        upgradeDescA[0] = "+10";
        upgradeDescA[1] = "+10";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "+1 Missile";
        //desc line two
        upgradeDescB[0] = "damage";
        upgradeDescB[1] = "damage";
        upgradeDescB[2] = "firerate";
        upgradeDescB[3] = "";
        //desc line three
        upgradeDescC[0] = "per missile";
        upgradeDescC[1] = "per missile";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[13];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[14];
    }

    public void upgradeSpecial() {}

    public void updateSprite() {}
}