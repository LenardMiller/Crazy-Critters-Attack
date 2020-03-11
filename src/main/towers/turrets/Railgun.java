package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.particles.RailgunBlast;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Railgun extends Turret {

    private PImage[] vaporTrail;
    private PImage[] vaporEndSprites;
    private int numVaporFrames;
    private int betweenVaporFrames;
    private int betweenVaporTimer;
    private int currentVaporFrame;
    private PVector vaporStart;
    private PVector vaporEnd;
    private float vaporAngle;
    private int vaporLength;
    private PVector vaporPartLength;

    public Railgun(PApplet p, Tile tile) {
        super(p,tile);
        name = "railgun";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        offset = 6;
        hit = false;
        delay = 500; //500 frames
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        damage = 300;
        pjSpeed = -1;
        error = 0; //0
        numFireFrames = 15;
        numLoadFrames = 9;
        numIdleFrames = 6;
        numVaporFrames = 15;
        betweenVaporFrames = 3;
        betweenVaporTimer = 0;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        vaporTrail = new PImage[numVaporFrames];
        vaporEndSprites = new PImage[11];
        currentVaporFrame = 16;
        betweenIdleFrames = 3;
        betweenFireFrames = 3;
        spriteType = 0;
        vaporTrail = spritesAnimH.get("railgunVaporTrailTR");
        vaporEndSprites = spritesAnimH.get("railgunBlastPT");
        loadSprites();
        debrisType = "ultimate";
        price = 150;
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
        spa.setMag(30);
        spp.add(spa);
        particles.add(new RailgunBlast(p,spp.x,spp.y,0));

        currentVaporFrame = 0;
        vaporStart = spp;
        vaporEnd = targetEnemy.position;
        vaporAngle = angle;
        float c = sqrt(sq(vaporEnd.x-vaporStart.x)+sq(vaporEnd.y-vaporStart.y));
        vaporLength = (int)(c/24);
        vaporPartLength = PVector.fromAngle(vaporAngle - radians(90));
        vaporPartLength.setMag(24);

        targetEnemy.effectDamage(damage,this);
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
        if (currentVaporFrame < numVaporFrames) {
            for (int i = 0; i <= vaporLength; i++) {
                p.pushMatrix();
                float x = vaporStart.x + (vaporPartLength.x*i);
                float y = vaporStart.y + (vaporPartLength.y*i);
                p.translate(x, y);
                p.rotate(vaporAngle);
                p.image(vaporTrail[currentVaporFrame], -2, 0);
                if (i == vaporLength && currentVaporFrame < 11) p.image(vaporEndSprites[currentVaporFrame], -13, -15);
                p.popMatrix();
            }
            if (betweenVaporTimer < betweenVaporFrames) betweenVaporTimer++;
            else {
                currentVaporFrame++;
                betweenVaporTimer = 0;
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

    void aim(Enemy enemy) {
        PVector position = tile.position;
        PVector e = PVector.div(enemy.size, 2);
        PVector target = enemy.position;
        target = PVector.add(target, e);

        PVector ratio = PVector.sub(target, position);
//        angle = findAngleBetween(position,target);
        if (position.x == target.x) { //if on the same x
            if (position.y >= target.y) { //if below target or on same y, angle right
                angle = 0;
            } else if (position.y < target.y) { //if above target, angle left
                angle = PI;
            }
        } else if (position.y == target.y) { //if on same y
            if (position.x > target.x) { //if  right of target, angle down
                angle = 3 * HALF_PI;
            } else if (position.x < target.x) { //if left of target, angle up
                angle = HALF_PI;
            }
        } else {
            if (position.x < target.x && position.y > target.y) { //if to left and below
                angle = (atan(abs(ratio.x + 15) / abs(ratio.y)));
            } else if (position.x < target.x && position.y < target.y) { //if to left and above
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + HALF_PI;
            } else if (position.x > target.x && position.y < target.y) { //if to right and above
                angle = (atan(abs(ratio.x + 15) / abs(ratio.y))) + PI;
            } else if (position.x > target.x && position.y > target.y) { //if to right and below
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + 3 * HALF_PI;
            }
        }
        if (visualize && debug) { //cool lines
            p.stroke(255);
            p.line(position.x - size.x / 2, position.y - size.y / 2, target.x - enemy.size.x / 2, target.y - enemy.size.y / 2);
            p.stroke(255, 0, 0, 150);
            p.line(target.x - enemy.size.x / 2, p.height, target.x - enemy.size.x / 2, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y - enemy.size.y / 2, 0, target.y - enemy.size.y / 2);
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