package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Gravel;
import main.projectiles.Pebble;
import main.projectiles.Rock;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;

public class Slingshot extends Turret {

    boolean painful;
    boolean gravel;

    public Slingshot(PApplet p, Tile tile) {
        super(p,tile);
        name = "slingshot";
        delay = 1.6f;
        pjSpeed = 400;
        range = 250;
        damage = 15; //15
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("slingshot");
        material = Material.wood;
        basePrice = SLINGSHOT_PRICE;

        titleLines = new String[]{"Slingshot"};
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (painful) projectiles.add(new Rock(p, position.x, position.y, angle, this, getDamage(),
                pjSpeed, (int) effectLevel, (int) effectDuration));
        if (gravel) {
            float offset = 0.03f;
            int count = 8;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Gravel(p, position.x, position.y, a, this, getDamage(), pjSpeed));
                a += offset;
            }
        }
        if (!painful && !gravel) projectiles.add(new Pebble(p,position.x, position.y, angle, this, getDamage(), pjSpeed));
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 75;
        upgradePrices[2] = 400;
        upgradePrices[3] = 75;
        upgradePrices[4] = 100;
        upgradePrices[5] = 650;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Longer Range";
        upgradeTitles[2] = "Gravel Slinger";
        upgradeTitles[3] = "Faster Reload";
        upgradeTitles[4] = "Heavier Rocks";
        upgradeTitles[5] = "Painful Rocks";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "range";

        upgradeDescA[2] = "Flings";
        upgradeDescB[2] = "gravel";
        upgradeDescC[2] = "pellets";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Huge rocks";
        upgradeDescB[5] = "crush";
        upgradeDescC[5] = "critters";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[17];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[16];
    }

    @Override
    public void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> {
                    range += 30;
                    pjSpeed += 200;
                } case 1 -> {
                    range += 40;
                    pjSpeed += 200;
                } case 2 -> {
                    gravel = true;
                    damage -= 10;
                    range += 50;
                    name = "slingshotGravel";
                    titleLines = new String[]{"Gravel Slinger"};
                    extraInfo.add((arg) -> selection.displayInfoLine(arg,
                            new Color(0x9CB1BD), "Gravel Chunks", null));
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> delay -= 0.3f;
                case 4 -> damage += 5;
                case 5 -> {
                    material = Material.stone;
                    damageSound = sounds.get("stoneDamage");
                    breakSound = sounds.get("stoneBreak");
                    placeSound = sounds.get("stonePlace");
                    painful = true;
                    damage += 30;
                    delay += 0.1f;
                    effectDuration = 6;
                    effectLevel = 8;
                    name = "slingshotRock";
                    titleLines = new String[]{"Heavy Slingshot"};
                    Color infoColor = new Color(0xDA8383);
                    extraInfo.add((arg) -> selection.displayInfoLine(arg, infoColor,"Bleeding:", null));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, infoColor, "DPS", ((int) (effectLevel / 0.2f)) + ""));
                    extraInfo.add((arg) -> selection.displayInfoLine(
                            arg, infoColor, "Duration", nf(effectDuration, 1, 1) + "s"));
                    loadSprites();
                }
            }
        }
    }
}
