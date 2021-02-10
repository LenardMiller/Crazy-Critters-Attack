package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.findAngle;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Flamethrower extends Turret {

    public float targetAngle;
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
        pjSpeed = 5;
        range = 250;
        numFireFrames = 4;
        numLoadFrames = 1;
        numIdleFrames = 4;
        effectLevel = 50;
        effectDuration = 300;
        betweenIdleFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 20;
        flameTimer = 2;
        rotationSpeed = 0.02f;
        loadSprites();
        debrisType = "metal";
        price = FLAMETHROWER_PRICE;
        value = price;
        priority = 0; //close
        damageSound = soundsH.get("metalDamage");
        breakSound = soundsH.get("metalBreak");
        placeSound = soundsH.get("metalPlace");
        fireSound = soundsH.get("fireImpact");
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
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
        float angleB = angle + radians(p.random(-1, 1));
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(angleB - HALF_PI);
        spa.setMag(24);
        spp.add(spa);
        projectiles.add(new Flame(p, spp.x, spp.y, angleB, this, damage, (int) effectLevel, effectDuration, flameTimer, false));
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
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
        //titles
        upgradeTitles[0] = "Range";
        upgradeTitles[1] = "Swivel";
        upgradeTitles[2] = "Fireball";

        upgradeTitles[3] = "Base Damage";
        upgradeTitles[4] = "Effect Power";
        upgradeTitles[5] = "Magic Fire";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "rotation";
        upgradeDescC[1] = "speed";

        upgradeDescA[2] = "explosive";
        upgradeDescB[2] = "fireball";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Doubles";
        upgradeDescB[3] = "base";
        upgradeDescC[3] = "damage";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "& duration";

        upgradeDescA[5] = "idk,";
        upgradeDescB[5] = "sumthin";
        upgradeDescC[5] = "weird";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[15];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[23];

        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[9];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[11];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[14];
    }

    public void upgradeSpecial(int id) {
        if (nextLevelA == 0) flameTimer += 2;
        if (nextLevelA == 1) rotationSpeed += 0.02;
        if (nextLevelB == 1) {
            effectDuration += 100;
            effectLevel += 2;
        }
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    flameTimer += 1;
                    range += 75;
                    break;
                case 1:
                    rotationSpeed += 0.02;
                    break;
                case 2:
                    range += 50;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 20;
                    break;
                case 4:
                    effectDuration += 100;
                    effectLevel += 2;
                    break;
                case 5:
                    effectDuration += 100;
                    effectLevel += 2;
                    break;
            }
        }
    }

    public void updateSprite() {
    }
}