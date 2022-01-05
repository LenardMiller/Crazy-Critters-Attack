package main.towers;

import main.damagingThings.arcs.OrangeArc;
import main.damagingThings.arcs.RedArc;
import main.damagingThings.projectiles.Flame;
import main.damagingThings.shockwaves.FireShockwave;
import main.damagingThings.shockwaves.NuclearShockwave;
import main.gui.InGameGui;
import main.gui.guiObjects.PopupText;
import main.misc.Tile;
import main.particles.Debris;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.turrets.Booster;
import main.towers.turrets.Turret;
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
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

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
    protected String material;
    protected SoundFile damageSound;
    protected SoundFile breakSound;
    protected SoundFile placeSound;
    protected ArrayList<Booster.Boost> boosts;

    protected Tower(PApplet p, Tile tile) {
        this.p = p;
        this.tile = tile;
        Tile otherTile = tiles.get((int)(tile.position.x/50) - 1,(int)(tile.position.y/50) - 1);
        if (otherTile != null) otherTile.setBreakable(null);

        boosts = new ArrayList<>();
        alive = true;
        name = "null";
        size = new PVector(120, 37);
        this.maxHp = 1;
        hp = maxHp;
        hit = false;
        tintColor = 255;
        material = "wood";
        price = 0;
        visualize = false;
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        updateCombatPoints();
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
            topParticles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), material));
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
            tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBreakable(material + "DebrisBr_TL");
        } else moneyGain = (int) (value * 0.8);
        if (moneyGain > 0) popupTexts.add(new PopupText(p, new PVector(tile.position.x - 25, tile.position.y - 25), moneyGain));
        money += moneyGain;
        if (hasBoostedDeathEffect()) deathEffect();
        updateFlooring();
        updateTowerArray();
        updateCombatPoints();
    }

    protected void deathEffect() {
        int radius = value / 10;
        if (radius < 40) radius = 40;
        float x = tile.position.x - (TILE_SIZE / 2f);
        float y = tile.position.y - (TILE_SIZE / 2f);
        shockwaves.add(new FireShockwave(p, x, y, 0, radius * 2, value, null, 15,
          30));
        shockwaves.add(new NuclearShockwave(p, x, y, radius, value, null));
        for (int i = 0; i < radius / 5; i++) {
            projectiles.add(new Flame(p, x, y, p.random(360), null, value / 8, value / 8f,
              value / 8f, (int) p.random(radius, radius * 2), true));
        }
        int arcCount = 3;
        if (radius > 300) arcCount = 8;
        if (radius > 600) arcCount = 16;
        for (int i = 0; i < arcCount; i++) {
            arcs.add(new OrangeArc(p, x, y, null, value / 8, radius / 10, radius * 2,
              -1, 50));
        }
        for (int i = 0; i < arcCount / 3; i++) {
            arcs.add(new RedArc(p, x, y, null, value / 8, radius / 10, radius * 2,
              -1));
        }
        if (radius > 200) playSoundRandomSpeed(p, sounds.get("hugeExplosion"), 1);
        else playSoundRandomSpeed(p, sounds.get("smallExplosion"), 1);
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
            topParticles.add(new Debris(p,spawnPos.x, spawnPos.y, p.random(360), material));
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
            if (hasBoostedDeathEffect()) {
                topParticles.add(new MiscParticle(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(360), "fire"));
            }
        }
    }

    public int boostedMaxHp() {
        int h = 0;
        for (Booster.Boost boost : boosts) {
            float amount = boost.health;
            if (this instanceof Turret) amount *= 2;
            int h2 = (int) (maxHp * amount);
            if (h2 > h) h = h2;
        }
        return h;
    }

    public int getMaxHp() {
        return maxHp + boostedMaxHp();
    }

    public boolean hasBoostedDeathEffect() {
        if (name.equals("explosiveBooster")) return true;
        for (Booster.Boost boost : boosts) {
            if (boost.deathEffect) return true;
        }
        return false;
    }
}
