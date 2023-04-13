package main.towers.turrets;

import main.Main;
import main.misc.IntVector;
import main.misc.Tile;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.Wall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class IceTower extends Turret {

    public int wallHp;
    public int wallTimeUntilDamage;

    public int frozenTotal;

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
        range = 350;
        barrelLength = 30;
        offset = 0;
        wallHp = 40;
        wallTimeUntilDamage = 15;
        material = "darkMetal";
        damageSound = sounds.get("metalDamage");
        breakSound = sounds.get("metalBreak");
        placeSound = sounds.get("metalPlace");
        fireSound = sounds.get("iceFire");
        basePrice = ICE_TOWER_PRICE;
        titleLines = new String[]{"Freeze Ray"};
        infoDisplay = (o) -> {
            selection.setTextPurple("Encases enemies", o);
            iceWallInfo(o);
        };
        statsDisplay = (o) -> {
            if (frozenTotal == 1) p.text("1 wall created", 910, 500 + offset);
            else p.text(nfc(frozenTotal) + " walls created", 910, 500 + offset);
        };

        BETWEEN_VAPOR_FRAMES = down60ToFramerate(3);
        currentVaporFrame = 16;
        betweenIdleFrames = 5;
        betweenFireFrames = 3;
        VAPOR_TRAIL = animatedSprites.get("iceTowerVaporTrailTR");
    }

    @Override
    public void update() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        updateBoosts();
        if ((enemies.size() > 0 && !machine.dead && !paused) || name.equals("autoIceTower")) checkTarget();
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
                && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    @Override
    protected void checkTarget() {
        if (name.equals("autoIceTower")) {
            if (state == State.Idle) {
                state = State.Fire;
                frame = 0;
                spawnProjectiles(tile.position, 0);
            }
            return;
        }
        getTargetEnemy();
        if (targetEnemy != null && state != State.Fire) aim(targetEnemy);
        if (state == State.Idle && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            state = State.Fire;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    private Tile selectTargetTile() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (Tower tower : Main.towers) if (!(tower instanceof IceWall)) towers.add(tower);
        Collections.shuffle(towers);
        Tile tile = null;
        for (Tower tower : towers) {
            IntVector wallGridPosition = worldPositionToGridPosition(tower.tile.position);
            ArrayList<Tile> surroundingTiles = new ArrayList<>();
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    surroundingTiles.add(tiles.get(wallGridPosition.x + x, wallGridPosition.y + y));
                }
            }
            Collections.shuffle(surroundingTiles);
            for (Tile surroundingTile : surroundingTiles) {
                tile = surroundingTile;
                if (tilePlaceable(tile)) break;
            }
            if (tilePlaceable(tile)) break;
        }
        if (tilePlaceable(tile)) return tile;
        else return null;
    }

    private boolean tilePlaceable(Tile tile) {
        if (tile == null) return false;
        Tile otherTile = tiles.get(tile.getGridPosition().sub(1));
        if (otherTile == null) return tile.tower == null;
        return !otherTile.machine && otherTile.obstacleName == null && tile.tower == null;
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (name.equals("autoIceTower")) {
            Tile targetTile = selectTargetTile();
            if (targetTile != null) {
                placeWall(targetTile);
                playSoundRandomSpeed(p, fireSound, 1);
            } else state = State.Idle;
            return;
        }

        currentVaporFrame = 0;
        vaporStart = position;
        PVector vaporEnd = targetEnemy.position;
        vaporAngle = angle;
        float c = sqrt(sq(vaporEnd.x-vaporStart.x)+sq(vaporEnd.y-vaporStart.y));
        vaporLength = (int)(c/24);
        vaporPartLength = PVector.fromAngle(vaporAngle - radians(90));
        vaporPartLength.setMag(24);

        targetEnemy.damageWithoutBuff(getDamage(), this, "ice", PVector.fromAngle(angle), damage > 0);

        int targetSize = ceil(targetEnemy.pfSize / 2f);
        if (name.equals("superIceTower") && targetSize > 1) {
            IntVector targetTopLeft = worldPositionToTowerGridPosition(
                    PVector.add(
                            PVector.sub(
                                    targetEnemy.position,
                                    PVector.div(
                                            targetEnemy.size,
                                            2
                                    )
                            ),
                            new PVector(25, 25)
                    )
            );
            for (int x = 0; x < targetSize; x++) {
                for (int y = 0; y < targetSize; y++) {
                    placeWall(tiles.get(targetTopLeft.x + x, targetTopLeft.y + y));
                }
            }
        } else {
            Tile tile = tiles.get((roundTo(targetEnemy.position.x, 50) / 50) + 1, (roundTo(targetEnemy.position.y, 50) / 50) + 1);
            placeWall(tile);
        }
    }

    private void placeWall(Tile tile) { //todo: nullpointerexception
        if (tile.tower == null) {
            tile.tower = new IceWall(p, tile, wallHp, wallTimeUntilDamage);
            Wall wall = (Wall) tile.tower;
            wall.placeEffect(false);
            updateCombatPoints();
            updateTowerArray();
            frozenTotal++;
        } else if (tile.tower instanceof IceWall) {
            tile.tower.heal(1);
            frozenTotal++;
        }
    }

    @Override
    public void displayTop() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0,60);
        if (sprite != null) p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        p.tint(255);
        p.popMatrix();
        //vaporTrail
        displayVaporTrail();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (sprite != null) p.image(sprite,-size.x/2-offset,-size.y/2-offset);
        else System.out.println("Turret sprite missing!");
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
        upgradePrices[0] = 750;
        upgradePrices[1] = 1200;
        upgradePrices[2] = 30000;

        upgradePrices[3] = 1000;
        upgradePrices[4] = 1500;
        upgradePrices[5] = 25000;
        //titles
        upgradeTitles[0] = "Longer Lasting";
        upgradeTitles[1] = "Stronger Ice";
        upgradeTitles[2] = "Auto Defence";

        upgradeTitles[3] = "Increase Range";
        upgradeTitles[4] = "Faster Freezing";
        upgradeTitles[5] = "Superfreeze";
        //descriptions
        upgradeDescA[0] = "Ice lasts";
        upgradeDescB[0] = "longer";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Increase";
        upgradeDescB[1] = "ice HP";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "Reinforces";
        upgradeDescB[2] = "defences";
        upgradeDescC[2] = "with ice";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Freeze";
        upgradeDescB[5] = "bigger";
        upgradeDescC[5] = "critters";

        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[35];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[36];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[42];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[41];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    wallTimeUntilDamage *= 2;
                    break;
                case 1:
                    wallHp *= 2;
                    break;
                case 2:
                    name = "autoIceTower";
                    range = 5000;
                    wallTimeUntilDamage = -1;
                    angle = 0;
                    material = "crystal";
                    placeSound = sounds.get("crystalPlace");
                    damageSound = sounds.get("crystalDamage");
                    breakSound = sounds.get("crystalBreak");
                    titleLines = new String[]{"Ice Defender"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Reinforces defences", o);
                        iceWallInfo(o);
                    };
                    loadSprites();
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
                    name = "superIceTower";
                    material = "titanium";
                    placeSound = sounds.get("titaniumPlace");
                    breakSound = sounds.get("titaniumBreak");
                    damageSound = sounds.get("titaniumDamage");
                    titleLines = new String[]{"Super Freeze", "Ray"};
                    wallHp *= 2;
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Encases any enemy", o);
                        iceWallInfo(o);
                    };
                    loadSprites();
                    break;
            }
        }
    }

    private void iceWallInfo(int offset) {
        p.fill(new Color(100, 150, 255).getRGB(), 254);
        p.text("Ice HP: " + wallHp, 910, 356 + 20 + offset);
        float lifespan = (wallTimeUntilDamage / (float) FRAMERATE) * 10;
        if (wallTimeUntilDamage == -1) p.text("Ice doesn't melt", 910, 376 + 20 + offset);
        else p.text("Ice lifespan: " + round(lifespan) + "s", 910, 376 + 20 + offset);
    }
}
