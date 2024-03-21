package main.towers;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Tile.updateFlooring;
import static main.misc.Tile.updateTowerArray;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Wall extends Tower {

    public static final int BUY_PRICE = 25;

    public final int[] upgradeHp;
    public final PImage[][] upgradeSprites;
    public final String[] upgradeNames;

    private final CornerSpriteDS wood;
    private final CornerSpriteDS stone;
    private final CornerSpriteDS metal;
    private final CornerSpriteDS crystal;
    private final CornerSpriteDS titanium;

    protected PImage tlCSprite;
    protected PImage trCSprite;
    protected PImage blCSprite;
    protected PImage brCSprite;
    protected PImage tSSprite;
    protected PImage bSSprite;
    protected PImage lSSprite;
    protected PImage rSSprite;

    protected PImage[] sprite;

    public Wall(PApplet p, Tile tile) {
        super(p,tile);

        name = "woodWall";
        size = new PVector(50,50);
        maxHp = 50;
        hp = maxHp;
        hit = false;
        sprite = animatedSprites.get("woodWallTW");
        material = Material.wood;
        damageSound = sounds.get(material + "Damage");
        breakSound = sounds.get(material + "Break");
        placeSound = sounds.get(material + "PlaceShort");
        basePrice = BUY_PRICE;
        nextLevelB = 0;

        upgradePrices = new int[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        upgradeSprites = new PImage[4][4];
        upgradeNames = new String[4];
        upgradeHp = new int[4];
        setUpgrades();
        updateTowerArray();

        wood = new CornerSpriteDS();
        stone = new CornerSpriteDS();
        metal = new CornerSpriteDS();
        crystal = new CornerSpriteDS();
        titanium = new CornerSpriteDS();
        loadSprites();
    }

    @Override
    public void place(boolean quiet) {
        if (!quiet) {
            playSoundRandomSpeed(p, placeSound, 1);
            spawnParticles();
        }
        int x = (int)(tile.position.x / 50);
        int y = (int)(tile.position.y / 50);
        tiles.get(x-1,y-1).flooringLayer.set(name);
        updateFlooring();
        connectWallQueues++;
    }

    @Override
    public void update() {
        if (hp <= 0) die(false);
        updateBoosts();
    }

    @Override
    public void displayBase() {
        float x = tile.position.x-size.x;
        float y = tile.position.y-size.y;
        p.tint(0,60);
        String sT = shadowType();
        if (sT != null) p.image(staticSprites.get("shadow" + sT + "TW"), x, y);
        else p.image(sprite[0],x+5,y+5);
        p.tint(255);
    }

    private String shadowType() {
        int x = (int)tile.position.x/50;
        int y = (int)tile.position.y/50;
        boolean t = y > 0 && tiles.get(x,y-1).tower != null && tiles.get(x,y-1).tower instanceof Wall;
        boolean l = x > 0 && tiles.get(x-1,y).tower != null && tiles.get(x-1,y).tower instanceof Wall;
        if (!t && !l) return "Both";
        if (!l) return "BL";
        if (!t) return "TR";
        else return null;
    }

    @Override
    public void controlAnimation() {
        if (hit) { //change to red if under attack
            tintColor = 0;
            hit = false;
        }
        float x = tile.position.x-size.x;
        float y = tile.position.y-size.y;

        p.tint(255,tintColor,tintColor);
        float hpRatio = Math.min(hp / (float) getMaxHp(), 1);
        if (!debug) {
            int crack = abs(ceil((hpRatio * 4) - 1) - 3);
            if (crack < 4) p.image(sprite[crack],x,y);
            else p.image(sprite[3],x,y);
        }
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
    public int getValue() {
        int value = basePrice;
        for (int i = 0; i < nextLevelB; i++) {
            value += upgradePrices[i];
        }
        // account for damage to wall
        value *= hp / (float) getMaxHp();
        return value;
    }

    private void setUpgrades() {
        //price
        upgradePrices[0] = 100;
        upgradePrices[1] = 500;
        upgradePrices[2] = 3000;
        upgradePrices[3] = 10000;
        //titles
        upgradeTitles[0] = "Stone";
        upgradeTitles[1] = "Metal";
        upgradeTitles[2] = "Crystal";
        upgradeTitles[3] = "Titanium";
        //sprites
        upgradeSprites[0] = animatedSprites.get("stoneWallTW");
        upgradeSprites[1] = animatedSprites.get("metalWallTW");
        upgradeSprites[2] = animatedSprites.get("crystalWallTW");
        upgradeSprites[3] = animatedSprites.get("titaniumWallTW");
        //names
        upgradeNames[0] = "stone";
        upgradeNames[1] = "metal";
        upgradeNames[2] = "crystal";
        upgradeNames[3] = "titanium";
        //hp
        upgradeHp[0] = 75;
        upgradeHp[1] = 125;
        upgradeHp[2] = 500;
        upgradeHp[3] = 1250;
    }

    @Override
    public void upgrade(int id, boolean quiet) {
        sprite = upgradeSprites[nextLevelB];
        maxHp += upgradeHp[nextLevelB];
        hp += upgradeHp[nextLevelB];
        name = upgradeNames[nextLevelB] + "Wall";
        material = Material.valueOf(upgradeNames[nextLevelB]);

        damageSound = sounds.get(material + "Damage");
        breakSound = sounds.get(material + "Break");
        placeSound = sounds.get(material + "PlaceShort");

        if (!quiet) {
            spawnParticles();
            playSoundRandomSpeed(p, placeSound, 1);
        }

        nextLevelB++;
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];
        int x = (int)(tile.position.x / 50);
        int y = (int)(tile.position.y / 50);
        tiles.get(x-1,y-1).flooringLayer.set(name);
        updateFlooring();
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

        CornerSpriteDS spriteDS = wood;
        if (name.equals("stone") || name.equals("stoneWall")) spriteDS = stone;
        if (name.equals("metal") || name.equals("metalWall")) spriteDS = metal;
        if (name.equals("crystal") || name.equals("crystalWall")) spriteDS = crystal;
        if (name.equals("titanium") || name.equals("titaniumWall")) spriteDS = titanium;
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
            String name = null;
            switch (i) {
                case 0 -> {
                    spriteDS = wood;
                    name = "wood";
                } case 1 -> {
                    spriteDS = stone;
                    name = "stone";
                } case 2 -> {
                    spriteDS = metal;
                    name = "metal";
                } case 3 -> {
                    spriteDS = crystal;
                    name = "crystal";
                } case 4 -> {
                    spriteDS = titanium;
                    name = "titanium";
                }
            }
            String idA = null;
            String idB = null;
            String idC = null;
            for (int a = 0; a < 2; a++) {
                for (int b = 0; b < 2; b++) {
                    for (int c = 0; c < 2; c++) {
                        if (a == 0) idA = "t";
                        if (a == 1) idA = "b";
                        if (b == 0) idB = "l";
                        if (b == 1) idB = "r";
                        if (c == 0) idC = "c";
                        if (c == 1) idC = "v";
                        String id = idA+idB+idC;
                        String fullName = name + id + "WallTW";
                        spriteDS.add(staticSprites.get(fullName),idA,idB,idC);
                    }
                }
            }
            spriteDS.t = staticSprites.get(name + "tWallTW");
            spriteDS.b = staticSprites.get(name + "bWallTW");
            spriteDS.l = staticSprites.get(name + "lWallTW");
            spriteDS.r = staticSprites.get(name + "rWallTW");
        }
    }

    protected static class CornerSpriteDS {

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
            tb = tbS.equals("t");
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