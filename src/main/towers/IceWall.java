package main.towers;

import main.enemies.Enemy;
import main.misc.CollisionBox;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateFlooring;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateNodes;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class IceWall extends Wall {

    private final CornerSpriteDS ICE;
    private final int TIME_UNTIL_DAMAGE;

    private int damageTimer;

    public IceWall(PApplet p, Tile tile, int maxHp, int timeUntilDamage) {
        super(p,tile);

        name = "iceWall";
        this.maxHp = maxHp;
        hp = maxHp;
        TIME_UNTIL_DAMAGE = timeUntilDamage;
        sprite = animatedSprites.get("iceWallTW");
        debrisType = "ice";
        damageSound = sounds.get("iceDamage");
        breakSound = sounds.get("iceBreak");
        price = 0;
        value = 0;
        nextLevelB = 4;

        updateTowerArray();
        ICE = new CornerSpriteDS();
        loadSprites();
    }

    @Override
    public void placeEffects() {
        spawnParticles();
        connectWallQueues++;
    }

    @Override
    public void main() {
        if (hp <= 0) die(false);
        value = (int)(((float)hp / (float)maxHp) * price);
        if (!paused && alive) {
            if (TIME_UNTIL_DAMAGE != -1) {
                damageTimer++;
                if (damageTimer >= TIME_UNTIL_DAMAGE) {
                    hp -= maxHp / 10;
                    damageTimer = 0;
                    refreshHpBar();
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                CollisionBox enemyCollisionBox = new CollisionBox(p, PVector.div(enemy.size, -2), enemy.size);
                if (enemyCollisionBox.pointIsInsideBox(enemy.position, tile.getCenter())) enemy.intersectingIceCount++;
                int targetSize = ceil(enemy.pfSize / 2f);
                if (enemy.intersectingIceCount >= targetSize) {
                    enemy.damageWithBuff(0, "frozen", 1, 0.2f, null,
                      false, "frozen", new PVector(0, 0), i);
                }
            }
        }
    }

    @Override
    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        alive = false;
        tile.tower = null;
        updateFlooring();
        updateTowerArray();
        updateNodes();
    }

    @Override
    public void controlAnimation() {
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        float x = tile.position.x-size.x;
        float y = tile.position.y-size.y;

        p.tint(255, tintColor, tintColor, 150);
        float hpRatio = (float)hp/(float)maxHp;
        if (!debug) {
            int crack = abs(ceil((hpRatio * 4) - 1) - 3);
            if (crack < 4) p.image(sprite[crack],x,y);
            else p.image(sprite[3],x,y);
        }
        p.tint(255, tintColor, tintColor);
        //sides
        if (tSSprite != null) p.image(tSSprite,x,y);
        if (rSSprite != null) p.image(rSSprite,x,y);
        if (bSSprite != null) p.image(bSSprite,x,y);
        if (lSSprite != null) p.image(lSSprite,x,y);
        //corners
        if (tlCSprite != null) p.image(tlCSprite,x,y);
        if (trCSprite != null) p.image(trCSprite,x,y);
        if (blCSprite != null) p.image(blCSprite,x,y);
        if (brCSprite != null) p.image(brCSprite,x,y);
        p.tint(255);

        if (tintColor < 255) tintColor += 20;
    }

    @Override
    public void updateSprite() {
        Tile searchTile;
        boolean tl;
        boolean t;
        boolean tr;
        boolean l;
        boolean r;
        boolean bl;
        boolean b;
        boolean br;

        searchTile = tiles.get(((int)tile.position.x/50)-1,((int)tile.position.y/50)-1);
        tl = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50),((int)tile.position.y/50)-1);
        t = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50)+1,((int)tile.position.y/50)-1);
        tr = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50)-1,((int)tile.position.y/50));
        l = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50)+1,((int)tile.position.y/50));
        r = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50)-1,((int)tile.position.y/50)+1);
        bl = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50),((int)tile.position.y/50)+1);
        b = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));
        searchTile = tiles.get(((int)tile.position.x/50)+1,((int)tile.position.y/50)+1);
        br = (searchTile != null && searchTile.tower != null && searchTile.tower.name.equals(name));

        //is corner
        boolean tlC;
        boolean trC;
        boolean blC;
        boolean brC;
        //is corner concave or convex
        boolean tlCcv = false;
        boolean trCcv = false;
        boolean blCcv = false;
        boolean brCcv = false;
        //is side
        boolean tS;
        boolean bS;
        boolean lS;
        boolean rS;

        tS = !t;
        bS = !b;
        lS = !l;
        rS = !r;
        if ((l && t) && !tl) {
            tlC = true;
            tlCcv = true;
        } else tlC = !(t || l);
        if ((r && t) && !tr) {
            trC = true;
            trCcv = true;
        } else trC = !(t || r);
        if ((l && b) && !bl) {
            blC = true;
            blCcv = true;
        } else blC = !(b || l);
        if ((r && b) && !br) {
            brC = true;
            brCcv = true;
        } else brC = !(b || r);

        CornerSpriteDS spriteDS = ICE;
        if (tS) tSSprite = spriteDS.t;
        else tSSprite = null;
        if (bS) bSSprite = spriteDS.b;
        else bSSprite = null;
        if (lS) lSSprite = spriteDS.l;
        else lSSprite = null;
        if (rS) rSSprite = spriteDS.r;
        else rSSprite = null;

        //oops, invert c/v
        if (tlC) tlCSprite = spriteDS.get(true,true,!tlCcv);
        else tlCSprite = null;
        if (trC) trCSprite = spriteDS.get(true,false,!trCcv);
        else trCSprite = null;
        if (blC) blCSprite = spriteDS.get(false,true,!blCcv);
        else blCSprite = null;
        if (brC) brCSprite = spriteDS.get(false,false,!brCcv);
        else brCSprite = null;
    }

    private void loadSprites() {
        CornerSpriteDS spriteDS = ICE;
        String name = "Ice";
        String idA = "null";
        String idB = "null";
        String idC = "null";
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                for (int c = 0; c < 2; c++) {
                    if (a == 0) idA = "T";
                    if (a == 1) idA = "B";
                    if (b == 0) idB = "l";
                    if (b == 1) idB = "r";
                    if (c == 0) idC = "c";
                    if (c == 1) idC = "v";
                    String id = idA+idB+idC;
                    spriteDS.add(staticSprites.get(name + id + "WallTW"),idA,idB,idC);
                }
            }
        }
        spriteDS.t = staticSprites.get(name + "TWallTW");
        spriteDS.b = staticSprites.get(name + "BWallTW");
        spriteDS.l = staticSprites.get(name + "LWallTW");
        spriteDS.r = staticSprites.get(name + "RWallTW");
    }
}