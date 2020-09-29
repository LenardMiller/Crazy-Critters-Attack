package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.findAngle;
import static main.misc.MiscMethods.updateTowerArray;

public class Flamethrower extends Turret {

    public float targetAngle;
    private int effectLevel;
    private int effectDuration;
    private int flameTimer;
    private float rotationSpeed;

    public Flamethrower(PApplet p, Tile tile) {
        super(p, tile);
        name = "flamethrower";
        size = new PVector(50, 50);
        offset = 7;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 4;
        delay += (round(p.random(-(delay / 10f), delay / 10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 5;
        error = 1;
        numFireFrames = 4;
        numLoadFrames = 1;
        numIdleFrames = 4;
        effectLevel = 1;
        effectDuration = 300;
        betweenIdleFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 2;
        flameTimer = 5;
        rotationSpeed = 0.02f;
        loadSprites();
        debrisType = "metal";
        price = 400;
        value = price;
        priority = 0; //close
        nextLevelA = 0;
        nextLevelB = 2;
        setUpgrades();
        updateTowerArray();
    }

    public void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && spriteType != 1) aim(targetEnemy);
        if (spriteType == 0 && targetEnemy != null) { //if done animating
            spriteType = 1;
            frame = 0;
            fire();
        }
    }

    public void fire() { //needed to change projectile fired
        float angleB = angle + radians(p.random(-error, error));
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(angleB - HALF_PI);
        spa.setMag(24);
        spp.add(spa);
        projectiles.add(new Flame(p, spp.x, spp.y, angleB, this, damage, effectLevel, effectDuration, flameTimer));
        delayTime = p.frameCount + delay; //waits this time before firing
    }

    public void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x-25,tile.position.y-25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / pjSpeed;
            PVector enemyHeading = PVector.fromAngle(enemy.angle);
            enemyHeading.setMag(enemy.speed * time);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        targetAngle = findAngle(position,target);

        if (visualize && debug) { //cool lines
            p.stroke(255);
            p.line(position.x, position.y, target.x, target.y);
            p.stroke(255, 0, 0, 150);
            p.line(target.x, p.height, target.x, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y, 0, target.y);
        }
    }

    public void main() { //need to check target
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (enemies.size() > 0 && alive) checkTarget();
        if (p.mousePressed && p.mouseX < tile.position.x && p.mouseX > tile.position.x - size.x && p.mouseY < tile.position.y && p.mouseY > tile.position.y - size.y && alive) {
            selection.swapSelected(tile.id);
        }
        if (angle < targetAngle) {
            angle += rotationSpeed;
        } if (angle > targetAngle) {
            angle -= rotationSpeed;
        }
    }

    private void setUpgrades() {
        //damage
        upgradeDamage[0] = 0;
        upgradeDamage[1] = 0;
        upgradeDamage[2] = 2;
        upgradeDamage[3] = 0;
        //delay (firerate)
        upgradeDelay[0] = 0;
        upgradeDelay[1] = 0;
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
        upgradeTitles[0] = "Range";
        upgradeTitles[1] = "Swivel";
        upgradeTitles[2] = "Base Damage";
        upgradeTitles[3] = "Effect Power";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "Increase";
        upgradeDescA[2] = "Doubles";
        upgradeDescA[3] = "Increase";
        //desc line two
        upgradeDescB[0] = "range";
        upgradeDescB[1] = "rotation";
        upgradeDescB[2] = "base";
        upgradeDescB[3] = "damage";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "speed";
        upgradeDescC[2] = "damage";
        upgradeDescC[3] = "& duration";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[12];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[15];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[11];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("stoneWallTW");
        upgradeSprites[3] = spritesH.get("metalWallTW");
    }

    public void upgradeSpecial() {
        if (nextLevelA == 0) flameTimer += 2;
        if (nextLevelA == 1) rotationSpeed += 0.02;
        if (nextLevelB == 1) {
            effectDuration += 100;
            effectLevel += 2;
        }
    }

    public void updateSprite() {
    }
}