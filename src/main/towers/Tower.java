package main.towers;

import main.Main;
import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public abstract class Tower {

    public PApplet p;

    public String name;
    public float angle;
    public int delay;
    public float error;
    public PVector position;
    public PVector size;
    public int maxHp;
    public int twHp;
    public int damage;
    public boolean hit;
    public PImage sprite;
    int barTrans;
    protected int tintColor;
    protected String debrisType;
    public int price;
    public int value;
    public boolean turret;
    public boolean visualize;
    public int priority;
    public int nextLevelZero;
    public int nextLevelOne;

    protected boolean[] upgradeSpecial;
    public int[] upgradePrices;
    protected int[] upgradeHealth;
    protected int[] upgradeDamage;
    protected int[] upgradeDelay;
    protected float[] upgradeError;
    public String[] upgradeNames;
    protected String[] upgradeDebris;
    public String[] upgradeTitles;
    public String[] upgradeDescOne;
    public String[] upgradeDescTwo;
    public String[] upgradeDescThree;
    public PImage[] upgradeIcons;
    protected PImage[] upgradeSprites;

    public Tower(PApplet p, float x, float y) {
        this.p = p;

        name = "null";
        position = new PVector(x, y);
        size = new PVector(120, 37);
        delay = 0;
        error = 0;
        this.maxHp = 1;
        twHp = maxHp;
        hit = false;
        sprite = spritesH.get("nullWallTW");
        barTrans = 255;
        tintColor = 255;
        debrisType = "null";
        price = 0;
        turret = false;
        visualize = false;
        nextLevelOne = 2;
        upgradeSpecial = new boolean[4];
        upgradeDamage = new int[4];
        upgradeDelay = new int[4];
        upgradePrices = new int[4];
        upgradeHealth = new int[4];
        upgradeError = new float[4];
        upgradeNames = new String[4];
        upgradeDescOne = new String[4];
        upgradeDescTwo = new String[4];
        upgradeDescThree = new String[4];
        upgradeDebris = new String[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        upgradeSprites = new PImage[4];
        setUpgrades();
    }

    private void setUpgrades(){
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
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 225;
        upgradePrices[3] = 500;
        //heath
        upgradeHealth[0] = 75;
        upgradeHealth[1] = 125;
        upgradeHealth[2] = 250;
        upgradeHealth[3] = 500;
        //error (accuracy)
        upgradeError[0] = 0;
        upgradeError[1] = 0;
        upgradeError[2] = 0;
        upgradeError[3] = 0;
        //names
        upgradeNames[0] = "stoneWall";
        upgradeNames[1] = "metalWall";
        upgradeNames[2] = "crystalWall";
        upgradeNames[3] = "ultimateWall";
        //debris
        upgradeDebris[0] = "stone";
        upgradeDebris[1] = "metal";
        upgradeDebris[2] = "crystal";
        upgradeDebris[3] = "dev";
        //titles
        upgradeTitles[0] = "stone";
        upgradeTitles[1] = "metal";
        upgradeTitles[2] = "crystal";
        upgradeTitles[3] = "ultimate";
        //desc line one
        upgradeDescOne[0] = "+75 HP";
        upgradeDescOne[1] = "+100 HP";
        upgradeDescOne[2] = "+225 HP";
        upgradeDescOne[3] = "+500 HP";
        //desc line two
        upgradeDescTwo[0] = "";
        upgradeDescTwo[1] = "";
        upgradeDescTwo[2] = "";
        upgradeDescTwo[3] = "";
        //desc line three
        upgradeDescThree[0] = "";
        upgradeDescThree[1] = "";
        upgradeDescThree[2] = "";
        upgradeDescThree[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[1];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[2];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[3];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[4];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("crystalWallTW");
        upgradeSprites[3] = spritesH.get("ultimateWallTW");
    }

    public void main(ArrayList<Tower> towers, int i){
        if (twHp <= 0){
            die(i);
            towers.remove(i);
            path.nodeCheckObs();
        }
        value = (twHp / maxHp) * price;
        if (p.mousePressed && p.mouseX < position.x && p.mouseX > position.x-size.x && p.mouseY < position.y && p.mouseY > position.y-size.y && Main.alive){ //clicked on
            selection.swapSelected(i);
        }
        display();
    }

    private void display() {
        if (hit){ //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        p.tint(255,tintColor,tintColor);
        p.image(sprite,position.x-size.x,position.y-size.y);
        p.tint(255);
        if (twHp > 0){ //no inverted bars
            HpBar();
        }
        if (tintColor < 255){
            tintColor += 20;
        }
    }

    public void collideEN(int dangerLevel) { //if it touches an enemy, animate and loose health
        twHp -= dangerLevel;
        hit = true;
        barTrans = 255;
        int num = (int)(p.random(1,4));
        for (int i = num; i >= 0; i--){ //spray debris
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void upgrade(int id) { //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        price += upgradePrices[nextLevelOne];
        maxHp += upgradeHealth[nextLevelOne];
        twHp += upgradeHealth[nextLevelOne];
        name = upgradeNames[nextLevelOne];
        debrisType = upgradeDebris[nextLevelOne];
        sprite = upgradeSprites[nextLevelOne];
        nextLevelOne++;
        if (nextLevelOne < upgradeNames.length){
            upgradeGuiObjectOne.sprite = upgradeIcons[nextLevelOne];
        }
        else{
            upgradeGuiObjectOne.sprite = spritesAnimH.get("upgradeIC")[0];
        }
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        path.nodeCheckObs();
    }

    protected void die(int i){
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        if (selection.id > i){
            selection.id--;
        }
        if (selection.id == i){
            selection.id = 0;
        }
    }

    private void HpText(){ //displays the towers health
        p.text(twHp, position.x-size.x/2, position.y + size.y/4);
    }

    protected void HpBar(){ //displays the towers health with style
        p.fill(0,255,0,barTrans);
        if (barTrans > 0 && twHp > maxHp/2){ //after hit or if below 50%
            barTrans--;
        }
        p.noStroke();
        p.rect(position.x-size.x, position.y + size.y/4, (size.x)*(((float) twHp)/((float) maxHp)), -6);
    }
}
