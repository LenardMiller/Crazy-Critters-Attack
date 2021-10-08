package main.levelStructure;

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
        String treeGiant = "treeGiant";

        Wave[] waves = new Wave[3];

        waves[0] = wavePreset(p, 60, 40, "Big Bug");
        waves[0].addSpawns(bigBug, 1);
        waves[0].addSpawns(midBug, 10);
        waves[0].addSpawns(smallBug, 20);
        waves[0].addSpawns(butterfly, 5);
        waves[0].addSpawns(smallWorm, 5);

        waves[1] = wavePreset(p, 60, 40, bigBug);
        waves[1].addSpawns(bigBug, 3);
        waves[1].addSpawns(midBug, 10);

        waves[2] = wavePreset(p, 30, 20, midTree);
        waves[2].addSpawns(smallTree, 20);
        waves[2].addSpawns(midTree, 10);

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
