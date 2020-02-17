package main.towers.turrets;

import main.particles.Debris;
import main.projectiles.MagicMissile;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class MagicMissileer extends Turret{

    private boolean four;

    public MagicMissileer(PApplet p, Tile tile) {
        super(p,tile);
        name = "magicMissleer";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 200; //200 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        damage = 15;
        pjSpeed = 5;
        error = 0; //0 degrees
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
        price = 150;
        value = price;
        priority = 2; //strong
        nextLevelA = 0;
        nextLevelB = 2;
        four = false;
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
        delayTime = p.frameCount + delay; //waits this time before firing
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), angle, this, damage, 0,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), angle, this, damage, 1,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), angle, this, damage, 2,tile.position));
        if (four) {
            projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), angle, this, damage, (int)(p.random(0,2.99f)),tile.position));
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
        //special
        upgradeSpecial[0] = false;
        upgradeSpecial[1] = false;
        upgradeSpecial[2] = false;
        upgradeSpecial[3] = true;
        //damage
        upgradeDamage[0] = 10;
        upgradeDamage[1] = 10;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 0;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
        upgradeDelay[2] = -45;
        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 200;
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
        upgradeNames[3] = "magicMissleerFour";
        //debris
        upgradeDebris[0] = "crystal";
        upgradeDebris[1] = "crystal";
        upgradeDebris[2] = "crystal";
        upgradeDebris[3] = "crystal";
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
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgrade(int id) {
        int nextLevel;
        if (id == 0) nextLevel = nextLevelA;
        else nextLevel = nextLevelB;
        damage += upgradeDamage[nextLevel];
        delay += upgradeDelay[nextLevel];
        price += upgradePrices[nextLevel];
        value += upgradePrices[nextLevel];
        maxHp += upgradeHealth[nextLevel];
        hp += upgradeHealth[nextLevel];
        error += upgradeError[nextLevel];
        name = upgradeNames[nextLevel];
        debrisType = upgradeDebris[nextLevel];
        sprite = upgradeSprites[nextLevel];
        if (upgradeSpecial[nextLevel]) four = true;
        if (id == 0) {
            nextLevelA++;
            if (nextLevelA < upgradeNames.length / 2) upgradeIconA.sprite = upgradeIcons[nextLevelA];
            else upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
        } else if (id == 1) nextLevelB++;
        if (id == 1) {
            if (nextLevelB < upgradeNames.length) upgradeIconB.sprite = upgradeIcons[nextLevelB];
            else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        }
        //reset names
        upgradeNames[0] = name;
        upgradeNames[1] = name;
        upgradeNames[2] = name;
        int num = floor(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void updateSprite() {}
}