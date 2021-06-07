package main.towers;

import main.gui.InGameGui;
import main.gui.guiObjects.PopupText;
import main.misc.Tile;
import main.particles.Debris;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Booster;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.incrementByTo;
import static main.misc.WallSpecialVisuals.updateFlooring;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;
import static main.sound.SoundUtlities.playSoundRandomSpeed;

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
    public boolean alive;
    public PVector size;
    public PApplet p;
    public Tile tile;
    public PImage sprite;
    public String name;
    public String[] upgradeTitles;
    public PImage[] upgradeIcons;

    protected int tintColor;
    protected int barAlpha;
    protected int boostTimer;
    protected String debrisType;
    protected SoundFile damageSound;
    protected SoundFile breakSound;
    protected SoundFile placeSound;
    protected ArrayList<Booster.Boost> boosts;

    protected Tower(PApplet p, Tile tile) {
        this.p = p;
        this.tile = tile;
        tiles.get((int)(tile.position.x/50) - 1,(int)(tile.position.y/50) - 1).setBreakable(null);

        boosts = new ArrayList<>();
        alive = true;
        name = "null";
        size = new PVector(120, 37);
        this.maxHp = 1;
        hp = maxHp;
        hit = false;
        tintColor = 255;
        debrisType = "wood";
        price = 0;
        visualize = false;
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        updateNodes();
        updateTowerArray();
    }

    public abstract void main();

    public abstract void displayBase();

    public abstract void controlAnimation();

    public PVector getCenter() {
        return new PVector(tile.position.x - (size.x / 2), tile.position.y - (size.y / 2));
    }

    public void displayHpBar() {
        Color barColor = new Color(0, 255, 0);
        if (boostedMaxHp() > 0) barColor = InGameGui.BOOSTED_TEXT_COLOR;
        p.stroke(barColor.getRGB(), barAlpha / 2f);
        float barWidth = size.x * (hp / (float) getMaxHp());
        p.noFill();
        p.rect(tile.position.x - size.x, tile.position.y, size.y, -5);
        p.fill(barColor.getRGB(), barAlpha);
        if (hp > 0) p.rect(tile.position.x - size.x, tile.position.y, barWidth, -5);
        if (hp == getMaxHp()) barAlpha = (int) incrementByTo(barAlpha, 3, 0);
        else refreshHpBar();
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
            topParticles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    public abstract void upgrade(int id);

    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        alive = false;
        tile.tower = null;
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
        if (moneyGain > 0) popupTexts.add(new PopupText(p, new PVector(tile.position.x - 25, tile.position.y - 25), moneyGain));
        money += moneyGain;
        updateFlooring();
        updateTowerArray();
        updateNodes();
    }

    public void heal(float relativeAmount) {
        if (hp < getMaxHp()) {
            refreshHpBar();
            for (int i = 0; i < 5; i++) {
                topParticles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x), p.random(tile.position.y - size.y, tile.position.y), p.random(0, 360), "greenPuff"));
            }
        }
        hp += relativeAmount * getMaxHp();
        if (hp > getMaxHp()) hp = getMaxHp();
    }

    public void updateSprite() {}

    protected void spawnParticles() {
        PVector center = new PVector(tile.position.x-size.x/2, tile.position.y-size.y/2);
        int num = (int) p.random(30,50); //shower debris
        for (int j = num; j >= 0; j--) {
            PVector deviation = new PVector(p.random(-size.x/2,size.x/2), p.random(-size.y/2,size.y/2));
            PVector spawnPos = PVector.add(center, deviation);
            topParticles.add(new Debris(p,spawnPos.x, spawnPos.y, p.random(360), debrisType));
        }
        num = (int) p.random(6, 12);
        for (int k = num; k >= 0; k--) {
            PVector deviation = new PVector(p.random(-size.x/2,size.x/2), p.random(-size.y/2,size.y/2));
            PVector spawnPos = PVector.add(center, deviation);
            topParticles.add(new Ouch(p, spawnPos.x, spawnPos.y, p.random(360), "greyPuff"));
        }
    }

    //boosts

    public void addBoost(Booster.Boost boost) {
        boostTimer = 0;
        for (Booster.Boost b : boosts) if (b == boost) return;
        boosts.add(boost);
    }

    protected void updateBoosts() {
        if (!paused) {
            displayBoost();
            boostTimer++;
            if (boostTimer > FRAMERATE / 3) boosts = new ArrayList<>();
        }
    }

    protected void displayBoost() {
        if (this instanceof IceWall) return;
        if (boosts.size() > 0 && p.random(30) < 1) {
            topParticles.add(new MiscParticle(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(360), "orangeMagic"));
        }
    }

    public int boostedMaxHp() {
        int h = 0;
        for (Booster.Boost boost : boosts) {
            int h2 = (int) (maxHp * boost.health);
            if (h2 > h) h = h2;
        }
        return h;
    }

    public int getMaxHp() {
        return maxHp + boostedMaxHp();
    }
}
