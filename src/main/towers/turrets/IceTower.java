package main.towers.turrets;

import main.misc.Tile;
import main.towers.IceWall;
import main.towers.Wall;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;

public class IceTower extends Turret {

    private int wallHp;
    private int wallTimeUntilDamage;

    public IceTower(PApplet p, Tile tile) {
        super(p, tile);
        name = "iceTower";
        delay = randomizeDelay(p, 10);
        pjSpeed = -1;
        range = 300;
        barrelLength = 30;
        offset = 6;
        wallHp = 50;
        wallTimeUntilDamage = 30;
        debrisType = "metal";
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("energyBlasterFire");
        price = ICE_TOWER_PRICE;
        value = price;

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        targetEnemy.damageWithoutBuff(damage, this, "ice", PVector.fromAngle(angle), damage > 0);

        Tile tile = tiles.get((roundTo(targetEnemy.position.x, 50) / 50) + 1, (roundTo(targetEnemy.position.y, 50) / 50) + 1);
        if (tile.tower == null) {
            tile.tower = new IceWall(p, tile, wallHp, wallTimeUntilDamage);
            Wall wall = (Wall) tile.tower;
            wall.placeEffects();
            updateNodes();
            updateTowerArray();
        } else if (tile.tower instanceof IceWall) tile.tower.heal(1);
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
