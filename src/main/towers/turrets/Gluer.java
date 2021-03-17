package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.projectiles.Glue;
import main.projectiles.SpikeyGlue;
import main.projectiles.SplatterGlue;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Gluer extends Turret {

    boolean spikey;
    boolean splatter;

    public Gluer(PApplet p, Tile tile) {
        super(p,tile);
        name = "gluer";
        size = new PVector(50,50);
        offset = 0;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 2.5f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 400;
        numFireFrames = 5;
        betweenFireFrames = down60ToFramerate(1);
        numLoadFrames = 7;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 0;
        range = 300;
        effectDuration = 3;
        effectLevel = 0.7f;
        spikey = false;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("glueFire");
        fireParticle = "glue";
        barrelLength = 28;
        loadSprites();
        debrisType = "stone";
        price = GLUER_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    protected void spawnProjectile(PVector position, float angle) {
        if (spikey) projectiles.add(new SpikeyGlue(p,position.x,position.y, angle, this, damage, effectLevel, effectDuration));
        else if (splatter) projectiles.add(new SplatterGlue(p,position.x,position.y, angle, this, damage, effectLevel, effectDuration));
        else projectiles.add(new Glue(p,position.x,position.y, angle, this, damage, effectLevel, effectDuration));
    }

    protected void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float finalDist;
        if (priority == 0) finalDist = 1000000;
        else finalDist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            float newSpeed = enemy.maxSpeed * effectLevel;
            if (!enemy.stealthMode && enemy.speed > newSpeed) { //make sure effect would actually slow down enemy
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900 && dist < range) {
                    if (priority == 0 && dist < finalDist) { //close
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 1 && dist > finalDist) { //far
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 2) {
                        if (enemy.maxHp > maxHp) { //strong
                            e = enemy;
                            finalDist = dist;
                            maxHp = enemy.maxHp;
                        } else if (enemy.maxHp == maxHp && dist < finalDist) { //strong -> close
                            e = enemy;
                            finalDist = dist;
                        }
                    }
                }
            }
        }
        targetEnemy = e;
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 125;
        upgradePrices[1] = 150;
        upgradePrices[2] = 500;

        upgradePrices[3] = 150;
        upgradePrices[4] = 150;
        upgradePrices[5] = 700;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Long Glue";
        upgradeTitles[2] = "Glue Splash";

        upgradeTitles[3] = "Gluier Glue";
        upgradeTitles[4] = "Hard Glue";
        upgradeTitles[5] = "Spikey Glue";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "glue";
        upgradeDescC[1] = "duration";

        upgradeDescA[2] = "Glue";
        upgradeDescB[2] = "splatters";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "glue";
        upgradeDescC[3] = "strength";

        upgradeDescA[4] = "Glue";
        upgradeDescB[4] = "does";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "Release";
        upgradeDescB[5] = "spikes on";
        upgradeDescC[5] = "death";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[25];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[28];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[27];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[26];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 30;
                    break;
                case 1:
                    effectDuration += 1;
                    break;
                case 2:
                    effectDuration += 1;
                    range += 30;
                    splatter = true;
                    name = "splashGluer";
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    effectLevel = 0.6f;
                    break;
                case 4:
                    damage = 35;
                    break;
                case 5:
                    damage += 65;
                    effectLevel = 0.5f;
                    spikey = true;
                    name = "shatterGluer";
                    debrisType = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}
