package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.MagicMissile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class MagicMissileer extends Turret {

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

    @Override
    protected void checkTarget() {
        getTargetEnemy();
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire();
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 0,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 1,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 2,tile.position));
        if (name.equals("magicMissleerFour")) {
            projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, (int)(p.random(0,2.99f)),tile.position));
        }
    }

    protected void fire() {
        spawnProjectiles(new PVector(0,0), angle);
    }

    @Override
    public void displayMain() {
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
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 200;
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

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }
}