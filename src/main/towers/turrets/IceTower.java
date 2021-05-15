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
        damage = 0;
        pjSpeed = -1;
        range = 300;
        loadSprites();
        debrisType = "metal";
        price = ICE_TOWER_PRICE;
        value = price;
        setUpgrades();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {

    }

    private void setUpgrades() {

    }

    @Override
    protected void upgradeSpecial(int id) {

    }
}
