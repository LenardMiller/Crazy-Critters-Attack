package main.towers.turrets;

import main.projectiles.arcs.Arc;
import main.projectiles.arcs.DemonArc;
import main.projectiles.arcs.RedArc;
import main.projectiles.shockwaves.LightningShockwave;
import main.misc.Tile;
import main.sound.SoundWithAlts;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.randomizeDelay;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class TeslaTower extends Turret {

    public int arcLength;

    private boolean lightning;
    private boolean highPower;

    private final SoundWithAlts thunderSound;

    public TeslaTower(PApplet p, Tile tile) {
        super(p,tile);
        name = "tesla";
        delay = randomizeDelay(p, 3);
        damage = 300;
        arcLength = 3;
        pjSpeed = -1;
        range = 225;
        betweenIdleFrames = down60ToFramerate(3);
        material = Material.metal;
        basePrice = TESLA_TOWER_PRICE;
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("teslaFire");
        thunderSound = soundsWithAlts.get("thunder");
        titleLines = new String[]{"Tesla Tower"};
        infoDisplay = (o) -> {
            selection.setTextPurple("Jumping electricity", o);
            jumpInfo(o, 1);
        };
    }

    @Override
    protected void checkTarget() {
        getTargetEnemy();
        if (state == State.Idle && targetEnemy != null) { //if done animating
            state = State.Fire;
            frame = 0;
            fire();
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {}

    protected void fire() {
        if (lightning) {
            thunderSound.playRandomWithRandomSpeed(1);
            PVector targetPosition = new PVector(targetEnemy.position.x, targetEnemy.position.y);
            PVector myPosition = new PVector(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
            shockwaves.add(new LightningShockwave(p, targetPosition.x, targetPosition.y, 150, damage, this));
            //damaging arcs
            for (int i = 0; i < 3; i++) {
                arcs.add(new Arc(p, targetPosition.x, targetPosition.y, this, getDamage() / 2, arcLength, 600, Priority.values()[i], targetEnemy));
            } //decorative arcs
            for (int i = 0; i < p.random(5, 10); i++) {
                arcs.add(new Arc(p, targetPosition.x, targetPosition.y, this, 0, arcLength, 200, Priority.None));
            } //decorative self arcs
            for (int i = 0; i < 3; i++) {
                arcs.add(new Arc(p, myPosition.x, myPosition.y, this, 0, arcLength, 100, Priority.None));
            }
        } else if (!highPower) {
            playSoundRandomSpeed(p, fireSound, 1);
            PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
            arcs.add(new Arc(p, position.x, position.y, this, getDamage(), arcLength, getRange(), priority));
        }
    }

    @Override
    public void update() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        updateBoosts();
        if (highPower && !paused && alive) {
            PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
            arcs.add(new DemonArc(p, position.x, position.y, this, getDamage(), arcLength, getRange(), priority));
        } else {
            if (enemies.size() > 0 && !machine.dead && !paused) checkTarget();
        }
        if (p.mousePressed && boardMousePosition.x < tile.position.x && boardMousePosition.x > tile.position.x - size.x && boardMousePosition.y < tile.position.y
                && boardMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    @Override
    public void displayTop() {
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

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 800;
        upgradePrices[1] = 1000;
        upgradePrices[2] = 12_000;

        upgradePrices[3] = 600;
        upgradePrices[4] = 1200;
        upgradePrices[5] = 10_000;
        //titles
        upgradeTitles[0] = "Longer Arcs";
        upgradeTitles[1] = "Longer Arcs";
        upgradeTitles[2] = "Call Lightning";

        upgradeTitles[3] = "Faster Recharge";
        upgradeTitles[4] = "High Voltage";
        upgradeTitles[5] = "Perma-arc";
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

        upgradeDescA[5] = "Vastly";
        upgradeDescB[5] = "increase";
        upgradeDescC[5] = "firerate";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[1];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[2];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[33];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[34];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[59];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0, 1 -> {
                    arcLength++;
                    range += 25;
                } case 2 -> {
                    range = 5000;
                    damage += 2500;
                    delay += 4;
                    arcLength++;
                    lightning = true;
                    material = Material.crystal;
                    placeSound = sounds.get("crystalPlace");
                    damageSound = sounds.get("crystalDamage");
                    breakSound = sounds.get("crystalBreak");
                    name = "lightning";
                    betweenFireFrames = 2;
                    titleLines = new String[]{"Lightning Caller"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Jumping electricity", o);
                        selection.setTextPurple("Splash", o);
                        jumpInfo(o, 2);
                    };
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> delay -= 1;
                case 4 -> damage += 200;
                case 5 -> {
                    delay = 0.2f;
                    highPower = true;
                    betweenIdleFrames = 3;
                    damage /= 8;
                    name = "highPowerTesla";
                    material = Material.darkMetal;
                    titleLines = new String[]{"The Demon", "Circuit"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Jumping Electricity", o);
                        jumpInfo(o, 1);
                    };
                    loadSprites();
                }
            }
        }
    }

    private void jumpInfo(int offset, int purpleCount) {
        p.fill(new Color(100, 150, 255).getRGB(), 254);
        p.text("Jumps: " + arcLength, 910, 356 + 20 * purpleCount + offset);
    }
}