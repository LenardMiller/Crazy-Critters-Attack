package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Glue;
import main.projectiles.SpikeyGlue;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class Gluer extends Turret {

    float effectLevel;
    int effectDuration;
    boolean spikey;

    public Gluer(PApplet p, Tile tile) {
        super(p,tile);
        name = "gluer";
        size = new PVector(50,50);
        offset = 0;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 150; //default: 150 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 7;
        numFireFrames = 5;
        betweenFireFrames = 1;
        numLoadFrames = 7;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 0;
        range = 250;
        effectDuration = 50;
        effectLevel = 0.7f;
        spikey = false;
        damageSound = soundsH.get("stoneDamage");
        breakSound = soundsH.get("stoneBreak");
        placeSound = soundsH.get("stonePlace");
        fireSound = soundsH.get("glueFire");
        loadSprites();
        debrisType = "stone";
        price = GLUER_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void fire() {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        delayTime = p.frameCount + delay; //waits this time before firing
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);float particleCount = p.random(1,5);
        spa.setMag(28); //barrel length
        spp.add(spa);
        String part = "glue";
        if (spikey) projectiles.add(new SpikeyGlue(p,spp.x,spp.y, angleB, this, damage, effectLevel, effectDuration));
        else projectiles.add(new Glue(p,spp.x,spp.y, angleB, this, damage, effectLevel, effectDuration));
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),part));
        }
    }

    void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float finalDist;
        if (priority == 0) finalDist = 1000000;
        else finalDist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            float newSpeed = enemy.maxSpeed * effectLevel;
            if (!enemy.stealthMode && enemy.speed > newSpeed) { //make sure effect would actually slow down enemy todo: doesn't always work!?
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900 && dist < range) {
                    if (priority == 0 && dist < finalDist) { //close
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 1 && dist > finalDist) { //far
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 2) {
                        if (enemy.maxHp > maxHp) { //strong
                            e = enemy;
                            finalDist = dist;
                            maxHp = enemy.maxHp;
                        } else if (enemy.maxHp == maxHp && dist < finalDist) { //strong -> close
                            e = enemy;
                            finalDist = dist;
                        }
                    }
                }
            }
        }
        targetEnemy = e;
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 75;
        upgradePrices[1] = 150;
        upgradePrices[2] = 500;

        upgradePrices[3] = 150;
        upgradePrices[4] = 150;
        upgradePrices[5] = 700;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Long Glue";
        upgradeTitles[2] = "Glue Splash";

        upgradeTitles[3] = "Gluier Glue";
        upgradeTitles[4] = "Hard Glue";
        upgradeTitles[5] = "Spikey Glue";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "glue";
        upgradeDescC[1] = "duration";

        upgradeDescA[2] = "Glue";
        upgradeDescB[2] = "splatters";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "glue";
        upgradeDescC[3] = "strength";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "glue";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "Enemies";
        upgradeDescB[5] = "shatter into";
        upgradeDescC[5] = "shards";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[0];

        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[0];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 30;
                    break;
                case 1:
                    effectDuration += 35;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    effectDuration += 35;
                    range += 30;
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    effectLevel = 0.6f;
                    break;
                case 4:
                    damage = 35;
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    damage += 35;
                    effectLevel = 0.5f;
                    spikey = true;
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {}
}
