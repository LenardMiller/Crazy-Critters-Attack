package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Bolt;
import main.projectiles.ReinforcedBolt;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Crossbow extends Turret {

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
        delay = 4.5f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 1400;
        range = 320;
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 30;
        pierce = 1;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("crossbow");
        loadSprites();
        debrisType = "wood";
        price = CROSSBOW_PRICE;
        value = price;
        priority = 1; //last
        multishot = false;
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    protected void fire(float barrelLength, String particleType) {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
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
        upgradePrices[2] = 750;
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
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[9];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[18];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[19];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    pierce += 2;
                    break;
                case 1:
                    damage += 20;
                    break;
                case 2:
                    damage += 300;
                    pierce += 5;
                    reinforced = true;
                    name = "crossbowReinforced";
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 30;
                    break;
                case 4:
                    delay -= 1.1f;
                    break;
                case 5:
                    multishot = true;
                    name = "crossbowMultishot";
                    fireSound = sounds.get("shotbow");
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}