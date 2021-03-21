package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Gravel;
import main.projectiles.Pebble;
import main.projectiles.Rock;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class Slingshot extends Turret {

    boolean painful;
    boolean gravel;

    public Slingshot(PApplet p, Tile tile) {
        super(p,tile);
        name = "slingshot";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 1.6f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 700;
        range = 230;
        spriteType = 0;
        damage = 15; //15
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("slingshot");
        loadSprites();
        debrisType = "wood";
        price = SLINGSHOT_PRICE;
        value = price;
        priority = 0; //first
        painful = false;
        gravel = false;
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    protected void fire(float barrelLength, String particleType) {
        fireSound.stop();
        fireSound.play(p.random(0.8f, 1.2f), volume);
        if (painful) projectiles.add(new Rock(p, tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
        if (gravel) {
            float offset = 0.03f;
            int count = 8;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Gravel(p, tile.position.x-size.x/2,tile.position.y-size.y/2, a, this, damage));
                a += offset;
            }
        }
        if (!painful && !gravel) projectiles.add(new Pebble(p,tile.position.x-size.x/2,tile.position.y-size.y/2, angle, this, damage));
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 75;
        upgradePrices[2] = 400;
        upgradePrices[3] = 75;
        upgradePrices[4] = 100;
        upgradePrices[5] = 500;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Super Range";
        upgradeTitles[2] = "Gravel Slinger";
        upgradeTitles[3] = "Damage Up";
        upgradeTitles[4] = "Faster Firing";
        upgradeTitles[5] = "Painful Rocks";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "range";

        upgradeDescA[2] = "Shoots";
        upgradeDescB[2] = "gravel at";
        upgradeDescC[2] = "enemies";

        upgradeDescA[3] = "+5";
        upgradeDescB[3] = "damage";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Inflicts";
        upgradeDescB[5] = "bleeding,";
        upgradeDescC[5] = "+30 dmg";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[17];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[16];
    }

    public void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 30;
                    break;
                case 1:
                    range += 40;
                    break;
                case 2:
                    gravel = true;
                    damage -= 10;
                    name = "slingshotGravel";
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    damage += 5;
                    break;
                case 4:
                    delay -= 0.3f;
                    break;
                case 5:
                    debrisType = "stone";
                    damageSound = sounds.get("stoneDamage");
                    breakSound = sounds.get("stoneBreak");
                    placeSound = sounds.get("stonePlace");
                    painful = true;
                    damage += 30;
                    delay += 0.1f;
                    effectDuration = 6;
                    effectLevel = 15;
                    name = "slingshotRock";
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}
