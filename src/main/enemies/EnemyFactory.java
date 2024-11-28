package main.enemies;

import main.enemies.burrowingEnemies.*;
import main.enemies.flyingEnemies.*;
import main.enemies.shootingEnemies.*;
import processing.core.PApplet;
import processing.core.PVector;

public class EnemyFactory {

    private static final int DEFAULT_CORPSE_LIFESPAN = 8;

    public static Enemy get(PApplet p, String name, PVector pos) {
        switch (name) {
            case "smolBug" -> {
                return new Enemy(p, pos.x, pos.y,
                        "smolBug",
                        new PVector(25, 25),
                        1,
                        13,
                        24,
                        10,
                        1,
                        20,
                        new int[]{17},
                        3,
                        2,
                        new PVector(25, 25),
                        DEFAULT_CORPSE_LIFESPAN,
                        new PVector(11, 11),
                        Enemy.HitParticle.greenOuch,
                        "squish",
                        "crunch",
                        "bugGrowlVeryQuick",
                        "smallBugLoop"
                );
            } case "midBug" -> {
                return new MidBug(p, pos.x, pos.y);
            } case "Big Bugs", "bigBug" -> {
                return new BigBug(p, pos.x, pos.y);
            } case "treeSprite" -> {
                return new TreeSprite(p, pos.x, pos.y);
            } case "Tree Spirits", "treeSpirit" -> {
                return new TreeSpirit(p, pos.x, pos.y);
            } case "Tree Giants", "treeGiant" -> {
                return new TreeGiant(p, pos.x, pos.y);
            } case "snake" -> {
                return new Snake(p, pos.x, pos.y);
            } case "littleWorm", "worm" -> {
                return new Worm(p, pos.x, pos.y);
            } case "butterfly" -> {
                return new Butterfly(p, pos.x, pos.y);
            } case "scorpion" -> {
                return new Scorpion(p, pos.x, pos.y);
            } case "sidewinder" -> {
                return new Sidewinder(p, pos.x, pos.y);
            } case "emperor" -> {
                return new Emperor(p, pos.x, pos.y);
            } case "midWorm" -> {
                return new MidWorm(p, pos.x, pos.y);
            } case "Worms", "Megaworms", "bigWorm" -> {
                return new BigWorm(p, pos.x, pos.y);
            } case "albinoBug" -> {
                return new AlbinoBug(p, pos.x, pos.y);
            } case "bigAlbinoBug" -> {
                return new BigAlbinoBug(p, pos.x, pos.y);
            } case "albinoButterfly" -> {
                return new AlbinoButterfly(p, pos.x, pos.y);
            } case "smallGolem" -> {
                return new SmallGolem(p, pos.x, pos.y);
            } case "midGolem", "golem" -> {
                return new Golem(p, pos.x, pos.y);
            } case "bigGolem", "giantGolem" -> {
                return new GiantGolem(p, pos.x, pos.y);
            } case "bat" -> {
                return new Bat(p, pos.x, pos.y);
            } case "bigBat" -> {
                return new GiantBat(p, pos.x, pos.y);
            } case "wtf" -> {
                return new Wtf(p, pos.x, pos.y);
            } case "antlion" -> {
                return new Antlion(p, pos.x, pos.y);
            } case "Antlions", "snowAntlion" -> {
                return new SnowAntlion(p, pos.x, pos.y);
            } case "Wolves", "wolf" -> {
                return new Wolf(p, pos.x, pos.y);
            } case "Snow Sharks", "shark" -> {
                return new Shark(p, pos.x, pos.y);
            } case "Velociraptors", "velociraptor" -> {
                return new Velociraptor(p, pos.x, pos.y);
            } case "Ice Entities", "iceEntity" -> {
                return new IceEntity(p, pos.x, pos.y);
            } case "Ice Monstrosity", "Ice Monstrosities", "iceMonstrosity" -> {
                return new IceMonstrosity(p, pos.x, pos.y);
            } case "Frost", "frost" -> {
                return new Frost(p, pos.x, pos.y);
            } case "Mammoth", "Mammoths", "mammoth" -> {
                return new Mammoth(p, pos.x, pos.y);
            } case "Mud Creatures", "mudCreature" -> {
                return new MudCreature(p, pos.x, pos.y);
            } case "Mud Flingers", "mudFlinger" -> {
                return new MudFlinger(p, pos.x, pos.y);
            } case "Enraged Giants", "Enraged Giant", "enragedGiant" -> {
                return new EnragedGiant(p, pos.x, pos.y);
            } case "Mantises", "Mantis", "mantis" -> {
                return new Mantis(p, pos.x, pos.y);
            } case "Roaches", "roach" -> {
                return new Roach(p, pos.x, pos.y);
            } case "Roots", "root" -> {
                return new Root(p, pos.x, pos.y);
            } case "Mantoids", "mantoid" -> {
                return new Mantoid(p, pos.x, pos.y);
            } case "Twisted", "twisted" -> {
                return new Twisted(p, pos.x, pos.y);
            } case "fae", "Fae" -> {
                return new Fae(p, pos.x, pos.y);
            } case "Mutant Bug", "Mutant Bugs" -> {
                return new MutantBug(p, pos.x, pos.y);
            } default -> {
                System.out.println("Could not get enemy of type: \"" + name + "\"");
                return null;
            }
        }
    }
}
