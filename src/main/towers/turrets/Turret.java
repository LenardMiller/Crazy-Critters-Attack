package main.towers.turrets;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.gui.guiObjects.PopupText;
import main.misc.CompressArray;
import main.misc.Tile;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateFlooring;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public abstract class Turret extends Tower {

    public boolean hasPriority;
    public int pjSpeed;
    public int range;
    /**
     * Close, far, maxHP
     */
    public int priority;
    public int pierce;
    public int killsTotal;
    public int damageTotal;
    public int damage;
    public float effectDuration;
    public float effectLevel;
    public float delay;
    /**
     * Radians
     */
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
        hasPriority = true;
        size = new PVector(50, 50);
        maxHp = 20;
        hp = maxHp;
        delay = 4;
        pjSpeed = 500;
        spriteArray = new ArrayList<>();
        upgradePrices = new int[6];
        upgradeTitles = new String[6];
        upgradeDescA = new String[6];
        upgradeDescB = new String[6];
        upgradeDescC = new String[6];
        upgradeIcons = new PImage[6];
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
            if (!(enemy.state == 0 && enemy instanceof BurrowingEnemy)) {
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.onScreen() && dist < getRange()) {
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

    /**
     * Sets the target angle to match the target.
     * Leads shots if enemy moving.
     * @param enemy enemy to aim at
     */
    protected void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / pjSpeed;
            PVector enemyHeading = PVector.fromAngle(enemy.angle);
            if (enemy.state == 0) enemyHeading.setMag(enemy.speed * time); //only lead if enemy moving
            else enemyHeading.setMag(0);
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
        PVector barrel = PVector.fromAngle(displayAngle-HALF_PI);
        float particleCount = p.random(1,5);
        barrel.setMag(barrelLength); //barrel length
        projectileSpawn.add(barrel);
        spawnProjectiles(projectileSpawn, displayAngle);
        if (particleType != null && !particleType.equals("null")) {
            for (int i = 0; i < particleCount; i++) {
                PVector spa2 = PVector.fromAngle(displayAngle - HALF_PI + radians(p.random(-20, 20)));
                spa2.setMag(-5);
                PVector spp2 = new PVector(projectileSpawn.x, projectileSpawn.y);
                spp2.add(spa2);
                topParticles.add(new MiscParticle(p, spp2.x, spp2.y, displayAngle + radians(p.random(-45, 45)), particleType));
            }
        }
    }

    protected abstract void spawnProjectiles(PVector position, float angle);

    protected void loadSprites() {
        sBase = staticSprites.get(name + "BaseTR");
        sIdle = staticSprites.get(name + "IdleTR");
        fireFrames = animatedSprites.get(name + "FireTR");
        loadFrames = animatedSprites.get(name + "LoadTR");
        if (animatedSprites.get(name + "IdleTR") != null) {
            idleFrames = animatedSprites.get(name + "IdleTR");
            sIdle = idleFrames[0];
        }
        else idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};
    }

    @Override
    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        updateBoosts();
        if (enemies.size() > 0 && alive && !paused) checkTarget();
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
                && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    @Override
    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        tile.tower = null;
        alive = false;
        updateTowerArray();
        if (selection.turret == this) {
            selection.name = "null";
            inGameGui.flashA = 255;
        }
        else if (!selection.name.equals("null")) selection.swapSelected(selection.turret);
        int moneyGain;
        if (!sold) {
            moneyGain = (int) (value * 0.4);
            tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBreakable(debrisType + "DebrisBr_TL");
        } else moneyGain = (int) (value * 0.8);
        popupTexts.add(new PopupText(p, new PVector(tile.position.x - 25, tile.position.y - 25), moneyGain));
        money += moneyGain;
        updateFlooring();
        connectWallQueues++;
        updateNodes();
    }

    @Override
    public void controlAnimation() {
        if (!paused) {
            if (hp < getMaxHp() && p.random(30) < 1) {
                topParticles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(360), "greyPuff"));
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
                        int newSize = secondsToFrames(getDelay());
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
        displayMain();
    }

    protected void displayMain() {
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

    @Override
    public void displayBase() {
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    @Override
    public void upgrade(int id) {
        upgradeSpecial(id);
        int price = 0;
        if (id == 0) {
            price = upgradePrices[nextLevelA];
            if (price > money) return;
            if (nextLevelA > 2) return;
            if (nextLevelB == 6 && nextLevelA == 2) return;
            nextLevelA++;
        } else if (id == 1) {
            price = upgradePrices[nextLevelB];
            if (price > money) return;
            if (nextLevelB > 5) return;
            if (nextLevelB == 5 && nextLevelA == 3) return;
            nextLevelB++;
        }
        inGameGui.flashA = 255;
        money -= price;
        value += price;
        //icons
        if (nextLevelA < upgradeTitles.length / 2) inGameGui.upgradeIconA.sprite = upgradeIcons[nextLevelA];
        else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];

        playSoundRandomSpeed(p, placeSound, 1);
        spawnParticles();
        //prevent having fire animations longer than delays
        while (getDelay() <= fireFrames.length * betweenFireFrames + idleFrames.length && betweenFireFrames > 0) betweenFireFrames--;
    }

    protected abstract void setUpgrades();

    protected abstract void upgradeSpecial(int id);

    //boosts

    public int boostedDamage() {
        int d = 0;
        for (Booster.Boost boost : boosts) {
            int d2 = (int) (damage * boost.damage);
            if (d2 > d) d = d2;
        }
        return d;
    }

    public int getDamage() {
        return damage + boostedDamage();
    }

    public int boostedRange() {
        int r = 0;
        for (Booster.Boost boost : boosts) {
            int r2 = (int) (range * boost.range);
            if (r2 > r) r = r2;
        }
        return r;
    }

    public int getRange() {
        return range + boostedRange();
    }

    public float boostedFirerate() {
        float f = 0;
        for (Booster.Boost boost : boosts) {
            float f2 = delay * boost.firerate;
            if (f2 > f) f = f2;
        }
        return f;
    }

    public float getDelay() {
        return delay - boostedFirerate();
    }
}