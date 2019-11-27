package main.towers.turrets;

import main.particles.Debris;
import main.projectiles.EnergyBlast;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.HALF_PI;

public class EnergyBlaster extends Turret{

    private int effectRadius;
    private boolean bigExplosion;

    public EnergyBlaster(PApplet p, Tile tile) {
        super(p,tile);
        name = "energyBlaster";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 240; //240 frames
        delay += (round(p.random(-(delay/10),delay/10))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        damage = 30;
        pjSpeed = 16;
        error = 3; //3 degrees
        numFireFrames = 14; //14
        numLoadFrames = 42; //42
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        damage = 20;
        effectRadius = 20;
        bigExplosion = false;
        loadSprites();
        debrisType = "metal";
        price = 150;
        value = price;
        priority = 2; //strong
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        angle += radians(p.random(-error,error));
        delayTime = p.frameCount + delay; //waits this time before firing
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angle-HALF_PI);
        spa.setMag(40);
        spp.add(spa);
        projectiles.add(new EnergyBlast(p,spp.x,spp.y, angle, damage, effectRadius, bigExplosion));
    }

    private void setUpgrades(){
        //special
        upgradeSpecial[0] = false;
        upgradeSpecial[1] = true;
        upgradeSpecial[2] = false;
        upgradeSpecial[3] = false;
        //damage
        upgradeDamage[0] = 0;
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
        upgradeError[0] = -1.5f;
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
        upgradeTitles[0] = "More Precise";
        upgradeTitles[1] = "Splashier";
        upgradeTitles[2] = "Faster Firing";
        upgradeTitles[3] = "Yet Faster Firing";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "increase";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "further";
        //desc line two
        upgradeDescB[0] = "accuracy";
        upgradeDescB[1] = "explosion";
        upgradeDescB[2] = "firerate";
        upgradeDescB[3] = "increase";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "radius";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "firerate";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[12];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[10];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgrade(int id){
        int nextLevel;
        if (id == 0){
            nextLevel = nextLevelA;
        } else{
            nextLevel = nextLevelB;
        }
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
        if (upgradeSpecial[nextLevel]){
            effectRadius += 10;
            bigExplosion = true;
        }
        if (id == 0){
            nextLevelA++;
        } else if (id == 1){
            nextLevelB++;
        }
        if (id == 0){
            if (nextLevelA < upgradeNames.length/2){
                upgradeIconA.sprite = upgradeIcons[nextLevelA];
            } else{
                upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        if (id == 1){
            if (nextLevelB < upgradeNames.length){
                upgradeIconB.sprite = upgradeIcons[nextLevelB];
            } else{
                upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void display(){
        p.tint(255,tintColor,tintColor);
        p.image(sBase,tile.position.x-size.x,tile.position.y-size.y);
        p.pushMatrix();
        p.translate(tile.position.x-size.x/2,tile.position.y-size.y/2);
        p.rotate(angle);
        p.image(sprite,-size.x/2-11,-size.y/2-11);
        p.popMatrix();
        p.tint(255,255,255);
    }
}