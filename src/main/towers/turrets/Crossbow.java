package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Bolt;
import main.projectiles.ReinforcedBolt;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateTowerArray;

public class Crossbow extends Turret {

    private int pierce;
    private boolean multishot;
    private boolean reinforced;

    public Crossbow(PApplet p, Tile tile) {
        super(p,tile);
        offset = 2;
        name = "crossbow";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 270;
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        delayTime = delay;
        pjSpeed = 24;
        range = 320;
        numFireFrames = 13;
        numLoadFrames = 81;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 30;
        pierce = 2;
        damageSound = soundsH.get("woodDamage");
        breakSound = soundsH.get("woodBreak");
        loadSprites();
        debrisType = "wood";
        price = CROSSBOW_PRICE;
        value = price;
        priority = 1; //last
        multishot = false;
        setUpgrades();
        updateTowerArray();
    }

    public void fire(){ //needed to change projectile fired
        delayTime = p.frameCount + delay; //waits this time before firing
        if (multishot) {
            float offset = 0.07f;
            int count = 7;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Bolt(p,tile.position.x-size.x/2,tile.position.y-size.y/2, a, this, damage, pierce));
                a += offset;
            }
        } else {
            if (reinforced) projectiles.add(new ReinforcedBolt(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage, pierce));
            else projectiles.add(new Bolt(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage, pierce));
        }
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 75;
        upgradePrices[1] = 175;
        upgradePrices[2] = 850;
        upgradePrices[3] = 100;
        upgradePrices[4] = 150;
        upgradePrices[5] = 650;
        //titles
        upgradeTitles[0] = "Pointier";
        upgradeTitles[1] = "Sharper";
        upgradeTitles[2] = "Reinforced";
        upgradeTitles[3] = "Increase range";
        upgradeTitles[4] = "Faster Firing";
        upgradeTitles[5] = "Barrage";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "piercing";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "+20";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "+300";
        upgradeDescB[2] = "damage,";
        upgradeDescC[2] = "+piercing";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Fire";
        upgradeDescB[5] = "multiple";
        upgradeDescC[5] = "bolts";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[9];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[18];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[10];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[19];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    pierce += 2;
                    break;
                case 1:
                    damage += 20;
                    if (nextLevelB > 5) nextLevelA++;
                    break;
                case 2:
                    damage += 300;
                    pierce += 100;
                    reinforced = true;
                    name = "crossbowReinforced";
                    loadSprites();
                    if (nextLevelB == 5) nextLevelB++;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 30;
                    break;
                case 4:
                    delay -= 70;
                    if (nextLevelA > 2) nextLevelB++;
                    break;
                case 5:
                    multishot = true;
                    name = "crossbowMultishot";
                    loadSprites();
                    if (nextLevelA == 2) nextLevelA++;
                    break;
            }
        }
    }

    public void updateSprite() {};
}