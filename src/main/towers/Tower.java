package main.towers;

import main.misc.Tile;
import main.particles.Debris;
import main.particles.Ouch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateFlooring;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public abstract class Tower {

    public int maxHp;
    public int hp;
    public int nextLevelA;
    public int nextLevelB;
    public int price;
    public int value;
    public int[] upgradePrices;
    public boolean hit;
    public boolean visualize;
    public PVector size;
    public PApplet p;
    public Tile tile;
    public Object updateSprite;
    public PImage sprite;
    public String name;
    public String[] upgradeTitles;
    public PImage[] upgradeIcons;

    protected int tintColor;
    protected int barAlpha;
    protected String debrisType;
    protected SoundFile damageSound;
    protected SoundFile breakSound;
    protected SoundFile placeSound;

    protected Tower(PApplet p, Tile tile) {
        this.p = p;
        this.tile = tile;
        tiles.get((int)(tile.position.x/50) - 1,(int)(tile.position.y/50) - 1).setBreakable(null);

        name = "null";
        size = new PVector(120, 37);
        this.maxHp = 1;
        hp = maxHp;
        hit = false;
        tintColor = 255;
        debrisType = "wood";
        price = 0;
        visualize = false;
        nextLevelB = 0;
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        updateNodes();
        updateTowerArray();
    }

    public void main() {
        if (hp <= 0) die(false);
        value = (int)(((float)hp / (float)maxHp) * price);
    }

    public void displayBase() {
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        p.tint(0,60);
        p.image(sprite,tile.position.x-size.x,tile.position.y-size.y);
        p.tint(255,tintColor,tintColor);
        p.image(sprite,tile.position.x-size.x+2,tile.position.y-size.y+2);
        p.tint(255);
        if (tintColor < 255 && !paused) tintColor += 20;
    }

    public void controlAnimation() {}

    public void displayHpBar() {
        p.fill(new Color(0, 255, 0).getRGB(), barAlpha);
        float barWidth = size.x * (hp / (float) maxHp);
        if (hp > 0) p.rect(tile.position.x - size.x, tile.position.y, barWidth, -5);
        barAlpha = (int) incrementByTo(barAlpha, 3, 0);
    }

    public void refreshHpBar() {
        barAlpha = 255;
    }

    /**
     * If it touches an enemy, animate and loose health.
     * @param damage amount of damage taken
     */
    public void damage(int damage) {
        refreshHpBar();
        hp -= damage;
        hit = true;
        playSoundRandomSpeed(p, damageSound, 1);
        int num = (int)(p.random(4,10));
        for (int i = num; i >= 0; i--){ //spray debris
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public void upgrade(int id) {
        nextLevelB++;
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];
        playSoundRandomSpeed(p, placeSound, 1);
        spawnParticles();
        updateNodes();
    }

    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        tile.tower = null;
        if (selection.turret == this) {
            selection.name = "null";
            inGameGui.flashA = 255;
        }
        else if (!selection.name.equals("null")) selection.swapSelected(selection.turret);
        if (!sold) tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBreakable(debrisType + "DebrisBGC_TL");
        updateFlooring();
        updateTowerArray();
        updateNodes();
    }

    public void heal(float relativeAmount) {
        if (hp < maxHp) {
            refreshHpBar();
            for (int i = 0; i < 5; i++) {
                particles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x), p.random(tile.position.y - size.y, tile.position.y), p.random(0, 360), "greenPuff"));
            }
        }
        hp += relativeAmount*maxHp;
        if (hp > maxHp) hp = maxHp;
    }

    public void sell() {
        money += (int) (value * .8);
        die(true);
    }

    public abstract void updateSprite();

    protected void spawnParticles() {
        PVector center = new PVector(tile.position.x-size.x/2, tile.position.y-size.y/2);
        int num = (int) p.random(30,50); //shower debris
        for (int j = num; j >= 0; j--) {
            PVector deviation = new PVector(p.random(-size.x/2,size.x/2), p.random(-size.y/2,size.y/2));
            PVector spawnPos = PVector.add(center, deviation);
            particles.add(new Debris(p,spawnPos.x, spawnPos.y, p.random(360), debrisType));
        }
        num = (int) p.random(6, 12);
        for (int k = num; k >= 0; k--) {
            PVector deviation = new PVector(p.random(-size.x/2,size.x/2), p.random(-size.y/2,size.y/2));
            PVector spawnPos = PVector.add(center, deviation);
            particles.add(new Ouch(p, spawnPos.x, spawnPos.y, p.random(360), "greyPuff"));
        }
    }
}
