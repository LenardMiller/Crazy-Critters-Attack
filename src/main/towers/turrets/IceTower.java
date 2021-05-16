package main.towers.turrets;

import main.misc.Tile;
import main.towers.IceWall;
import main.towers.Wall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;

public class IceTower extends Turret {

    private int wallHp;
    private int wallTimeUntilDamage;

    private final PImage[] VAPOR_TRAIL;
    private final int BETWEEN_VAPOR_FRAMES;
    private int betweenVaporTimer;
    private int currentVaporFrame;
    private PVector vaporStart;
    private float vaporAngle;
    private int vaporLength;
    private PVector vaporPartLength;

    public IceTower(PApplet p, Tile tile) {
        super(p, tile);
        name = "iceTower";
        delay = randomizeDelay(p, 10);
        pjSpeed = -1;
        range = 250;
        barrelLength = 30;
        offset = 6;
        wallHp = 50;
        wallTimeUntilDamage = 30;
        debrisType = "metal";
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("energyBlasterFire");
        price = ICE_TOWER_PRICE;
        value = price;

        BETWEEN_VAPOR_FRAMES = down60ToFramerate(3);
        currentVaporFrame = 16;
        betweenIdleFrames = 3;
        betweenFireFrames = 3;
        VAPOR_TRAIL = animatedSprites.get("iceTowerVaporTrailTR");

        loadSprites();
        setUpgrades();
        spawnParticles();
        playSoundRandomSpeed(p, placeSound, 1);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        currentVaporFrame = 0;
        vaporStart = position;
        PVector vaporEnd = targetEnemy.position;
        vaporAngle = angle;
        float c = sqrt(sq(vaporEnd.x-vaporStart.x)+sq(vaporEnd.y-vaporStart.y));
        vaporLength = (int)(c/24);
        vaporPartLength = PVector.fromAngle(vaporAngle - radians(90));
        vaporPartLength.setMag(24);

        targetEnemy.damageWithoutBuff(damage, this, "ice", PVector.fromAngle(angle), damage > 0);

        Tile tile = tiles.get((roundTo(targetEnemy.position.x, 50) / 50) + 1, (roundTo(targetEnemy.position.y, 50) / 50) + 1);
        if (tile.tower == null) {
            tile.tower = new IceWall(p, tile, wallHp, wallTimeUntilDamage);
            Wall wall = (Wall) tile.tower;
            wall.placeEffects();
            updateNodes();
            updateTowerArray();
        } else if (tile.tower instanceof IceWall) tile.tower.heal(1);
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
        if (currentVaporFrame < VAPOR_TRAIL.length) {
            for (int i = 0; i <= vaporLength; i++) {
                p.pushMatrix();
                float x = vaporStart.x + (vaporPartLength.x*i);
                float y = vaporStart.y + (vaporPartLength.y*i);
                p.translate(x, y);
                p.rotate(vaporAngle);
                float alpha = 255 * ((VAPOR_TRAIL.length - currentVaporFrame) / (float) VAPOR_TRAIL.length);
                p.tint(255, alpha);
                p.image(VAPOR_TRAIL[currentVaporFrame], -2, 0);
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
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 200;

        upgradePrices[3] = 50;
        upgradePrices[4] = 100;
        upgradePrices[5] = 200;
        //titles
        upgradeTitles[0] = "Longer Lasting";
        upgradeTitles[1] = "Stronger Ice";
        upgradeTitles[2] = "Auto Defence";

        upgradeTitles[3] = "Increase Range";
        upgradeTitles[4] = "Faster Freezing";
        upgradeTitles[5] = "Superfreeze";
        //descriptions
        upgradeDescA[0] = "Ice";
        upgradeDescB[0] = "lasts";
        upgradeDescC[0] = "longer";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "ice";
        upgradeDescC[1] = "HP";

        upgradeDescA[2] = "Automatically";
        upgradeDescB[2] = "reinforce";
        upgradeDescC[2] = "defences";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Freeze";
        upgradeDescB[5] = "bigger";
        upgradeDescC[5] = "enemies";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[25];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[27];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[34];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[28];
    }

    @Override
    protected void upgradeSpecial(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    wallTimeUntilDamage *= 2;
                    break;
                case 1:
                    wallHp *= 2;
                    break;
                case 2:
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 50;
                    break;
                case 4:
                    delay -= 3;
                    break;
                case 5:
                    break;
            }
        }
    }
}
