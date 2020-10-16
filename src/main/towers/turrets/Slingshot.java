package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Gravel;
import main.projectiles.Pebble;
import main.projectiles.Rock;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Slingshot extends Turret {

    boolean painful;
    boolean gravel;

    public Slingshot(PApplet p, Tile tile) {
        super(p,tile);
        name = "slingshot";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 100;
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 12;
        range = 200;
        numFireFrames = 34;
        numLoadFrames = 59;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        damage = 15; //15
        loadSprites();
        debrisType = "wood";
        price = SLINGSHOT_PRICE;
        value = price;
        priority = 0; //first
        painful = false;
        gravel = false;
        setUpgrades();
        updateTowerArray();
    }

    public void fire() { //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        if (painful) projectiles.add(new Rock(p, tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
        if (gravel) {
            float offset = 0.03f;
            int count = 8;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Gravel(p, tile.position.x-size.x/2,tile.position.y-size.y/2, a, this, damage));
                a += offset;
            }
        }
        if (!painful && !gravel) projectiles.add(new Pebble(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 75;
        upgradePrices[2] = 200;
        upgradePrices[3] = 75;
        upgradePrices[4] = 100;
        upgradePrices[5] = 200;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Super Range";
        upgradeTitles[2] = "Gravel Slinger";
        upgradeTitles[3] = "Damage Up";
        upgradeTitles[4] = "Faster Firing";
        upgradeTitles[5] = "Painful Rocks";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "range";

        upgradeDescA[2] = "Shoots";
        upgradeDescB[2] = "gravel at";
        upgradeDescC[2] = "enemies";

        upgradeDescA[3] = "+5";
        upgradeDescB[3] = "damage";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Inflicts";
        upgradeDescB[5] = "bleeding,";
        upgradeDescC[5] = "+50 dmg";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[6];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[4];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[13];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 30;
                    break;
                case 1:
                    range += 40;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    gravel = true;
                    damage -= 10;
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 5;
                    break;
                case 4:
                    delay -= 20;
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    painful = true;
                    damage += 50;
                    delay += 10;
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {}
}
