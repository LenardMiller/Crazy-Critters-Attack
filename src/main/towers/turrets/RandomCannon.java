package main.towers.turrets;

import main.damagingThings.projectiles.Laundry;
import main.damagingThings.projectiles.MiscProjectile;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static processing.core.PConstants.HALF_PI;

public class RandomCannon extends Turret {

    boolean laundry;
    boolean barrel;

    public RandomCannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "miscCannon";
        delay = randomizeDelay(p, 0.4f);
        pjSpeed = 700;
        betweenFireFrames = down60ToFramerate(8);
        damage = 10;
        range = 200;
        barrelLength = 18;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("luggageBlaster");
        debrisType = "wood";
        price = RANDOM_CANNON_PRICE;
        value = price;

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    /**
     * Overrides fireParticle
     */
    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (barrel) angle += p.random(-0.1f, 0.1f);
        float particleCount = p.random(1,5);
        if (barrel) particleCount = 1;
        String part = "smoke";
        int spriteType = (int)(p.random(0,5.99f));
        if (laundry && p.random(0,3) < 1) {
            projectiles.add(new Laundry(p,position.x,position.y, angle, this, getDamage()));
            part = "poison";
        }
        else projectiles.add(new MiscProjectile(p,position.x,position.y, angle, this, spriteType, getDamage()));
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
        upgradeDescA[0] = "increase";
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

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    damage += 10;
                    break;
                case 1:
                    delay -= 0.15f;
                    break;
                case 2:
                    damageSound = sounds.get("stoneDamage");
                    breakSound = sounds.get("stoneBreak");
                    placeSound = sounds.get("stonePlace");
                    debrisType = "stone";
                    damage += 10;
                    barrel = true;
                    delay = 0;
                    barrelLength = 27;
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
