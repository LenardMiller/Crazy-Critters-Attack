package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Shockwave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class SeismicTower extends Turret {

    private float shockwaveWidth;
    private boolean seismicSense;

    public SeismicTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "seismic";
        size = new PVector(50,50);
        offset = 4;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 150; //default: 200 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 7;
        numFireFrames = 14;
        betweenFireFrames = 1;
        numLoadFrames = 20;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 30;
        range = 225;
        shockwaveWidth = 60;
        seismicSense = false;
        damageSound = soundsH.get("stoneDamage");
        breakSound = soundsH.get("stoneBreak");
        placeSound = soundsH.get("stonePlace");
        fireSound = soundsH.get("seismicSlam");
        loadSprites();
        debrisType = "stone";
        price = SEISMIC_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void checkTarget() { //todo: fix pausing to aim if 360
        getTargetEnemy();
        if (targetEnemy != null && spriteType != 1) aim(targetEnemy);
        if (spriteType == 0 && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            spriteType = 1;
            frame = 0;
        }
        if (spriteType == 1 && frame == fireFrames.length - 1) fire();
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
            if (!enemy.stealthMode || seismicSense) {
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

    public void displayPassB2() {
        int hammerCount = 6;
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.tint(0, 60);
        if (name.equals("seismicSlammer")) {
            for (int i = 0; i <= hammerCount; i++) {
                p.rotate(TWO_PI / hammerCount);
                p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            }
        } else {
            p.rotate(angle);
            p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        }
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.tint(255, tintColor, tintColor);
        if (name.equals("seismicSlammer")) {
            for (int i = 0; i <= hammerCount; i++) {
                p.rotate(TWO_PI / hammerCount);
                p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            }
        } else {
            p.rotate(angle);
            p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        }
        p.popMatrix();
        p.tint(255);
    }

    public void fire() {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        delayTime = p.frameCount + delay; //waits this time before firing
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(angleB - HALF_PI);
        spa.setMag(29); //barrel length
        spp.add(spa);
        shockwaves.add(new Shockwave(p, spp.x, spp.y, (int) range, angleB, shockwaveWidth, damage, this));
        float a = angleB;
        if (shockwaveWidth == 360) {
            a = 0;
            for (int i = 0; i < 6; i++) {
                fireParticles(a);
                a += TWO_PI / 6;
            }
        } else fireParticles(a);
    }

    private void fireParticles(float a) {
        float particleCount = p.random(1, 5);
        String part = "smoke";
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(a - HALF_PI);
        spa.setMag(29); //barrel length
        spp.add(spa);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(a - HALF_PI + radians(p.random(-20, 20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x, spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p, spp2.x, spp2.y, a + radians(p.random(-45, 45)), part));
        }
        particleCount = p.random(1, 5);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(a - HALF_PI + radians(p.random(-20, 20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x, spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p, spp2.x, spp2.y, p.random(0, 360), part));
        }
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 200;
        upgradePrices[1] = 200;
        upgradePrices[2] = 1000;
        upgradePrices[3] = 250;
        upgradePrices[4] = 300;
        upgradePrices[5] = 800;
        //titles
        upgradeTitles[0] = "Faster Firing";
        upgradeTitles[1] = "Larger AOE";
        upgradeTitles[2] = "360 Wave";
        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Damage Boost";
        upgradeTitles[5] = "Seismic Sense";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "area of";
        upgradeDescC[1] = "effect";

        upgradeDescA[2] = "Shockwave";
        upgradeDescB[2] = "encircles";
        upgradeDescC[2] = "tower";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "+30";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Detect";
        upgradeDescB[5] = "stealthy";
        upgradeDescC[5] = "enemies";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[20];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[21];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[22];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 70;
                    break;
                case 1:
                    shockwaveWidth += 30;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    debrisType = "metal";
                    //todo: metal sounds
                    shockwaveWidth = 360;
                    delay -= 60;
                    name = "seismicSlammer";
                    numFireFrames = 3;
                    numLoadFrames = 9;
                    loadSprites();
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 50;
                    break;
                case 4:
                    damage += 30;
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    debrisType = "metal";
                    //todo: metal sounds
                    seismicSense = true;
                    shockwaveWidth -= 40;
                    range += 50;
                    damage += 150;
                    name = "seismicSniper";
                    loadSprites();
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {}
}
