package main.towers.turrets;

import main.damagingThings.projectiles.DarkBlast;
import main.damagingThings.projectiles.EnergyBlast;
import main.damagingThings.projectiles.NuclearBlast;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.randomizeDelay;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class EnergyBlaster extends Turret{

    private int effectRadius;
    private boolean bigExplosion;
    private boolean nuclear;
    private boolean dark;

    public EnergyBlaster(PApplet p, Tile tile) {
        super(p,tile);
        offset = 13;
        name = "energyBlaster";
        delay = randomizeDelay(p, 4.2f);
        damage = 400;
        pjSpeed = 1000;
        range = 300;
        betweenFireFrames = down60ToFramerate(2);
        effectRadius = 35;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("energyBlasterFire");
        fireParticle = "energy";
        barrelLength = 40;
        debrisType = "darkMetal";
        price = ENERGY_BLASTER_PRICE;
        value = price;
        priority = 2; //strong

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (nuclear) {
            projectiles.add(new NuclearBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius));
            for (int i = 0; i < p.random(3, 5); i++) {
                midParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "nuclear"));
            }
        } else if (dark) {
            projectiles.add(new DarkBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius));
            for (int i = 0; i < p.random(3, 5); i++) {
                midParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "dark"));
            }
        } else {
            projectiles.add(new EnergyBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius, bigExplosion));
            for (int i = 0; i < p.random(3, 5); i++) {
                midParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "energy"));
            }
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 500;
        upgradePrices[1] = 650;
        upgradePrices[2] = 4000;

        upgradePrices[3] = 400;
        upgradePrices[4] = 800;
        upgradePrices[5] = 6500;
        //titles
        upgradeTitles[0] = "Faster Reload";
        upgradeTitles[1] = "Big Blasts";
        upgradeTitles[2] = "Nuclear Blasts";

        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Sniping";
        upgradeTitles[5] = "Dark Vortex";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "explosion";
        upgradeDescC[1] = "radius";

        upgradeDescA[2] = "Huge";
        upgradeDescB[2] = "explosion";
        upgradeDescC[2] = "radius";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "range and";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "Massively";
        upgradeDescB[5] = "increase";
        upgradeDescC[5] = "damage";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[21];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[29];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[30];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 0.6f;
                    break;
                case 1:
                    effectRadius += 15;
                    bigExplosion = true;
                    break;
                case 2:
                    damage += 550;
                    delay -= 1f;
                    effectRadius = 200;
                    name = "nuclearBlaster";
                    fireParticle = "nuclear";
                    debrisType = "metal";
                    nuclear = true;
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 35;
                    break;
                case 4:
                    range += 40;
                    damage += 200;
                    break;
                case 5:
                    range += 65;
                    damage += 3050;
                    name = "darkBlaster";
                    fireParticle = "dark";
                    dark = true;
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}