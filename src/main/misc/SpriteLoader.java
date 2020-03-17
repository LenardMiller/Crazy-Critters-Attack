package main.misc;

import processing.core.PApplet;
import processing.core.PImage;

import static main.Main.spritesAnimH;
import static main.Main.spritesH;

public class SpriteLoader {
    
    public SpriteLoader() {}
    
    public static void loadSpritesAnim(PApplet p) {
        //icons & buttons
        getSprite(p,"upgrade","IC","upgrades/",16);
        getSprite(p,"wave","IC","waveIcons/",4);
        getSprite(p,"waveBg","IC","waveBackgrounds/",1);
        getSprite(p,"moneyAdd","BT","moneyAdd/",2);
        getSprite(p,"upgrade","BT","upgradeButton/",4);
        getSprite(p,"sellTower","BT","sellTower/",2);
        getSprite(p,"targetPriority","BT","targetPriority/",2);
        getSprite(p,"towerTabSwitch","BT","towerTabSwitch/",2);
        getSprite(p,"wallBuy","BT","wallBuy/",2);
        getSprite(p,"play","BT","play/",3);
        //particles
        getSprite(p,"energyBuff","PT","buff/energy/",8);
        getSprite(p,"fireBuff","PT","buff/fire/",8);
        getSprite(p,"smokeBuff","PT","buff/smoke/",8);
        getSprite(p,"poisonBuff","PT","buff/poison/",8);
        getSprite(p,"waterBuff","PT","buff/water/",8);
        getSprite(p,"nullBuff","PT","buff/null/",8);
        getSprite(p,"decayBuff","PT","buff/decay/",8);
        getSprite(p,"greenOuchEnemy","PT","enemy/greenOuch/",11);
        getSprite(p,"greyPuffEnemy","PT","enemy/greyPuff/",11);
        getSprite(p,"greenPuffEnemy","PT","enemy/greenPuff/",10);
        getSprite(p,"pinkOuchEnemy","PT","enemy/pinkOuch/",11);
        getSprite(p,"redOuchEnemy","PT","enemy/redOuch/",11);
        getSprite(p,"leafOuchEnemy","PT","enemy/leafOuch/",11);
        getSprite(p,"mediumExplosion","PT","mediumExplosion/",18);
        getSprite(p,"largeExplosion","PT","largeExplosion/",18);
        getSprite(p,"energyExDebris","PT","explosionDebris/energy/",4);
        getSprite(p,"greenMagicBuff","PT","buff/greenMagic/",8);
        getSprite(p,"boltBreak","PT","boltBreak/",4);
        getSprite(p,"railgunBlast","PT","railgunBlast/",11);
        //projectiles
        getSprite(p,"misc","PJ","misc/",6);
        getSprite(p,"flame","PJ","flame/",23);
        //enemies
        getSprite(p,"treeGiantAttack","EN","treeGiant/attack/",63);
        getSprite(p,"treeGiantMove","EN","treeGiant/move/",91);
        getSprite(p,"treeSpiritAttack","EN","treeSpirit/attack/",42);
        getSprite(p,"treeSpiritMove","EN","treeSpirit/move/",47);
        getSprite(p,"treeSpriteMove","EN","treeSprite/move/",30);
        getSprite(p,"treeSpriteAttack","EN","treeSprite/attack/",50);
        getSprite(p,"midBugAttack","EN","midBug/attack/",42);
        getSprite(p,"midBugMove","EN","midBug/move/",8);
        getSprite(p,"smolBugAttack","EN","smolBug/attack/",34);
        getSprite(p,"smolBugMove","EN","smolBug/move/",8);
        getSprite(p,"bigBugAttack","EN","bigBug/attack/",100);
        getSprite(p,"bigBugMove","EN","bigBug/move/",12);
        getSprite(p,"snakeAttack","EN","snake/attack/",15);
        getSprite(p,"snakeMove","EN","snake/move/",10);
        getSprite(p,"wormMove","EN","worm/move/",1);
        getSprite(p,"wormAttack","EN","worm/attack/",34); //todo: make not look like dick
        getSprite(p,"butterflyAttack","EN","butterfly/attack/",16);
        getSprite(p,"butterflyMove","EN","butterfly/move/",8);
        getSprite(p,"dummyAttack","EN","dummy/attack/",1);
        getSprite(p,"dummyMove","EN","dummy/move/",1);
        //turrets
        getSprite(p,"slingshotFire","TR","slingshot/fire/",34);
        getSprite(p,"slingshotLoad","TR","slingshot/load/",59);
        getSprite(p,"teslaFire","TR","tesla/fire/",6);
        getSprite(p,"teslaLoad","TR","tesla/load/",5);
        getSprite(p,"teslaIdle","TR","tesla/idle/",18);
        getSprite(p,"crossbowFire","TR","crossbow/fire/",13);
        getSprite(p,"crossbowLoad","TR","crossbow/load/",81);
        getSprite(p,"miscCannonFire","TR","miscCannon/fire/",12);
        getSprite(p,"miscCannonLoad","TR","miscCannon/load/",1);
        getSprite(p,"energyBlasterFire","TR","energyBlaster/fire/",14);
        getSprite(p,"energyBlasterLoad","TR","energyBlaster/load/",42);
        getSprite(p,"magicMissleerFire","TR","magicMissleer/fire/",8);
        getSprite(p,"magicMissleerLoad","TR","magicMissleer/load/",26);
        getSprite(p,"magicMissleerIdle","TR","magicMissleer/idle/",8);
        getSprite(p,"nightmareFire","TR","nightmare/fire/",14);
        getSprite(p,"nightmareLoad","TR","nightmare/load/",22);
        getSprite(p,"flamethrowerFire","TR","flamethrower/fire/",4);
        getSprite(p,"flamethrowerLoad","TR","flamethrower/load/",1);
        getSprite(p,"flamethrowerIdle","TR","flamethrower/idle/",4);
        getSprite(p,"railgunFire","TR","railgun/fire/",15);
        getSprite(p,"railgunLoad","TR","railgun/load/",9);
        getSprite(p,"railgunIdle","TR","railgun/idle/",6);
        getSprite(p,"railgunVaporTrail","TR","railgun/vaporTrail/",15);
        getSprite(p,"waveMotionFire","TR","waveMotion/fire/",18);
        getSprite(p,"waveMotionLoad","TR","waveMotion/load/",80);
        getSprite(p,"waveMotionIdle","TR","waveMotion/idle/",14);
        getSprite(p,"waveMotionBeam","TR","waveMotion/beam/",18);
        //walls
        getSprite(p,"woodWall","TW","wood/",4);
        getSprite(p,"stoneWall","TW","stone/",4);
        getSprite(p,"metalWall","TW","metal/",4);
        getSprite(p,"crystalWall","TW","crystal/",4);
        getSprite(p,"ultimateWall","TW","ultimate/",4);
        //machines
        getSprite(p,"stoneDrill","MA","stoneDrill/",4);
    }

    private static void getSprite(PApplet p, String name, String type, String folder, int length) {
        String mainFolder = "";
        switch (type) {
            case "IC":
                mainFolder = "guiObjects/";
                break;
            case "BT":
                mainFolder = "guiObjects/buttons/";
                break;
            case "PT":
                mainFolder = "particles/";
                break;
            case "PJ":
                mainFolder = "projectiles/";
                break;
            case "EN":
                mainFolder = "enemies/";
                break;
            case "TR":
                mainFolder = "towers/turrets/";
                break;
            case "TW":
                mainFolder = "towers/walls/";
                break;
            case "MA":
                mainFolder = "machines/";
                break;
        }
        String fullName = name+type;
        String path = "sprites/"+mainFolder+folder;
        spritesAnimH.put(fullName, new PImage[length]);
        for (int i = length-1; i >= 0; i--) {
            spritesAnimH.get(fullName)[i] = p.loadImage(path + PApplet.nf(i,3) + ".png");
        }
    }

    public static void loadSprites(PApplet p) {
        //enemies
        spritesH.put("treeGiantEN",p.loadImage("sprites/enemies/treeGiant/idle.png"));
        spritesH.put("treeSpiritEN",p.loadImage("sprites/enemies/treeSpirit/idle.png"));
        spritesH.put("treeSpriteEN",p.loadImage("sprites/enemies/treeSprite/idle.png"));
        spritesH.put("midBugEN",p.loadImage("sprites/enemies/midBug/idle.png"));
        spritesH.put("bigBugEN",p.loadImage("sprites/enemies/bigBug/idle.png"));
        spritesH.put("snakeEN",p.loadImage("sprites/enemies/snake/idle.png"));
        spritesH.put("dummyEN",p.loadImage("sprites/enemies/dummy/idle.png"));
        //icons
        spritesH.put("moneyIc",p.loadImage("sprites/guiObjects/money.png"));
        spritesH.put("wavePrimaryIc",p.loadImage("sprites/guiObjects/wavePrimary.png"));
        spritesH.put("waveSecondaryIc",p.loadImage("sprites/guiObjects/waveSecondary.png"));
        spritesH.put("currentLineIc",p.loadImage("sprites/guiObjects/currentLine.png"));
        //particles
        spritesH.put("crystalPt",p.loadImage("sprites/particles/debris/crystal.png"));
        spritesH.put("devWoodPt",p.loadImage("sprites/particles/debris/devWood.png"));
        spritesH.put("metalPt",p.loadImage("sprites/particles/debris/metal.png"));
        spritesH.put("stonePt",p.loadImage("sprites/particles/debris/stone.png"));
        spritesH.put("ultimatePt",p.loadImage("sprites/particles/debris/ultimate.png"));
        spritesH.put("woodPt",p.loadImage("sprites/particles/debris/wood.png"));
        spritesH.put("darkMetalPt",p.loadImage("sprites/particles/debris/darkMetal.png"));
        spritesH.put("dirtPt",p.loadImage("sprites/particles/debris/dirt.png"));
        spritesH.put("nullPt",p.loadImage("sprites/particles/null/null.png"));
        //projectiles
        spritesH.put("boltPj",p.loadImage("sprites/projectiles/bolt.png"));
        spritesH.put("devPj",p.loadImage("sprites/projectiles/dev.png"));
        spritesH.put("energyPj",p.loadImage("sprites/projectiles/energy.png"));
        spritesH.put("magicMisslePj",p.loadImage("sprites/projectiles/magicMissle.png"));
        spritesH.put("nullPj",p.loadImage("sprites/projectiles/null.png"));
        spritesH.put("pebblePj",p.loadImage("sprites/projectiles/pebble.png"));
        spritesH.put("needlePj",p.loadImage("sprites/projectiles/needle.png"));
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
        spritesH.put("nightmareBaseTR",p.loadImage("sprites/towers/turrets/nightmare/base.png"));
        spritesH.put("nightmareFullTR",p.loadImage("sprites/towers/turrets/nightmare/full.png"));
        spritesH.put("nightmareIdleTR",p.loadImage("sprites/towers/turrets/nightmare/idle.png"));
        spritesH.put("flamethrowerBaseTR",p.loadImage("sprites/towers/turrets/flamethrower/base.png"));
        spritesH.put("flamethrowerFullTR",p.loadImage("sprites/towers/turrets/flamethrower/full.png"));
        spritesH.put("flamethrowerIdleTR",p.loadImage("sprites/towers/turrets/flamethrower/idle.png"));
        spritesH.put("railgunBaseTR",p.loadImage("sprites/towers/turrets/railgun/base.png"));
        spritesH.put("railgunFullTR",p.loadImage("sprites/towers/turrets/railgun/full.png"));
        spritesH.put("railgunIdleTR",p.loadImage("sprites/towers/turrets/railgun/idle.png"));
        spritesH.put("waveMotionBaseTR",p.loadImage("sprites/towers/turrets/waveMotion/base.png"));
        spritesH.put("waveMotionFullTR",p.loadImage("sprites/towers/turrets/waveMotion/full.png"));
        spritesH.put("waveMotionIdleTR",p.loadImage("sprites/towers/turrets/waveMotion/idle.png"));
        //walls
        spritesH.put("shadowBothTW",p.loadImage("sprites/towers/walls/overlays/shadowBoth.png"));
        spritesH.put("shadowBLTW",p.loadImage("sprites/towers/walls/overlays/shadowBL.png"));
        spritesH.put("shadowTRTW",p.loadImage("sprites/towers/walls/overlays/shadowTR.png"));
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
        //tiles
        //BGA
        spritesH.put("dirtBGA_TL",p.loadImage("sprites/tiles/BGA/dirt/base.png"));
        spritesH.put("grassBGA_TL",p.loadImage("sprites/tiles/BGA/grass/base.png"));
        for (int i = 0; i < 1; i++) {
            String name = null;
            if (i == 0) name = "grass";
            spritesH.put(name + "BGA_T_TL", p.loadImage("sprites/tiles/BGA/" + name + "/t.png"));
            spritesH.put(name + "BGA_R_TL", p.loadImage("sprites/tiles/BGA/" + name + "/r.png"));
            spritesH.put(name + "BGA_B_TL", p.loadImage("sprites/tiles/BGA/" + name + "/b.png"));
            spritesH.put(name + "BGA_L_TL", p.loadImage("sprites/tiles/BGA/" + name + "/l.png"));
        }
        //BGB
        spritesH.put("dirtPatchBGB_TL",p.loadImage("sprites/tiles/BGB/dirtPatch.png"));
        spritesH.put("grassPatchBGB_TL",p.loadImage("sprites/tiles/BGB/grassPatch.png"));
        spritesH.put("grassCornerBL_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/bl.png"));
        spritesH.put("grassCornerBR_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/br.png"));
        spritesH.put("grassCornerTL_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/tl.png"));
        spritesH.put("grassCornerTR_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/tr.png"));
        //BGW
        spritesH.put("woodWallBGW_TL",p.loadImage("sprites/tiles/BGW/woodWall/base.png"));
        spritesH.put("stoneWallBGW_TL",p.loadImage("sprites/tiles/BGW/stoneWall/base.png"));
        spritesH.put("metalWallBGW_TL",p.loadImage("sprites/tiles/BGW/metalWall/base.png"));
        spritesH.put("crystalWallBGW_TL",p.loadImage("sprites/tiles/BGW/crystalWall/base.png"));
        spritesH.put("titaniumWallBGW_TL",p.loadImage("sprites/tiles/BGW/titaniumWall/base.png"));
        for (int i = 0; i < 2; i++) { //simple connections
            String name = null;
            if (i == 0) name = "woodWall";
            if (i == 1) name = "stoneWall";
            spritesH.put(name + "BGW_T_TL", p.loadImage("sprites/tiles/BGW/" + name + "/t.png"));
            spritesH.put(name + "BGW_R_TL", p.loadImage("sprites/tiles/BGW/" + name + "/r.png"));
            spritesH.put(name + "BGW_B_TL", p.loadImage("sprites/tiles/BGW/" + name + "/b.png"));
            spritesH.put(name + "BGW_L_TL", p.loadImage("sprites/tiles/BGW/" + name + "/l.png"));
        }
        for (int i = 0; i < 3; i++) { //diagonal double connections
            String name = null;
            if (i == 0) name = "metalWall";
            if (i == 1) name = "crystalWall";
            if (i == 2) name = "titaniumWall";
            spritesH.put(name + "BGW_BLI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bli.png"));
            spritesH.put(name + "BGW_BLO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/blo.png"));
            spritesH.put(name + "BGW_BRI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bri.png"));
            spritesH.put(name + "BGW_BRO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bro.png"));
            spritesH.put(name + "BGW_TLI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tli.png"));
            spritesH.put(name + "BGW_TLO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tlo.png"));
            spritesH.put(name + "BGW_TRI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tri.png"));
            spritesH.put(name + "BGW_TRO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tro.png"));
        }
        //BGC
        spritesH.put("rockBGC_TL",p.loadImage("sprites/tiles/BGC/rock.png"));
        spritesH.put("smallRockBGC_TL",p.loadImage("sprites/tiles/BGC/smallRock.png"));
        spritesH.put("leavesBGC_TL",p.loadImage("sprites/tiles/BGC/leaves.png"));
        spritesH.put("dandelionsBGC_TL",p.loadImage("sprites/tiles/BGC/dandelions.png"));
        spritesH.put("woodDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/wood.png"));
        spritesH.put("stoneDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/stone.png"));
        spritesH.put("metalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/metal.png"));
        spritesH.put("darkMetalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/darkMetal.png"));
        spritesH.put("crystalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/crystal.png"));
        spritesH.put("titaniumDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/titanium.png"));
        //obstacles
        spritesH.put("smallTreeOb_TL",p.loadImage("sprites/tiles/obstacles/smallTree.png"));
        spritesH.put("treeBLOb_TL",p.loadImage("sprites/tiles/obstacles/tree/bl.png"));
        spritesH.put("treeBROb_TL",p.loadImage("sprites/tiles/obstacles/tree/br.png"));
        spritesH.put("treeTLOb_TL",p.loadImage("sprites/tiles/obstacles/tree/tl.png"));
        spritesH.put("treeTROb_TL",p.loadImage("sprites/tiles/obstacles/tree/tr.png"));
    }
}
