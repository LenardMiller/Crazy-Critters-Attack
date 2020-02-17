package main.towers;

import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateNodes;
import static main.misc.MiscMethods.updateTowerArray;

public abstract class Tower {

    public PApplet p;

    public Tile tile;

    public int damageTotal;
    public int killsTotal;
    public String name;
    public float angle;
    public int delay;
    public float error;
    public PVector size;
    public int maxHp;
    public int hp;
    public int damage;
    public boolean hit;
    public PImage sprite;
    public int barTrans;
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

    protected boolean[] upgradeSpecial;
    public int[] upgradePrices;
    public int[] upgradeHealth;
    protected int[] upgradeDamage;
    protected int[] upgradeDelay;
    protected float[] upgradeError;
    public String[] upgradeNames;
    protected String[] upgradeDebris;
    public String[] upgradeTitles;
    public String[] upgradeDescA;
    public String[] upgradeDescB;
    public String[] upgradeDescC;
    public PImage[] upgradeIcons;
    public PImage[] upgradeSprites;

    public Tower(PApplet p, Tile tile) {
        this.p = p;
        this.tile = tile;

        name = "null";
        size = new PVector(120, 37);
        delay = 0;
        error = 0;
        this.maxHp = 1;
        hp = maxHp;
        hit = false;
        barTrans = 255;
        tintColor = 255;
        debrisType = "null";
        price = 0;
        turret = false;
        visualize = false;
        nextLevelB = 2;
        upgradeSpecial = new boolean[4];
        upgradeDamage = new int[4];
        upgradeDelay = new int[4];
        upgradePrices = new int[4];
        upgradeHealth = new int[4];
        upgradeError = new float[4];
        upgradeNames = new String[4];
        upgradeDescA = new String[4];
        upgradeDescB = new String[4];
        upgradeDescC = new String[4];
        upgradeDebris = new String[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        upgradeSprites = new PImage[4];
        updateNodes();
        updateTowerArray();
    }

    public void main(){
        if (hp <= 0) die();
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

    public void damage(int dangerLevel) { //if it touches an enemy, animate and loose health
        hp -= dangerLevel;
        hit = true;
        barTrans = 255;
        int num = (int)(p.random(1,4));
        for (int i = num; i >= 0; i--){ //spray debris
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void upgrade(int id) {
        price += upgradePrices[nextLevelB];
        maxHp += upgradeHealth[nextLevelB];
        hp += upgradeHealth[nextLevelB];
        name = upgradeNames[nextLevelB];
        debrisType = upgradeDebris[nextLevelB];
        sprite = upgradeSprites[nextLevelB];
        nextLevelB++;
        if (nextLevelB < upgradeNames.length) upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        updateNodes();
    }

    public void die() {
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        tile.tower = null;
        updateTowerArray();
        if (selection.id == tile.id) selection.name = "null";
        else if (!selection.name.equals("null")) selection.swapSelected(selection.tower.tile.id);
        updateNodes();
    }

    public void repair() {
        money -= ceil((float)(price) - (float)(value));
        hp = maxHp;
    }

    public void sell() {
        money += (int) (value * .8);
        die();
    }

    public abstract void updateSprite();
}
