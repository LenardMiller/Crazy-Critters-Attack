package main.enemies;

import main.particles.Debris;
import main.particles.ExplosionDebris;
import main.projectiles.enemyProjeciles.Laser;
import main.projectiles.enemyProjeciles.MudBall;
import main.projectiles.enemyProjeciles.Sandball;
import main.projectiles.enemyProjeciles.Snowball;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PApplet.radians;

public class EnemyFactory {

    private static final int CORPSE_LIFESPAN = 8;
    private static final int CORPSE_DELAY = 3;

    public static Enemy get(PApplet p, String name, PVector pos) {
        switch (name) {
            case "smolBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "smolBug",
                        new PVector(25, 25),
                        1, 13, 24, 10, 1, 20,
                        new int[]{17}, 3, 2,
                        new PVector(11, 11), new PVector(25, 25),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop"
                );
            } case "midBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "midBug",
                        new PVector(25, 25),
                        1, 13, 30, 20, 3, 40,
                        new int[]{7}, 3, 1,
                        new PVector(25, 25), new PVector(14, 14),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop"
                );
            } case "Big Bugs", "bigBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "bigBug",
                        new PVector(53, 53),
                        2, 26, 18, 100, 10, 1750,
                        new int[]{9}, 6, 0,
                        new PVector(53, 53), new PVector(32, 32),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrunch", "squash",
                        "bugGrownQuick", "bigBugLoop");
            } case "treeSprite" -> {
                return new Enemy(p, pos.x, pos.y,
                        "treeSprite",
                        new PVector(25, 25),
                        1, 15, 24, 15, 2, 40,
                        new int[]{25}, 1, 1,
                        new PVector(50, 50), new PVector(21, 21),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.leafOuch,
                        "leaves", "leavesImpact",
                        "swooshPunch", "leafyStepsLoop");
            } case "Tree Spirits", "treeSpirit" -> {
                return new Enemy(p, pos.x, pos.y,
                        "treeSpirit",
                        new PVector(42, 42),
                        2, 21, 21, 30, 3, 350,
                        new int[]{22}, 1, 1,
                        new PVector(84, 84), new PVector(38, 38),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.leafOuch,
                        "leaves", "leavesImpact",
                        "swooshPunch", "leafyStepsLoop");
            } case "Tree Giants", "treeGiant" -> {
                return new Enemy(p, pos.x, pos.y,
                        "treeGiant",
                        new PVector(76, 76),
                        3, 30, 18, 250, 15, 4000,
                        new int[]{28}, 1, 2,
                        new PVector(152, 152), new PVector(68, 68),
                        12, CORPSE_DELAY,
                        Enemy.HitParticle.leafOuch,
                        "bigLeaves", "bigLeavesImpact",
                        "swooshPunchSlow", "bigLeafyStepsLoop");
            } case "snake" -> {
                return new Enemy(p, pos.x, pos.y,
                        "snake",
                        new PVector(25, 25),
                        1, 12, 42, 20, 3, 60,
                        new int[]{8}, 1, 2,
                        new PVector(25, 25), new PVector(9, 9),
                        6, CORPSE_DELAY,
                        Enemy.HitParticle.redOuch,
                        "hiss", "hissSquish",
                        "snakeStrike", "rattle");
            } case "littleWorm", "worm" -> {
                return new BurrowingEnemy(p, pos.x, pos.y,
                        "worm",
                        new PVector(25, 25),
                        1, 12, 36, 30, 3, 100,
                        new int[]{18}, 0, 1,
                        new PVector(25, 25), new PVector(7, 7),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrownVeryQuick", "littleDig");
            } case "butterfly" -> {
                return new FlyingEnemy(p, pos.x, pos.y,
                        "butterfly",
                        new PVector(25, 25),
                        1, 12, 48, 30, 1, 60,
                        new int[]{3}, 0, 2,
                        new PVector(25, 25), new PVector(18, 18),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "flap", "smallWingbeats");
            } case "scorpion" -> {
                return new Enemy(p, pos.x, pos.y,
                        "scorpion",
                        new PVector(25, 25),
                        1, 13, 39, 25, 4, 125,
                        new int[]{5}, 4, 3,
                        new PVector(25, 25), new PVector(13, 13),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "smallWhipCrack", "smallBugLoop"
                );
            } case "sidewinder" -> {
                return new Enemy(p, pos.x, pos.y,
                        "sidewinder",
                        new PVector(25, 25),
                        1, 12, 42, 20, 5, 60,
                        new int[]{4}, 1, 2,
                        new PVector(25, 25), new PVector(9, 9),
                        6, CORPSE_DELAY,
                        Enemy.HitParticle.redOuch,
                        "hiss", "hissSquish",
                        "snakeStrike", "rattle");
            } case "emperor" -> {
                return new Enemy(p, pos.x, pos.y,
                        "emperor",
                        new PVector(50, 50),
                        2, 25, 27, 200, 18, 4000,
                        new int[]{7}, 6, 5,
                        new PVector(50, 50), new PVector(20, 20),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrunch", "squash",
                        "whipCrack", "bigBugLoop");
            } case "midWorm" -> {
                return new BurrowingEnemy(p, pos.x, pos.y,
                        "midWorm",
                        new PVector(25, 25),
                        1, 12, 42, 60, 7, 800,
                        new int[]{15}, 0, 2,
                        new PVector(25, 25), new PVector(9, 9),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrownVeryQuick", "littleDig");
            } case "Worms", "Megaworms", "bigWorm" -> {
                return new BurrowingEnemy(p, pos.x, pos.y,
                        "bigWorm",
                        new PVector(50, 50),
                        2, 25, 24, 250, 15, 10_000,
                        new int[]{29}, 0, 2,
                        new PVector(50, 50), new PVector(31, 31),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrunchRoar", "squash",
                        "bugGrownSlow", "bigDig");
            } case "albinoBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "albinoBug",
                        new PVector(25, 25),
                        1, 13, 30, 20, 3, 40,
                        new int[]{9}, 3, 1,
                        new PVector(25, 25), new PVector(14, 14),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.glowOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop"
                );
            } case "bigAlbinoBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "bigAlbinoBug",
                        new PVector(53, 53),
                        2, 26, 18, 100, 15, 2000,
                        new int[]{9}, 6, 0,
                        new PVector(53, 53), new PVector(32, 32),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.glowOuch,
                        "bigCrunch", "squash",
                        "bugGrowlQuick", "bigBugLoop");
            } case "albinoButterfly" -> {
                return new FlyingEnemy(p, pos.x, pos.y,
                        "albinoButterfly",
                        new PVector(25, 25),
                        1, 12, 55, 30, 2, 100,
                        new int[]{3}, 0, 2,
                        new PVector(25, 25), new PVector(18, 18),
                        6, 2,
                        Enemy.HitParticle.glowOuch,
                        "crunch", "squish",
                        "flap", "smallWingbeats");
            } case "smallGolem" -> {
                return new Enemy(p, pos.x, pos.y,
                        "smallGolem",
                        new PVector(25, 25),
                        1, 15, 30, 30, 5, 100,
                        new int[]{12}, 1, 1,
                        new PVector(50, 50), new PVector(24, 24),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.lichenOuch,
                        "gravelDie", "gravelExplode",
                        "swooshPunch", "stonesMove");
            } case "midGolem", "golem" -> {
                return new Enemy(p, pos.x, pos.y,
                        "golem",
                        new PVector(42, 42),
                        2, 21, 21, 60, 6, 700,
                        new int[]{22}, 1, 1,
                        new PVector(84, 84), new PVector(36, 36),
                        CORPSE_LIFESPAN, 3,
                        Enemy.HitParticle.lichenOuch,
                        "gravelDie", "gravelExplode",
                        "swooshPunch", "stonesMove");
            } case "bigGolem", "giantGolem" -> {
                return new Enemy(p, pos.x, pos.y,
                        "giantGolem",
                        new PVector(76, 76),
                        3, 30, 18, 500, 18, 20_000,
                        new int[]{28}, 1, 1,
                        new PVector(152, 152), new PVector(68, 68),
                        12, CORPSE_DELAY,
                        Enemy.HitParticle.lichenOuch,
                        "rocksCrumble", "rocksCrumble",
                        "swooshPunchSlow", "bigStonesMove");
            } case "bat" -> {
                return new FlyingEnemy(p, pos.x, pos.y,
                        "bat",
                        new PVector(50, 50),
                        1, 12, 45, 50, 3, 500,
                        new int[]{3}, 3, 5,
                        new PVector(50, 50), new PVector(23, 23),
                        CORPSE_LIFESPAN, 2,
                        Enemy.HitParticle.redOuch,
                        "squeak", "squeakSquish",
                        "flap", "wingbeats");
            } case "bigBat" -> {
                return new FlyingEnemy(p, pos.x, pos.y,
                        "giantBat",
                        new PVector(102, 102),
                        2, 25, 30, 500, 5, 12_500,
                        new int[]{3}, 5, 6,
                        new PVector(100, 100), new PVector(50, 50),
                        10, 2,
                        Enemy.HitParticle.redOuch,
                        "bigSqueak", "bigSqueakSquish",
                        "flap", "bigWingbeats");
            } case "wtf" -> {
                return new Enemy(p, pos.x, pos.y,
                        "wtf",
                        new PVector(100, 100),
                        4, 40, 18, 2500, 40, 120_000,
                        new int[]{11}, 10, 4,
                        new PVector(100, 100), new PVector(1000, 100),
                        12, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrunchRoar", "squashRoar",
                        "bugGrownSlow", "bigBugLoop");
            } case "antlion" -> {
                return new ShootingEnemy(p, pos.x, pos.y,
                        "antlion",
                        new PVector(25, 25),
                        1, 13, 24, 40, 2, 200,
                        100, 7, 6, 6,
                        new int[]{6}, 5, 6,
                        new PVector(25, 25), new PVector(11, 11),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop", "spit",
                        (angle, position) -> {
                            projectiles.add(new Sandball(p, 2, position.x, position.y, angle));
                            for (int i = 0; i < 5; i++) {
                                towerParticles.add(new Debris(p, position.x, position.y,
                                        angle + radians(p.random(-15, 15)), "sand"));
                            }}
                );
            } case "Antlions", "snowAntlion" -> {
                return new ShootingEnemy(p, pos.x, pos.y,
                        "snowAntlion",
                        new PVector(25, 25),
                        1, 13, 30, 60, 2, 200,
                        110, 7, 6, 6,
                        new int[]{6}, 5, 6,
                        new PVector(25, 25), new PVector(11, 11),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop", "spit",
                        (angle, position) -> {
                            projectiles.add(new Snowball(p, 2, position.x, position.y, angle));
                            for (int i = 0; i < 5; i++) {
                                towerParticles.add(new Debris(p, position.x, position.y,
                                        angle + radians(p.random(-15, 15)), "snow"));
                            }}
                );
            } case "Wolves", "wolf" -> {
                return new Enemy(p, pos.x, pos.y,
                        "wolf",
                        new PVector(50, 50),
                        2, 25, 60, 40, 8, 100,
                        new int[]{8}, 5, 2,
                        new PVector(50, 50), new PVector(25, 25),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.redOuch,
                        "bark", "barkSquish",
                        "dogAttack", "snowRunning");
            } case "Snow Sharks", "shark" -> {
                return new BurrowingEnemy(p, pos.x, pos.y,
                        "shark",
                        new PVector(25, 25),
                        1, 12, 42, 75, 8, 175,
                        new int[]{4}, 0, 5,
                        new PVector(25, 25), new PVector(20, 20),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bite", "littleDig");
            } case "Velociraptors", "velociraptor" -> {
                return new Enemy(p, pos.x, pos.y,
                        "velociraptor",
                        new PVector(100, 100),
                        3, 25, 75, 300, 20, 4000,
                        new int[]{8}, 3, 3,
                        new PVector(50, 50), new PVector(45, 45),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.redOuch,
                        "dino", "dinoSquish",
                        "biteGrowl", "manyFootsteps");
            } case "Ice Entities", "iceEntity" -> {
                return new IceEntity(p, pos.x, pos.y);
            } case "Ice Monstrosity", "Ice Monstrosities", "iceMonstrosity" -> {
                return new IceMonstrosity(p, pos.x, pos.y);
            } case "Frost", "frost" -> {
                return new Frost(p, pos.x, pos.y);
            } case "Mammoth", "Mammoths", "mammoth" -> {
                return new Enemy(p, pos.x, pos.y,
                        "mammoth",
                        new PVector(100, 100),
                        4, 50, 30, 2500, 50, 200_000,
                        new int[]{9}, 2, 2,
                        new PVector(100, 100), new PVector(56, 56),
                        12, CORPSE_DELAY,
                        Enemy.HitParticle.redOuch,
                        "mammoth", "mammothSquash",
                        "biteGrowlSlow", "bigFootsteps");
            } case "Mud Creatures", "mudCreature" -> {
                return new Enemy(p, pos.x, pos.y,
                        "mudCreature",
                        new PVector(50, 50),
                        2, 25, 90, 500, 40, 15_000,
                        new int[]{9}, 3, 2,
                        new PVector(50, 50), new PVector(25, 25),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.mudOuch,
                        "mudDie", "mudSquish",
                        "slime", "slimeMovement");
            } case "Mud Flingers", "mudFlinger" -> {
                return new ShootingEnemy(p, pos.x, pos.y,
                        "mudFlinger",
                        new PVector(50, 50),
                        2, 25, 30, 500, 12, 15_000,
                        140, 7, 4, 9,
                        new int[]{6}, 5, 4,
                        new PVector(50, 50), new PVector(25, 25),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.mudOuch,
                        "mudDie", "mudSquish",
                        "slime", "slimeMovement", "spit",
                        (angle, position) -> {
                            projectiles.add(new MudBall(p, 4, position.x, position.y, angle));
                            for (int i = 0; i < 5; i++) {
                                towerParticles.add(new Debris(p, position.x, position.y,
                                        angle + radians(p.random(-15, 15)), "mud"));
                            }}
                );
            } case "Enraged Giants", "Enraged Giant", "enragedGiant" -> {
                return new EnragedGiant(p, pos.x, pos.y);
            } case "Mantises", "Mantis", "mantis" -> {
                return new Enemy(p, pos.x, pos.y,
                        "mantis",
                        new PVector(50, 50),
                        2, 25, 35, 400, 12, 7500,
                        new int[]{12}, 6, 1,
                        new PVector(50, 50), new PVector(30, 30),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrunch", "squash",
                        "bugGrowlQuick", "bigBugLoop");
            } case "Roaches", "roach" -> {
                return new Enemy(p, pos.x, pos.y,
                        "roach",
                        new PVector(25, 25),
                        1, 12, 65, 100, 7, 3000,
                        new int[]{3}, 6, 2,
                        new PVector(25, 25), new PVector(14, 14),
                        6, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "crunch", "squish",
                        "bugGrowlVeryQuick", "smallBugLoop");
            } case "Roots", "root" -> {
                return new BurrowingEnemy(p, pos.x, pos.y,
                        "root",
                        new PVector(25, 25),
                        1, 12, 40, 250, 20, 30_000,
                        new int[]{18}, 0, 1,
                        new PVector(25, 25), new PVector(8, 8),
                        6, CORPSE_DELAY,
                        Enemy.HitParticle.sapOuch,
                        "crunch", "squish",
                        "smallWhipCrack", "littleDig");
            } case "Mantoids", "mantoid" -> {
                return new FlyingEnemy(p, pos.x, pos.y,
                        "mantoid",
                        new PVector(50, 50),
                        2, 25, 50, 1000, 10, 20_000,
                        new int[]{12}, 2, 2,
                        new PVector(50, 50), new PVector(38, 38),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.greenOuch,
                        "bigCrumch", "squash",
                        "bugGrowlQuick", "buzz");
            } case "Twisted", "twisted" -> {
                return new Enemy(p, pos.x, pos.y,
                        "twisted",
                        new PVector(50, 50),
                        2, 25, 30, 750, 30, 45_000,
                        new int[]{4}, 5, 10,
                        new PVector(50, 50), new PVector(26, 26),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.brownLeafOuch,
                        "leaves", "leavesImpact",
                        "swooshPunch", "leafyStepsLoop");
            } case "fae", "Fae" -> {
                return new Fae(p, pos.x, pos.y);
            } case "Mutant Bug", "Mutant Bugs", "mutantBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "mutantBug",
                        new PVector(100, 100),
                        4, 40, 25, 10_000, 75, 750_000,
                        new int[]{15}, 10, 2,
                        new PVector(100, 100), new PVector(45, 45),
                        12, CORPSE_DELAY,
                        Enemy.HitParticle.glowOuch,
                        "bigCrunchRoar", "squashRoar",
                        "whipCrack", "bigBugLoop");
            } case "Robugs", "roboBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "roboBug",
                        new PVector(25, 25),
                        1, 13, 30, 100, 10, 400,
                        new int[]{11}, 3, 2,
                        new PVector(15, 15), new PVector(25, 25),
                        CORPSE_LIFESPAN, CORPSE_DELAY,
                        Enemy.HitParticle.oilOuch,
                        "robugCrunch", "robugSquish",
                        "robugGrowlVeryQuick", "smallBugLoop");
            } case "Cybugs" -> {
                return new ShootingEnemy(p, pos.x, pos.y,
                        "cybug",
                        new PVector(53, 53),
                        2, 26, 18, 500, 10, 5000,
                        100, 25, 6, 1,
                        new int[]{2}, 6, 4,
                        new PVector(53, 53), new PVector(32, 32),
                        10, CORPSE_DELAY,
                        Enemy.HitParticle.oilOuch,
                        "robugBigCrunch", "robugSquash",
                        "robugGrowlQuick", "bigBugLoop", "robugGrowlQuick",
                        (angle, position) -> {
                            projectiles.add(new Laser(p, 2, position.x, position.y, angle));
                            for (int i = 0; i < 6; i++) {
                                towerParticles.add(new ExplosionDebris(p,
                                        position.x, position.y, p.random(360),
                                        "energy", p.random(5)));
                            }
                        }
                );
            } default -> {
                    System.err.println("Could not get enemy of type: \"" + name + "\"");
                    return null;
            }
        }
    }
}
