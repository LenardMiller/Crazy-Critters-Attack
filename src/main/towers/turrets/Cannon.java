package main.towers.turrets;

import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.CannonBall;
import main.projectiles.Dynamite;
import main.projectiles.FragBall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

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
        delay = 230;
        delay += (round(p.random(-(delay/10f),delay/10f))); //injects 10% randomness so all don't fire at once
        pjSpeed = 14;
        numFireFrames = 6;
        betweenFireFrames = 1;
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
        damageSound = soundsH.get("stoneDamage");
        breakSound = soundsH.get("stoneBreak");
        placeSound = soundsH.get("stonePlace");
        fireSound = soundsH.get("smallExplosion");
        loadSprites();
        debrisType = "stone";
        price = CANNON_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void fire() {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);float particleCount = p.random(1,5);
        if (dynamite) spa.setMag(0);
        else spa.setMag(29); //barrel length
        spp.add(spa);
        String part = "smoke";
        if (frags) projectiles.add(new FragBall(p,spp.x,spp.y, angleB, this, damage, effectRadius));
        else if (dynamite) projectiles.add(new Dynamite(p,spp.x,spp.y, angleB, this, damage, effectRadius));
        else projectiles.add(new CannonBall(p,spp.x,spp.y, angleB, this, damage, effectRadius));
        if (!dynamite) {
            for (int i = 0; i < particleCount; i++) {
                PVector spa2 = PVector.fromAngle(angleB - HALF_PI + radians(p.random(-20, 20)));
                spa2.setMag(-5);
                PVector spp2 = new PVector(spp.x, spp.y);
                spp2.add(spa2);
                particles.add(new BuffParticle(p, spp2.x, spp2.y, angleB + radians(p.random(-45, 45)), part));
            }
        }
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 175;
        upgradePrices[1] = 250;
        upgradePrices[2] = 700;
        upgradePrices[3] = 150;
        upgradePrices[4] = 200;
        upgradePrices[5] = 800;
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
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[8];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[13];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[23];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[5];
        upgradeIcons[4] = spritesAnimH.get("upgradeIC")[7];
        upgradeIcons[5] = spritesAnimH.get("upgradeIC")[24];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    damage += 20;
                    break;
                case 1:
                    damage += 30;
                    break;
                case 2:
                    damage += 300;
                    effectRadius = 60;
                    pjSpeed = 10;
                    dynamite = true;
                    fireSound = soundsH.get("slingshot");
                    name = "dynamiteLauncher";
                    debrisType = "wood";
                    damageSound = soundsH.get("woodDamage");
                    breakSound = soundsH.get("woodBreak");
                    placeSound = soundsH.get("woodPlace");
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
                    delay -= 50;
                    break;
                case 5:
                    range += 30;
                    delay -= 60;
                    frags = true;
                    name = "fragCannon";
                    debrisType = "metal";
                    placeSound = soundsH.get("metalPlace");
                    damageSound = soundsH.get("metalDamage");
                    //todo: metal sounds
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}
