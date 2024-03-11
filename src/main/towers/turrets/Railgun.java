package main.towers.turrets;

import main.misc.Tile;
import main.particles.RailgunBlast;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Railgun extends Turret {

    public static String pid = "T1-5000-50000-12";
    public static String description =
            "Fires a hypersonic bolt of plasma at the toughest critter onscreen, doing extreme damage.";
    public static char shortcut = 'T';
    public static String title1 = "Railgun";
    public static String title2 = null;
    public static int price = 25_000;

    private PImage[] vaporTrail;
    private PImage[] vaporEndSprites;
    private final int NUM_VAPOR_FRAMES;
    private final int BETWEEN_VAPOR_FRAMES;
    private int betweenVaporTimer;
    private int currentVaporFrame;
    private PVector vaporStart;
    private float vaporAngle;
    private int vaporLength;
    private PVector vaporPartLength;

    public Railgun(PApplet p, Tile tile) {
        super(p,tile);
        name = "railgun";
        offset = 6;
        hit = false;
        delay = 12;
        damage = 50000;
        pjSpeed = -1;
        range = 5000;
        NUM_VAPOR_FRAMES = 15;
        BETWEEN_VAPOR_FRAMES = down60ToFramerate(3);
        vaporTrail = new PImage[NUM_VAPOR_FRAMES];
        vaporEndSprites = new PImage[11];
        currentVaporFrame = 16;
        betweenIdleFrames = 3;
        betweenFireFrames = 3;
        vaporTrail = animatedSprites.get("railgunVaporTrailTR");
        vaporEndSprites = animatedSprites.get("railgunBlastPT");
        material = Material.titanium;
        barrelLength = 30;
        basePrice = price;
        priority = Priority.Strong;
        titleLines = new String[]{"Railgun"};

        placeSound = sounds.get("titaniumPlace");
        breakSound = sounds.get("titaniumBreak");
        damageSound = sounds.get("titaniumDamage");
        fireSound = sounds.get("railgun");
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        topParticles.add(new RailgunBlast(p,position.x,position.y,0));
        playSoundRandomSpeed(p, fireSound, 1);

        currentVaporFrame = 0;
        vaporStart = position;
        PVector vaporEnd = targetEnemy.position;
        vaporAngle = angle;
        float c = sqrt(sq(vaporEnd.x-vaporStart.x)+sq(vaporEnd.y-vaporStart.y));
        vaporLength = (int)(c/24);
        vaporPartLength = PVector.fromAngle(vaporAngle - radians(90));
        vaporPartLength.setMag(24);

        targetEnemy.damageWithoutBuff(getDamage(),this, null, PVector.fromAngle(vaporAngle - HALF_PI), true);
    }

    @Override
    public void displayTop() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.tint(255);
        p.popMatrix();
        //vaporTrail
        displayVaporTrail();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.popMatrix();
        p.tint(255);
    }

    private void displayVaporTrail() {
        if (currentVaporFrame < NUM_VAPOR_FRAMES) {
            for (int i = 0; i <= vaporLength; i++) {
                p.pushMatrix();
                float x = vaporStart.x + (vaporPartLength.x*i);
                float y = vaporStart.y + (vaporPartLength.y*i);
                p.translate(x, y);
                p.rotate(vaporAngle);
                p.image(vaporTrail[currentVaporFrame], -2, 0);
                if (i == vaporLength && currentVaporFrame < 11) p.image(vaporEndSprites[currentVaporFrame], -13, -15);
                p.popMatrix();
            }
            if (betweenVaporTimer < BETWEEN_VAPOR_FRAMES) betweenVaporTimer++;
            else {
                currentVaporFrame++;
                betweenVaporTimer = 0;
            }
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 5000;
        upgradePrices[1] = 8000;
        upgradePrices[2] = 250000;

        upgradePrices[3] = 7500;
        upgradePrices[4] = 10000;
        upgradePrices[5] = 300000;
        //titles
        upgradeTitles[0] = "Heat sinks";
        upgradeTitles[1] = "Coil gun";
        upgradeTitles[2] = "Remote Strike";

        upgradeTitles[3] = "High energy";
        upgradeTitles[4] = "Higher energy";
        upgradeTitles[5] = "Annihilate";
        //desc line one
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "firerate";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Further";
        upgradeDescB[1] = "increase";
        upgradeDescC[1] = "firerate";

        upgradeDescA[2] = "pew";
        upgradeDescB[2] = "pew";
        upgradeDescC[2] = "";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "damage";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Further";
        upgradeDescB[4] = "increase";
        upgradeDescC[4] = "damage";

        upgradeDescA[5] = "not like";
        upgradeDescB[5] = "the movie";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[10];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[22];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[40];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    delay -= 2;
                    break;
                case 1:
                    delay -= 3;
                    break;
                case 2:
                    delay = 0;
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                case 4:
                    damage += 25000;
                    break;
                case 5:
                    damage = 999999;
                    break;
            }
        }
    }
}