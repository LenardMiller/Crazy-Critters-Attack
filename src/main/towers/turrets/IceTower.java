package main.towers.turrets;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.ICE_TOWER_PRICE;
import static main.Main.animatedSprites;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.Utilities.randomizeDelay;

public class IceTower extends Turret {

    public IceTower(PApplet p, Tile tile) {
        super(p, tile);
        name = "iceTower";
        delay = randomizeDelay(p, 6);
        pjSpeed = -1;
        range = 300;
        barrelLength = 30;
        offset = 6;
        debrisType = "metal";
        price = ICE_TOWER_PRICE;
        value = price;

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        targetEnemy.damageWithoutBuff(damage, this, "ice", PVector.fromAngle(angle), true);
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 0;
        upgradePrices[1] = 0;
        upgradePrices[2] = 0;
        upgradePrices[3] = 0;
        //titles
        upgradeTitles[0] = "";
        upgradeTitles[1] = "";
        upgradeTitles[2] = "";
        upgradeTitles[3] = "";
        //desc line one
        upgradeDescA[0] = "";
        upgradeDescA[1] = "";
        upgradeDescA[2] = "";
        upgradeDescA[3] = "";
        //desc line two
        upgradeDescB[0] = "";
        upgradeDescB[1] = "";
        upgradeDescB[2] = "";
        upgradeDescB[3] = "";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[0];
    }

    @Override
    protected void upgradeSpecial(int id) {

    }
}
