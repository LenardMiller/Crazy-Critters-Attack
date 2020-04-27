package main.towers.turrets;

import main.enemies.Enemy;
import main.particles.Debris;
import main.misc.Tile;
import main.towers.Tower;
import main.misc.CompressArray;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.findAngle;
import static main.misc.MiscMethods.updateTowerArray;

public abstract class Turret extends Tower {

    PImage sBase;
    PImage sIdle;
    public int delayTime;
    int offset;
    int pjSpeed;
    int numFireFrames;
    int numLoadFrames;
    int numIdleFrames;
    PImage[] fireFrames;
    PImage[] loadFrames;
    PImage[] idleFrames;
    int spriteType;
    int frame;
    int frameTimer;
    int betweenIdleFrames;
    int betweenFireFrames;
    float loadDelay;
    float loadDelayTime;
    private ArrayList<Integer> spriteArray;
    Enemy targetEnemy;
    public int effectLevel;
    public int effectDuration;

    Turret(PApplet p, Tile tile) {
        super(p, tile);
        this.p = p;
        offset = 0;
        name = null;
        size = new PVector(50, 50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 240;
        delayTime = delay;
        pjSpeed = 2;
        error = 0;
        numFireFrames = 1;
        numLoadFrames = 1;
        numIdleFrames = 1;
        debrisType = null;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        idleFrames = new PImage[numIdleFrames];
        spriteArray = new ArrayList<>();
        spriteType = 0;
        effectLevel = 0;
        effectDuration = 0;
        frame = 0;
        loadDelay = 0;
        betweenIdleFrames = 0;
        loadDelayTime = 0;
        turret = true;
        loadSprites();
        upgradeDamage = new int[4];
        upgradeDelay = new int[4];
        upgradePrices = new int[4];
        upgradeHealth = new int[4];
        upgradeError = new float[4];
        upgradeNames = new String[4];
        upgradeDebris = new String[4];
        upgradeTitles = new String[4];
        upgradeDescA = new String[4];
        upgradeDescB = new String[4];
        upgradeDescC = new String[4];
        upgradeIcons = new PImage[4];
        upgradeSprites = new PImage[4];
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

    void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float dist;
        if (priority == 0) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!enemy.stealthMode) {
                float x = abs(tile.position.x - enemy.position.x);
                float y = abs(tile.position.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900) {
                    if (priority == 0 && t < dist) { //close
                        e = enemy;
                        dist = t;
                    }
                    if (priority == 1 && t > dist) { //far
                        e = enemy;
                        dist = t;
                    }
                    if (priority == 2) if (enemy.maxHp > maxHp) { //strong
                        e = enemy;
                        maxHp = enemy.maxHp;
                    } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                        e = enemy;
                        dist = t;
                    }
                }
            }
        }
        targetEnemy = e;
    }

    void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x-25,tile.position.y-25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / pjSpeed;
            PVector enemyHeading = PVector.fromAngle(enemy.angle);
            enemyHeading.setMag(enemy.speed * time);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        angle = findAngle(position,target);

        if (visualize && debug) { //cool lines
            p.stroke(255);
            p.line(position.x, position.y, target.x, target.y);
            p.stroke(255, 0, 0, 150);
            p.line(target.x, p.height, target.x, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y, 0, target.y);
        }
    }

    public void fire() {
        delayTime = p.frameCount + delay; //waits this time before firing
        angle += radians(p.random(-error, error));
    }

    public void loadSprites() {
        sBase = spritesH.get(name + "BaseTR");
        sIdle = spritesH.get(name + "IdleTR");
        fireFrames = spritesAnimH.get(name + "FireTR");
        loadFrames = spritesAnimH.get(name + "LoadTR");
        if (numIdleFrames > 1) idleFrames = spritesAnimH.get(name + "IdleTR");
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
    }

    public void displayPassB() {
        if (tintColor < 255) tintColor += 20;
        if (spriteType == 0) { //idle
            sprite = sIdle;
            if (numIdleFrames > 1) {
                if (frame < numIdleFrames) {
                    sprite = idleFrames[frame];
                    if (frameTimer >= betweenIdleFrames) {
                        frame++;
                        frameTimer = 0;
                    } else frameTimer++;
                } else {
                    frame = 0;
                    sprite = idleFrames[frame];
                }
            }
        } else if (spriteType == 1) { //fire
            if (frame < numFireFrames - 1) { //if not done, keep going
                if (frameTimer >= betweenFireFrames) {
                    frame++;
                    frameTimer = 0;
                    sprite = fireFrames[frame];
                } else frameTimer++;
            } else { //if done, switch to load
                if (numLoadFrames > 0) {
                    int oldSize = numLoadFrames;
                    int newSize = (delayTime - p.frameCount);
                    spriteArray = new ArrayList<>();
                    if (oldSize > newSize) for (int i = 0; i < oldSize; i++) spriteArray.add(i);
                    if (oldSize > newSize) {
                        while (spriteArray.size() != newSize) {
                            compress = new CompressArray(oldSize, newSize, spriteArray);
                            compress.main();
                        }
                    } else {
                        compress = new CompressArray(oldSize-1,newSize,spriteArray);
                        compress.main();
                        spriteArray = compress.compArray;
                    }
                }
                frame = 0;
                spriteType = 2;
            }
        } else if (spriteType == 2) { //load
            frame++;
            if (frame < spriteArray.size()) sprite = loadFrames[spriteArray.get(frame)];
            else { //if time runs out, switch to idle
                frame = 0;
                sprite = sIdle;
                spriteType = 0;
            }
        }
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        displayPassB2();
    }

    public void displayPassB2() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset); //todo: crash here
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    public void displayPassA() {
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    public void upgrade(int id) {
        int nextLevel;
        if (id == 0) nextLevel = nextLevelA;
        else nextLevel = nextLevelB;
        damage += upgradeDamage[nextLevel];
        delay += upgradeDelay[nextLevel];
        price += upgradePrices[nextLevel];
        value += upgradePrices[nextLevel];
        maxHp += upgradeHealth[nextLevel];
        hp += upgradeHealth[nextLevel];
        error += upgradeError[nextLevel];
        name = upgradeNames[nextLevel];
        debrisType = upgradeDebris[nextLevel];
        sprite = upgradeSprites[nextLevel];
        upgradeSpecial();
        if (id == 0) {
            nextLevelA++;
            if (nextLevelA < upgradeNames.length / 2) upgradeIconA.sprite = upgradeIcons[nextLevelA];
            else upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
        } else if (id == 1) nextLevelB++;
        if (id == 1) {
            if (nextLevelB < upgradeNames.length) upgradeIconB.sprite = upgradeIcons[nextLevelB];
            else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        }
        int num = (int) (p.random(30, 50)); //shower debris
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p, (tile.position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (tile.position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debrisType));
        }
        //prevent having fire animations longer than delays
        while (delay <= numFireFrames*betweenFireFrames + numIdleFrames) betweenFireFrames--;
    }

    public void upgradeSpecial() {}
}  