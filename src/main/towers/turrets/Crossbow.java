package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Bolt;
import main.projectiles.ReinforcedBolt;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;

public class Crossbow extends Turret {

    private static final Color SPECIAL_COLOR = new Color(0xDEA674);

    private boolean multishot;
    private boolean reinforced;

    public static String pid = "W3-320-30-4.5";
    public static String description =
            "Fires a powerful bolt at the furthest critter from it. " +
            "Has very high damage, range and piercing but has low rate of fire.";
    public static char shortcut = 'Z';
    public static String title1 = "Crossbow";
    public static String title2 = null;
    public static int price = 200;

    public Crossbow(PApplet p, Tile tile) {
        super(p,tile);
        offset = 2;
        name = "crossbow";
        delay = 4.5f;
        pjSpeed = 1000;
        range = 320;
        damage = 30;
        pierce = 1;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("crossbow");
        material = Material.wood;
        basePrice = price;
        priority = Priority.Far;
        titleLines = new String[]{"Crossbow"};
        extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Pierce", "" + pierce));
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (multishot) {
            float offset = 0.07f;
            int count = 7;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Bolt(p, position.x, position.y, a, this, getDamage(), pierce, pjSpeed));
                a += offset;
            }
        } else {
            if (reinforced) projectiles.add(new ReinforcedBolt(p,position.x, position.y, angle, this, getDamage(), pierce, pjSpeed));
            else projectiles.add(new Bolt(p,position.x, position.y, angle, this, getDamage(), pierce, pjSpeed));
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 75;
        upgradePrices[1] = 175;
        upgradePrices[2] = 750;
        upgradePrices[3] = 100;
        upgradePrices[4] = 150;
        upgradePrices[5] = 650;
        //titles
        upgradeTitles[0] = "Pointier";
        upgradeTitles[1] = "Sharper";
        upgradeTitles[2] = "Reinforced";

        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Faster Firing";
        upgradeTitles[5] = "Barrage";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "piercing";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Increase";
        upgradeDescB[2] = "damage &";
        upgradeDescC[2] = "piercing";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Fire a";
        upgradeDescB[5] = "spread of";
        upgradeDescC[5] = "bolts";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[9];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[43];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[18];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[44];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[19];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> pierce += 2;
                case 1 -> damage += 20;
                case 2 -> {
                    damage += 300;
                    pierce += 5;
                    pjSpeed += 400;
                    reinforced = true;
                    titleLines = new String[]{"Reinforced", "Crossbow"};
                    name = "crossbowReinforced";
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> {
                    range += 30;
                    pjSpeed += 400;
                } case 4 -> delay -= 1.1f;
                case 5 -> {
                    multishot = true;
                    name = "crossbowMultishot";
                    fireSound = sounds.get("shotbow");
                    titleLines = new String[]{"Shotbow"};
                    extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Multiple Bolts", null));
                    loadSprites();
                }
            }
        }
    }

    public void updateSprite() {}
}