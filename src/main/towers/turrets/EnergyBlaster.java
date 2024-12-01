package main.towers.turrets;

import main.projectiles.DarkBlast;
import main.projectiles.EnergyBlast;
import main.projectiles.NuclearBlast;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class EnergyBlaster extends Turret {

    private static final Color EXPLOSION_COLOR = new Color(0xff8383);
    private static final Color VORTEX_COLOR = new Color(0xBA7FF8);

    public static String pid = "M1-300-800-5";
    public static String description =
            "Fires an explosive energy ball at the toughest critter it can see. " +
                    "Has long range and high damage, but low rate of fire.";
    public static char shortcut = 'E';
    public static String title1 = "Energy Blaster";
    public static String title2 = null;
    public static int price = 1250;

    private int effectRadius;
    private boolean bigExplosion;
    private boolean nuclear;
    private boolean dark;

    public EnergyBlaster(PApplet p, Tile tile) {
        super(p,tile);
        offset = 13;
        name = "energyBlaster";
        delay = 5f;
        damage = 800;
        pjSpeed = 800;
        range = 300;
        betweenFireFrames = down60ToFramerate(2);
        effectRadius = 50;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("energyBlasterFire");
        fireParticle = "energy";
        barrelLength = 40;
        material = Material.darkMetal;
        basePrice = price;
        priority = Priority.Strong;
        titleLines = new String[]{"Energy Blaster"};
        extraInfo.add((arg) -> selection.displayInfoLine(arg, EXPLOSION_COLOR, "Small Explosion", null));
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (nuclear) {
            projectiles.add(new NuclearBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius, pjSpeed));
            for (int i = 0; i < p.random(3, 5); i++) {
                towerParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "nuclear"));
            }
        } else if (dark) {
            projectiles.add(new DarkBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius, pjSpeed));
            for (int i = 0; i < p.random(3, 5); i++) {
                towerParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "dark"));
            }
        } else {
            projectiles.add(new EnergyBlast(p, position.x, position.y, angle, this, getDamage(), effectRadius, bigExplosion, pjSpeed));
            for (int i = 0; i < p.random(3, 5); i++) {
                towerParticles.add(new MiscParticle(p, position.x, position.y,
                  angle + radians(p.random(-45, 45)), "energy"));
            }
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 500;
        upgradePrices[1] = 650;
        upgradePrices[2] = 7000;

        upgradePrices[3] = 400;
        upgradePrices[4] = 800;
        upgradePrices[5] = 8000;
        //titles
        upgradeTitles[0] = "Faster Reload";
        upgradeTitles[1] = "Big Blasts";
        upgradeTitles[2] = "Nuclear Blasts";

        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Rifling";
        upgradeTitles[5] = "Dark Vortex";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "explosion";
        upgradeDescC[1] = "radius";

        upgradeDescA[2] = "Huge";
        upgradeDescB[2] = "explosion";
        upgradeDescC[2] = "radius";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "range and";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "Massively";
        upgradeDescB[5] = "increase";
        upgradeDescC[5] = "damage";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[55];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[29];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[54];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[56];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[30];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> delay -= 1.2f;
                case 1 -> {
                    effectRadius += 50;
                    bigExplosion = true;
                    extraInfo.remove(0);
                    if (nextLevelB > 5) {
                        extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                                VORTEX_COLOR, "Large Vortex", null));
                    } else {
                        extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                                EXPLOSION_COLOR, "Large Explosion", null));
                    }
                } case 2 -> {
                    damage += 800;
                    delay -= 1f;
                    effectRadius = 125;
                    name = "energyBlasterNuclear";
                    fireParticle = "nuclear";
                    material = Material.metal;
                    nuclear = true;
                    titleLines = new String[]{"Nuclear Blaster"};
                    extraInfo.remove(0);
                    extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                            new Color(0xFFFD83), "Nuclear Explosion", null));
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> {
                    range += 35;
                    pjSpeed += 200;
                } case 4 -> {
                    range += 40;
                    pjSpeed += 200;
                    damage += 400;
                } case 5 -> {
                    range += 65;
                    pjSpeed += 200;
                    damage = 5000;
                    name = "energyBlasterDark";
                    fireParticle = "dark";
                    dark = true;
                    titleLines = new String[]{"Dark Blaster"};
                    loadSprites();
                    extraInfo.remove(0);
                    if (nextLevelA <= 1) {
                        extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                                VORTEX_COLOR, "Small Vortex", null));
                    } else {
                        extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                                VORTEX_COLOR, "Large Vortex", null));
                    }
                }
            }
        }
    }
}