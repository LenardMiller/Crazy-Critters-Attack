package main.towers.turrets;

import main.misc.Tile;
import main.particles.Floaty;
import main.particles.MiscParticle;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Booster extends Turret {

    public Boost boost;

    public Booster(PApplet p, Tile tile) {
        super(p, tile);
        name = "booster";
        delay = -1;
        range = 1;
        pjSpeed = -1;
        debrisType = "darkMetal";
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        betweenIdleFrames = 4;
        price = BOOSTER_PRICE;
        value = price;
        hasPriority = false;

        boost = new Boost();
        boost.health = 0.5f;

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void loadSprites() {
        sBase = staticSprites.get(name + "BaseTR");
        sIdle = staticSprites.get(name + "IdleTR");
        idleFrames = animatedSprites.get(name + "IdleTR");
        sIdle = idleFrames[0];
    }

    @Override
    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        if (!paused) {
            giveBoost();
            spawnProjectiles(new PVector(tile.position.x - 25, tile.position.y - 25), p.random(360));
        }
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
          && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    /**
     * Now spawns particles
     */
    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (p.random(15) < 1) {
            topParticles.add(new MiscParticle(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(360), "orangeMagic"));
            float speed = p.random(25, 35);
            if (range > 1) speed = p.random(35, 50);
            midParticles.add(new Floaty(p, position.x, position.y, speed, "orangeBubble"));
        }
    }

    @Override
    public void upgrade(int id) {
        upgradeSpecial(id);
        int price = 0;
        if (id == 0) {
            price = upgradePrices[nextLevelA];
            if (price > money) return;
            if (nextLevelA > 2) return;
            if (nextLevelB == 6 && nextLevelA == 2) return;
            nextLevelA++;
        } else if (id == 1) {
            price = upgradePrices[nextLevelB];
            if (price > money) return;
            if (nextLevelB > 5) return;
            if (nextLevelB == 5 && nextLevelA == 3) return;
            nextLevelB++;
        }
        inGameGui.flashA = 255;
        money -= price;
        value += price;
        //icons
        if (nextLevelA < upgradeTitles.length / 2) inGameGui.upgradeIconA.sprite = upgradeIcons[nextLevelA];
        else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];

        playSoundRandomSpeed(p, placeSound, 1);
        spawnParticles();
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 1500;
        upgradePrices[1] = 2000;
        upgradePrices[2] = 25000;

        upgradePrices[3] = 2250;
        upgradePrices[4] = 3000;
        upgradePrices[5] = 20000;
        //titles
        upgradeTitles[0] = "Boost Range";
        upgradeTitles[1] = "Increase Area";
        upgradeTitles[2] = "Money Boost";

        upgradeTitles[3] = "Boost Damage";
        upgradeTitles[4] = "Boost Firerate";
        upgradeTitles[5] = "Explosives";
        //descriptions
        upgradeDescA[0] = "Boost";
        upgradeDescB[0] = "tower";
        upgradeDescC[0] = "range";

        upgradeDescA[1] = "Affect";
        upgradeDescB[1] = "more";
        upgradeDescC[1] = "towers";

        upgradeDescA[2] = "Increase";
        upgradeDescB[2] = "income";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Boost";
        upgradeDescB[3] = "tower";
        upgradeDescC[3] = "damage";

        upgradeDescA[4] = "Boost";
        upgradeDescB[4] = "tower";
        upgradeDescC[4] = "firerate";

        upgradeDescA[5] = "Destroyed";
        upgradeDescB[5] = "towers";
        upgradeDescC[5] = "explode";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[21];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[14];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[23];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    boost.range = 0.2f;
                    break;
                case 1:
                    range++;
                    break;
                case 2:
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    boost.damage = 0.3f;
                    break;
                case 4:
                    boost.firerate = 0.25f;
                    break;
                case 5:
                    break;
            }
        }
    }

    private void giveBoost() {
        for (int i = 0; i < 8; i++) {
            int checkX = -1;
            int checkY = -1;
            switch (i) {
                case 0:
                    if (range < 2) continue;
                    break;
                case 1:
                    checkX = 0;
                    break;
                case 2:
                    if (range < 2) continue;
                    checkX = 1;
                    break;
                case 3:
                    checkY = 0;
                    break;
                case 4:
                    checkX = 1;
                    checkY = 0;
                    break;
                case 5:
                    if (range < 2) continue;
                    checkY = 1;
                    break;
                case 6:
                    checkX = 0;
                    checkY = 1;
                    break;
                case 7:
                    if (range < 2) continue;
                    checkX = 1;
                    checkY = 1;
                    break;
            }
            PVector pos = tile.getGridPosition();
            int x = (int) pos.x + checkX;
            int y = (int) pos.y + checkY;
            Tower tower = tiles.get(x, y).tower;
            if (tower == null || tower instanceof Booster) continue;
            tower.addBoost(boost);
        }
    }

    public static class Boost {

        public float health;
        public float damage;
        public float range;
        public float firerate;

    }
}
