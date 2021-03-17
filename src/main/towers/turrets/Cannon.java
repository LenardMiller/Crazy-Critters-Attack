package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.CannonBall;
import main.projectiles.Dynamite;
import main.projectiles.FragBall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.from60ToFramerate;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Cannon extends Turret {

    private int effectRadius;
    private boolean frags;
    private boolean dynamite;

    public Cannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "cannon";
        size = new PVector(50,50);
        offset = 5;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 4;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 850;
        numFireFrames = 6;
        betweenFireFrames = from60ToFramerate(1);
        numLoadFrames = 18;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 40;
        range = 250;
        effectRadius = 25;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("smallExplosion");
        loadSprites();
        debrisType = "stone";
        price = CANNON_PRICE;
        value = price;
        priority = 0; //close
        fireParticle = "smoke";
        barrelLength = 29;
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    protected void spawnProjectile(PVector position, float angle) {
        if (frags) projectiles.add(new FragBall(p,position.x,position.y, angle, this, damage, effectRadius));
        else if (dynamite) projectiles.add(new Dynamite(p,position.x,position.y, angle, this, damage, effectRadius));
        else projectiles.add(new CannonBall(p,position.x,position.y, angle, this, damage, effectRadius));
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 175;
        upgradePrices[1] = 250;
        upgradePrices[2] = 850;
        upgradePrices[3] = 150;
        upgradePrices[4] = 200;
        upgradePrices[5] = 1000;
        //titles
        upgradeTitles[0] = "Stronger shot";
        upgradeTitles[1] = "Powerful shot";
        upgradeTitles[2] = "Dynamite";
        upgradeTitles[3] = "Extra range";
        upgradeTitles[4] = "Rapid reload";
        upgradeTitles[5] = "Frags";
        //description
        upgradeDescA[0] = "+20";
        upgradeDescB[0] = "damage";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "+30";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Creates";
        upgradeDescB[2] = "huge";
        upgradeDescC[2] = "explosions";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Bursts into";
        upgradeDescB[5] = "shrapnel";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[23];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[24];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    damage += 20;
                    break;
                case 1:
                    damage += 30;
                    break;
                case 2: //todo: aiming is fucked
                    damage += 300;
                    effectRadius = 60;
                    pjSpeed = 10;
                    dynamite = true;
                    fireSound = sounds.get("slingshot");
                    name = "dynamiteLauncher";
                    debrisType = "wood";
                    fireParticle = null;
                    damageSound = sounds.get("woodDamage");
                    breakSound = sounds.get("woodBreak");
                    placeSound = sounds.get("woodPlace");
                    barrelLength = 0;
                    loadSprites();
                    numLoadFrames = 80;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 35;
                    break;
                case 4:
                    delay -= 0.8f;
                    break;
                case 5:
                    range += 30;
                    delay -= 1;
                    frags = true;
                    name = "fragCannon";
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
