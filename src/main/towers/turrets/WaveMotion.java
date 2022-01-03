package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

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
        delay = randomizeDelay(p, 7);
        damage = 500;
        pjSpeed = -1;
        range = 500;
        beam = new PImage[0];
        currentBeamFrame = 19;
        betweenIdleFrames = 3;
        betweenFireFrames = 4;
        beam = animatedSprites.get("waveMotionBeamTR");
        placeSound = sounds.get("titaniumPlace");
        breakSound = sounds.get("titaniumBreak");
        damageSound = sounds.get("titaniumDamage");
        fireSound = sounds.get("beam");
        material = "darkMetal";
        price = WAVE_MOTION_PRICE;
        value = price;
        priority = 1; //far

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {}

    @Override
    protected void fire(float barrelLength, String particleType) {
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

        playSoundRandomSpeed(p, fireSound, 1);
    }

    @Override
    public void displayMain() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.tint(255);
        p.popMatrix();
        //beam
        if (currentBeamFrame < fireFrames.length) {
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
            if (!paused) {
                beamDamage(s, e);
                if (betweenBeamTimer < betweenFireFrames) betweenBeamTimer++;
                else {
                    currentBeamFrame++;
                    betweenBeamTimer = 0;
                }
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
            boolean b = angleDif > HALF_PI && angleDif < PI + HALF_PI;
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
            if (distFromEnemyToBeam < 10) enemy.damageWithoutBuff(getDamage(), this, "energy", new PVector(0,0), false);
            else if (distFromEnemyToBeam < 30 && currentBeamFrame % 4 == 0) enemy.damageWithoutBuff(
              getDamage(), this, "energy", new PVector(0,0), false);
            else if (distFromEnemyToBeam < 70 && currentBeamFrame % 8 == 0) enemy.damageWithoutBuff(
              getDamage(), this, "energy", new PVector(0,0), false);
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 200;

        upgradePrices[3] = 50;
        upgradePrices[4] = 100;
        upgradePrices[5] = 200;
        //titles
        upgradeTitles[0] = "Further targeting";
        upgradeTitles[1] = "Faster recharge";
        upgradeTitles[2] = "sweep";

        upgradeTitles[3] = "Higher energy";
        upgradeTitles[4] = "Stable beam";
        upgradeTitles[5] = "organ melting";
        //desc line one
        upgradeDescA[0] = "Longer";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Faster";
        upgradeDescB[1] = "firing";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "swoosh";
        upgradeDescB[2] = "";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "More";
        upgradeDescB[3] = "damage";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Sustain";
        upgradeDescB[4] = "beam";
        upgradeDescC[4] = "for longer";

        upgradeDescA[5] = "idk";
        upgradeDescB[5] = "";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[21];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[35];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[3];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 200;
                    break;
                case 1:
                    delay -= 4;
                    break;
                case 2:
                    delay = 0;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 100;
                    break;
                case 4:
                    betweenFireFrames += 4;
                    break;
                case 5:
                    betweenFireFrames = 10;
                    damage = 1000;
                    break;
            }
        }
    }
}