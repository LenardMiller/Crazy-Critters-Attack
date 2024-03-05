package main.towers.turrets;

import main.projectiles.Laundry;
import main.projectiles.MiscProjectile;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class RandomCannon extends Turret {

    boolean laundry;
    boolean barrel;

    int pjRotationSpeed;
    int particleCount;

    public RandomCannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "miscCannon";
        delay = 0.4f;
        pjSpeed = 300;
        betweenFireFrames = down60ToFramerate(5);
        damage = 10;
        range = 200;
        particleCount = 3;
        barrelLength = 18;
        pjRotationSpeed = 10;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("luggageBlaster");
        material = Material.wood;
        basePrice = RANDOM_CANNON_PRICE;
        titleLines = new String[]{"Luggage", "Launcher"};
    }

    /**
     * Overrides fireParticle
     */
    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (barrel) angle += p.random(-0.1f, 0.1f);
        float particleCount = p.random(this.particleCount / 3f,this.particleCount);
        if (barrel) particleCount = 1;
        String part = "smoke";
        int spriteType = (int)(p.random(0,5.99f));
        if (laundry && p.random(0,3) < 1) {
            projectiles.add(new Laundry(p,position.x,position.y, angle, this, getDamage(), pjSpeed, pjRotationSpeed));
            part = "poison";
        }
        else projectiles.add(new MiscProjectile(p,position.x,position.y, angle, this, spriteType, getDamage(), pjSpeed, pjRotationSpeed));
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(angle-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(position.x,position.y);
            spp2.add(spa2);
            midParticles.add(new MiscParticle(p,spp2.x,spp2.y,angle+radians(p.random(-45,45)),part));
        }
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 150;
        upgradePrices[1] = 150;
        upgradePrices[2] = 500;

        upgradePrices[3] = 75;
        upgradePrices[4] = 125;
        upgradePrices[5] = 600;
        //titles
        upgradeTitles[0] = "Heavy Luggage";
        upgradeTitles[1] = "More Luggage";
        upgradeTitles[2] = "Rotating Barrel";

        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Longest Range";
        upgradeTitles[5] = "Dirty Laundry";
        //description
        upgradeDescA[0] = "Increase";
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
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[45];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[10];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[15];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[46];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[47];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[12];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> {
                    damage += 10;
                    particleCount += 3;
                } case 1 -> {
                    delay -= 0.15f;
                    pjRotationSpeed += 10;
                } case 2 -> {
                    damageSound = sounds.get("stoneDamage");
                    breakSound = sounds.get("stoneBreak");
                    placeSound = sounds.get("stonePlace");
                    material = Material.stone;
                    barrel = true;
                    delay = 0;
                    pjRotationSpeed += 10;
                    barrelLength = 27;
                    name = "miscCannonBarrel";
                    titleLines = new String[]{"Minibarrel"};
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> {
                    range += 20;
                    pjSpeed += 100;
                } case 4 -> {
                    range += 30;
                    pjSpeed += 100;
                } case 5 -> {
                    particleCount += 3;
                    laundry = true;
                    pjSpeed += 100;
                    range += 30;
                    damage += 10;
                    effectDuration = 6;
                    effectLevel = 25;
                    betweenFireFrames = 1;
                    name = "miscCannonLaundry";
                    titleLines = new String[]{"Dirty Luggage", "Launcher"};
                    Color specialColor = new Color(0x92E062);
                    extraInfo.add((arg) -> selection.displayInfoLine(arg, specialColor, "Toxic Splatters:", null));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, specialColor, "DPS", ((int) effectLevel) + ""));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, specialColor, "Duration", nf(effectDuration, 1, 1) + "s"));
                    loadSprites();
                }
            }
        }
    }

    public void updateSprite() {}
}
