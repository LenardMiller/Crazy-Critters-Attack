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
        range = 250;
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
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 200;

        upgradePrices[3] = 50;
        upgradePrices[4] = 100;
        upgradePrices[5] = 200;
        //titles
        upgradeTitles[0] = "Longer Lasting";
        upgradeTitles[1] = "Stronger Ice";
        upgradeTitles[2] = "Auto Defence";

        upgradeTitles[3] = "Increase Range";
        upgradeTitles[4] = "Faster Freezing";
        upgradeTitles[5] = "Superfreeze";
        //descriptions
        upgradeDescA[0] = "Ice";
        upgradeDescB[0] = "lasts";
        upgradeDescC[0] = "longer";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "ice";
        upgradeDescC[1] = "HP";

        upgradeDescA[2] = "Automatically";
        upgradeDescB[2] = "reinforce";
        upgradeDescC[2] = "defences";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Freeze";
        upgradeDescB[5] = "bigger";
        upgradeDescC[5] = "enemies";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[25];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[27];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[34];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[28];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    wallTimeUntilDamage *= 2;
                    break;
                case 1:
                    wallHp *= 2;
                    break;
                case 2:
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 50;
                    break;
                case 4:
                    delay -= 3;
                    break;
                case 5:
                    break;
            }
        }
    }
}
