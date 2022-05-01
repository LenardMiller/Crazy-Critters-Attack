package main.towers.turrets;

import main.projectiles.shockwaves.SeismicShockwave;
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
import static processing.core.PConstants.HALF_PI;

public class SeismicTower extends Turret {

    /**
     * Degrees
     */
    public float shockwaveWidth;

    private boolean seismicSense;

    public SeismicTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "seismic";
        offset = 4;
        delay = randomizeDelay(p, 2.5f);
        pjSpeed = 400;
        betweenFireFrames = down60ToFramerate(1);
        damage = 50;
        range = 225;
        shockwaveWidth = 60;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("seismicSlam");
        barrelLength = 29;
        material = "stone";
        price = SEISMIC_PRICE;
        value = price;
        titleLines = new String[]{"Seismic Tower"};
        infoDisplay = (o) -> selection.setTextPurple("Shockwave", o);

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && state != 1 && shockwaveWidth < 360) aim(targetEnemy);
        if (state == 0 && targetEnemy != null && (abs(targetAngle - angle) < 0.02 || shockwaveWidth > 360)) { //if done animating and aimed
            state = 1;
            frame = 0;
        }
        if (state == 1 && frame >= fireFrames.length - 1) fire(barrelLength, fireParticle);
    }

    @Override
    protected boolean enemyCanBeAttacked(Enemy enemy) {
        return !(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy) || seismicSense;
    }

    @Override
    public void displayMain() {
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
              (int) barrelLength, getRange(), angle, shockwaveWidth, getDamage(), this));
        } else {
            fireParticles(a);
            shockwaves.add(new SeismicShockwave(p, position.x, position.y, 0, getRange(), angle, shockwaveWidth,
              getDamage(), this));
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
        upgradePrices[0] = 200;
        upgradePrices[1] = 200;
        upgradePrices[2] = 1500;
        upgradePrices[3] = 250;
        upgradePrices[4] = 300;
        upgradePrices[5] = 1000;
        //titles
        upgradeTitles[0] = "Faster Firing";
        upgradeTitles[1] = "Larger AOE";
        upgradeTitles[2] = "360 Wave";
        upgradeTitles[3] = "Longer Range";
        upgradeTitles[4] = "Damage Boost";
        upgradeTitles[5] = "Seismic Sense";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "area of";
        upgradeDescC[1] = "effect";

        upgradeDescA[2] = "Shockwave";
        upgradeDescB[2] = "encircles";
        upgradeDescC[2] = "tower";


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
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[22];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 1.1f;
                    break;
                case 1:
                    shockwaveWidth += 30;
                    break;
                case 2:
                    material = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    shockwaveWidth = 720;
                    delay = 0.3f;
                    name = "seismicSlammer";
                    hasPriority = false;
                    selection.swapSelected(this);
                    titleLines = new String[]{"Seismic", "Slammer"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Shockwave", o);
                        selection.setTextPurple("360 degrees", o);
                    };
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 50;
                    break;
                case 4:
                    damage += 50;
                    break;
                case 5:
                    material = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    seismicSense = true;
                    effectLevel = 0;
                    effectDuration = 2;
                    shockwaveWidth -= 40;
                    range += 100;
                    damage += 150;
                    name = "seismicSniper";
                    titleLines = new String[]{"Seismic Sniper"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Shockwave", o);
                        selection.setTextPurple("Stuns burrowers", o);
                    };
                    loadSprites();
                    break;
            }
        }
    }
}
