package main.misc;

import processing.core.PApplet;
import processing.core.PImage;

import static main.Main.animatedSprites;
import static main.Main.staticSprites;

public class SpriteLoader {
    
    public SpriteLoader() {}

    /**
     * Loads animations.
     * todo: load dynamically?
     * @param p the PApplet
     */
    public static void loadAnimatedSprites(PApplet p) {
        //icons & buttons
        getSprite(p,"upgrade","IC","upgrades/",35);
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
        getSprite(p,"decayBuff","PT","buff/decay/",8);
        getSprite(p,"glueBuff","PT","buff/glue/",8);
        getSprite(p,"stunBuff","PT","buff/stun/",8);
        getSprite(p,"electricityBuff","PT","buff/electricity/",8);
        getSprite(p,"nuclearBuff","PT","buff/nuclear/",8);
        getSprite(p,"darkBuff","PT","buff/dark/",8);
        getSprite(p,"blueSmokeBuff","PT","buff/blueSmoke/",8);
        getSprite(p,"blueGreenFireBuff","PT","buff/blueGreenFire/",8);
        getSprite(p,"greenOuchEnemy","PT","enemy/greenOuch/",11);
        getSprite(p,"greyPuffEnemy","PT","enemy/greyPuff/",11);
        getSprite(p,"greenPuffEnemy","PT","enemy/greenPuff/",10);
        getSprite(p,"poisonPuffEnemy","PT","enemy/poisonPuff/",10);
        getSprite(p,"gluePuffEnemy","PT","enemy/gluePuff/",10);
        getSprite(p,"pinkOuchEnemy","PT","enemy/pinkOuch/",11);
        getSprite(p,"redOuchEnemy","PT","enemy/redOuch/",11);
        getSprite(p,"glowOuchEnemy","PT","enemy/glowOuch/",11);
        getSprite(p,"leafOuchEnemy","PT","enemy/leafOuch/",11);
        getSprite(p,"lichenOuchEnemy","PT","enemy/lichenOuch/",11);
        getSprite(p,"fireMediumExplosion","PT","mediumExplosion/fire/",18);
        getSprite(p,"puffMediumExplosion","PT","mediumExplosion/puff/",18);
        getSprite(p,"energyMediumExplosion","PT","mediumExplosion/energy/",18);
        getSprite(p,"fireLargeExplosion","PT","largeExplosion/fire/",17);
        getSprite(p,"poisonLargeExplosion","PT","largeExplosion/poison/",17);
        getSprite(p,"glueLargeExplosion","PT","largeExplosion/glue/",17);
        getSprite(p,"energyLargeExplosion","PT","largeExplosion/energy/",17);
        getSprite(p,"energyExDebris","PT","explosionDebris/energy/",4);
        getSprite(p,"poisonExDebris","PT","explosionDebris/poison/",4);
        getSprite(p,"metalExDebris","PT","explosionDebris/metal/",4);
        getSprite(p,"glueExDebris","PT","explosionDebris/glue/",4);
        getSprite(p,"fireExDebris","PT","explosionDebris/fire/",4);
        getSprite(p,"nuclearExDebris","PT","explosionDebris/nuclear/",4);
        getSprite(p,"darkExDebris","PT","explosionDebris/dark/",4);
        getSprite(p,"electricityExDebris","PT","explosionDebris/electricity/",4);
        getSprite(p,"greenMagicBuff","PT","buff/greenMagic/",8);
        getSprite(p,"boltBreak","PT","boltBreak/",4);
        getSprite(p,"railgunBlast","PT","railgunBlast/",11);
        getSprite(p,"greenOuchPile","PT", "pile/greenOuch/", 4);
        getSprite(p,"redOuchPile","PT", "pile/redOuch/", 4);
        getSprite(p,"glowOuchPile","PT", "pile/glowOuch/", 4);
        getSprite(p,"leafOuchPile","PT", "pile/leafOuch/", 4);
        getSprite(p,"lichenOuchPile","PT", "pile/lichenOuch/", 4);
        getSprite(p,"dirtPile","PT","pile/dirt/", 4);
        getSprite(p,"sandPile","PT","pile/sand/", 4);
        getSprite(p,"stonePile","PT","pile/stone/", 4);
        //projectiles
        getSprite(p,"misc","PJ","misc/",6);
        getSprite(p,"flame","PJ","flame/",23);
        getSprite(p,"blueFlame","PJ","blueFlame/",21);
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
        getSprite(p,"giantGolemAttack","EN","giantGolem/attack/",63);
        getSprite(p,"giantGolemMove","EN","giantGolem/move/",91);
        getSprite(p,"giantGolemDie","EN","giantGolem/die/",7);
        getSprite(p,"giantGolemParts","EN","giantGolem/parts/",6);
        getSprite(p,"golemAttack","EN","golem/attack/",42);
        getSprite(p,"golemMove","EN","golem/move/",49);
        getSprite(p,"golemDie","EN","golem/die/",5);
        getSprite(p,"golemParts","EN","golem/parts/",6);
        getSprite(p,"smallGolemMove","EN","smallGolem/move/",30);
        getSprite(p,"smallGolemAttack","EN","smallGolem/attack/",25);
        getSprite(p,"smallGolemDie","EN","smallGolem/die/",4);
        getSprite(p,"smallGolemParts","EN","smallGolem/parts/",6);
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
        getSprite(p,"albinoButterflyAttack","EN","albinoButterfly/attack/",16);
        getSprite(p,"albinoButterflyMove","EN","albinoButterfly/move/",8);
        getSprite(p,"albinoButterflyDie","EN","albinoButterfly/die/",5);
        getSprite(p,"albinoButterflyParts", "EN", "albinoButterfly/parts/", 5);
        getSprite(p,"dummyAttack","EN","dummy/attack/",1);
        getSprite(p,"dummyMove","EN","dummy/move/",1);
        getSprite(p,"dummyDie","EN","dummy/die/",1);
        getSprite(p,"dummyParts","EN", "dummy/parts/", 1);
        getSprite(p,"giantBatAttack","EN","giantBat/attack/",6);
        getSprite(p,"giantBatMove","EN","giantBat/move/",6);
        getSprite(p,"giantBatDie","EN","giantBat/die/",5);
        getSprite(p,"giantBatParts","EN", "giantBat/parts/", 6);
        getSprite(p,"batAttack","EN","bat/attack/",6);
        getSprite(p,"batMove","EN","bat/move/",6);
        getSprite(p,"batDie","EN","bat/die/",5);
        getSprite(p,"batParts","EN", "bat/parts/", 6);
        getSprite(p,"wtfAttack","EN","wtf/attack/",21);
        getSprite(p,"wtfMove","EN","wtf/move/",8);
        getSprite(p,"wtfDie","EN","wtf/die/",5);
        getSprite(p,"wtfParts","EN","wtf/parts/",12);
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
        getSprite(p,"lightningFire","TR","tesla/lightning/fire/",4);
        getSprite(p,"lightningLoad","TR","tesla/lightning/load/",16);
        getSprite(p,"lightningIdle","TR","tesla/lightning/idle/",18);
        getSprite(p,"highPowerTeslaIdle","TR","tesla/highPower/idle/",6);
        getSprite(p,"highPowerTeslaFire","TR","tesla/highPower/fire/",4);
        getSprite(p,"highPowerTeslaLoad","TR","tesla/highPower/load/",1);
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
        getSprite(p,"miscCannonBarrelFire","TR","miscCannon/barrel/fire/",3);
        getSprite(p,"miscCannonBarrelLoad","TR","miscCannon/barrel/load/",1);
        getSprite(p,"energyBlasterFire","TR","energyBlaster/fire/",14);
        getSprite(p,"energyBlasterLoad","TR","energyBlaster/load/",42);
        getSprite(p,"nuclearBlasterFire","TR","energyBlaster/nuclearBlaster/fire/",14);
        getSprite(p,"nuclearBlasterLoad","TR","energyBlaster/nuclearBlaster/load/",43);
        getSprite(p,"darkBlasterFire","TR","energyBlaster/darkBlaster/fire/",9);
        getSprite(p,"darkBlasterLoad","TR","energyBlaster/darkBlaster/load/",12);
        getSprite(p,"magicMissleerFire","TR","magicMissleer/fire/",8);
        getSprite(p,"magicMissleerLoad","TR","magicMissleer/load/",26);
        getSprite(p,"magicMissleerIdle","TR","magicMissleer/idle/",8);
        getSprite(p,"nightmareFire","TR","nightmare/fire/",14);
        getSprite(p,"nightmareLoad","TR","nightmare/load/",22);
        getSprite(p,"flamethrowerFire","TR","flamethrower/fire/",2);
        getSprite(p,"flamethrowerLoad","TR","flamethrower/load/",1);
        getSprite(p,"flamethrowerIdle","TR","flamethrower/idle/",4);
        getSprite(p,"flamewheelFire","TR","/flamethrower/flamewheel/fire/",7);
        getSprite(p,"flamewheelLoad","TR","/flamethrower/flamewheel/load/",1);
        getSprite(p,"flamewheelCore","TR","/flamethrower/flamewheel/core/",6);
        getSprite(p,"magicFlamethrowerFire","TR","/flamethrower/magic/fire/",2);
        getSprite(p,"magicFlamethrowerLoad","TR","/flamethrower/magic/load/",1);
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
        getSprite(p,"titaniumWall","TW","Titanium/",4);
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
        animatedSprites.put(fullName, new PImage[length]);
        for (int i = length-1; i >= 0; i--) {
            animatedSprites.get(fullName)[i] = p.loadImage(path + PApplet.nf(i,3) + ".png");
        }
    }

    private static void getMachineSprite(PApplet p, String name, int[] lengths) {
        String fullName = name + "MA";
        String path = "sprites/machines/" + name + "/";
        animatedSprites.put(fullName, new PImage[lengths[0]]); //base
        for (int i = lengths[0]-1; i >= 0; i--) {
            animatedSprites.get(fullName)[i] = p.loadImage(path + "base/" + PApplet.nf(i,3) + ".png");
        }
        for (int i = 1; i <= 3; i++) { //damage variants
            animatedSprites.put(fullName + "d" + i, new PImage[lengths[i]]);
            for (int j = lengths[i]-1; j >= 0; j--) {
                animatedSprites.get(fullName + "d" + i)[j] = p.loadImage(path + "d" + i + "/" + PApplet.nf(j,3) + ".png");
            }
        }
    }

    public static void loadStaticSprites(PApplet p) {
        //icons
        staticSprites.put("moneyIc",p.loadImage("sprites/guiObjects/money.png"));
        staticSprites.put("wavePrimaryIc",p.loadImage("sprites/guiObjects/wavePrimary.png"));
        staticSprites.put("waveSecondaryIc",p.loadImage("sprites/guiObjects/waveSecondary.png"));
        staticSprites.put("currentLineIc",p.loadImage("sprites/guiObjects/currentLine.png"));
        //particles
        staticSprites.put("crystalPt",p.loadImage("sprites/particles/debris/crystal.png"));
        staticSprites.put("metalPt",p.loadImage("sprites/particles/debris/metal.png"));
        staticSprites.put("stonePt",p.loadImage("sprites/particles/debris/stone.png"));
        staticSprites.put("titaniumPt",p.loadImage("sprites/particles/debris/titanium.png"));
        staticSprites.put("woodPt",p.loadImage("sprites/particles/debris/wood.png"));
        staticSprites.put("darkMetalPt",p.loadImage("sprites/particles/debris/darkMetal.png"));
        staticSprites.put("dirtPt",p.loadImage("sprites/particles/debris/dirt.png"));
        staticSprites.put("sandPt",p.loadImage("sprites/particles/debris/sand.png"));
        //projectiles
        staticSprites.put("boltPj",p.loadImage("sprites/projectiles/bolt.png"));
        staticSprites.put("cannonBallPj",p.loadImage("sprites/projectiles/cannonBall.png"));
        staticSprites.put("reinforcedBoltPj",p.loadImage("sprites/projectiles/reinforcedBolt.png"));
        staticSprites.put("devPj",p.loadImage("sprites/projectiles/dev.png"));
        staticSprites.put("energyPj",p.loadImage("sprites/projectiles/energy.png"));
        staticSprites.put("nuclearPj",p.loadImage("sprites/projectiles/nuclear.png"));
        staticSprites.put("darkPj",p.loadImage("sprites/projectiles/dark.png"));
        staticSprites.put("magicMisslePj",p.loadImage("sprites/projectiles/magicMissle.png"));
        staticSprites.put("pebblePj",p.loadImage("sprites/projectiles/pebble.png"));
        staticSprites.put("rockPj",p.loadImage("sprites/projectiles/rock.png"));
        staticSprites.put("needlePj",p.loadImage("sprites/projectiles/needle.png"));
        staticSprites.put("laundryPj",p.loadImage("sprites/projectiles/laundry.png"));
        staticSprites.put("gluePj",p.loadImage("sprites/projectiles/glue.png"));
        staticSprites.put("spikeyGluePj",p.loadImage("sprites/projectiles/spikeyGlue.png"));
        staticSprites.put("glueSpikePj",p.loadImage("sprites/projectiles/glueSpike.png"));
        staticSprites.put("dynamitePj",p.loadImage("sprites/projectiles/dynamite.png"));
        //turrets
        staticSprites.put("crossbowBaseTR",p.loadImage("sprites/towers/turrets/crossbow/base.png"));
        staticSprites.put("crossbowFullTR",p.loadImage("sprites/towers/turrets/crossbow/full.png"));
        staticSprites.put("crossbowIdleTR",p.loadImage("sprites/towers/turrets/crossbow/idle.png"));
        staticSprites.put("cannonBaseTR",p.loadImage("sprites/towers/turrets/cannon/base.png"));
        staticSprites.put("cannonFullTR",p.loadImage("sprites/towers/turrets/cannon/full.png"));
        staticSprites.put("cannonIdleTR",p.loadImage("sprites/towers/turrets/cannon/idle.png"));
        staticSprites.put("fragCannonBaseTR",p.loadImage("sprites/towers/turrets/cannon/fragCannon/base.png"));
        staticSprites.put("fragCannonIdleTR",p.loadImage("sprites/towers/turrets/cannon/fragCannon/idle.png"));
        staticSprites.put("dynamiteLauncherBaseTR",p.loadImage("sprites/towers/turrets/cannon/dynamiteLauncher/base.png"));
        staticSprites.put("dynamiteLauncherIdleTR",p.loadImage("sprites/towers/turrets/cannon/dynamiteLauncher/idle.png"));
        staticSprites.put("crossbowMultishotBaseTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/base.png"));
        staticSprites.put("crossbowMultishotFullTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/full.png"));
        staticSprites.put("crossbowMultishotIdleTR",p.loadImage("sprites/towers/turrets/crossbow/multishot/idle.png"));
        staticSprites.put("crossbowReinforcedBaseTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/base.png"));
        staticSprites.put("crossbowReinforcedFullTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/full.png"));
        staticSprites.put("crossbowReinforcedIdleTR",p.loadImage("sprites/towers/turrets/crossbow/reinforced/idle.png"));
        staticSprites.put("energyBlasterBaseTR",p.loadImage("sprites/towers/turrets/energyBlaster/base.png"));
        staticSprites.put("energyBlasterFullTR",p.loadImage("sprites/towers/turrets/energyBlaster/full.png"));
        staticSprites.put("energyBlasterIdleTR",p.loadImage("sprites/towers/turrets/energyBlaster/idle.png"));
        staticSprites.put("nuclearBlasterBaseTR",p.loadImage("sprites/towers/turrets/energyBlaster/nuclearBlaster/base.png"));
        staticSprites.put("nuclearBlasterFullTR",p.loadImage("sprites/towers/turrets/energyBlaster/nuclearBlaster/full.png"));
        staticSprites.put("nuclearBlasterIdleTR",p.loadImage("sprites/towers/turrets/energyBlaster/nuclearBlaster/idle.png"));
        staticSprites.put("darkBlasterBaseTR",p.loadImage("sprites/towers/turrets/energyBlaster/darkBlaster/base.png"));
        staticSprites.put("darkBlasterFullTR",p.loadImage("sprites/towers/turrets/energyBlaster/darkBlaster/full.png"));
        staticSprites.put("darkBlasterIdleTR",p.loadImage("sprites/towers/turrets/energyBlaster/darkBlaster/idle.png"));
        staticSprites.put("magicMissleerBaseTR",p.loadImage("sprites/towers/turrets/magicMissleer/base.png"));
        staticSprites.put("magicMissleerFullTR",p.loadImage("sprites/towers/turrets/magicMissleer/full.png"));
        staticSprites.put("magicMissleerIdleTR",p.loadImage("sprites/towers/turrets/magicMissleer/idle.png"));
        staticSprites.put("gluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/base.png"));
        staticSprites.put("gluerFullTR",p.loadImage("sprites/towers/turrets/gluer/full.png"));
        staticSprites.put("gluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/idle.png"));
        staticSprites.put("shatterGluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/spiker/base.png"));
        staticSprites.put("shatterGluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/spiker/idle.png"));
        staticSprites.put("splashGluerBaseTR",p.loadImage("sprites/towers/turrets/gluer/splasher/base.png"));
        staticSprites.put("splashGluerIdleTR",p.loadImage("sprites/towers/turrets/gluer/splasher/idle.png"));
        staticSprites.put("seismicBaseTR",p.loadImage("sprites/towers/turrets/seismic/base.png"));
        staticSprites.put("seismicFullTR",p.loadImage("sprites/towers/turrets/seismic/full.png"));
        staticSprites.put("seismicIdleTR",p.loadImage("sprites/towers/turrets/seismic/idle.png"));
        staticSprites.put("seismicSniperBaseTR",p.loadImage("sprites/towers/turrets/seismic/sniper/base.png"));
        staticSprites.put("seismicSniperFullTR",p.loadImage("sprites/towers/turrets/seismic/sniper/full.png"));
        staticSprites.put("seismicSniperIdleTR",p.loadImage("sprites/towers/turrets/seismic/sniper/idle.png"));
        staticSprites.put("seismicSlammerBaseTR",p.loadImage("sprites/towers/turrets/seismic/slammer/base.png"));
        staticSprites.put("seismicSlammerIdleTR",p.loadImage("sprites/towers/turrets/seismic/slammer/idle.png"));
        staticSprites.put("miscCannonBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/base.png"));
        staticSprites.put("miscCannonFullTR",p.loadImage("sprites/towers/turrets/miscCannon/full.png"));
        staticSprites.put("miscCannonIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/idle.png"));
        staticSprites.put("miscCannonLaundryBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/base.png"));
        staticSprites.put("miscCannonLaundryFullTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/full.png"));
        staticSprites.put("miscCannonLaundryIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/laundry/idle.png"));
        staticSprites.put("miscCannonBarrelBaseTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/base.png"));
        staticSprites.put("miscCannonBarrelFullTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/full.png"));
        staticSprites.put("miscCannonBarrelIdleTR",p.loadImage("sprites/towers/turrets/miscCannon/barrel/idle.png"));
        staticSprites.put("slingshotBaseTR",p.loadImage("sprites/towers/turrets/slingshot/base.png"));
        staticSprites.put("slingshotFullTR",p.loadImage("sprites/towers/turrets/slingshot/full.png"));
        staticSprites.put("slingshotIdleTR",p.loadImage("sprites/towers/turrets/slingshot/idle.png"));
        staticSprites.put("slingshotRockBaseTR",p.loadImage("sprites/towers/turrets/slingshot/rock/base.png"));
        staticSprites.put("slingshotRockFullTR",p.loadImage("sprites/towers/turrets/slingshot/rock/full.png"));
        staticSprites.put("slingshotRockIdleTR",p.loadImage("sprites/towers/turrets/slingshot/rock/idle.png"));
        staticSprites.put("slingshotGravelBaseTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/base.png"));
        staticSprites.put("slingshotGravelFullTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/full.png"));
        staticSprites.put("slingshotGravelIdleTR",p.loadImage("sprites/towers/turrets/slingshot/gravel/idle.png"));
        staticSprites.put("teslaBaseTR",p.loadImage("sprites/towers/turrets/tesla/base.png"));
        staticSprites.put("teslaFullTR",p.loadImage("sprites/towers/turrets/tesla/full.png"));
        staticSprites.put("lightningBaseTR",p.loadImage("sprites/towers/turrets/tesla/lightning/base.png"));
        staticSprites.put("highPowerTeslaBaseTR",p.loadImage("sprites/towers/turrets/tesla/highPower/base.png"));
        staticSprites.put("nightmareBaseTR",p.loadImage("sprites/towers/turrets/nightmare/base.png"));
        staticSprites.put("nightmareFullTR",p.loadImage("sprites/towers/turrets/nightmare/full.png"));
        staticSprites.put("nightmareIdleTR",p.loadImage("sprites/towers/turrets/nightmare/idle.png"));
        staticSprites.put("flamethrowerBaseTR",p.loadImage("sprites/towers/turrets/flamethrower/base.png"));
        staticSprites.put("flamethrowerFullTR",p.loadImage("sprites/towers/turrets/flamethrower/full.png"));
        staticSprites.put("flamethrowerIdleTR",p.loadImage("sprites/towers/turrets/flamethrower/idle.png"));
        staticSprites.put("flamewheelBaseTR",p.loadImage("sprites/towers/turrets/flamethrower/flamewheel/base.png"));
        staticSprites.put("flamewheelIdleTR",p.loadImage("sprites/towers/turrets/flamethrower/flamewheel/idle.png"));
        staticSprites.put("magicFlamethrowerBaseTR",p.loadImage("sprites/towers/turrets/flamethrower/magic/base.png"));
        staticSprites.put("magicFlamethrowerIdleTR",p.loadImage("sprites/towers/turrets/flamethrower/magic/idle.png"));
        staticSprites.put("railgunBaseTR",p.loadImage("sprites/towers/turrets/railgun/base.png"));
        staticSprites.put("railgunFullTR",p.loadImage("sprites/towers/turrets/railgun/full.png"));
        staticSprites.put("railgunIdleTR",p.loadImage("sprites/towers/turrets/railgun/idle.png"));
        staticSprites.put("waveMotionBaseTR",p.loadImage("sprites/towers/turrets/waveMotion/base.png"));
        staticSprites.put("waveMotionFullTR",p.loadImage("sprites/towers/turrets/waveMotion/full.png"));
        staticSprites.put("waveMotionIdleTR",p.loadImage("sprites/towers/turrets/waveMotion/idle.png"));
        //walls
        staticSprites.put("shadowBothTW",p.loadImage("sprites/towers/walls/overlays/shadowBoth.png"));
        staticSprites.put("shadowBLTW",p.loadImage("sprites/towers/walls/overlays/shadowBL.png"));
        staticSprites.put("shadowTRTW",p.loadImage("sprites/towers/walls/overlays/shadowTR.png"));
        staticSprites.put("repairTW",p.loadImage("sprites/towers/walls/overlays/repair.png"));
        staticSprites.put("upgradeTW",p.loadImage("sprites/towers/walls/overlays/upgrade.png"));
        staticSprites.put("placeTW",p.loadImage("sprites/towers/walls/overlays/place.png"));
        for (int i = 0; i < 5; i++) {
            String name = "null";
            if (i == 0) name = "Wood";
            if (i == 1) name = "Stone";
            if (i == 2) name = "Metal";
            if (i == 3) name = "Crystal";
            if (i == 4) name = "Titanium";
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
                        staticSprites.put(name + id + "WallTW", p.loadImage("sprites/towers/walls/" + name + "/" + id + ".png"));
                    }
                }
            }
            staticSprites.put(name + "TWallTW", p.loadImage("sprites/towers/walls/" + name + "/t.png"));
            staticSprites.put(name + "BWallTW", p.loadImage("sprites/towers/walls/" + name + "/b.png"));
            staticSprites.put(name + "LWallTW", p.loadImage("sprites/towers/walls/" + name + "/l.png"));
            staticSprites.put(name + "RWallTW", p.loadImage("sprites/towers/walls/" + name + "/r.png"));
        }
        //tiles
        //BGA
        staticSprites.put("stoneBGA_TL",p.loadImage("sprites/tiles/BGA/stone/base.png"));
        staticSprites.put("dirtBGA_TL",p.loadImage("sprites/tiles/BGA/dirt/base.png"));
        staticSprites.put("sandBGA_TL", p.loadImage("sprites/tiles/BGA/sand/base.png"));
        staticSprites.put("grassBGA_TL",p.loadImage("sprites/tiles/BGA/grass/base.png"));
        for (int i = 0; i < 1; i++) {
            String name;
            name = "grass";
            staticSprites.put(name + "BGA_T_TL", p.loadImage("sprites/tiles/BGA/" + name + "/t.png"));
            staticSprites.put(name + "BGA_R_TL", p.loadImage("sprites/tiles/BGA/" + name + "/r.png"));
            staticSprites.put(name + "BGA_B_TL", p.loadImage("sprites/tiles/BGA/" + name + "/b.png"));
            staticSprites.put(name + "BGA_L_TL", p.loadImage("sprites/tiles/BGA/" + name + "/l.png"));
        }
        //BGB
        staticSprites.put("dirtPatchBGB_TL",p.loadImage("sprites/tiles/BGB/dirtPatch.png"));
        staticSprites.put("grassPatchBGB_TL",p.loadImage("sprites/tiles/BGB/grassPatch.png"));
        staticSprites.put("grassCornerBL_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/bl.png"));
        staticSprites.put("grassCornerBR_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/br.png"));
        staticSprites.put("grassCornerTL_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/tl.png"));
        staticSprites.put("grassCornerTR_BGB_TL",p.loadImage("sprites/tiles/BGB/grassCorners/tr.png"));
        staticSprites.put("lichenBGB_TL",p.loadImage("sprites/tiles/BGB/lichen.png"));
        //BGW
        staticSprites.put("woodWallBGW_TL",p.loadImage("sprites/tiles/BGW/woodWall/base.png"));
        staticSprites.put("stoneWallBGW_TL",p.loadImage("sprites/tiles/BGW/stoneWall/base.png"));
        staticSprites.put("metalWallBGW_TL",p.loadImage("sprites/tiles/BGW/metalWall/base.png"));
        staticSprites.put("crystalWallBGW_TL",p.loadImage("sprites/tiles/BGW/crystalWall/base.png"));
        staticSprites.put("titaniumWallBGW_TL",p.loadImage("sprites/tiles/BGW/titaniumWall/base.png"));
        for (int i = 0; i < 2; i++) { //simple connections
            String name = null;
            if (i == 0) name = "woodWall";
            if (i == 1) name = "stoneWall";
            staticSprites.put(name + "BGW_T_TL", p.loadImage("sprites/tiles/BGW/" + name + "/t.png"));
            staticSprites.put(name + "BGW_R_TL", p.loadImage("sprites/tiles/BGW/" + name + "/r.png"));
            staticSprites.put(name + "BGW_B_TL", p.loadImage("sprites/tiles/BGW/" + name + "/b.png"));
            staticSprites.put(name + "BGW_L_TL", p.loadImage("sprites/tiles/BGW/" + name + "/l.png"));
        }
        for (int i = 0; i < 3; i++) { //diagonal double connections
            String name = null;
            if (i == 0) name = "metalWall";
            if (i == 1) name = "crystalWall";
            if (i == 2) name = "titaniumWall";
            staticSprites.put(name + "BGW_BLI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bli.png"));
            staticSprites.put(name + "BGW_BLO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/blo.png"));
            staticSprites.put(name + "BGW_BRI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bri.png"));
            staticSprites.put(name + "BGW_BRO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/bro.png"));
            staticSprites.put(name + "BGW_TLI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tli.png"));
            staticSprites.put(name + "BGW_TLO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tlo.png"));
            staticSprites.put(name + "BGW_TRI_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tri.png"));
            staticSprites.put(name + "BGW_TRO_TL", p.loadImage("sprites/tiles/BGW/" + name + "/tro.png"));
        }
        //BGC
        staticSprites.put("rockBGC_TL",p.loadImage("sprites/tiles/BGC/rock.png"));
        staticSprites.put("smallRockBGC_TL",p.loadImage("sprites/tiles/BGC/smallRock.png"));
        staticSprites.put("leavesBGC_TL",p.loadImage("sprites/tiles/BGC/leaves.png"));
        staticSprites.put("dandelionsBGC_TL",p.loadImage("sprites/tiles/BGC/dandelions.png"));
        staticSprites.put("glowshrooms1BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms1.png"));
        staticSprites.put("glowshrooms2BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms2.png"));
        staticSprites.put("glowshrooms3BGC_TL",p.loadImage("sprites/tiles/BGC/glowshrooms3.png"));
        staticSprites.put("woodDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/wood.png"));
        staticSprites.put("stoneDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/stone.png"));
        staticSprites.put("metalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/metal.png"));
        staticSprites.put("darkMetalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/darkMetal.png"));
        staticSprites.put("crystalDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/crystal.png"));
        staticSprites.put("titaniumDebrisBGC_TL",p.loadImage("sprites/tiles/BGC/debris/titanium.png"));
        //obstacles
        staticSprites.put("smallTreeOb_TL",p.loadImage("sprites/tiles/obstacles/smallTree.png"));
        staticSprites.put("treeBLOb_TL",p.loadImage("sprites/tiles/obstacles/tree/bl.png"));
        staticSprites.put("treeBROb_TL",p.loadImage("sprites/tiles/obstacles/tree/br.png"));
        staticSprites.put("treeTLOb_TL",p.loadImage("sprites/tiles/obstacles/tree/tl.png"));
        staticSprites.put("treeTROb_TL",p.loadImage("sprites/tiles/obstacles/tree/tr.png"));
        staticSprites.put("cactus0Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/0.png"));
        staticSprites.put("cactus1Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/1.png"));
        staticSprites.put("cactus2Ob_TL",p.loadImage("sprites/tiles/obstacles/cactus/2.png"));
        staticSprites.put("caveWallBaseOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/base.png"));
        staticSprites.put("caveWallLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/l.png"));
        staticSprites.put("caveWallROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/r.png"));
        staticSprites.put("caveWallTOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/t.png"));
        staticSprites.put("caveWallBOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/b.png"));
        staticSprites.put("caveWallBROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/br.png"));
        staticSprites.put("caveWallBLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/bl.png"));
        staticSprites.put("caveWallTLOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tl.png"));
        staticSprites.put("caveWallTROb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tr.png"));
        staticSprites.put("caveWallBRCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/brc.png"));
        staticSprites.put("caveWallBLCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/blc.png"));
        staticSprites.put("caveWallTLCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tlc.png"));
        staticSprites.put("caveWallTRCOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/trc.png"));
        staticSprites.put("caveWallBRDOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/brd.png"));
        staticSprites.put("caveWallBLDOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/bld.png"));
        staticSprites.put("caveWallTLDOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/tld.png"));
        staticSprites.put("caveWallTRDOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/trd.png"));
        staticSprites.put("caveWallPillarOb_TL",p.loadImage("sprites/tiles/obstacles/caveWall/pillar.png"));
    }
}
