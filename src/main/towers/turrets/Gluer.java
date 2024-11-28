package main.towers.turrets;

import main.enemies.Enemy;
import main.enemies.BurrowingEnemy;
import main.gui.inGame.Selection;
import main.misc.Tile;
import main.particles.MiscParticle;
import main.projectiles.glue.Glue;
import main.projectiles.glue.SpikeyGlue;
import main.projectiles.glue.SplatterGlue;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Gluer extends Turret {

    private static final Color SPECIAL_COLOR = new Color(0xfef3c0);

    public static String pid = "S2-300-0-2.5";
    public static String description =
            "Fires a glob of glue at the nearest unglued critter. " +
                    "Slows critter movement and attack speed, but does no damage. " +
                    "Glue more strongly affects flying critters.";
    public static char shortcut = 'S';
    public static String title1 = "Gluer";
    public static String title2 = null;
    public static int price = 300;

    public int gluedTotal;

    private boolean spikey;
    private boolean splatter;

    public Gluer(PApplet p, Tile tile) {
        super(p,tile);
        name = "gluer";
        delay = 2.5f;
        pjSpeed = 350;
        betweenFireFrames = down60ToFramerate(1);
        range = 300;
        effectDuration = 3;
        effectLevel = 0.7f;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("glueFire");
        fireParticle = "glue";
        barrelLength = 28;
        material = Material.stone;
        basePrice = price;
        priority = Priority.Unbuffed;
        effect = "glued";
        titleLines = new String[]{"Gluer"};
        extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Slowing Glue:", null));
        extraInfo.add((arg) -> selection.displayInfoLine(
                arg, SPECIAL_COLOR, "Slow Level", (ceil((1 - effectLevel) * 10) * 10) + "%"));
        extraInfo.add((arg) -> selection.displayInfoLine(
                arg, SPECIAL_COLOR, "Duration", nf(effectDuration, 1, 1) + "s"));
        statsDisplay = () -> {
            selection.displayInfoLine(-4, Selection.STAT_TEXT_COLOR, "Glued", gluedTotal + "");
            int age = levels[currentLevel].currentWave - birthday;
            selection.displayInfoLine(-3, Selection.STAT_TEXT_COLOR, "Survived", age + "");
            selection.displayInfoLine(-2, Selection.STAT_TEXT_COLOR, "Kills", killsTotal + "");
            selection.displayInfoLine(-1, Selection.STAT_TEXT_COLOR, "Damage", nfc(damageTotal));
        };
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        for (int i = 0; i < p.random(3, 5); i++) {
            towerParticles.add(new MiscParticle(p, position.x, position.y,
              angle + radians(p.random(-45, 45)), "glue"));
        }
        if (spikey) projectiles.add(new SpikeyGlue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration, pjSpeed));
        else if (splatter) projectiles.add(new SplatterGlue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration, pjSpeed));
        else projectiles.add(new Glue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration, pjSpeed));
    }

    @Override
    protected boolean enemyCanBeAttacked(Enemy enemy) {
        //make sure effect would actually slow down enemy
        return !(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy) && enemy.speed > enemy.speed * effectLevel;
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 125;
        upgradePrices[1] = 150;
        upgradePrices[2] = 600;

        upgradePrices[3] = 150;
        upgradePrices[4] = 150;
        upgradePrices[5] = 1000;
        //titles
        upgradeTitles[0] = "Long Range";
        upgradeTitles[1] = "Tough Glue";
        upgradeTitles[2] = "Glue Splash";

        upgradeTitles[3] = "Stickier Glue";
        upgradeTitles[4] = "Dense Glue";
        upgradeTitles[5] = "Spiky Glue";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "glue";
        upgradeDescC[1] = "duration";

        upgradeDescA[2] = "Glue";
        upgradeDescB[2] = "splatters";
        upgradeDescC[2] = "on critters";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "glue";
        upgradeDescC[3] = "strength";

        upgradeDescA[4] = "Glue";
        upgradeDescB[4] = "does";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "Release";
        upgradeDescB[5] = "spikes on";
        upgradeDescC[5] = "death";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[50];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[25];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[28];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[27];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[51];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[26];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> {
                    range += 30;
                    pjSpeed += 100;
                } case 1 -> effectDuration += 2;
                case 2 -> {
                    effectDuration += 2;
                    pjSpeed += 100;
                    range += 50;
                    delay -= 1;
                    splatter = true;
                    name = "splashGluer";
                    titleLines = new String[]{"Glue Splasher"};
                    extraInfo.remove(0);
                    extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                            SPECIAL_COLOR, "Glue Splatter:", null));
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> effectLevel = 0.6f;
                case 4 -> damage = 35;
                case 5 -> {
                    effect = "spikyGlued";
                    damage += 65;
                    effectLevel = 0.5f;
                    spikey = true;
                    name = "shatterGluer";
                    material = Material.metal;
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    titleLines = new String[]{"Glue Spiker"};
                    extraInfo.remove(0);
                    extraInfo.add(0, (arg) -> selection.displayInfoLine(arg,
                            SPECIAL_COLOR, "Spikey Glue:", null));
                    loadSprites();
                }
            }
        }
    }
}
