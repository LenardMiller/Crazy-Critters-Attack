package main.misc;

import processing.core.PApplet;
import processing.core.PImage;

import static main.Main.spritesAnimH;
import static main.Main.spritesH;

public class SpriteLoader {
    
    public SpriteLoader() {}
    
    public static void loadSpritesAnim(PApplet p) {
        //icons & buttons
        spritesAnimH.put("upgradeIC",new PImage[15]);
        for (int i = 14; i >= 0; i--){
            spritesAnimH.get("upgradeIC")[i] = p.loadImage("sprites/guiObjects/upgrades/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("livesAddBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("livesAddBT")[i] = p.loadImage("sprites/guiObjects/buttons/livesAdd/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("moneyAddBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("moneyAddBT")[i] = p.loadImage("sprites/guiObjects/buttons/moneyAdd/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("nullBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("nullBT")[i] = p.loadImage("sprites/guiObjects/buttons/null/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("upgradeBT",new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("upgradeBT")[i] = p.loadImage("sprites/guiObjects/buttons/upgradeButton/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("sellTowerBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("sellTowerBT")[i] = p.loadImage("sprites/guiObjects/buttons/sellTower/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("targetPriorityBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("targetPriorityBT")[i] = p.loadImage("sprites/guiObjects/buttons/targetPriority/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("towerTabSwitchBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("towerTabSwitchBT")[i] = p.loadImage("sprites/guiObjects/buttons/towerTabSwitch/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("wallBuyBT",new PImage[2]);
        for (int i = 1; i >= 0; i--){
            spritesAnimH.get("wallBuyBT")[i] = p.loadImage("sprites/guiObjects/buttons/wallBuy/" + PApplet.nf(i,3) + ".png");
        }
        //particles
        spritesAnimH.put("energyBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("energyBuffPT")[i] = p.loadImage("sprites/particles/buff/energy/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("fireBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("fireBuffPT")[i] = p.loadImage("sprites/particles/buff/fire/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("poisonBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("poisonBuffPT")[i] = p.loadImage("sprites/particles/buff/poison/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("waterBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("waterBuffPT")[i] = p.loadImage("sprites/particles/buff/water/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("nullBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("nullBuffPT")[i] = p.loadImage("sprites/particles/buff/null/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("greenOuchEnemyPT",new PImage[11]);
        for (int i = 10; i >= 0; i--){
            spritesAnimH.get("greenOuchEnemyPT")[i] = p.loadImage("sprites/particles/enemy/greenOuch/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("greyPuffEnemyPT",new PImage[11]);
        for (int i = 10; i >= 0; i--){
            spritesAnimH.get("greyPuffEnemyPT")[i] = p.loadImage("sprites/particles/enemy/greyPuff/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("greenPuffEnemyPT",new PImage[10]);
        for (int i = 9; i >= 0; i--){
            spritesAnimH.get("greenPuffEnemyPT")[i] = p.loadImage("sprites/particles/enemy/greenPuff/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("pinkOuchEnemyPT",new PImage[11]);
        for (int i = 10; i >= 0; i--){
            spritesAnimH.get("pinkOuchEnemyPT")[i] = p.loadImage("sprites/particles/enemy/pinkOuch/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("redOuchEnemyPT",new PImage[11]);
        for (int i = 10; i >= 0; i--){
            spritesAnimH.get("redOuchEnemyPT")[i] = p.loadImage("sprites/particles/enemy/redOuch/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("mediumExplosionPT",new PImage[18]);
        for (int i = 17; i >= 0; i--){
            spritesAnimH.get("mediumExplosionPT")[i] = p.loadImage("sprites/particles/mediumExplosion/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("largeExplosionPT",new PImage[18]);
        for (int i = 17; i >= 0; i--){
            spritesAnimH.get("largeExplosionPT")[i] = p.loadImage("sprites/particles/largeExplosion/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("energyExDebrisPT",new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("energyExDebrisPT")[i] = p.loadImage("sprites/particles/explosionDebris/energy/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("greenMagicBuffPT",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("greenMagicBuffPT")[i] = p.loadImage("sprites/particles/buff/greenMagic/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("boltBreakPT",new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("boltBreakPT")[i] = p.loadImage("sprites/particles/boltBreak/" + PApplet.nf(i,3) + ".png");
        }
        //projectiles
        spritesAnimH.put("miscPJ",new PImage[6]);
        for (int i = 5; i >= 0; i--){
            spritesAnimH.get("miscPJ")[i] = p.loadImage("sprites/projectiles/misc/" + PApplet.nf(i,3) + ".png");
        }
        //enemies
        spritesAnimH.put("nullAttackEN", new PImage[1]);
        spritesAnimH.get("nullAttackEN")[0] = p.loadImage("sprites/enemies/null/attack/000.png");
        spritesAnimH.put("nullMoveEN", new PImage[1]);
        spritesAnimH.get("nullMoveEN")[0] = p.loadImage("sprites/enemies/null/move/000.png");
        spritesAnimH.put("treeGiantAttackEN", new PImage[63]);
        for (int i = 62; i >= 0; i--){
            spritesAnimH.get("treeGiantAttackEN")[i] = p.loadImage("sprites/enemies/treeGiant/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("treeGiantMoveEN", new PImage[91]);
        for (int i = 90; i >= 0; i--){
            spritesAnimH.get("treeGiantMoveEN")[i] = p.loadImage("sprites/enemies/treeGiant/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("treeSpiritAttackEN", new PImage[42]);
        for (int i = 41; i >= 0; i--){
            spritesAnimH.get("treeSpiritAttackEN")[i] = p.loadImage("sprites/enemies/treeSpirit/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("treeSpiritMoveEN", new PImage[47]);
        for (int i = 46; i >= 0; i--){
            spritesAnimH.get("treeSpiritMoveEN")[i] = p.loadImage("sprites/enemies/treeSpirit/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("treeSpriteMoveEN", new PImage[30]);
        for (int i = 29; i >= 0; i--){
            spritesAnimH.get("treeSpriteMoveEN")[i] = p.loadImage("sprites/enemies/treeSprite/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("treeSpriteAttackEN", new PImage[50]);
        for (int i = 49; i >= 0; i--){
            spritesAnimH.get("treeSpriteAttackEN")[i] = p.loadImage("sprites/enemies/treeSprite/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("midBugAttackEN", new PImage[42]);
        for (int i = 41; i >= 0; i--){
            spritesAnimH.get("midBugAttackEN")[i] = p.loadImage("sprites/enemies/midBug/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("midBugMoveEN", new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("midBugMoveEN")[i] = p.loadImage("sprites/enemies/midBug/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("smolBugAttackEN", new PImage[34]);
        for (int i = 33; i >= 0; i--){
            spritesAnimH.get("smolBugAttackEN")[i] = p.loadImage("sprites/enemies/smolBug/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("smolBugMoveEN", new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("smolBugMoveEN")[i] = p.loadImage("sprites/enemies/smolBug/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("bigBugAttackEN", new PImage[100]);
        for (int i = 99; i >= 0; i--){
            spritesAnimH.get("bigBugAttackEN")[i] = p.loadImage("sprites/enemies/bigBug/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("bigBugMoveEN", new PImage[12]);
        for (int i = 11; i >= 0; i--){
            spritesAnimH.get("bigBugMoveEN")[i] = p.loadImage("sprites/enemies/bigBug/move/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("snakeAttackEN", new PImage[15]);
        for (int i = 14; i >= 0; i--){
            spritesAnimH.get("snakeAttackEN")[i] = p.loadImage("sprites/enemies/snake/attack/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("snakeMoveEN", new PImage[10]);
        for (int i = 9; i >= 0; i--) {
            spritesAnimH.get("snakeMoveEN")[i] = p.loadImage("sprites/enemies/snake/move/" + PApplet.nf(i, 3) + ".png");
        }
        //turrets
        spritesAnimH.put("slingshotFireTR",new PImage[34]);
        for (int i = 33; i >= 0; i--){
            spritesAnimH.get("slingshotFireTR")[i] = p.loadImage("sprites/towers/turrets/slingshot/fire/fire" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("slingshotLoadTR",new PImage[59]);
        for (int i = 58; i >= 0; i--){
            spritesAnimH.get("slingshotLoadTR")[i] = p.loadImage("sprites/towers/turrets/slingshot/load/load" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("teslaFireTR",new PImage[6]);
        for (int i = 5; i >= 0; i--){
            spritesAnimH.get("teslaFireTR")[i] = p.loadImage("sprites/towers/turrets/tesla/fire/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("teslaLoadTR",new PImage[5]);
        for (int i = 4; i >= 0; i--){
            spritesAnimH.get("teslaLoadTR")[i] = p.loadImage("sprites/towers/turrets/tesla/load/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("teslaIdleTR",new PImage[18]);
        for (int i = 17; i >= 0; i--){
            spritesAnimH.get("teslaIdleTR")[i] = p.loadImage("sprites/towers/turrets/tesla/idle/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("crossbowFireTR",new PImage[13]);
        for (int i = 12; i >= 0; i--){
            spritesAnimH.get("crossbowFireTR")[i] = p.loadImage("sprites/towers/turrets/crossbow/fire/fire" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("crossbowLoadTR",new PImage[81]);
        for (int i = 80; i >= 0; i--){
            spritesAnimH.get("crossbowLoadTR")[i] = p.loadImage("sprites/towers/turrets/crossbow/load/load" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("miscCannonFireTR",new PImage[12]);
        for (int i = 11; i >= 0; i--){
            spritesAnimH.get("miscCannonFireTR")[i] = p.loadImage("sprites/towers/turrets/miscCannon/fire/fire" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("miscCannonLoadTR",new PImage[1]);
        for (int i = 0; i >= 0; i--){
            spritesAnimH.get("miscCannonLoadTR")[i] = p.loadImage("sprites/towers/turrets/miscCannon/load/load" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("energyBlasterFireTR",new PImage[14]);
        for (int i = 13; i >= 0; i--){
            spritesAnimH.get("energyBlasterFireTR")[i] = p.loadImage("sprites/towers/turrets/energyBlaster/fire/fire" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("energyBlasterLoadTR",new PImage[42]);
        for (int i = 41; i >= 0; i--){
            spritesAnimH.get("energyBlasterLoadTR")[i] = p.loadImage("sprites/towers/turrets/energyBlaster/load/load" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("magicMissleerFireTR",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("magicMissleerFireTR")[i] = p.loadImage("sprites/towers/turrets/magicMissleer/fire/fire" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("magicMissleerLoadTR",new PImage[26]);
        for (int i = 25; i >= 0; i--){
            spritesAnimH.get("magicMissleerLoadTR")[i] = p.loadImage("sprites/towers/turrets/magicMissleer/load/load" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("magicMissleerIdleTR",new PImage[8]);
        for (int i = 7; i >= 0; i--){
            spritesAnimH.get("magicMissleerIdleTR")[i] = p.loadImage("sprites/towers/turrets/magicMissleer/idle/" + PApplet.nf(i,3) + ".png");
        }
        //walls
        spritesAnimH.put("woodWallTW", new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("woodWallTW")[i] = p.loadImage("sprites/towers/walls/wood/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("stoneWallTW", new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("stoneWallTW")[i] = p.loadImage("sprites/towers/walls/stone/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("metalWallTW", new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("metalWallTW")[i] = p.loadImage("sprites/towers/walls/metal/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("crystalWallTW", new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("crystalWallTW")[i] = p.loadImage("sprites/towers/walls/crystal/" + PApplet.nf(i,3) + ".png");
        }
        spritesAnimH.put("ultimateWallTW", new PImage[4]);
        for (int i = 3; i >= 0; i--){
            spritesAnimH.get("ultimateWallTW")[i] = p.loadImage("sprites/towers/walls/ultimate/" + PApplet.nf(i,3) + ".png");
        }
    }

    public static void loadSprites(PApplet p) {
        //enemies
        spritesH.put("nullEN",p.loadImage("sprites/enemies/null/idle.png"));
        spritesH.put("treeGiantEN",p.loadImage("sprites/enemies/treeGiant/idle.png"));
        spritesH.put("treeSpiritEN",p.loadImage("sprites/enemies/treeSpirit/idle.png"));
        spritesH.put("treeSpriteEN",p.loadImage("sprites/enemies/treeSprite/idle.png"));
        spritesH.put("midBugEN",p.loadImage("sprites/enemies/midBug/idle.png"));
        spritesH.put("bigBugEN",p.loadImage("sprites/enemies/bigBug/idle.png"));
        spritesH.put("snakeEN",p.loadImage("sprites/enemies/snake/idle.png"));
        //icons
        spritesH.put("livesIc",p.loadImage("sprites/guiObjects/lives.png"));
        spritesH.put("moneyIc",p.loadImage("sprites/guiObjects/money.png"));
        spritesH.put("nullIc",p.loadImage("sprites/guiObjects/null.png"));
        //particles
        spritesH.put("crystalPt",p.loadImage("sprites/particles/debris/crystal.png"));
        spritesH.put("devWoodPt",p.loadImage("sprites/particles/debris/devWood.png"));
        spritesH.put("metalPt",p.loadImage("sprites/particles/debris/metal.png"));
        spritesH.put("stonePt",p.loadImage("sprites/particles/debris/stone.png"));
        spritesH.put("ultimatePt",p.loadImage("sprites/particles/debris/ultimate.png"));
        spritesH.put("woodPt",p.loadImage("sprites/particles/debris/wood.png"));
        spritesH.put("nullPt",p.loadImage("sprites/particles/null/null.png"));
        //projectiles
        spritesH.put("boltPj",p.loadImage("sprites/projectiles/bolt.png"));
        spritesH.put("devPj",p.loadImage("sprites/projectiles/dev.png"));
        spritesH.put("energyPj",p.loadImage("sprites/projectiles/energy.png"));
        spritesH.put("magicMisslePj",p.loadImage("sprites/projectiles/magicMissle.png"));
        spritesH.put("nullPj",p.loadImage("sprites/projectiles/null.png"));
        spritesH.put("pebblePj",p.loadImage("sprites/projectiles/pebble.png"));
        //turrets
        spritesH.put("crossbowBaseTR",p.loadImage("sprites/towers/turrets/crossbow/base.png"));
        spritesH.put("crossbowFullTR",p.loadImage("sprites/towers/turrets/crossbow/full.png"));
        spritesH.put("crossbowIdleTR",p.loadImage("sprites/towers/turrets/crossbow/idle.png"));
        spritesH.put("energyBlasterBaseTR",p.loadImage("sprites/towers/turrets/energyBlaster/base.png"));
        spritesH.put("energyBlasterFullTR",p.loadImage("sprites/towers/turrets/energyBlaster/full.png"));
        spritesH.put("energyBlasterIdleTR",p.loadImage("sprites/towers/turrets/energyBlaster/idle.png"));
        spritesH.put("magicMissleerBaseTR",p.loadImage("sprites/towers/turrets/magicMissleer/base.png"));
        spritesH.put("magicMissleerFullTR",p.loadImage("sprites/towers/turrets/magicMissleer/full.png"));
        spritesH.put("magicMissleerIdleTR",p.loadImage("sprites/towers/turrets/magicMissleer/idle.png"));
        spritesH.put("miscCannonBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/base.png"));
        spritesH.put("miscCannonFullTR",p.loadImage("sprites/towers/turrets/miscCannon/full.png"));
        spritesH.put("miscCannonIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/idle.png"));
        spritesH.put("slingshotBaseTR",p.loadImage("sprites/towers/turrets/slingshot/base.png"));
        spritesH.put("slingshotFullTR",p.loadImage("sprites/towers/turrets/slingshot/full.png"));
        spritesH.put("slingshotIdleTR",p.loadImage("sprites/towers/turrets/slingshot/idle.png"));
        spritesH.put("teslaBaseTR",p.loadImage("sprites/towers/turrets/tesla/base.png"));
        spritesH.put("teslaFullTR",p.loadImage("sprites/towers/turrets/tesla/full.png"));
        spritesH.put("teslaIdleTR",p.loadImage("sprites/towers/turrets/tesla/idle.png"));
        //walls
        spritesH.put("repairTW",p.loadImage("sprites/towers/walls/overlays/repair.png"));
        spritesH.put("upgradeTW",p.loadImage("sprites/towers/walls/overlays/upgrade.png"));
        spritesH.put("placeTW",p.loadImage("sprites/towers/walls/overlays/place.png"));
        for (int i = 0; i < 5; i++) {
            String name = "null";
            if (i == 0) name = "Wood";
            if (i == 1) name = "Stone";
            if (i == 2) name = "Metal";
            if (i == 3) name = "Crystal";
            if (i == 4) name = "Ultimate";
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
                        spritesH.put(name + id + "WallTW", p.loadImage("sprites/towers/walls/" + name + "/" + id + ".png"));
                    }
                }
            }
            spritesH.put(name + "TWallTW", p.loadImage("sprites/towers/walls/" + name + "/t.png"));
            spritesH.put(name + "BWallTW", p.loadImage("sprites/towers/walls/" + name + "/b.png"));
            spritesH.put(name + "LWallTW", p.loadImage("sprites/towers/walls/" + name + "/l.png"));
            spritesH.put(name + "RWallTW", p.loadImage("sprites/towers/walls/" + name + "/r.png"));
        }
    }
}
