package main.towers;

import main.misc.Tile;
import main.particles.Debris;
import main.particles.Ouch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.misc.MiscMethods.*;

public abstract class Tower {

    public PApplet p;

    public Tile tile;

    public int damageTotal;
    public int killsTotal;
    public String name;
    public float angle;
    public int delay;
    public float range;
    public PVector size;
    public int maxHp;
    public int hp;
    public int damage;
    public boolean hit;
    public PImage sprite;
    public Object updateSprite;
    protected int tintColor;
    protected String debrisType;
    public int price;
    public int value;
    public boolean turret;
    public boolean visualize;
    public int priority;
    public int nextLevelA;
    public int nextLevelB;
    protected SoundFile damageSound;
    protected SoundFile breakSound;
    protected SoundFile placeSound;

    public int[] upgradePrices;
    public String[] upgradeTitles;
    public String[] upgradeDescA;
    public String[] upgradeDescB;
    public String[] upgradeDescC;
    public PImage[] upgradeIcons;

    public Tower(PApplet p, Tile tile) {
        this.p = p;
        this.tile = tile;
        tiles.get((int)(tile.position.x/50) - 1,(int)(tile.position.y/50) - 1).setBgC(null);

        name = "null";
        size = new PVector(120, 37);
        delay = 0;
        range = 0;
        this.maxHp = 1;
        hp = maxHp;
        hit = false;
        tintColor = 255;
        debrisType = "null";
        price = 0;
        turret = false;
        visualize = false;
        nextLevelB = 0;
        upgradeDescA = new String[4];
        upgradeDescB = new String[4];
        upgradeDescC = new String[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        updateNodes();
        updateTowerArray();
    }

    public void main() {
        if (hp <= 0) die(false);
        value = (int)(((float)hp / (float)maxHp) * price);
        if (turret) {
            if (inputHandler.leftMousePressedPulse && p.mouseX < tile.position.x && p.mouseX > tile.position.x - size.x && p.mouseY < tile.position.y && p.mouseY > tile.position.y - size.y && alive) { //clicked on
                selection.swapSelected(tile.id);
            }
        }
    }

    public void displayPassA() {
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        p.tint(0,60);
        p.image(sprite,tile.position.x-size.x,tile.position.y-size.y);
        p.tint(255,tintColor,tintColor);
        p.image(sprite,tile.position.x-size.x+2,tile.position.y-size.y+2);
        p.tint(255);
        if (tintColor < 255) tintColor += 20;
    }

    public void displayPassB() {}

    public void damage(int dmg) { //if it touches an enemy, animate and loose health
        hp -= dmg;
        hit = true;
        damageSound.play(p.random(0.8f, 1.2f), volume);
        int num = (int)(p.random(1,4));
        for (int i = num; i >= 0; i--){ //spray debris
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void upgrade(int id) {
        nextLevelB++;
        if (nextLevelB < upgradeTitles.length) upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        placeSound.play(p.random(0.8f, 1.2f), volume);
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        updateNodes();
    }

    public void die(boolean sold) {
        breakSound.play(p.random(0.8f, 1.2f), volume);
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--) {
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        tile.tower = null;
        updateTowerArray();
        if (selection.id == tile.id) {
            selection.name = "null";
            inGameGui.flashA = 255;
        }
        else if (!selection.name.equals("null")) selection.swapSelected(selection.tower.tile.id);
        if (!sold) tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBgC(debrisType + "DebrisBGC_TL");
        if (!turret) {
            updateWallTiles();
            connectWallQueues++;
        }
        updateNodes();
    }

    public void heal() {
        if (hp < maxHp) {
            for (int i = 0; i < 5; i++) {
                particles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x), p.random(tile.position.y - size.y, tile.position.y), p.random(0, 360), "greenPuff"));
            }
        }
        hp = maxHp;
    }

    public void repair() {
        placeSound.play(p.random(0.8f, 1.2f), volume);
        money -= ceil((float)(price) - (float)(value));
        heal();
    }

    public void sell() {
        money += (int) (value * .8);
        die(true);
    }

    public abstract void updateSprite();
}
