package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[5];
        waves[0] = new Wave(p, 1600, 600, new Color(255,100,100), new Color(10,10,10), "Small Bugs");
        waves[0].addSpawns("smolBug",5);
        waves[0].load();

        waves[1] = new Wave(p, 2800, 1100, new Color(255,100,100), new Color(10,10,10), "Small Bugs");
        waves[1].addSpawns("smolBug", 10);
        waves[1].load();

        waves[2] = new Wave(p, 2000, 800, new Color(175, 0, 0), new Color(10, 10, 10), "Bugs");
        waves[2].addSpawns("smolBug", 5);
        waves[2].addSpawns("midBug", 3);
        waves[2].load();

        waves[3] = new Wave(p, 2400, 1000, new Color(175, 0, 0), new Color(10, 10, 10), "Bugs");
        waves[3].addSpawns("smolBug", 3);
        waves[3].addSpawns("midBug", 5);
        waves[3].load();

        waves[4] = new Wave(p, 2000, 800, new Color(32, 222, 32), new Color(30, 80, 130), "Tree Sprites");
        waves[4].addSpawns("smolBug", 10);
        waves[4].addSpawns("treeSprite",5);
        waves[4].load();

        return waves;
    }
}
