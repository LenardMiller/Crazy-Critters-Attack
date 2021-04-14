package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.Arc;
import main.projectiles.RedArc;
import main.shockwaves.LightningShockwave;
import main.sound.SoundWithAlts;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class TeslaTower extends Turret {

    public int arcLength;

    private boolean lightning;
    private boolean highPower;

    private final SoundWithAlts THUNDER_SOUND;

    public TeslaTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "tesla";
        size = new PVector(50,50);
        maxHp = 20;
        hp = maxHp;
        hit = false;
        delay = 3;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        damage = 250;
        arcLength = 3;
        pjSpeed = -1;
        range = 200;
        betweenIdleFrames = down60ToFramerate(3);
        state = 0;
        loadSprites();
        debrisType = "metal";
        price = TESLATOWER_PRICE;
        value = price;
        priority = 0; //close
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("teslaFire");
        THUNDER_SOUND = soundsWithAlts.get("thunder");
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire();
        }
    }

    protected void fire() {
        if (lightning) {
            THUNDER_SOUND.playRandomWithRandomSpeed(1);
            PVector targetPosition = new PVector(targetEnemy.position.x, targetEnemy.position.y);
            PVector myPosition = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            shockwaves.add(new LightningShockwave(p, targetPosition.x, targetPosition.y, 100, damage, this));
            //damaging arcs
            for (int i = 0; i < 3; i++) {
                arcs.add(new Arc(p, targetPosition.x, targetPosition.y, this, damage / 2, arcLength, 300, i, targetEnemy));
            } //decorative arcs
            for (int i = 0; i < p.random(5, 10); i++) {
                arcs.add(new Arc(p, targetPosition.x, targetPosition.y, this, 0, arcLength, 100, -1));
            } //decorative self arcs
            for (int i = 0; i < 3; i++) {
                arcs.add(new Arc(p, myPosition.x, myPosition.y, this, 0, arcLength, 50, -1));
            }
        } else if (highPower) {
            playSoundRandomSpeed(p, fireSound, 1);
            PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
            arcs.add(new RedArc(p, position.x, position.y, this, damage, arcLength, range, priority));
        }
        else {
            playSoundRandomSpeed(p, fireSound, 1);
            PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
            arcs.add(new Arc(p, position.x, position.y, this, damage, arcLength, range, priority));
        }
    }

    public void displayMain() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        //using this sprite so that static doesn't have a shadow
        p.image(loadFrames[0],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        else p.image(loadFrames[loadFrames.length - 1],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 600;
        upgradePrices[1] = 700;
        upgradePrices[2] = 7000;

        upgradePrices[3] = 400;
        upgradePrices[4] = 800;
        upgradePrices[5] = 8000;
        //titles
        upgradeTitles[0] = "Farther Jumping";
        upgradeTitles[1] = "Farther Jumping";
        upgradeTitles[2] = "Lightning";

        upgradeTitles[3] = "Battery Size";
        upgradeTitles[4] = "Shocking";
        upgradeTitles[5] = "High Energy";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "jump";
        upgradeDescC[0] = "distance";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "jump";
        upgradeDescC[1] = "distance";

        upgradeDescA[2] = "Calls";
        upgradeDescB[2] = "lightning";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "recharge";
        upgradeDescC[3] = "rate";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "vastly"; //todo: cap
        upgradeDescB[5] = "increase";
        upgradeDescC[5] = "firerate";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[1];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[2];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[33];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[34];
    }

    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                case 1:
                    arcLength++;
                    range += 25;
                    break;
                case 2:
                    range = 1000;
                    damage += 1600;
                    delay += 2;
                    lightning = true;
                    name = "lightning";
                    betweenFireFrames = 2;
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    delay -= 0.6f;
                    break;
                case 4:
                    damage += 150;
                    break;
                case 5:
                    delay = 0;
                    range += 50;
                    damage += 50;
                    highPower = true;
                    betweenIdleFrames = 3;
                    name = "highPowerTesla";
                    debrisType = "darkMetal";
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}