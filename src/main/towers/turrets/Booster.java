package main.towers.turrets;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.playSoundRandomSpeed;

public class Booster extends Turret {

    public float damageBoost;

    public Booster(PApplet p, Tile tile) {
        super(p, tile);
        name = "booster";
        delay = -1;
        range = 1;
        pjSpeed = -1;
        debrisType = "crystal";
        damageSound = sounds.get("crystalDamage");
        breakSound = sounds.get("crystalBreak");
        placeSound = sounds.get("crystalPlace");
        price = BOOSTER_PRICE;
        value = price;

        damageBoost = 0.1f;

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void loadSprites() {
        sBase = staticSprites.get(name + "BaseTR");
        sIdle = staticSprites.get(name + "IdleTR");
        if (false) { //idk, I may need this at some point
            fireFrames = animatedSprites.get(name + "FireTR");
            loadFrames = animatedSprites.get(name + "LoadTR");
        }
        idleFrames = animatedSprites.get(name + "IdleTR");
        sIdle = idleFrames[0];
    }

    @Override
    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
          && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {}

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
        upgradeTitles[0] = "";
        upgradeTitles[1] = "";
        upgradeTitles[2] = "";

        upgradeTitles[3] = "";
        upgradeTitles[4] = "";
        upgradeTitles[5] = "";
        //descriptions
        upgradeDescA[0] = "";
        upgradeDescB[0] = "";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "";
        upgradeDescB[1] = "";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "";
        upgradeDescB[2] = "";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "";
        upgradeDescB[3] = "";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "";
        upgradeDescB[4] = "";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "";
        upgradeDescB[5] = "";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[0];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[0];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }
}
