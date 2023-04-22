package main.towers.turrets;

import main.projectiles.arcs.YellowArc;
import main.projectiles.homing.ElectricMissile;
import main.projectiles.homing.MagicMissile;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

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

        final PImage idleSprite;
        final PImage[] fireAnimation;
        final PImage[] loadAnimation;

        ElectricComponent(PImage idle, PImage[] fire, PImage[] load) {
            idleSprite = idle;
            fireAnimation = fire;
            loadAnimation = load;
        }

        PImage getSprite(Turret.State state, int frame) {
            switch (state) {
                case Idle:
                    return idleSprite;
                case Fire:
                    return fireAnimation[frame];
                case Load:
                    return loadAnimation[frame];
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
        material = Material.crystal;
        basePrice = MAGIC_MISSILEER_PRICE;
        priority = Priority.Strong;
        titleLines = new String[]{"Magic Missile", "Launcher"};
        infoDisplay = (o) -> {
            selection.setTextPurple("Homing", o);
            missileCountInfo(o);
        };
    }

    @Override
    protected void loadSprites() {
        if (name.equals("electricMissleer")) {
            sBase = staticSprites.get("magicMissleerBaseTR");
            idleSprite = staticSprites.get(name + "IdleTR");
            idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};

            fireFrames = animatedSprites.get(name + "CoreFireTR");
            loadFrames = animatedSprites.get(name + "CoreLoadTR");
            return;
        }
        String tempName = name;
        if (tempName.equals("magicSwarm")) tempName = "magicMissleer";
        sBase = staticSprites.get(tempName + "BaseTR");
        idleSprite = staticSprites.get(tempName + "IdleTR");
        fireFrames = animatedSprites.get(tempName + "FireTR");
        loadFrames = animatedSprites.get(tempName + "LoadTR");
        if (animatedSprites.get(tempName + "IdleTR") != null) {
            idleFrames = animatedSprites.get(tempName + "IdleTR");
            idleSprite = idleFrames[0];
        }
        else idleFrames = new PImage[]{staticSprites.get(tempName + "IdleTR")};
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
    protected void spawnProjectiles(PVector position, float angle) {
        if (name.equals("magicSwarm")) {
            for (int i = 0; i < 12; i++) {
                projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),
                  p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this,
                  getDamage(), Turret.Priority.values()[(int) p.random(3)], tile.position));
            }
        } else if (name.equals("electricMissleer")) {
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Close, tile.position, effectDuration, effectLevel));
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Far, tile.position, effectDuration, effectLevel));
            projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Strong, tile.position, effectDuration, effectLevel));
            if (additionalMissile) {
                projectiles.add(new ElectricMissile(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
                  getDamage(), Turret.Priority.values()[(int) p.random(3)], tile.position, effectDuration, effectLevel));
            }
        } else {
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Close, tile.position));
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Far, tile.position));
            projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
              p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
              getDamage(), Priority.Strong, tile.position));
            if (additionalMissile) {
                projectiles.add(new MagicMissile(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(0, TWO_PI), this,
                  getDamage(),Turret.Priority.values()[(int) p.random(3)], tile.position));
            }
        }
    }

    private void fire() {
        playSoundRandomSpeed(p, fireSound, 1);
        spawnProjectiles(new PVector(0,0), angle);
        if (name.equals("electricMissleer")) {
            for (int i = 0; i < 3; i++) {
                arcs.add(new YellowArc(p, getCenter().x, getCenter().y, this, 0, 0, (int) p.random(20, 100), Priority.None));
            }
        }
    }

    @Override
    public void displayBase() {
        if (name.equals("electricMissleer")) return;
        p.tint(255, tintColor, tintColor);
        if (sBase != null) p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    @Override
    public void displayTop() {
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
            p.image(ElectricComponent.Core.idleSprite, (-size.x/2-offset),-size.y/2-offset);
            p.pushMatrix();
            p.rotate(specialAngle);
            p.image(ElectricComponent.InnerRing.idleSprite, (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
            p.pushMatrix();
            p.rotate(-specialAngle);
            p.image(ElectricComponent.OuterRing.idleSprite, (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
        } else p.image(fireFrames[5],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (name.equals("magicSwarm") && sprite != null) {
            for (int i = 0; i < 3; i++) {
                p.pushMatrix();
                p.rotate(specialAngle + (i * (TWO_PI / 3)));
                p.image(sprite,(-size.x/2-offset) + displacement,-size.y/2-offset);
                p.popMatrix();
            }
        } else if (name.equals("electricMissleer")) {
            int frame = this.frame;
            if (state == State.Load) frame = compressedLoadFrames.get(this.frame);

            p.image(ElectricComponent.Core.getSprite(state, frame), (-size.x/2-offset),-size.y/2-offset);
            p.pushMatrix();
            p.rotate(specialAngle);
            p.image(ElectricComponent.InnerRing.getSprite(state, frame), (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
            p.pushMatrix();
            p.rotate(-specialAngle);
            p.image(ElectricComponent.OuterRing.getSprite(state, frame), (-size.x/2-offset),-size.y/2-offset);
            p.popMatrix();
        } else if (sprite != null) p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);

        if (!paused) {
            float specialRotationSpeed = 0.01f;
            if (name.equals("electricMissleer")) {
                switch (state) {
                    case Idle:
                        specialRotationSpeed = 0.03f;
                        break;
                    case Fire:
                        specialRotationSpeed = 0.03f * (1 - (frame / (float) fireFrames.length));
                        break;
                    case Load:
                        specialRotationSpeed = 0.03f * (frame / (float) compressedLoadFrames.size());
                        break;
                }

                if (p.random(25) < 1 && state == State.Idle)
                    arcs.add(new YellowArc(p, getCenter().x, getCenter().y, this, 0, 0, (int) p.random(20, 100), Priority.None));
            }
            if (specialAngle < TWO_PI) specialAngle += specialRotationSpeed;
            else specialAngle = 0;

        }
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 750;
        upgradePrices[1] = 1500;
        upgradePrices[2] = 40000;

        upgradePrices[3] = 1000;
        upgradePrices[4] = 2000;
        upgradePrices[5] = 30000;
        //titles
        upgradeTitles[0] = "More Range";
        upgradeTitles[1] = "More Magic";
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
        upgradeDescB[2] = "critters";
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
    protected void upgradeEffect(int id) {
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
                    damage *= 5f;
                    name = "electricMissleer";
                    material = Material.darkMetal;
                    placeSound = sounds.get("titaniumPlace");
                    breakSound = sounds.get("titaniumBreak");
                    damageSound = sounds.get("titaniumDamage");
                    titleLines = new String[]{"Electrified Missile", "Launcher"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Homing", o);
                        selection.setTextPurple("Electrifies critters", o);
                        missileCountInfo(o);
                    };
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
                    titleLines = new String[]{"Magic Missile", "Swarm"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Homing", o);
                        selection.setTextPurple("Twelve missiles", o);
                    };
                    break;
            }
        }
    }

    private void missileCountInfo(int offset) {
        if (additionalMissile) selection.setTextPurple("Four missiles", offset);
        else selection.setTextPurple("Three missiles", offset);
    }
}