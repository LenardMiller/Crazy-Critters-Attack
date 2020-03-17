package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.*;

public class WaveMotion extends Turret {

    private PImage[] beam;
    private int betweenBeamTimer;
    private int currentBeamFrame;
    private PVector beamStart;
    private float beamAngle;
    private int beamLength;
    private PVector beamPartLength;

    public WaveMotion(PApplet p, Tile tile) {
        super(p,tile);
        name = "waveMotion";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        offset = 0;
        hit = false;
        delay = 400; //400 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        damage = 2;
        pjSpeed = -1;
        error = 0; //0
        numFireFrames = 18;
        numLoadFrames = 80;
        numIdleFrames = 14;
        betweenBeamTimer = 0;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        beam = new PImage[numFireFrames];
        currentBeamFrame = 19;
        betweenIdleFrames = 3;
        betweenFireFrames = 4;
        spriteType = 0;
        beam = spritesAnimH.get("waveMotionBeamTR");
        loadSprites();
        debrisType = "darkMetal";
        price = 250;
        value = price;
        priority = 2; //strong
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire() {
        delayTime = p.frameCount + delay; //waits this time before firing
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angle-HALF_PI);
        spa.setMag(35);
        spp.add(spa);

        currentBeamFrame = 0;
        beamStart = spp;
        PVector beamEnd = targetEnemy.position;
        PVector ref = new PVector(beamEnd.x-beamStart.x, beamEnd.y-beamStart.y);
        ref.setMag(5000);
        beamEnd = ref;
        beamAngle = angle;
        float c = sqrt(sq(beamEnd.x - beamStart.x)+sq(beamEnd.y - beamStart.y));
        beamLength = (int)(c/24);
        beamPartLength = PVector.fromAngle(beamAngle - radians(90));
        beamPartLength.setMag(24);
    }

    public void displayPassB2() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.tint(255);
        p.popMatrix();
        //vaporTrail
        if (currentBeamFrame < numFireFrames) {
            PVector s = new PVector();
            PVector e = new PVector();
            for (int i = 0; i <= beamLength; i++) {
                p.pushMatrix();
                float x = beamStart.x + (beamPartLength.x*i);
                float y = beamStart.y + (beamPartLength.y*i);
                p.translate(x, y);
                p.rotate(beamAngle);
                p.image(beam[currentBeamFrame], -10, 0);
                p.popMatrix();
                if (i == 0) s = new PVector(x,y);
                if (i == beamLength) e = new PVector(x,y);
            }
            beamDamage(s,e);
            if (betweenBeamTimer < betweenFireFrames) betweenBeamTimer++;
            else {
                currentBeamFrame++;
                betweenBeamTimer = 0;
            }
        }
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void beamDamage(PVector start, PVector end) { //todo: fix hitting enemies behind tower
        for (Enemy enemy : enemies) {
            float enemyXref = enemy.position.x-start.x;
            float enemyYref = (enemy.position.y-start.y)*-1;
            float m = findSlope(start,end);
            float angle = atan(m);
            if (angle < 0) angle += TWO_PI;
            float tanAngle = tan(angle);
            float tanAngleMin90 = tan(angle-radians(90));
            float intersectionX = (tanAngleMin90*enemyXref-enemyYref)/(tanAngle-tanAngleMin90);
            float intersectionY = tan(angle)*intersectionX;
            float distToIntersection = sqrt(sq(intersectionX)+sq(intersectionY));
            float distToEnemy = sqrt(sq(enemyXref)+sq(enemyYref));
            float distFromEnemyToBeam = sqrt(sq(distToEnemy)-sq(distToIntersection));
            distFromEnemyToBeam -= enemy.radius/2;
            if (distFromEnemyToBeam < 1) distFromEnemyToBeam = 1;
            if (distFromEnemyToBeam < 10) enemy.damageSimple(damage, this);
            else if (distFromEnemyToBeam < 50 && currentBeamFrame % 4 == 0) enemy.damageSimple(damage, this);
            else if (distFromEnemyToBeam < 100 && currentBeamFrame % 8 == 0) enemy.damageSimple(damage, this);
        }
    }

    private void setUpgrades(){
        //damage
        upgradeDamage[0] = 0;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 0;
        upgradeDamage[3] = 0;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
        upgradeDelay[2] = 0;
        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 0;
        upgradePrices[1] = 0;
        upgradePrices[2] = 0;
        upgradePrices[3] = 0;
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
        upgradeDebris[0] = "ultimate";
        upgradeDebris[1] = "ultimate";
        upgradeDebris[2] = "ultimate";
        upgradeDebris[3] = "ultimate";
        //titles
        upgradeTitles[0] = "";
        upgradeTitles[1] = "";
        upgradeTitles[2] = "";
        upgradeTitles[3] = "";
        //desc line one
        upgradeDescA[0] = "";
        upgradeDescA[1] = "";
        upgradeDescA[2] = "";
        upgradeDescA[3] = "";
        //desc line two
        upgradeDescB[0] = "";
        upgradeDescB[1] = "";
        upgradeDescB[2] = "";
        upgradeDescB[3] = "";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[0];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[0];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgradeSpecial() {}

    public void updateSprite() {}
}