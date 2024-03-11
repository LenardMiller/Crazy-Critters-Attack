package main.towers.turrets;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.misc.Tile;
import main.particles.MiscParticle;
import main.projectiles.shockwaves.SeismicShockwave;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static processing.core.PConstants.HALF_PI;

public class SeismicTower extends Turret {

    public static final Color SPECIAL_COLOR = new Color(0xBBBBBB);

    public static String pid = "S3-225-50-2.5";
    public static String description =
            "Sends a shockwave towards the nearest group of critters. " +
                    "Shockwaves stun burrowing critters, but can't hit flying critters.";
    public static char shortcut = 'X';
    public static String title1 = "Seismic Tower";
    public static String title2 = null;
    public static int price = 500;

    /** Degrees */
    public float shockwaveWidth;

    private boolean seismicSense;

    public SeismicTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "seismic";
        offset = 4;
        delay = 2.5f;
        pjSpeed = 350;
        betweenFireFrames = down60ToFramerate(1);
        damage = 50;
        range = 225;
        shockwaveWidth = 60;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("seismicSlam");
        barrelLength = 29;
        material = Material.stone;
        basePrice = price;
        titleLines = new String[]{"Seismic Tower"};
        extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Shockwave", null));
        extraInfo.add((arg) -> selection.displayInfoLine(arg, SPECIAL_COLOR, "Stuns Burrowers", null));
    }

    @Override
    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && state != State.Fire && shockwaveWidth < 360) aim(targetEnemy);
        if (state == State.Idle && targetEnemy != null && (abs(targetAngle - angle) < 0.02 || shockwaveWidth > 360)) { //if done animating and aimed
            state = State.Fire;
            frame = 0;
        }
        if (state == State.Fire && frame >= fireFrames.length - 1) fire(barrelLength, fireParticle);
    }

    @Override
    protected boolean enemyCanBeAttacked(Enemy enemy) {
        return !(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy) || seismicSense;
    }

    @Override
    public void displayTop() {
        int hammerCount = 6;
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.tint(0, 60);
        if (name.equals("seismicSlammer")) {
            for (int i = 0; i <= hammerCount; i++) {
                p.rotate(TWO_PI / hammerCount);
                p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            }
        } else {
            p.rotate(angle);
            p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        }
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.tint(255, tintColor, tintColor);
        if (name.equals("seismicSlammer")) {
            for (int i = 0; i <= hammerCount; i++) {
                p.rotate(TWO_PI / hammerCount);
                p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
            }
        } else {
            p.rotate(angle);
            p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        }
        p.popMatrix();
        p.tint(255);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        float a = angle;
        if (shockwaveWidth >= 360) {
            a = 0;
            for (int i = 0; i < 6; i++) {
                fireParticles(a);
                a += TWO_PI / 6;
            }
            shockwaves.add(new SeismicShockwave(p, tile.position.x - size.x / 2, tile.position.y - size.y / 2,
              (int) barrelLength, getRange(), angle, shockwaveWidth, getDamage(), this,
                    false, true, pjSpeed));
        } else {
            fireParticles(a);
            shockwaves.add(new SeismicShockwave(p, position.x, position.y, 0,
                    getRange(), angle, shockwaveWidth, getDamage(), this, seismicSense, false, pjSpeed));
        }
    }

    private void fireParticles(float a) {
        float particleCount = p.random(1, 5);
        String part = "smoke";
        PVector spp = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        PVector spa = PVector.fromAngle(a - HALF_PI);
        spa.setMag(29); //barrel length
        spp.add(spa);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(a - HALF_PI + radians(p.random(-20, 20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x, spp.y);
            spp2.add(spa2);
            midParticles.add(new MiscParticle(p, spp2.x, spp2.y, a + radians(p.random(-45, 45)), part));
        }
        particleCount = p.random(1, 5);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(a - HALF_PI + radians(p.random(-20, 20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x, spp.y);
            spp2.add(spa2);
            midParticles.add(new MiscParticle(p, spp2.x, spp2.y, p.random(0, 360), part));
        }
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 250;
        upgradePrices[1] = 250;
        upgradePrices[2] = 1500;

        upgradePrices[3] = 250;
        upgradePrices[4] = 300;
        upgradePrices[5] = 1000;
        //titles
        upgradeTitles[0] = "Faster Reset";
        upgradeTitles[1] = "Wider Wave";
        upgradeTitles[2] = "Omniwave";

        upgradeTitles[3] = "Longer Wave";
        upgradeTitles[4] = "Forceful Wave";
        upgradeTitles[5] = "Seismic Sense";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "area of";
        upgradeDescC[1] = "effect";

        upgradeDescA[2] = "Full";
        upgradeDescB[2] = "shockwave";
        upgradeDescC[2] = "rings";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Can target";
        upgradeDescB[5] = "burrowing";
        upgradeDescC[5] = "critters";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[20];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[21];
        
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[52];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[53];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[22];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> delay -= 1.1f;
                case 1 -> shockwaveWidth += 30;
                case 2 -> {
                    material = Material.metal;
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    shockwaveWidth = 720;
                    delay = 0.3f;
                    name = "seismicSlammer";
                    hasPriority = false;
                    selection.swapSelected(this);
                    titleLines = new String[]{"Seismic", "Slammer"};
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> {
                    range += 50;
                    pjSpeed += 100;
                } case 4 -> damage += 50;
                case 5 -> {
                    material = Material.metal;
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    seismicSense = true;
                    effectLevel = 0;
                    effectDuration = 2;
                    shockwaveWidth -= 40;
                    pjSpeed += 100;
                    range += 100;
                    damage += 150;
                    name = "seismicSniper";
                    titleLines = new String[]{"Seismic Sniper"};
                    extraInfo.remove(1);
                    extraInfo.add(1, (arg) -> selection.displayInfoLine(arg,
                            SPECIAL_COLOR, "Targets Burrowers", null));
                    loadSprites();
                }
            }
        }
    }
}
