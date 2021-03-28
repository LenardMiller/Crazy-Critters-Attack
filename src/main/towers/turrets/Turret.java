package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.CompressArray;
import main.misc.Tile;
import main.particles.BuffParticle;
import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.misc.WallSpecialVisuals.updateWallTiles;

public abstract class Turret extends Tower {

    public int pjSpeed;
    public int range;
    public float effectDuration;
    public int priority;
    public int pierce;
    public int killsTotal;
    public int damageTotal;
    public int damage;
    public float effectLevel;
    public float delay;
    public float angle;
    public String[] upgradeDescA;
    public String[] upgradeDescB;
    public String[] upgradeDescC;

    protected int offset;
    protected int state;
    protected int frame;
    protected int frameTimer;
    protected int betweenIdleFrames;
    protected int betweenFireFrames;
    protected float loadDelay;
    protected float loadDelayTime;
    protected float targetAngle;
    protected float barrelLength;
    protected String fireParticle;
    protected PImage sIdle;
    protected PImage sBase;
    protected PImage[] fireFrames;
    protected PImage[] loadFrames;
    protected PImage[] idleFrames;
    protected Enemy targetEnemy;
    protected SoundFile fireSound;

    private ArrayList<Integer> spriteArray;

    protected Turret(PApplet p, Tile tile) {
        super(p, tile);
        this.p = p;
        offset = 0;
        name = null;
        size = new PVector(50, 50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 4;
        pjSpeed = 500;
        range = 0;
        debrisType = null;
        barrelLength = 0;
        fireParticle = "null";
        spriteArray = new ArrayList<>();
        state = 0;
        effectLevel = 0;
        effectDuration = 0;
        frame = 0;
        loadDelay = 0;
        betweenIdleFrames = 0;
        loadDelayTime = 0;
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

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && state != 1) aim(targetEnemy);
        if (state == 0 && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            state = 1;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    protected void getTargetEnemy() {
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

    protected void aim(Enemy enemy) {
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
        angle += angleDifference(targetAngle, angle) / (FRAMERATE/6f);

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

    protected void fire(float barrelLength, String particleType) {
        playSoundRandomSpeed(p, fireSound, 1);
        float displayAngle = angle;
        PVector projectileSpawn = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector angleVector = PVector.fromAngle(displayAngle-HALF_PI);
        float particleCount = p.random(1,5);
        angleVector.setMag(barrelLength); //barrel length
        projectileSpawn.add(angleVector);
        spawnProjectile(projectileSpawn, displayAngle);
        if (particleType != null && !particleType.equals("null")) {
            for (int i = 0; i < particleCount; i++) {
                PVector spa2 = PVector.fromAngle(displayAngle - HALF_PI + radians(p.random(-20, 20)));
                spa2.setMag(-5);
                PVector spp2 = new PVector(projectileSpawn.x, projectileSpawn.y);
                spp2.add(spa2);
                particles.add(new BuffParticle(p, spp2.x, spp2.y, displayAngle + radians(p.random(-45, 45)), particleType));
            }
        }
    }

    protected void spawnProjectile(PVector position, float angle) {}

    protected void loadSprites() {
        sBase = staticSprites.get(name + "BaseTR");
        sIdle = staticSprites.get(name + "IdleTR");
        fireFrames = animatedSprites.get(name + "FireTR");
        loadFrames = animatedSprites.get(name + "LoadTR");
        if (animatedSprites.get(name + "IdleTR") != null) idleFrames = animatedSprites.get(name + "IdleTR");
        else idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};
    }

    public void main() {
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

    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        tile.tower = null;
        updateTowerArray();
        if (selection.id == tile.id) {
            selection.name = "null";
            inGameGui.flashA = 255;
        }
        else if (!selection.name.equals("null")) selection.swapSelected(selection.id);
        if (!sold) tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBgC(debrisType + "DebrisBGC_TL");
        updateWallTiles();
        connectWallQueues++;
        updateNodes();
    }

    public void displayPassB() {
        if (!paused) {
            if (hp < maxHp && p.random(0, 30) < 1) {
                particles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x), p.random(tile.position.y - size.y, tile.position.y), p.random(0, 360), "greyPuff"));
            }
            if (tintColor < 255) tintColor += 20;
            if (state == 0) { //idle
                sprite = sIdle;
                if (idleFrames.length > 1) {
                    if (frame < idleFrames.length) {
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
            } else if (state == 1) { //fire
                if (frame < fireFrames.length - 1) { //if not done, keep going
                    if (frameTimer >= betweenFireFrames) {
                        frame++;
                        frameTimer = 0;
                        sprite = fireFrames[frame];
                    } else frameTimer++;
                } else { //if done, switch to load
                    if (loadFrames.length > 0) {
                        int oldSize = loadFrames.length;
                        int newSize = secondsToFrames(delay);
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
                    state = 2;
                }
            } else if (state == 2) { //load
                frame++;
                if (frame < spriteArray.size() && spriteArray.get(frame) < loadFrames.length) {
                    sprite = loadFrames[spriteArray.get(frame)];
                } else { //if time runs out, switch to idle
                    frame = 0;
                    sprite = sIdle;
                    state = 0;
                }
            }
            if (hit) { //change to red if under attack
                tintColor = 0;
                hit = false;
            }
        }
        displayPassB2();
    }

    protected void displayPassB2() {
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
            value += upgradePrices[nextLevelA];
            nextLevelA++;
        } else if (id == 1) {
            value += upgradePrices[nextLevelB];
            nextLevelB++;
        }
        //icons
        if (nextLevelA < upgradeTitles.length / 2) inGameGui.upgradeIconA.sprite = upgradeIcons[nextLevelA];
        else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];

        playSoundRandomSpeed(p, placeSound, 1);
        spawnParticles();
        //prevent having fire animations longer than delays
        while (delay <= fireFrames.length * betweenFireFrames + idleFrames.length) betweenFireFrames--;
    }

    protected void upgradeSpecial(int id) {
    }
}  