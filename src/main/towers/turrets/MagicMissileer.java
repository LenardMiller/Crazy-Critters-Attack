package main.towers.turrets;

import main.misc.Tile;
import main.projectiles.MagicMissile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.playSoundRandomSpeed;
import static main.misc.WallSpecialVisuals.updateTowerArray;

public class MagicMissileer extends Turret {

    public boolean additionalMissile;

    public MagicMissileer(PApplet p, Tile tile) {
        super(p,tile);
        name = "magicMissleer";
        size = new PVector(50,50);
        hasPriority = false;
        maxHp = 20;
        hp = maxHp;
        delay = 3.3f;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        damage = 150;
        pjSpeed = 300;
        range = 300;
        betweenIdleFrames = down60ToFramerate(8);
        state = 0;
        loadSprites();
        fireSound = sounds.get("magicMissleer");
        debrisType = "crystal";
        price = 300;
        value = price;
        priority = 2; //strong
        setUpgrades();
        updateTowerArray();

        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
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
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 0,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 1,tile.position));
        projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, 2,tile.position));
        if (additionalMissile) {
            projectiles.add(new MagicMissile(p,p.random(tile.position.x-size.x,tile.position.x),p.random(tile.position.y-size.y,tile.position.y), p.random(0,TWO_PI), this, damage, (int)(p.random(0,2.99f)),tile.position));
        }
    }

    private void fire() {
        playSoundRandomSpeed(p, fireSound, 1);
        spawnProjectiles(new PVector(0,0), angle);
    }

    @Override
    public void displayMain() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(fireFrames[0],-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 200;

        upgradePrices[3] = 50;
        upgradePrices[4] = 100;
        upgradePrices[5] = 200;
        //titles
        upgradeTitles[0] = "More range";
        upgradeTitles[1] = "More magic";
        upgradeTitles[2] = "shatter?";

        upgradeTitles[3] = "Faster Firing";
        upgradeTitles[4] = "More Missiles";
        upgradeTitles[5] = "swarm?";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "range";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "split";
        upgradeDescB[2] = "into";
        upgradeDescC[2] = "bits";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "firerate";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Fire an";
        upgradeDescB[4] = "additional";
        upgradeDescC[4] = "missile";

        upgradeDescA[5] = "Fire";
        upgradeDescB[5] = "many";
        upgradeDescC[5] = "missles";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[24];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[14];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[34];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    range += 100;
                    break;
                case 1:
                    damage += 100;
                    break;
                case 2:
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
                    break;
            }
        }
    }
}