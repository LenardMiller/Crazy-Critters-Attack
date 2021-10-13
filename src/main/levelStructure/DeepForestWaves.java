package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class DeepForestWaves {

    public static Wave[] genDeepForestWaves(PApplet p) {
        String bigBug = "Big Bugs";
        String midBug = "midBug";
        String smallBug = "smolBug";
        String butterfly = "butterfly";
        String smallWorm = "littleWorm";
        String smallTree = "treeSprite";
        String midTree = "Tree Spirits";
        String treeGiant = "Tree Giants";

        Wave[] waves = new Wave[10];

        waves[0] = wavePreset(p, 80, 40, "Big Bug");
        waves[0].polluter = new Polluter(p, 5, "deepForest/yellow");
        waves[0].addSpawns(bigBug, 1);
        waves[0].addSpawns(midBug, 10);
        waves[0].addSpawns(smallBug, 20);
        waves[0].addSpawns(butterfly, 5);
        waves[0].addSpawns(smallWorm, 5);

        waves[1] = wavePreset(p, 80, 40, bigBug);
        waves[1].addSpawns(bigBug, 3);
        waves[1].addSpawns(midBug, 10);

        waves[2] = wavePreset(p, 40, 20, midTree);
        waves[2].addSpawns(smallTree, 20);
        waves[2].addSpawns(midTree, 10);

        waves[3] = wavePreset(p, 40, 30, bigBug);
        waves[3].addSpawns(bigBug, 5);
        waves[3].addSpawns(midBug, 20);
        waves[3].addSpawns(smallWorm, 5);

        waves[4] = wavePreset(p, 80, 5, "Tree Giant");
        waves[4].addSpawns(treeGiant, 1);

        waves[5] = wavePreset(p, 60, 30, bigBug);
        waves[5].addSpawns(bigBug, 10);
        waves[5].addSpawns(butterfly, 5);

        waves[6] = wavePreset(p, 60, 30, midTree);
        waves[6].addSpawns(midTree, 40);

        waves[7] = wavePreset(p, 80, 40, treeGiant);
        waves[7].addSpawns(treeGiant, 3);
        waves[7].addSpawns(midTree, 10);

        waves[8] = wavePreset(p, 60, 30, bigBug);
        waves[8].addSpawns(bigBug, 15);
        waves[8].addSpawns(butterfly, 5);
        waves[8].addSpawns(smallWorm, 5);

        waves[9] = wavePreset(p, 150, 120, "forestHorde");
        waves[9].addSpawns(treeGiant, 5);
        waves[9].addSpawns(midTree, 30);
        waves[9].addSpawns(smallTree, 10);
        waves[9].addSpawns(bigBug, 25);
        waves[9].addSpawns(midBug, 10);
        waves[9].addSpawns(smallBug, 10);
        waves[9].addSpawns(smallWorm, 20);
        waves[9].addSpawns(butterfly, 20);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String title) {
        switch (title) {
            case "Big Bug":
            case "Big Bugs":
                return new Wave(p, length, spawnLength,
                        new Color(170, 0, 0),
                        new Color(10, 10, 10),
                        new Color(255, 0, 0),
                        title);
            case "Tree Spirits":
                return new Wave(p, length, spawnLength,
                        new Color(123, 78, 15),
                        new Color(20, 183, 83),
                        new Color(0, 50, 0),
                        title);
            case "Tree Giant":
            case "Tree Giants":
                return new Wave(p, length, spawnLength,
                        new Color(255, 0, 77),
                        new Color(0, 100, 0),
                        new Color(32, 255, 32),
                        title);
            case "forestHorde":
                return new Wave(p, length, spawnLength,
                        new Color(20, 160, 46),
                        new Color(143, 86, 59),
                        new Color(47, 28, 1),
                         "Horde");
            default:
                return new Wave(p, length, spawnLength,
                        new Color(255, 0, 255),
                        new Color(0),
                        new Color(0),
                        title
                );
        }
    }
}
