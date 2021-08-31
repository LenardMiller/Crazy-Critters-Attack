package main.towers.turrets;

import main.damagingThings.arcs.YellowArc;
import main.damagingThings.projectiles.homing.ElectricMissile;
import main.damagingThings.projectiles.homing.MagicMissile;
import main.misc.CompressArray;
import main.misc.Tile;
import main.particles.Ouch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class MagicMissileer extends Turret {

    public boolean additionalMissile;

    private float specialAngle;

    private enum ElectricComponent {
        OuterRing(
                staticSprites.get("electricMissleerOuterRingIdleTR"),
                animatedSprites.get("electricMissleerOuterRingFireTR"),
                animatedSprites.get("electricMissleerOuterRingLoadTR")),
        InnerRing(
                staticSprites.get("electricMissleerInnerRingIdleTR"),
                animatedSprites.get("electricMissleerInnerRingFireTR"),
                animatedSprites.get("electricMissleerInnerRingLoadTR")),
        Core(
                staticSprites.get("electricMissleerCoreIdleTR"),
                animatedSprites.get("electricMissleerCoreFireTR"),
                animatedSprites.get("electricMissleerCoreLoadTR"));

        final PImage IDLE;
        final PImage[] FIRE;
        final PImage[] LOAD;

        ElectricComponent(PImage idle, PImage[] fire, PImage[] load) {
            IDLE = idle;
            FIRE = fire;
            LOAD = load;
        }

        PImage getSprite(int state, int frame, int compressFrame) {
            switch (state) {
                case 0:
                    return IDLE;
                case 1:
                    return FIRE[frame];
                case 2:
                    return LOAD[compressFrame];
                default:
                    return null;
            }
        }
    }

    public MagicMissileer(PApplet p, Tile tile) {
        super(p,tile);
        name = "magicMissleer";
        size = new PVector(50,50);
        hasPriority = false;
        delay = randomizeDelay(p, 3);
        damage = 600;
        pjSpeed = 300;
        range = 200;
        betweenIdleFrames = down60ToFramerate(8);
        fireSound = sounds.get("magicMissleer");
        placeSound = sounds.get("crystalPlace");
        damageSound = sounds.get("crystalDamage");
        breakSound = sounds.get("crystalBreak");
        debrisType = "crystal";
        price = MAGIC_MISSILEER_PRICE;
        value = price;
        priority = 2; //strong

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void loadSprites() {
        if (name.equals("electricMissleer")) {
            sBase = staticSprites.get("magicMissleerBaseTR");
            sIdle = staticSprites.get(name + "IdleTR");
            idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};

            fireFrames = animatedSprites.get(name + "CoreFireTR");
            loadFrames = animatedSprites.get(name + "CoreLoadTR");
            return;
        }
        sBase = staticSprites.get(name + "BaseTR");
        sIdle = staticSprites.get(name + "IdleTR");
        fireFrames = animatedSprites.get(name + "FireTR");
        loadFrames = animatedSprites.get(name + "LoadTR");
        if (animatedSprites.get(name + "IdleTR") != null) {
            idleFrames = animatedSprites.get(name + "IdleTR");
            sIdle = idleFrames[0];
        }
        else idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};
    }

    @Override
    protected void checkTarget() {
        getTargetEnemy();
        if (state == 0 && targetEnemy != null) { //if done animating
            state = 1;
            frame = 0;
            fire();
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (name.equals("magicSwarm")) {
            for (int i = 0; i < 12; i++) {
                projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),
                  p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this,
                  getDamage(), (int)(p.random(0,2.99f)),tile.position));
            }
        } else if (name.equals("electricMissleer")) {
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 0, tile.position, effectDuration, effectLevel));
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 1, tile.position, effectDuration, effectLevel));
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 2, tile.position, effectDuration, effectLevel));
            if (additionalMissile) {
                projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
                  getDamage(), (int) (p.random(0, 2.99f)), tile.position, effectDuration, effectLevel));
            }
        } else {
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 0, tile.position));
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 1, tile.position));
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), 2, tile.position));
            if (additionalMissile) {
                projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
                  getDamage(), (int) (p.random(0, 2.99f)), tile.position));
            }
        }
    }

    private void fire() {
        playSoundRandomSpeed(p, fireSound, 1);
        spawnProjectiles(new PVector(0,0), angle);
        if (name.equals("electricMissleer")) {
            for (int i = 0; i < 3; i++) {
                arcs.add(new YellowArc(p, getCenter().x, getCenter().y, this, 0, 0, (int) p.random(20, 100), -1));
            }
        }
    }

    @Override
    public void displayBase() {
        if (name.equals("electricMissleer")) return;
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    @Override
    public void displayMain() {
        int displacement = 20;
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        if (name.equals("magicSwarm")) {
            for (int i = 0; i < 3; i++) {
                p.pushMatrix();
                p.rotate(specialAngle + (i * (TWO_PI / 3)));
                p.image(fireFrames[5],(-size.x/2-offset) + displacement,-size.y/2-offset);
                p.popMatrix();
            }
        } else if (name.equals("electricMissleer")) {
            p.image(ElectricComponent.Core.IDLE, (-size.x/2-offset),-size.y/2-offset);
            p.pushMatrix();
            p.rotate(specialAngle);
            p.image(ElectricComponent.InnerRing.IDLE, (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
            p.pushMatrix();
            p.rotate(-specialAngle);
            p.image(ElectricComponent.OuterRing.IDLE, (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
        } else p.image(fireFrames[5],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (name.equals("magicSwarm")) {
            for (int i = 0; i < 3; i++) {
                p.pushMatrix();
                p.rotate(specialAngle + (i * (TWO_PI / 3)));
                p.image(sprite,(-size.x/2-offset) + displacement,-size.y/2-offset);
                p.popMatrix();
            }
        } else if (name.equals("electricMissleer")) {
            int compressFrame = 0;
            if (state == 2) compressFrame = spriteArray.get(frame);

            p.image(ElectricComponent.Core.getSprite(state, frame, compressFrame), (-size.x/2-offset),-size.y/2-offset);
            p.pushMatrix();
            p.rotate(specialAngle);
            p.image(ElectricComponent.InnerRing.getSprite(state, frame, compressFrame), (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
            p.pushMatrix();
            p.rotate(-specialAngle);
            p.image(ElectricComponent.OuterRing.getSprite(state, frame, compressFrame), (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
        } else p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);

        if (!paused) {
            float specialRotationSpeed = 0.01f;
            if (name.equals("electricMissleer")) {
                switch (state) {
                    case 0:
                        specialRotationSpeed = 0.03f;
                        break;
                    case 1:
                        specialRotationSpeed = 0.03f * (1 - (frame / (float) fireFrames.length));
                        break;
                    case 2:
                        specialRotationSpeed = 0.03f * (frame / (float) spriteArray.size());
                }

                if (p.random(25) < 1 && state == 0)
                    arcs.add(new YellowArc(p, getCenter().x, getCenter().y, this, 0, 0, (int) p.random(20, 100), -1));
            }
            if (specialAngle < TWO_PI) specialAngle += specialRotationSpeed;
            else specialAngle = 0;

        }
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 750;
        upgradePrices[1] = 1250;
        upgradePrices[2] = 40000;

        upgradePrices[3] = 1250;
        upgradePrices[4] = 1500;
        upgradePrices[5] = 25000;
        //titles
        upgradeTitles[0] = "More range";
        upgradeTitles[1] = "More magic";
        upgradeTitles[2] = "Electrifying";

        upgradeTitles[3] = "Faster Firing";
        upgradeTitles[4] = "More Missiles";
        upgradeTitles[5] = "Missile Swarm";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Electrifies";
        upgradeDescB[2] = "enemies";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Fire an";
        upgradeDescB[4] = "additional";
        upgradeDescC[4] = "missile";

        upgradeDescA[5] = "Fire a";
        upgradeDescB[5] = "swarm of";
        upgradeDescC[5] = "missiles";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[40];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[14];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[39];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 50;
                    break;
                case 1:
                    damage += 400;
                    break;
                case 2:
                    effectLevel = 5000;
                    effectDuration = 10;
                    damage *= 2.5f;
                    name = "electricMissleer";
                    debrisType = "darkMetal";
                    placeSound = sounds.get("titaniumPlace");
                    breakSound = sounds.get("titaniumBreak");
                    damageSound = sounds.get("titaniumDamage");
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    delay -= 1;
                    break;
                case 4:
                    additionalMissile = true;
                    break;
                case 5:
                    range += 50;
                    delay -= 0.5f;
                    name = "magicSwarm";
                    break;
            }
        }
    }
}