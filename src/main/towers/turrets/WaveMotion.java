package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.*;
import static main.misc.WallSpecialVisuals.*;

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
        range = 0; //0
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
        price = 500;
        value = price;
        priority = 2; //strong
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
        //beam
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

    private void beamDamage(PVector start, PVector end) {
        for (Enemy enemy : enemies) {
            float enemyXref = enemy.position.x - start.x;
            float enemyYref = (enemy.position.y - start.y) * -1;
            float m = findSlope(start, end);
            float angle2 = atan(m);
            if (angle < 0) angle2 += TWO_PI;

            //prevent hitting enemies behind tower
            PVector position = new PVector(tile.position.x-25,tile.position.y-25);
            float angleToEnemy = findAngle(enemy.position,position);
            float angleDif = angleToEnemy - angle2;
            boolean b = angleDif < -HALF_PI && angleDif > -PI + HALF_PI || angleDif > HALF_PI && angleDif < PI + HALF_PI;
            boolean q = angle > PI+HALF_PI && angle <= TWO_PI || angle >= 0 && angle < HALF_PI;
            if (q) { //something to do with angle weirdness
                if (!b) continue;
            } else if (b) continue;

            float tanAngle = tan(angle2);
            float tanAngleMin90 = tan(angle2 - radians(90));
            float intersectionX = (tanAngleMin90 * enemyXref - enemyYref) / (tanAngle - tanAngleMin90);
            float intersectionY = tan(angle2) * intersectionX;
            float distToIntersection = sqrt(sq(intersectionX) + sq(intersectionY));
            float distToEnemy = sqrt(sq(enemyXref) + sq(enemyYref));
            float distFromEnemyToBeam = sqrt(sq(distToEnemy) - sq(distToIntersection));
            if (Float.isNaN(distFromEnemyToBeam)) distFromEnemyToBeam = 1;
            distFromEnemyToBeam -= enemy.radius / 2;
            if (distFromEnemyToBeam < 1) distFromEnemyToBeam = 1;
            if (distFromEnemyToBeam < 10) enemy.damageSimple(damage, this, "burning", new PVector(0,0), false);
            else if (distFromEnemyToBeam < 30 && currentBeamFrame % 4 == 0) enemy.damageSimple(damage, this, "burning", new PVector(0,0), false);
            else if (distFromEnemyToBeam < 70 && currentBeamFrame % 8 == 0) enemy.damageSimple(damage, this, "burning", new PVector(0,0), false);
        }
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 0;
        upgradePrices[1] = 0;
        upgradePrices[2] = 0;
        upgradePrices[3] = 0;
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
    }

    public void upgradeSpecial() {}

    public void updateSprite() {}
}