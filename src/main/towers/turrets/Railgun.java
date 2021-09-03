package main.towers.turrets;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.particles.Particle.ParticleTypes.RailgunBlast;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Railgun extends Turret {

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
        delay = 8;
        delay += p.random(-(delay/10f),delay/10f); //injects 10% randomness so all don't fire at once
        damage = 5000;
        pjSpeed = -1;
        range = 800;
        NUM_VAPOR_FRAMES = 15;
        BETWEEN_VAPOR_FRAMES = down60ToFramerate(3);
        vaporTrail = new PImage[NUM_VAPOR_FRAMES];
        vaporEndSprites = new PImage[11];
        currentVaporFrame = 16;
        betweenIdleFrames = 3;
        betweenFireFrames = 3;
        vaporTrail = animatedSprites.get("railgunVaporTrailTR");
        vaporEndSprites = animatedSprites.get("railgunBlastPT");
        debrisType = "titanium";
        barrelLength = 30;
        price = 400;
        value = price;
        priority = 2; //strong

        setUpgrades();
        loadSprites();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        topParticles.add(RailgunBlast.create(p,position.x,position.y,0));
        currentVaporFrame = 0;
        vaporStart = position;
        PVector vaporEnd = targetEnemy.position;
        vaporAngle = angle;
        float c = sqrt(sq(vaporEnd.x-vaporStart.x)+sq(vaporEnd.y-vaporStart.y));
        vaporLength = (int)(c/24);
        vaporPartLength = PVector.fromAngle(vaporAngle - radians(90));
        vaporPartLength.setMag(24);

        targetEnemy.damageWithoutBuff(getDamage(),this, "normal", PVector.fromAngle(vaporAngle - HALF_PI), true);
    }

    @Override
    public void displayMain() {
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
    protected void upgradeSpecial(int id) {}

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 0;
        upgradePrices[1] = 0;
        upgradePrices[2] = 0;
        upgradePrices[3] = 0;
        //titles
        upgradeTitles[0] = "";
        upgradeTitles[1] = "";
        upgradeTitles[2] = "";
        upgradeTitles[3] = "";
        //desc line one
        upgradeDescA[0] = "";
        upgradeDescA[1] = "";
        upgradeDescA[2] = "";
        upgradeDescA[3] = "";
        //desc line two
        upgradeDescB[0] = "";
        upgradeDescB[1] = "";
        upgradeDescB[2] = "";
        upgradeDescB[3] = "";
        //desc line three
        upgradeDescC[0] = "";
        upgradeDescC[1] = "";
        upgradeDescC[2] = "";
        upgradeDescC[3] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[0];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[0];
    }
}