package main.towers.turrets;

import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Needle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.Utilities.randomizeDelay;

public class Nightmare extends Turret {

    private int numProjectiles;
    private int effectLevel;

    public Nightmare(PApplet p, Tile tile) {
        super(p,tile);
        name = "nightmare";
        delay = randomizeDelay(p, 3.5f);
        pjSpeed = 1000;
        range = 200;
        damage = 100;
        numProjectiles = 3;
        effectLevel = 50;
        effectDuration = 3.6f;
        fireParticle = "decay";
        barrelLength = 20;
        loadSprites();
        debrisType = "darkMetal";
        price = 300;
        value = price;
        priority = 2; //strong

        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void fire(float barrelLength, String particleType) {
        for (int i = 0; i < numProjectiles; i++) {
            PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
            PVector spa = PVector.fromAngle(angle-HALF_PI);
            spa.setMag(20);
            spp.add(spa);
            spawnProjectiles(spp, angle);
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        projectiles.add(new Needle(p, position.x, position.y, angle, this, damage, effectLevel, effectDuration));
        for (int j = 0; j < 3; j++) {
            PVector spa2 = PVector.fromAngle(angle-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-2);
            PVector spp2 = new PVector(position.x,position.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p,spp2.x,spp2.y,angle+radians(p.random(-45,45)),"decay"));
        }
    }

    @Override
    protected void upgradeSpecial(int id) {}

    @Override
    protected void setUpgrades(){
//        //delay (firerate)
//        upgradeDelay[0] = -45;
//        upgradeDelay[1] = 0;
//        upgradeDelay[2] = 0;
//        upgradeDelay[3] = 0;
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 50;
        upgradePrices[3] = 100;
//        //error (accuracy)
//        upgradeRange[0] = 0;
//        upgradeRange[1] = 0;
//        upgradeRange[2] = -4;
//        upgradeRange[3] = 0;
        //titles
        upgradeTitles[0] = "Firerate";
        upgradeTitles[1] = "More Needles";
        upgradeTitles[2] = "Reduce Spread";
        upgradeTitles[3] = "Effect Power";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescA[1] = "Fire more";
        upgradeDescA[2] = "Increase";
        upgradeDescA[3] = "Increase";
        //desc line two
        upgradeDescB[0] = "firerate";
        upgradeDescB[1] = "projectiles";
        upgradeDescB[2] = "accuracy";
        upgradeDescB[3] = "damage,";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "duration";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[4];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[3];
    }

    protected void upgradeSpecial() {
        if (nextLevelA == 1) numProjectiles = 5;
        if (nextLevelB == 1) {
            effectDuration += 1;
            effectLevel += 3;
        }
    }

    public void updateSprite() {}
}