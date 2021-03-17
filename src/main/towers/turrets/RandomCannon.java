package main.towers.turrets;

import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Laundry;
import main.projectiles.MiscProjectile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.from60ToFramerate;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class RandomCannon extends Turret {

    boolean laundry;
    boolean barrel;

    public RandomCannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "miscCannon";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 0.4f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 700;
        numFireFrames = 5;
        betweenFireFrames = from60ToFramerate(8);
        numLoadFrames = 1;
        fireFrames = new PImage[numFireFrames];
        loadFrames = new PImage[numLoadFrames];
        spriteType = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 10;
        range = 200;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("luggageBlaster");
        loadSprites();
        debrisType = "wood";
        price = RANDOMCANNON_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    protected void fire(float barrelLength, String particleType) {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        float angleB = angle;
        int spriteType = (int)(p.random(0,5.99f));
        PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector spa = PVector.fromAngle(angleB-HALF_PI);float particleCount = p.random(1,5);
        if (barrel) {
            particleCount = 1;
            angleB += p.random(-0.1f,0.1f);
            spa.setMag(27);
        }
        else spa.setMag(18);
        spp.add(spa);
        String part = "smoke";
        if (laundry && p.random(0,3) < 1) { //this is why this is here
            projectiles.add(new Laundry(p,spp.x,spp.y, angleB, this, damage));
            part = "poison";
        }
        else projectiles.add(new MiscProjectile(p,spp.x,spp.y, angleB, this, spriteType, damage));
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(angleB-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x,spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angleB+radians(p.random(-45,45)),part));
        }
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 125;
        upgradePrices[1] = 150;
        upgradePrices[2] = 600;
        upgradePrices[3] = 75;
        upgradePrices[4] = 125;
        upgradePrices[5] = 500;
        //titles
        upgradeTitles[0] = "Damage Up";
        upgradeTitles[1] = "Faster Firing";
        upgradeTitles[2] = "Rotating Barrel";
        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Longest Range";
        upgradeTitles[5] = "Dirty Laundry";
        //description
        upgradeDescA[0] = "+15";
        upgradeDescB[0] = "damage";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "firerate";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Greatly";
        upgradeDescB[2] = "increase";
        upgradeDescC[2] = "firerate";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Further";
        upgradeDescB[4] = "increase";
        upgradeDescC[4] = "range";

        upgradeDescA[5] = "Toxic";
        upgradeDescB[5] = "splatters";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[10];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[15];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[12];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    damage += 15;
                    break;
                case 1:
                    delay -= 0.15f;
                    break;
                case 2:
                    damageSound = sounds.get("stoneDamage");
                    breakSound = sounds.get("stoneBreak");
                    placeSound = sounds.get("stonePlace");
                    debrisType = "stone";
                    barrel = true;
                    delay = 0.1f;
                    damage -= 5;
                    name = "miscCannonBarrel";
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 20;
                    break;
                case 4:
                    range += 30;
                    break;
                case 5:
                    laundry = true;
                    damage += 10;
                    effectDuration = 6;
                    effectLevel = 25;
                    name = "miscCannonLaundry";
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}
