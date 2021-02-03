package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.CompressArray;
import main.misc.Tile;
import main.particles.Debris;
import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public abstract class Turret extends Tower {

    PImage sBase;
    PImage sIdle;
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

    float targetAngle;
    SoundFile fireSound;

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
        pjSpeed = 2;
        range = 0;
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
        upgradePrices = new int[6];
        upgradeTitles = new String[6];
        upgradeDescA = new String[6];
        upgradeDescB = new String[6];
        upgradeDescC = new String[6];
        upgradeIcons = new PImage[6];
        nextLevelA = 0;
        nextLevelB = upgradeTitles.length / 2;
        updateTowerArray();
    }

    public void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && spriteType != 1) aim(targetEnemy);
        if (spriteType == 0 && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            spriteType = 1;
            frame = 0;
            fire();
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
            if (!enemy.stealthMode) {
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900 && dist < range) {
                    if (priority == 0 && dist < finalDist) { //close
                        e = enemy;
                        finalDist = dist;
                    } if (priority == 1 && dist > finalDist) { //far
                        e = enemy;
                        finalDist = dist;
                    } if (priority == 2) {
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

    public void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / pjSpeed;
            PVector enemyHeading = PVector.fromAngle(enemy.angle);
            enemyHeading.setMag(enemy.speed * time);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        targetAngle = clampAngle(findAngle(position, target));
        angle = clampAngle(angle);
        angle += angleDifference(targetAngle, angle) / 10;

        if (abs(targetAngle - angle) < 0.05) angle = targetAngle; //snap to prevent getting stuck

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
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void loadSprites() {
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
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
        if (enemies.size() > 0 && alive && !paused) checkTarget();
        if (p.mousePressed && p.mouseX < tile.position.x && p.mouseX > tile.position.x - size.x && p.mouseY < tile.position.y
                && p.mouseY > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    public void displayPassB() {
        if (!paused) {
            if (hp < maxHp && p.random(0, 30) < 1) {
                particles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x), p.random(tile.position.y - size.y, tile.position.y), p.random(0, 360), "greyPuff"));
            }
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
                        int newSize = delay;
                        spriteArray = new ArrayList<>();
                        if (oldSize > newSize) { //decreasing size
                            //creates the new spriteArray
                            for (int i = 0; i < oldSize; i++) spriteArray.add(i);
                            //compression
                            compress = new CompressArray(oldSize, newSize, spriteArray);
                            compress.main();
                        } else { //increasing size
                            compress = new CompressArray(oldSize - 1, newSize, spriteArray);
                            compress.main();
                            spriteArray = compress.compArray;
                        }
                    }
                    frame = 0;
                    spriteType = 2;
                }
            } else if (spriteType == 2) { //load
                frame++;
                if (frame < spriteArray.size() && spriteArray.get(frame) < loadFrames.length) {
                    sprite = loadFrames[spriteArray.get(frame)];
                } else { //if time runs out, switch to idle
                    frame = 0;
                    sprite = sIdle;
                    spriteType = 0;
                }
            }
            if (hit) { //change to red if under attack
                tintColor = 0;
                hit = false;
            }
        }
        displayPassB2();
    }

    public void displayPassB2() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0, 60);
        if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        p.popMatrix();
        p.tint(255);
    }

    public void displayPassA() {
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    public void upgrade(int id) {
        upgradeSpecial(id);
        if (id == 0) {
            placeSound.stop();
            placeSound.play(p.random(0.8f, 1.2f), volume);
        } else if (id == 1) {
            placeSound.stop();
            placeSound.play(p.random(0.8f, 1.2f), volume);
        }
        if (id == 0) {
            value += upgradePrices[nextLevelA];
            nextLevelA++;
        } else if (id == 1) {
            value += upgradePrices[nextLevelB];
            nextLevelB++;
        }
        if (nextLevelA < upgradeTitles.length / 2) upgradeIconA.sprite = upgradeIcons[nextLevelA];
        else upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
        if (nextLevelB < upgradeTitles.length) upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        //shower debris
        int num = (int) (p.random(30, 50));
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p, (tile.position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (tile.position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debrisType));
        }
        //prevent having fire animations longer than delays
        while (delay <= numFireFrames * betweenFireFrames + numIdleFrames) betweenFireFrames--;
    }

    public void upgradeSpecial(int id) {
    }
}  