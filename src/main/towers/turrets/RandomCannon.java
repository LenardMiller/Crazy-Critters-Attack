package main.towers.turrets;

import main.particles.Debris;
import main.projectiles.MiscProjectile;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.HALF_PI;

public class RandomCannon extends Turret{

    public RandomCannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "miscCannon";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 40; //default: 40 frames
        delay += (round(p.random(-(delay/10),delay/10))); //injects 10% randomness so all don't fire at once
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
        priority = 2; //strong
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        angle += PApplet.radians(p.random(-error,error));
        delayTime = p.frameCount + delay; //waits this time before firing
        int spriteType = (int)(p.random(0,5.99f));
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angle-HALF_PI);
        spa.setMag(20);
        spp.add(spa);
        projectiles.add(new MiscProjectile(p,spp.x,spp.y, angle, spriteType, damage));
    }

    private void setUpgrades(){
        //special
        upgradeSpecial[0] = false;
        upgradeSpecial[1] = false;
        upgradeSpecial[2] = false;
        upgradeSpecial[3] = true;
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

    public void upgrade(int id){
        int nextLevel;
        if (id == 0){
            nextLevel = nextLevelA;
        }
        else{
            nextLevel = nextLevelB;
        }
        damage += upgradeDamage[nextLevel];
        delay += upgradeDelay[nextLevel];
        price += upgradePrices[nextLevel];
        value += upgradePrices[nextLevel];
        maxHp += upgradeHealth[nextLevel];
        hp += upgradeHealth[nextLevel];
        error += upgradeError[nextLevel];
        //reset names
        upgradeNames[0] = name;
        upgradeNames[1] = name;
        upgradeNames[2] = name;
        upgradeNames[3] = name;
        //
        name = upgradeNames[nextLevel];
        debrisType = upgradeDebris[nextLevel];
        sprite = upgradeSprites[nextLevel];
        if (id == 0){
            nextLevelA++;
        }
        else if (id == 1){
            nextLevelB++;
        }
        if (id == 0){
            if (nextLevelA < upgradeNames.length/2){
                upgradeIconA.sprite = upgradeIcons[nextLevelA];
            }
            else{
                upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        if (id == 1){
            if (nextLevelB < upgradeNames.length){
                upgradeIconB.sprite = upgradeIcons[nextLevelB];
            }
            else{
                upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void updateSprite() {}
}
