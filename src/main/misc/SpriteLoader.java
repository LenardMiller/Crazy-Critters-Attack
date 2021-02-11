package main.misc;

import processing.core.PApplet;
import processing.core.PImage;

import static main.Main.spritesAnimH;
import static main.Main.spritesH;

public class SpriteLoader {
    
    public SpriteLoader() {}

    /**
     * Loads animations.
     * todo: load dynamically
     * @param p the PApplet
     */
    public static void loadSpritesAnim(PApplet p) {
        //icons & buttons
        getSprite(p,"upgrade","IC","upgrades/",29);
        getSprite(p,"moneyAdd","BT","moneyAdd/",3);
        getSprite(p,"upgrade","BT","upgradeButton/",5);
        getSprite(p,"sellTower","BT","sellTower/",3);
        getSprite(p,"targetPriority","BT","targetPriority/",3);
        getSprite(p,"towerTabSwitch","BT","towerTabSwitch/",3);
        getSprite(p,"wallBuy","BT","wallBuy/",3);
        getSprite(p,"play","BT","play/",4);
        getSprite(p, "genericButton", "BT", "genericButton/", 3);
        //particles
        getSprite(p,"energyBuff","PT","buff/energy/",8);
        getSprite(p,"fireBuff","PT","buff/fire/",8);
        getSprite(p,"smokeBuff","PT","buff/smoke/",8);
        getSprite(p,"poisonBuff","PT","buff/poison/",8);
        getSprite(p,"waterBuff","PT","buff/water/",8);
        getSprite(p,"nullBuff","PT","buff/null/",8);
        getSprite(p,"decayBuff","PT","buff/decay/",8);
        getSprite(p,"glueBuff","PT","buff/glue/",8);
        getSprite(p,"greenOuchEnemy","PT","enemy/greenOuch/",11);
        getSprite(p,"greyPuffEnemy","PT","enemy/greyPuff/",11);
        getSprite(p,"greenPuffEnemy","PT","enemy/greenPuff/",10);
        getSprite(p,"poisonPuffEnemy","PT","enemy/poisonPuff/",10);
        getSprite(p,"gluePuffEnemy","PT","enemy/gluePuff/",10);
        getSprite(p,"pinkOuchEnemy","PT","enemy/pinkOuch/",11);
        getSprite(p,"redOuchEnemy","PT","enemy/redOuch/",11);
        getSprite(p,"glowOuchEnemy","PT","enemy/glowOuch/",11);
        getSprite(p,"leafOuchEnemy","PT","enemy/leafOuch/",11);
        getSprite(p,"fireMediumExplosion","PT","mediumExplosion/fire/",18);
        getSprite(p,"puffMediumExplosion","PT","mediumExplosion/puff/",18);
        getSprite(p,"energyMediumExplosion","PT","mediumExplosion/energy/",18);
        getSprite(p,"fireLargeExplosion","PT","largeExplosion/fire/",17);
        getSprite(p,"poisonLargeExplosion","PT","largeExplosion/poison/",17);
        getSprite(p,"glueLargeExplosion","PT","largeExplosion/glue/",17);
        getSprite(p,"energyExDebris","PT","explosionDebris/energy/",4);
        getSprite(p,"poisonExDebris","PT","explosionDebris/poison/",4);
        getSprite(p,"metalExDebris","PT","explosionDebris/metal/",4);
        getSprite(p,"glueExDebris","PT","explosionDebris/glue/",4);
        getSprite(p,"fireExDebris","PT","explosionDebris/fire/",4);
        getSprite(p,"greenMagicBuff","PT","buff/greenMagic/",8);
        getSprite(p,"boltBreak","PT","boltBreak/",4);
        getSprite(p,"railgunBlast","PT","railgunBlast/",11);
        getSprite(p,"greenOuchPile","PT", "pile/greenOuch/", 4);
        getSprite(p,"redOuchPile","PT", "pile/redOuch/", 4);
        getSprite(p,"glowOuchPile","PT", "pile/glowOuch/", 4);
        getSprite(p,"leafOuchPile","PT", "pile/leafOuch/", 4);
        getSprite(p,"dirtPile","PT","pile/dirt/", 4);
        getSprite(p,"sandPile","PT","pile/sand/", 4);
        getSprite(p,"stonePile","PT","pile/stone/", 4);
        //projectiles
        getSprite(p,"misc","PJ","misc/",6);
        getSprite(p,"flame","PJ","flame/",23);
        //enemies
        getSprite(p,"treeGiantAttack","EN","treeGiant/attack/",63);
        getSprite(p,"treeGiantMove","EN","treeGiant/move/",91);
        getSprite(p,"treeGiantDie","EN","treeGiant/die/",7);
        getSprite(p,"treeGiantParts","EN","treeGiant/parts/",6);
        getSprite(p,"treeSpiritAttack","EN","treeSpirit/attack/",42);
        getSprite(p,"treeSpiritMove","EN","treeSpirit/move/",47);
        getSprite(p,"treeSpiritDie","EN","treeSpirit/die/",5);
        getSprite(p,"treeSpiritParts","EN","treeSpirit/parts/",6);
        getSprite(p,"treeSpriteMove","EN","treeSprite/move/",30);
        getSprite(p,"treeSpriteAttack","EN","treeSprite/attack/",50);
        getSprite(p,"treeSpriteDie","EN","treeSprite/die/",4);
        getSprite(p,"treeSpriteParts","EN","treeSprite/parts/",6);
        getSprite(p,"midBugAttack","EN","midBug/attack/",42);
        getSprite(p,"midBugMove","EN","midBug/move/",8);
        getSprite(p,"midBugDie","EN","midBug/die/",5);
        getSprite(p,"midBugParts","EN","midBug/parts/",9);
        getSprite(p,"albinoBugAttack","EN","albinoBug/attack/",42);
        getSprite(p,"albinoBugMove","EN","albinoBug/move/",8);
        getSprite(p,"albinoBugDie","EN","albinoBug/die/",5);
        getSprite(p,"albinoBugParts","EN","albinoBug/parts/",9);
        getSprite(p,"smolBugAttack","EN","smolBug/attack/",27);
        getSprite(p,"smolBugMove","EN","smolBug/move/",8);
        getSprite(p,"smolBugDie","EN","smolBug/die/", 4);
        getSprite(p,"smolBugParts","EN","smolBug/parts/",9);
        getSprite(p,"bigBugAttack","EN","bigBug/attack/",100);
        getSprite(p,"bigBugMove","EN","bigBug/move/",12);
        getSprite(p,"bigBugDie","EN","bigBug/die/",6);
        getSprite(p,"bigBugParts","EN","bigBug/parts/",9);
        getSprite(p,"bigAlbinoBugAttack","EN","bigAlbinoBug/attack/",100);
        getSprite(p,"bigAlbinoBugMove","EN","bigAlbinoBug/move/",12);
        getSprite(p,"bigAlbinoBugDie","EN","bigAlbinoBug/die/",6);
        getSprite(p,"bigAlbinoBugParts","EN","bigAlbinoBug/parts/",9);
        getSprite(p,"snakeAttack","EN","snake/attack/",15);
        getSprite(p,"snakeMove","EN","snake/move/",10);
        getSprite(p,"snakeDie","EN","snake/die/",3);
        getSprite(p,"snakeParts","EN","snake/parts/",3);
        getSprite(p,"sidewinderAttack","EN","sidewinder/attack/",15);
        getSprite(p,"sidewinderMove","EN","sidewinder/move/",10);
        getSprite(p,"sidewinderDie","EN","sidewinder/die/",3);
        getSprite(p,"sidewinderParts","EN","sidewinder/parts/",3);
        getSprite(p,"scorpionAttack","EN","scorpion/attack/",13);
        getSprite(p,"scorpionMove","EN","scorpion/move/",4);
        getSprite(p,"scorpionDie","EN","scorpion/die/",5);
        getSprite(p,"scorpionParts","EN","scorpion/parts/",5);
        getSprite(p,"emperorAttack","EN","emperor/attack/",15);
        getSprite(p,"emperorMove","EN","emperor/move/",8);
        getSprite(p,"emperorDie","EN","emperor/die/",5);
        getSprite(p,"emperorParts","EN","emperor/parts/",5);
        getSprite(p,"wormMove","EN","worm/move/",1);
        getSprite(p,"wormAttack","EN","worm/attack/",34);
        getSprite(p,"wormDie","EN","worm/die/",5);
        getSprite(p,"wormParts", "EN", "worm/parts/", 3);
        getSprite(p,"midWormMove","EN","midWorm/move/",1);
        getSprite(p,"midWormAttack","EN","midWorm/attack/",30);
        getSprite(p,"midWormDie","EN","midWorm/die/",6);
        getSprite(p,"midWormParts", "EN", "midWorm/parts/", 3);
        getSprite(p,"bigWormMove","EN","bigWorm/move/",1);
        getSprite(p,"bigWormAttack","EN","bigWorm/attack/",58);
        getSprite(p,"bigWormDie","EN","bigWorm/die/",9);
        getSprite(p,"bigWormParts", "EN", "bigWorm/parts/", 3);
        getSprite(p,"butterflyAttack","EN","butterfly/attack/",16);
        getSprite(p,"butterflyMove","EN","butterfly/move/",8);
        getSprite(p,"butterflyDie","EN","butterfly/die/",5);
        getSprite(p,"butterflyParts", "EN", "butterfly/parts/", 5);
        getSprite(p,"dummyAttack","EN","dummy/attack/",1);
        getSprite(p,"dummyMove","EN","dummy/move/",1);
        getSprite(p,"dummyDie","EN","dummy/die/",1);
        getSprite(p,"dummyParts","EN", "dummy/parts/", 1);
        //turrets
        getSprite(p,"slingshotFire","TR","slingshot/fire/",34);
        getSprite(p,"slingshotLoad","TR","slingshot/load/",59);
        getSprite(p,"slingshotRockFire","TR","slingshot/rock/fire/",34);
        getSprite(p,"slingshotRockLoad","TR","slingshot/rock/load/",59);
        getSprite(p,"slingshotGravelFire","TR","slingshot/gravel/fire/",34);
        getSprite(p,"slingshotGravelLoad","TR","slingshot/gravel/load/",59);
        getSprite(p,"teslaFire","TR","tesla/fire/",6);
        getSprite(p,"teslaLoad","TR","tesla/load/",5);
        getSprite(p,"teslaIdle","TR","tesla/idle/",18);
        getSprite(p,"crossbowFire","TR","crossbow/fire/",13);
        getSprite(p,"crossbowLoad","TR","crossbow/load/",81);
        getSprite(p,"cannonFire","TR","cannon/fire/",6);
        getSprite(p,"cannonLoad","TR","cannon/load/",18);
        getSprite(p,"fragCannonFire","TR","cannon/fragCannon/fire/",6);
        getSprite(p,"fragCannonLoad","TR","cannon/fragCannon/load/",18);
        getSprite(p,"dynamiteLauncherFire","TR","cannon/dynamiteLauncher/fire/",11);
        getSprite(p,"dynamiteLauncherLoad","TR","cannon/dynamiteLauncher/load/",80);
        getSprite(p,"seismicFire","TR","seismic/fire/",14);
        getSprite(p,"seismicLoad","TR","seismic/load/",20);
        getSprite(p,"seismicSniperFire","TR","seismic/sniper/fire/",14);
        getSprite(p,"seismicSniperLoad","TR","seismic/sniper/load/",20);
        getSprite(p,"seismicSlammerFire","TR","seismic/slammer/fire/",3);
        getSprite(p,"seismicSlammerLoad","TR","seismic/slammer/load/",9);
        getSprite(p,"crossbowMultishotFire","TR","crossbow/multishot/fire/",13);
        getSprite(p,"crossbowMultishotLoad","TR","crossbow/multishot/load/",81);
        getSprite(p,"crossbowReinforcedFire","TR","crossbow/reinforced/fire/",13);
        getSprite(p,"crossbowReinforcedLoad","TR","crossbow/reinforced/load/",81);
        getSprite(p,"gluerFire","TR","gluer/fire/",5);
        getSprite(p,"gluerLoad","TR","gluer/load/",7);
        getSprite(p,"shatterGluerFire","TR","gluer/spiker/fire/",5);
        getSprite(p,"shatterGluerLoad","TR","gluer/spiker/load/",7);
        getSprite(p,"splashGluerFire","TR","gluer/splasher/fire/",5);
        getSprite(p,"splashGluerLoad","TR","gluer/splasher/load/",7);
        getSprite(p,"miscCannonFire","TR","miscCannon/fire/",5);
        getSprite(p,"miscCannonLoad","TR","miscCannon/load/",1);
        getSprite(p,"miscCannonLaundryFire","TR","miscCannon/laundry/fire/",5);
        getSprite(p,"miscCannonLaundryLoad","TR","miscCannon/laundry/load/",1);
        getSprite(p,"miscCannonBarrelFire","TR","miscCannon/barrel/fire/",5);
        getSprite(p,"miscCannonBarrelLoad","TR","miscCannon/barrel/load/",1);
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
        getSprite(p,"woodWall","TW","Wood/",4);
        getSprite(p,"stoneWall","TW","Stone/",4);
        getSprite(p,"metalWall","TW","Metal/",4);
        getSprite(p,"crystalWall","TW","Crystal/",4);
        getSprite(p,"ultimateWall","TW","Ultimate/",4);
        //machines
        getMachineSprite(p,"stoneDrill",new int[]{12,12,13,20});
        getMachineSprite(p, "metalDrill", new int[]{8,8,9,11});
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
        }
        String fullName = name+type;
        String path = "sprites/"+mainFolder+folder;
        spritesAnimH.put(fullName, new PImage[length]);
        for (int i = length-1; i >= 0; i--) {
            spritesAnimH.get(fullName)[i] = p.loadImage(path + PApplet.nf(i,3) + ".png");
        }
    }

    private static void getMachineSprite(PApplet p, String name, int[] lengths) {
        String fullName = name + "MA";
        String path = "sprites/machines/" + name + "/";
        spritesAnimH.put(fullName, new PImage[lengths[0]]); //base
        for (int i = lengths[0]-1; i >= 0; i--) {
            spritesAnimH.get(fullName)[i] = p.loadImage(path + "base/" + PApplet.nf(i,3) + ".png");
        }
        for (int i = 1; i <= 3; i++) { //damage variants
            spritesAnimH.put(fullName + "d" + i, new PImage[lengths[i]]);
            for (int j = lengths[i]-1; j >= 0; j--) {
                spritesAnimH.get(fullName + "d" + i)[j] = p.loadImage(path + "d" + i + "/" + PApplet.nf(j,3) + ".png");
            }
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
        spritesH.put("sandPt",p.loadImage("sprites/particles/debris/sand.png"));
        spritesH.put("nullPt",p.loadImage("sprites/particles/null/null.png"));
        //projectiles
        spritesH.put("boltPj",p.loadImage("sprites/projectiles/bolt.png"));
        spritesH.put("cannonBallPj",p.loadImage("sprites/projectiles/cannonBall.png"));
        spritesH.put("reinforcedBoltPj",p.loadImage("sprites/projectiles/reinforcedBolt.png"));
        spritesH.put("devPj",p.loadImage("sprites/projectiles/dev.png"));
        spritesH.put("energyPj",p.loadImage("sprites/projectiles/energy.png"));
        spritesH.put("magicMisslePj",p.loadImage("sprites/projectiles/magicMissle.png"));
        spritesH.put("nullPj",p.loadImage("sprites/projectiles/null.png"));
        spritesH.put("pebblePj",p.loadImage("sprites/projectiles/pebble.png"));
        spritesH.put("rockPj",p.loadImage("sprites/projectiles/rock.png"));
        spritesH.put("needlePj",p.loadImage("sprites/projectiles/needle.png"));
        spritesH.put("laundryPj",p.loadImage("sprites/projectiles/laundry.png"));
        spritesH.put("gluePj",p.loadImage("sprites/projectiles/glue.png"));
        spritesH.put("spikeyGluePj",p.loadImage("sprites/projectiles/spikeyGlue.png"));
        spritesH.put("glueSpikePj",p.loadImage("sprites/projectiles/glueSpike.png"));
        spritesH.put("dynamitePj",p.loadImage("sprites/projectiles/dynamite.png"));
        //turrets
        spritesH.put("crossbowBaseTR",p.loadImage("sprites/towers/turrets/crossbow/base.png"));
        spritesH.put("crossbowFullTR",p.loadImage("sprites/towers/turrets/crossbow/full.png"));
        spritesH.put("crossbowIdleTR",p.loadImage("sprites/towers/turrets/crossbow/idle.png"));
        spritesH.put("cannonBaseTR",p.loadImage("sprites/towers/turrets/cannon/base.png"));
        spritesH.put("cannonFullTR",p.loadImage("sprites/towers/turrets/cannon/full.png"));
        spritesH.put("cannonIdleTR",p.loadImage("sprites/towers/turrets/cannon/idle.png"));
        spritesH.put("fragCannonBaseTR",p.loadImage("sprites/towers/turrets/cannon/fragCannon/base.png"));
        spritesH.put("fragCannonIdleTR",p.loadImage("sprites/towers/turrets/cannon/fragCannon/idle.png"));
        spritesH.put("dynamiteLauncherBaseTR",p.loadImage("sprites/towers/turrets/cannon/dynamiteLauncher/base.png"));
        spritesH.put("dynamiteLauncherIdleTR",p.loadImage("sprites/towers/turrets/cannon/dynamiteLauncher/idle.png"));
        spritesH.put("crossbowMultishotBaseTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/base.png"));
        spritesH.put("crossbowMultishotFullTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/full.png"));
        spritesH.put("crossbowMultishotIdleTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/idle.png"));
        spritesH.put("crossbowReinforcedBaseTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/base.png"));
        spritesH.put("crossbowReinforcedFullTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/full.png"));
        spritesH.put("crossbowReinforcedIdleTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/idle.png"));
        spritesH.put("energyBlasterBaseTR",p.loadImage("sprites/towers/turrets/energyBlaster/base.png"));
        spritesH.put("energyBlasterFullTR",p.loadImage("sprites/towers/turrets/energyBlaster/full.png"));
        spritesH.put("energyBlasterIdleTR",p.loadImage("sprites/towers/turrets/energyBlaster/idle.png"));
        spritesH.put("magicMissleerBaseTR",p.loadImage("sprites/towers/turrets/magicMissleer/base.png"));
        spritesH.put("magicMissleerFullTR",p.loadImage("sprites/towers/turrets/magicMissleer/full.png"));
        spritesH.put("magicMissleerIdleTR",p.loadImage("sprites/towers/turrets/magicMissleer/idle.png"));
        spritesH.put("gluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/base.png"));
        spritesH.put("gluerFullTR",p.loadImage("sprites/towers/turrets/gluer/full.png"));
        spritesH.put("gluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/idle.png"));
        spritesH.put("shatterGluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/spiker/base.png"));
        spritesH.put("shatterGluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/spiker/idle.png"));
        spritesH.put("splashGluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/splasher/base.png"));
        spritesH.put("splashGluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/splasher/idle.png"));
        spritesH.put("seismicBaseTR",p.loadImage("sprites/towers/turrets/seismic/base.png"));
        spritesH.put("seismicFullTR",p.loadImage("sprites/towers/turrets/seismic/full.png"));
        spritesH.put("seismicIdleTR",p.loadImage("sprites/towers/turrets/seismic/idle.png"));
        spritesH.put("seismicSniperBaseTR",p.loadImage("sprites/towers/turrets/seismic/sniper/base.png"));
        spritesH.put("seismicSniperFullTR",p.loadImage("sprites/towers/turrets/seismic/sniper/full.png"));
        spritesH.put("seismicSniperIdleTR",p.loadImage("sprites/towers/turrets/seismic/sniper/idle.png"));
        spritesH.put("seismicSlammerBaseTR",p.loadImage("sprites/towers/turrets/seismic/slammer/base.png"));
        spritesH.put("seismicSlammerIdleTR",p.loadImage("sprites/towers/turrets/seismic/slammer/idle.png"));
        spritesH.put("miscCannonBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/base.png"));
        spritesH.put("miscCannonFullTR",p.loadImage("sprites/towers/turrets/miscCannon/full.png"));
        spritesH.put("miscCannonIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/idle.png"));
        spritesH.put("miscCannonLaundryBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/base.png"));
        spritesH.put("miscCannonLaundryFullTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/full.png"));
        spritesH.put("miscCannonLaundryIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/idle.png"));
        spritesH.put("miscCannonBarrelBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/base.png"));
        spritesH.put("miscCannonBarrelFullTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/full.png"));
        spritesH.put("miscCannonBarrelIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/idle.png"));
        spritesH.put("slingshotBaseTR",p.loadImage("sprites/towers/turrets/slingshot/base.png"));
        spritesH.put("slingshotFullTR",p.loadImage("sprites/towers/turrets/slingshot/full.png"));
        spritesH.put("slingshotIdleTR",p.loadImage("sprites/towers/turrets/slingshot/idle.png"));
        spritesH.put("slingshotRockBaseTR",p.loadImage("sprites/towers/turrets/slingshot/rock/base.png"));
        spritesH.put("slingshotRockFullTR",p.loadImage("sprites/towers/turrets/slingshot/rock/full.png"));
        spritesH.put("slingshotRockIdleTR",p.loadImage("sprites/towers/turrets/slingshot/rock/idle.png"));
        spritesH.put("slingshotGravelBaseTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/base.png"));
        spritesH.put("slingshotGravelFullTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/full.png"));
        spritesH.put("slingshotGravelIdleTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/idle.png"));
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
        spritesH.put("stoneBGA_TL",p.loadImage("sprites/tiles/BGA/stone/base.png"));
        spritesH.put("dirtBGA_TL",p.loadImage("sprites/tiles/BGA/dirt/base.png"));
        spritesH.put("sandBGA_TL", p.loadImage("sprites/tiles/BGA/sand/base.png"));
        spritesH.put("grassBGA_TL",p.loadImage("sprites/tiles/BGA/grass/base.png"));
        for (int i = 0; i < 1; i++) {
            String name;
            name = "grass";
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
        spritesH.put("lichenBGB_TL",p.loadImage("sprites/tiles/BGB/lichen.png"));
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
        spritesH.put("glowshrooms1BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms1.png"));
        spritesH.put("glowshrooms2BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms2.png"));
        spritesH.put("glowshrooms3BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms3.png"));
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
        spritesH.put("cactus0Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/0.png"));
        spritesH.put("cactus1Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/1.png"));
        spritesH.put("cactus2Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/2.png"));
        spritesH.put("caveWallBaseOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/base.png"));
        spritesH.put("caveWallLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/l.png"));
        spritesH.put("caveWallROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/r.png"));
        spritesH.put("caveWallTOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/t.png"));
        spritesH.put("caveWallBOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/b.png"));
        spritesH.put("caveWallBROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/br.png"));
        spritesH.put("caveWallBLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/bl.png"));
        spritesH.put("caveWallTLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tl.png"));
        spritesH.put("caveWallTROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tr.png"));
        spritesH.put("caveWallBRCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/brc.png"));
        spritesH.put("caveWallBLCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/blc.png"));
        spritesH.put("caveWallTLCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tlc.png"));
        spritesH.put("caveWallTRCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/trc.png"));
    }
}
