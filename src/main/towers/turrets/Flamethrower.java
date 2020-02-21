package main.towers.turrets;

import main.enemies.Enemy;
import main.projectiles.Flame;
import main.towers.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Flamethrower extends Turret {

    public float targetAngle;

    public Flamethrower(PApplet p, Tile tile) {
        super(p, tile);
        name = "flamethrower";
        size = new PVector(50, 50);
        offset = 7;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 3;
        delay += (round(p.random(-(delay / 10f), delay / 10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 5;
        error = 0.25f;
        numFireFrames = 3;
        numLoadFrames = 1;
        numIdleFrames = 4;
        betweenIdleFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 1;
        loadSprites();
        debrisType = "metal";
        price = 150;
        value = price;
        priority = 0; //close
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void fire() { //needed to change projectile fired
        if (targetAngle > angle) angle += 0.05;
        if (targetAngle < angle) angle -= 0.05;
        float angleB = angle + radians(p.random(-error, error));
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(angleB - HALF_PI);
        spa.setMag(24);
        spp.add(spa);
        projectiles.add(new Flame(p, spp.x, spp.y, angleB, this, damage));
        delayTime = p.frameCount + delay; //waits this time before firing
    }

    void aim(Enemy enemy) {
        PVector position = tile.position;
        PVector e = PVector.div(enemy.size, 2);
        PVector target = enemy.position;
        target = PVector.add(target, e);
        PVector d = PVector.sub(target, position); //finds distance to enemy
        PVector t = PVector.div(d, pjSpeed); //finds time to hit

        PVector enemyHeading = PVector.fromAngle(enemy.angle);
        enemyHeading.setMag(enemy.speed*t.mag());

        target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y); //leads shots todo: fix again
        PVector ratio = PVector.sub(target, position);
//        angle = findAngleBetween(position,target);
        if (position.x == target.x) { //if on the same x
            if (position.y >= target.y) { //if below target or on same y, angle right
                targetAngle = 0;
            } else if (position.y < target.y) { //if above target, angle left
                targetAngle = PI;
            }
        } else if (position.y == target.y) { //if on same y
            if (position.x > target.x) { //if  right of target, angle down
                targetAngle = 3 * HALF_PI;
            } else if (position.x < target.x) { //if left of target, angle up
                targetAngle = HALF_PI;
            }
        } else {
            if (position.x < target.x && position.y > target.y) { //if to left and below
                targetAngle = (atan(abs(ratio.x + 15) / abs(ratio.y)));
            } else if (position.x < target.x && position.y < target.y) { //if to left and above
                targetAngle = (atan(abs(ratio.y) / abs(ratio.x))) + HALF_PI;
            } else if (position.x > target.x && position.y < target.y) { //if to right and above
                targetAngle = (atan(abs(ratio.x + 15) / abs(ratio.y))) + PI;
            } else if (position.x > target.x && position.y > target.y) { //if to right and below
                targetAngle = (atan(abs(ratio.y) / abs(ratio.x))) + 3 * HALF_PI;
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

    private void setUpgrades() {
        //special
        upgradeSpecial[0] = false;
        upgradeSpecial[1] = false;
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
        upgradeDebris[0] = "metal";
        upgradeDebris[1] = "metal";
        upgradeDebris[2] = "metal";
        upgradeDebris[3] = "metal";
        //titles
        upgradeTitles[0] = "PLACEHOLDER";
        upgradeTitles[1] = "PLACEHOLDER";
        upgradeTitles[2] = "PLACEHOLDER";
        upgradeTitles[3] = "PLACEHOLDER";
        //desc line one
        upgradeDescA[0] = "";
        upgradeDescA[1] = "";
        upgradeDescA[2] = "";
        upgradeDescA[3] = "";
        //desc line two
        upgradeDescB[0] = "";
        upgradeDescB[1] = "";
        upgradeDescB[2] = "";
        upgradeDescB[3] = ",";
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

    public void upgradeSpecial(int id) {
    }

    public void updateSprite() {
    }
}