package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.MagicMissile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class MagicMissileer extends Turret{

    public MagicMissileer(PApplet p, Tile tile) {
        super(p,tile);
        name = "magicMissleer";
        size = new PVector(50,50);
        hasPriority = false;
        maxHp = 20;
        hp = maxHp;
        delay = 3.3f; //200 frames
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        damage = 15;
        pjSpeed = 300;
        range = 0; //0 degrees
        betweenIdleFrames = down60ToFramerate(8);
        state = 0;
        loadSprites();
        debrisType = "crystal";
        price = 300;
        value = price;
        priority = 2; //strong
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire();
        }
    }

    protected void fire() {
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 0,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 1,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 2,tile.position));
        if (name.equals("magicMissleerFour")) {
            projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, (int)(p.random(0,2.99f)),tile.position));
        }
    }

    public void displayPassB2() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(fireFrames[0],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void setUpgrades() {
//        //damage
//        upgradeDamage[0] = 10;
//        upgradeDamage[1] = 10;
//        upgradeDamage[2] = 0;
//        upgradeDamage[3] = 0;
//        //delay (firerate)
//        upgradeDelay[0] = 0;
//        upgradeDelay[1] = 0;
//        upgradeDelay[2] = -45;
//        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 200;
//        //names
//        upgradeNames[0] = name;
//        upgradeNames[1] = name;
//        upgradeNames[2] = name;
//        upgradeNames[3] = "magicMissleerFour";
        //titles
        upgradeTitles[0] = "Magic Power";
        upgradeTitles[1] = "Mega Magic";
        upgradeTitles[2] = "Faster Firing";
        upgradeTitles[3] = "More Missiles";
        //desc line one
        upgradeDescA[0] = "+10";
        upgradeDescA[1] = "+10";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "+1 Missile";
        //desc line two
        upgradeDescB[0] = "damage";
        upgradeDescB[1] = "damage";
        upgradeDescB[2] = "firerate";
        upgradeDescB[3] = "";
        //desc line three
        upgradeDescC[0] = "per missile";
        upgradeDescC[1] = "per missile";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[14];
    }

    protected void upgradeSpecial() {}

    public void updateSprite() {}
}