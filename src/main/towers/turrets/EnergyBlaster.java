package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.DarkBlast;
import main.projectiles.EnergyBlast;
import main.projectiles.NuclearBlast;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class EnergyBlaster extends Turret{

    private int effectRadius;
    private boolean bigExplosion;
    private boolean nuclear;
    private boolean dark;

    public EnergyBlaster(PApplet p, Tile tile) {
        super(p,tile);
        offset = 13;
        name = "energyBlaster";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 4.2f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        damage = 100;
        pjSpeed = 1000;
        range = 300;
        betweenFireFrames = down60ToFramerate(2);
        state = 0;
        effectRadius = 35;
        bigExplosion = false;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("energyBlasterFire");
        fireParticle = "energy";
        barrelLength = 40;
        loadSprites();
        debrisType = "darkMetal";
        price = ENERGYBLASTER_PRICE;
        value = price;
        priority = 2; //strong
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    protected void spawnProjectile(PVector position, float angle) {
        if (nuclear) {
            projectiles.add(new NuclearBlast(p, position.x, position.y, angle, this, damage, effectRadius));
        } else if (dark) {
            projectiles.add(new DarkBlast(p, position.x, position.y, angle, this, damage, effectRadius));
        } else {
            projectiles.add(new EnergyBlast(p, position.x, position.y, angle, this, damage, effectRadius, bigExplosion));
        }
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 350;
        upgradePrices[1] = 400;
        upgradePrices[2] = 2000;

        upgradePrices[3] = 250;
        upgradePrices[4] = 600;
        upgradePrices[5] = 2500;
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
                    damage += 300;
                    delay -= 0.6f;
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
                    range += 35;
                    damage += 100;
                    break;
                case 5:
                    range += 45;
                    damage += 1500;
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