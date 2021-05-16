package main.towers.turrets;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.ICE_TOWER_PRICE;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.Utilities.randomizeDelay;

public class IceTower extends Turret {

    public IceTower(PApplet p, Tile tile) {
        super(p, tile);
        name = "iceTower";
        delay = randomizeDelay(p, 6);
        pjSpeed = -1;
        range = 300;
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

    }

    @Override
    protected void upgradeSpecial(int id) {

    }
}
