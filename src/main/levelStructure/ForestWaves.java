package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[10];
        waves[0] = new Wave(p, 1600, 600, new Color(255,100,100), new Color(10,10,10), "Small Bugs");
        waves[0].addSpawns("smolBug",5);

        waves[1] = new Wave(p, 2800, 1100, new Color(255,100,100), new Color(10,10,10), "Small Bugs");
        waves[1].addSpawns("smolBug", 10);

        waves[2] = new Wave(p, 2000, 800, new Color(175, 0, 0), new Color(10, 10, 10), "Bugs");
        waves[2].addSpawns("smolBug", 5);
        waves[2].addSpawns("midBug", 3);

        waves[3] = new Wave(p, 2400, 1000, new Color(175, 0, 0), new Color(10, 10, 10), "Bugs");
        waves[3].addSpawns("smolBug", 3);
        waves[3].addSpawns("midBug", 5);

        waves[4] = new Wave(p, 2000, 800, new Color(32, 222, 32), new Color(30, 80, 130), "Tree Sprites");
        waves[4].addSpawns("smolBug", 10);
        waves[4].addSpawns("treeSprite",5);

        waves[5] = new Wave(p, 2400, 1000, new Color(32, 222, 32), new Color(30, 80, 130), "Tree Sprites");
        waves[5].addSpawns("smolBug", 5);
        waves[5].addSpawns("midBug", 5);
        waves[5].addSpawns("treeSprite",10);

        waves[6] = new Wave(p, 2000, 400, new Color(44, 235, 83), new Color(255, 0, 0), "Snakes");
        waves[6].addSpawns("snake", 20);

        waves[7] = new Wave(p, 2400, 1000, new Color(20, 183, 24), new Color(123, 78, 15), "Tree Spirits");
        waves[7].addSpawns("treeSprite", 10);
        waves[7].addSpawns("treeSpirit", 5);

        waves[8] = new Wave(p, 2800, 1100, new Color(175, 0, 0), new Color(10, 10, 10), "Bugs");
        waves[8].addSpawns("smolBug", 20);
        waves[8].addSpawns("midBug", 10);
        waves[8].addSpawns("butterfly", 5);

        waves[9] = new Wave(p, 2400, 1000, new Color(10,10,10), new Color(175, 0, 10), "Big Bugs");
        waves[9].addSpawns("smolBug", 15);
        waves[9].addSpawns("midBug", 5);
        waves[9].addSpawns("bigBug", 3);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
