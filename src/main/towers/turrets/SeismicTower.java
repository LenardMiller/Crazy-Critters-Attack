package main.towers.turrets;

import main.enemies.Enemy;
import main.misc.Tile;
import main.particles.BuffParticle;
import main.projectiles.Shockwave;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static processing.core.PConstants.HALF_PI;

public class SeismicTower extends Turret {

    private float shockwaveWidth;
    private boolean seismicSense;

    public SeismicTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "seismic";
        size = new PVector(50,50);
        offset = 4;
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 2.5f; //default: 200 frames
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        pjSpeed = 400;
        betweenFireFrames = down60ToFramerate(1);
        state = 0;
        frame = 0;
        loadDelay = 0;
        loadDelayTime = 0;
        damage = 50;
        range = 225;
        shockwaveWidth = 60;
        seismicSense = false;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("seismicSlam");
        fireParticle = null;
        barrelLength = 29;
        loadSprites();
        debrisType = "stone";
        price = SEISMIC_PRICE;
        value = price;
        priority = 0; //close
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && state != 1 && shockwaveWidth < 360) aim(targetEnemy);
        if (state == 0 && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            state = 1;
            frame = 0;
        }
        if (state == 1 && frame == fireFrames.length - 1) fire(barrelLength, fireParticle);
    }

    protected void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float finalDist;
        if (priority == 0) finalDist = 1000000;
        else finalDist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!enemy.stealthMode || seismicSense) {
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.position.x > 0 && enemy.position.x < 900 && enemy.position.y > 0 && enemy.position.y < 900 && dist < range) {
                    if (priority == 0 && dist < finalDist) { //close
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 1 && dist > finalDist) { //far
                        e = enemy;
                        finalDist = dist;
                    }
                    if (priority == 2) {
                        if (enemy.maxHp > maxHp) { //strong
                            e = enemy;
                            finalDist = dist;
                            maxHp = enemy.maxHp;
                        } else if (enemy.maxHp == maxHp && dist < finalDist) { //strong -> close
                            e = enemy;
                            finalDist = dist;
                        }
                    }
                }
            }
        }
        targetEnemy = e;
    }

    public void displayPassB2() {
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

    protected void spawnProjectile(PVector position, float angle) {
        float a = angle;
        if (shockwaveWidth >= 360) {
            a = 0;
            for (int i = 0; i < 6; i++) {
                fireParticles(a);
                a += TWO_PI / 6;
            }
            shockwaves.add(new Shockwave(p, tile.position.x - size.x / 2, tile.position.y - size.y / 2,
                    range, angle, shockwaveWidth, damage, this, false, false));
        } else {
            fireParticles(a);
            shockwaves.add(new Shockwave(p, position.x, position.y, range, angle, shockwaveWidth, damage, this,
                    seismicSense, false));
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
            particles.add(new BuffParticle(p, spp2.x, spp2.y, a + radians(p.random(-45, 45)), part));
        }
        particleCount = p.random(1, 5);
        for (int i = 0; i < particleCount; i++) {
            PVector spa2 = PVector.fromAngle(a - HALF_PI + radians(p.random(-20, 20)));
            spa2.setMag(-5);
            PVector spp2 = new PVector(spp.x, spp.y);
            spp2.add(spa2);
            particles.add(new BuffParticle(p, spp2.x, spp2.y, p.random(0, 360), part));
        }
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 200;
        upgradePrices[1] = 200;
        upgradePrices[2] = 1500;
        upgradePrices[3] = 250;
        upgradePrices[4] = 300;
        upgradePrices[5] = 800;
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

        upgradeDescA[4] = "+30";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Stun";
        upgradeDescB[5] = "stealthy";
        upgradeDescC[5] = "enemies";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[20];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[21];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[22];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 1.1f;
                    break;
                case 1:
                    shockwaveWidth += 30;
                    break;
                case 2:
                    debrisType = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    shockwaveWidth = 720;
                    delay = 0.3f;
                    name = "seismicSlammer";
                    hasPriority = false;
                    selection.swapSelected(id);
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
                    debrisType = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    seismicSense = true;
                    effectLevel = 0;
                    effectDuration = 2;
                    shockwaveWidth -= 40;
                    range += 50;
                    damage += 150;
                    name = "seismicSniper";
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}
