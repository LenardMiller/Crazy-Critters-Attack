package main.towers;

import main.misc.Tile;
import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.MiscMethods.updateNodes;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.misc.WallSpecialVisuals.updateWallTiles;

public class Wall extends Tower {

    private final CornerSpriteDS WOOD;
    private final CornerSpriteDS STONE;
    private final CornerSpriteDS METAL;
    private final CornerSpriteDS CRYSTAL;
    private final CornerSpriteDS ULTIMATE;

    private PImage tlCSprite;
    private PImage trCSprite;
    private PImage blCSprite;
    private PImage brCSprite;
    private PImage tSSprite;
    private PImage bSSprite;
    private PImage lSSprite;
    private PImage rSSprite;

    private final PImage[][] UPGRADE_SPRITES;
    private PImage[] sprite;

    public Wall(PApplet p, Tile tile) {
        super(p,tile);

        name = "woodWall";
        size = new PVector(50,50);
        maxHp = 50;
        hp = maxHp;
        hit = false;
        sprite = spritesAnimH.get("woodWallTW");
        debrisType = "wood";
        damageSound = soundsH.get(debrisType + "Damage");
        breakSound = soundsH.get(debrisType + "Break");
        placeSound = soundsH.get(debrisType + "PlaceShort");
        price = 25;
        value = price;
        nextLevelB = 0;

        int x = (int)(tile.position.x / 50);
        int y = (int)(tile.position.y / 50);
        tiles.get(x-1,y-1).setBgW(name);

        upgradePrices = new int[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        UPGRADE_SPRITES = new PImage[4][4];
        setUpgrades();
        updateTowerArray();

        WOOD = new CornerSpriteDS();
        STONE = new CornerSpriteDS();
        METAL = new CornerSpriteDS();
        CRYSTAL = new CornerSpriteDS();
        ULTIMATE = new CornerSpriteDS();
        loadSprites();

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);
    }

    public void main(){
        if (hp <= 0) die(false);
        value = (int)(((float)hp / (float)maxHp) * price);
    }

    public void displayPassA() {
        float x = tile.position.x-size.x;
        float y = tile.position.y-size.y;
        p.tint(0,60);
        String sT = shadowType();
        if (sT != null) p.image(spritesH.get("shadow" + sT + "TW"), x, y);
        else p.image(sprite[0],x+5,y+5);
        p.tint(255);
    }

    private String shadowType() {
        int x = (int)tile.position.x/50;
        int y = (int)tile.position.y/50;
        boolean t = y > 0 && tiles.get(x,y-1).tower != null && !tiles.get(x,y-1).tower.turret;
        boolean l = x > 0 && tiles.get(x-1,y).tower != null && !tiles.get(x-1,y).tower.turret;
        if (!t && !l) return "Both";
        if (!l) return "BL";
        if (!t) return "TR";
        else return null;
    }

    public void displayPassB() {
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        float x = tile.position.x-size.x;
        float y = tile.position.y-size.y;

        p.tint(255,tintColor,tintColor);
        float hpRatio = (float)hp/(float)maxHp;
        int crack = abs(ceil((hpRatio*4)-1)-3);
        if (crack < 4) p.image(sprite[crack],x,y);
        else p.image(sprite[3],x,y);
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

    private void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 225;
        upgradePrices[3] = 500;
        //titles
        upgradeTitles[0] = "Stone";
        upgradeTitles[1] = "Metal";
        upgradeTitles[2] = "Crystal";
        upgradeTitles[3] = "Titanium";
        //sprites
        UPGRADE_SPRITES[0] = spritesAnimH.get("stoneWallTW");
        UPGRADE_SPRITES[1] = spritesAnimH.get("metalWallTW");
        UPGRADE_SPRITES[2] = spritesAnimH.get("crystalWallTW");
        UPGRADE_SPRITES[3] = spritesAnimH.get("ultimateWallTW");
    }

    public void upgrade(int id) {
        price += upgradePrices[nextLevelB];
        sprite = UPGRADE_SPRITES[nextLevelB];

        int oldMax = maxHp;
        switch (nextLevelB) {
            case 0:
                maxHp += 75;
                name = "stoneWall";
                debrisType = "stone";
                break;
            case 1:
                maxHp += 125;
                name = "metalWall";
                debrisType = "metal";
                break;
            case 2:
                maxHp += 250;
                name = "crystalWall";
                debrisType = "crystal";
                break;
            case 3:
                maxHp += 500;
                name = "titaniumWall";
                debrisType = "ultimate";
                break;
        }
        hp = (int) map(hp, hp, oldMax, oldMax, maxHp);

        damageSound = soundsH.get(debrisType + "Damage");
        breakSound = soundsH.get(debrisType + "Break");
        placeSound = soundsH.get(debrisType + "PlaceShort");

        placeSound.stop();
        placeSound.play(p.random(0.8f, 1.2f), volume);

        nextLevelB++;
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = spritesAnimH.get("upgradeIC")[0];
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(tile.position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (tile.position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
        int x = (int)(tile.position.x / 50);
        int y = (int)(tile.position.y / 50);
        tiles.get(x-1,y-1).setBgW(name);
        updateWallTiles();
        updateNodes();
    }

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

        CornerSpriteDS spriteDS = WOOD;
        if (name.equals("stone") || name.equals("stoneWall")) spriteDS = STONE;
        if (name.equals("metal") || name.equals("metalWall")) spriteDS = METAL;
        if (name.equals("crystal") || name.equals("crystalWall")) spriteDS = CRYSTAL;
        if (name.equals("ultimate") || name.equals("ultimateWall")) spriteDS = ULTIMATE;
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
        for (int i = 0; i < 5; i++) {
            CornerSpriteDS spriteDS = null;
            String name = "null";
            switch (i) {
                case 0:
                    spriteDS = WOOD;
                    name = "Wood";
                    break;
                case 1:
                    spriteDS = STONE;
                    name = "Stone";
                    break;
                case 2:
                    spriteDS = METAL;
                    name = "Metal";
                    break;
                case 3:
                    spriteDS = CRYSTAL;
                    name = "Crystal";
                    break;
                case 4:
                    spriteDS = ULTIMATE;
                    name = "Ultimate";
                    break;
            }
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
                        spriteDS.add(spritesH.get(name + id + "WallTW"),idA,idB,idC);
                    }
                }
            }
            spriteDS.t = spritesH.get(name + "TWallTW");
            spriteDS.b = spritesH.get(name + "BWallTW");
            spriteDS.l = spritesH.get(name + "LWallTW");
            spriteDS.r = spritesH.get(name + "RWallTW");
        }
    }

    private static class CornerSpriteDS {

        CornerSpriteDSItem[] items;
        PImage t;
        PImage b;
        PImage l;
        PImage r;

        CornerSpriteDS() {
            items = new CornerSpriteDSItem[0];
        }

        PImage get(boolean tb, boolean lr, boolean cv) {
            PImage r = null;
            for (CornerSpriteDSItem item : items) {
                if (item.tb == tb && item.lr == lr && item.cv == cv) r = item.sprite;
            }
            return r;
        }

        void add(PImage sprite, String tbS, String lrS, String cvS) {
            boolean tb;
            boolean lr;
            boolean cv;
            tb = tbS.equals("T");
            lr = lrS.equals("l");
            cv = cvS.equals("c");
            CornerSpriteDSItem[] newItems = new CornerSpriteDSItem[items.length+1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new CornerSpriteDSItem(sprite, tb, lr, cv);
            items = newItems;
        }

        private static class CornerSpriteDSItem {

            PImage sprite;
            boolean tb;
            boolean lr;
            boolean cv;

            CornerSpriteDSItem(PImage sprite, boolean tb, boolean lr, boolean cv) {
                this.sprite = sprite;
                this.tb = tb;
                this.lr = lr;
                this.cv = cv;
            }
        }
    }
}