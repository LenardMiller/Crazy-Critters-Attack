package main.towers.turrets;

import main.enemies.Enemy;
import main.particles.Debris;
import main.towers.Tile;
import main.towers.Tower;
import main.util.CompressArray;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public abstract class Turret extends Tower {

    PImage sBase;
    PImage sIdle;
    public int delayTime;
    int pjSpeed;
    int numFireFrames;
    int numLoadFrames;
    PImage[] fireFrames;
    PImage[] loadFrames;
    int spriteType;
    int frame;
    float loadDelay;
    float loadDelayTime;
    private ArrayList<Integer> spriteArray;

    Turret(PApplet p, Tile tile) {
        super(p, tile);
        this.p = p;
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
        debrisType = null;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteArray = new ArrayList<>();
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        turret = true;
        loadSprites();
        upgradeSpecial = new boolean[4];
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
        if (priority == 0) { //first
            aim(enemyTracker.firstPos, tile.position, enemyTracker.firstId);
        } else if (priority == 1) { //last
            aim(enemyTracker.lastPos, tile.position, enemyTracker.lastId);
        } else if (priority == 2) { //strong
            aim(enemyTracker.strongPos, tile.position, enemyTracker.strongId);
        } else { //first, placeholder for close
            aim(enemyTracker.firstPos, tile.position, enemyTracker.firstId);
        }
        if (frame == 0 && spriteType == 0) { //if done animating
            spriteType = 1;
            frame = 0;
            fire();
        }
    }

    private void aim(PVector target, PVector position, int id) {
        Enemy enemy = enemies.get(id);
        PVector e = PVector.div(enemy.size, 2);
        target = PVector.add(target, e);
        PVector d = PVector.sub(target, position); //finds distance to enemy
        PVector t = PVector.div(d, pjSpeed); //finds time to hit
        target = new PVector(target.x, target.y + (t.mag() * enemy.speed)); //leads shots
        PVector ratio = PVector.sub(target, position);
//        angle = findAngle(position,target);
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
            if (position.x < target.x && position.y > target.y) { //if to left and below NOT WORKING
                angle = (atan(abs(ratio.x + 15) / abs(ratio.y)));
            } else if (position.x < target.x && position.y < target.y) { //if to left and above
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + HALF_PI;
            } else if (position.x > target.x && position.y < target.y) { //if to right and above NOT WORKING
                angle = (atan(abs(ratio.x + 15) / abs(ratio.y))) + PI;
            } else if (position.x > target.x && position.y > target.y) { //if to right and below
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + 3 * HALF_PI;
            }
        }
        if (visualize) { //cool lines
            p.stroke(255);
            p.line(position.x - size.x / 2, position.y - size.y / 2, target.x - enemy.size.x / 2, target.y - enemy.size.y / 2);
            p.stroke(255, 0, 0, 150);
            p.line(target.x - enemy.size.x / 2, p.height, target.x - enemy.size.x / 2, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y - enemy.size.y / 2, 0, target.y - enemy.size.y / 2);
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
    }


    public void main() { //need to check target
        if (hp <= 0) {
            die();
            tile.tower = null;
        }
        if (enemies.size() > 0 && alive) {
            checkTarget();
        }
        if (p.mousePressed && p.mouseX < tile.position.x && p.mouseX > tile.position.x - size.x && p.mouseY < tile.position.y && p.mouseY > tile.position.y - size.y && alive) {
            selection.swapSelected(tile.id);
        }
        preDisplay();
    }

    private void preDisplay() {
        if (tintColor < 255) {
            tintColor += 20;
        }
        if (spriteType == 0) { //idle
            sprite = sIdle;
        } else if (spriteType == 1) { //fire
            if (frame < numFireFrames - 1) { //if not done, keep going
                frame++;

                sprite = fireFrames[frame];
            } else { //if done, switch to load
                if (numLoadFrames > 0) {
                    ArrayList<Integer> oldArray = new ArrayList<>();
                    int oldSize = numLoadFrames;
                    int newSize = (delayTime - p.frameCount);
                    spriteArray = new ArrayList<>();
                    for (int i = 0; i < oldSize; i++) {
                        oldArray.add(i);
                    }
                    for (int i = 0; i < oldSize; i++) {
                        spriteArray.add(i);
                    }
                    int count = 0;
                    while (spriteArray.size() != newSize) {
                        count++;
                        compress = new CompressArray(spriteArray.size(), newSize, count, oldArray, spriteArray);
                        compress.main();
                    }
                }
                frame = 0;
                spriteType = 2;
                //println();
                //println(spriteArray.size()+"<-"+oldSize);
            }
        } else if (spriteType == 2) { //load
            frame++;
            if (frame < spriteArray.size()) {
                sprite = loadFrames[spriteArray.get(frame)];
                //print(spriteArray.get(frame)+", ");
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
        if (hp > 0) {
            hpBar();
        }
        display();
    }

    public void display() {
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        p.tint(255, 255, 255);
    }

    public void upgrade(int id) {
        int nextLevel;
        if (id == 0) {
            nextLevel = nextLevelA;
        } else {
            nextLevel = nextLevelB;
        }
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
        if (id == 0) {
            nextLevelA++;
        } else if (id == 1) {
            nextLevelB++;
        }
        if (id == 0) {
            if (nextLevelA < upgradeNames.length / 2) {
                upgradeIconA.sprite = upgradeIcons[nextLevelA];
            } else {
                upgradeIconA.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        if (id == 1) {
            if (nextLevelB < upgradeNames.length) {
                upgradeIconB.sprite = upgradeIcons[nextLevelB];
            } else {
                upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
            }
        }
        int num = (int) (p.random(30, 50)); //shower debris
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p, (tile.position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (tile.position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debrisType));
        }
    }
}  