package main.towers.turrets;

import main.projectiles.Needle;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.randomizeDelay;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Nightmare extends Turret {

    private int numProjectiles;

    public Nightmare(PApplet p, Tile tile) {
        super(p,tile);
        name = "nightmare";
        delay = randomizeDelay(p, 3.5f);
        pjSpeed = 1000;
        range = 200;
        damage = 0;
        numProjectiles = 5;
        effectLevel = 1000;
        effectDuration = 3.6f;
        fireParticle = "decay";
        barrelLength = 20;
        loadSprites();
        material = "darkMetal";
        basePrice = NIGHTMARE_PRICE;
        priority = Priority.Strong;
        titleLines = new String[]{"Nightmare", "Shotgun"};
        infoDisplay = (o) -> {
            selection.setTextPurple("Decay", o);
            selection.setTextPurple(numProjectiles + " needles", o);
        };

        placeSound = sounds.get("titaniumPlace");
        breakSound = sounds.get("titaniumBreak");
        damageSound = sounds.get("titaniumDamage");
        fireSound = sounds.get("nightmareFire");
    }

    @Override
    protected void fire(float barrelLength, String particleType) {
        float angleDelta = PApplet.radians(10);
        playSoundRandomSpeed(p, fireSound, 1);
        for (int i = 0; i < numProjectiles; i++) {
            int num = ceil(i - numProjectiles / 2f);
            PVector spp = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
            PVector spa = PVector.fromAngle(angle-HALF_PI);
            spa.setMag(20);
            spp.add(spa);
            spawnProjectiles(spp, angle + num * angleDelta);
        }
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        projectiles.add(new Needle(p, position.x, position.y, angle, this, getDamage(), (int) effectLevel,
                effectDuration, range));
        for (int j = 0; j < 3; j++) {
            PVector spa2 = PVector.fromAngle(angle-HALF_PI+radians(p.random(-20,20)));
            spa2.setMag(-2);
            PVector spp2 = new PVector(position.x,position.y);
            spp2.add(spa2);
            midParticles.add(new MiscParticle(p,spp2.x,spp2.y,angle+radians(p.random(-45,45)),"decay"));
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 2500;
        upgradePrices[1] = 6000;
        upgradePrices[2] = 100000;

        upgradePrices[3] = 2000;
        upgradePrices[4] = 8000;
        upgradePrices[5] = 100000;
        //titles
        upgradeTitles[0] = "Firerate";
        upgradeTitles[1] = "More Needles";
        upgradeTitles[2] = "Dark Cloud";

        upgradeTitles[3] = "Range";
        upgradeTitles[4] = "Effect Power";
        upgradeTitles[5] = "Impalement";
        //descriptions
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Fire more";
        upgradeDescB[1] = "projectiles";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "whoosh";
        upgradeDescB[2] = "";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "damage &";
        upgradeDescC[4] = "duration";

        upgradeDescA[5] = "owie";
        upgradeDescB[5] = "";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[4];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[4];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[3];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[3];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 1;
                    break;
                case 1:
                    numProjectiles += 3;
                    break;
                case 2:
                    numProjectiles += 10;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 40;
                    break;
                case 4:
                    effectDuration += 3;
                    effectLevel += 1000;
                    break;
                case 5:
                    effectDuration += 5;
                    effectLevel += 1100;
                    break;
            }
        }
    }
}