package main.towers.turrets;

import main.damagingThings.projectiles.glue.Glue;
import main.damagingThings.projectiles.glue.SpikeyGlue;
import main.damagingThings.projectiles.glue.SplatterGlue;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.randomizeDelay;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Gluer extends Turret {

    public int gluedTotal;

    private boolean spikey;
    private boolean splatter;

    public Gluer(PApplet p, Tile tile) {
        super(p,tile);
        name = "gluer";
        delay = randomizeDelay(p, 2.5f);
        pjSpeed = 400;
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
        material = "stone";
        price = GLUER_PRICE;
        value = price;
        titleLines = new String[]{"Gluer"};
        infoDisplay = (o) -> selection.setTextPurple("Slows", o);
        statsDisplay = (o) -> {
            //glue stuff
            if (gluedTotal == 1) p.text("1 enemy glued", 910, 450 + offset);
            else p.text(nfc(gluedTotal) + " enemies glued", 910, 450 + offset);

            //default stuff
            if (killsTotal != 1) p.text(nfc(killsTotal) + " kills", 910, 475 + o);
            else p.text("1 kill", 910, 475 + o);
            p.text(nfc(damageTotal) + " total dmg", 910, 500 + o);
        };

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        for (int i = 0; i < p.random(3, 5); i++) {
            midParticles.add(new MiscParticle(p, position.x, position.y,
              angle + radians(p.random(-45, 45)), "glue"));
        }
        if (spikey) projectiles.add(new SpikeyGlue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration));
        else if (splatter) projectiles.add(new SplatterGlue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration));
        else projectiles.add(new Glue(p,position.x,position.y, angle, this, getDamage(), effectLevel, effectDuration));
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
        upgradeTitles[1] = "Long Glue";
        upgradeTitles[2] = "Glue Splash";

        upgradeTitles[3] = "Gluier Glue";
        upgradeTitles[4] = "Hard Glue";
        upgradeTitles[5] = "Spikey Glue";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "glue";
        upgradeDescC[1] = "duration";

        upgradeDescA[2] = "Glue";
        upgradeDescB[2] = "splatters";
        upgradeDescC[2] = "";


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
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[25];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[28];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[27];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[26];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 30;
                    break;
                case 1:
                    effectDuration += 2;
                    break;
                case 2:
                    effectDuration += 2;
                    range += 50;
                    delay -= 1;
                    splatter = true;
                    name = "splashGluer";
                    titleLines = new String[]{"Glue Splasher"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Slows", o);
                        selection.setTextPurple("Splatter", o);
                    };
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    effectLevel = 0.6f;
                    break;
                case 4:
                    damage = 35;
                    break;
                case 5:
                    damage += 65;
                    effectLevel = 0.5f;
                    spikey = true;
                    name = "shatterGluer";
                    material = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    titleLines = new String[]{"Glue Spiker"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Slows", o);
                        selection.setTextPurple("Embeds spikes", o);
                    };
                    loadSprites();
                    break;
            }
        }
    }
}
