package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[15];

        Color smolBugPrimary = new Color(255, 100, 100);
        Color smolBugSecondary = new Color(10, 10, 10);

        Color midBugPrimary = new Color(170, 0, 0);
        Color midBugSecondary = new Color(10, 10, 10);

        Color treeSpritePrimary = new Color(32, 222, 32);
        Color treeSpriteSecondary = new Color(30, 80, 130);

        Color snakePrimary = new Color(44, 235, 83);
        Color snakeSecondary = new Color(255, 0, 0);

        Color treeSpiritPrimary = new Color(20, 183, 83);
        Color treeSpiritSecondary = new Color(123, 78, 15);

        Color bigBugPrimary = new Color(175, 0, 0);
        Color bigBugSecondary = new Color(10, 10, 10);

        Color treeGiantPrimary = new Color(0, 100, 0);
        Color treeGiantSecondary = new Color(255, 0, 77);
        
        Color hordePrimary = new Color(0, 0, 0);
        Color hordeSecondary = new Color(255, 100, 0);

        waves[0] = new Wave(p, 1600, 600, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[0].addSpawns("smolBug",3);

        waves[1] = new Wave(p, 2800, 1100, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[1].addSpawns("smolBug", 6);

        waves[2] = new Wave(p, 2000, 800, midBugPrimary, midBugSecondary, "Bugs");
        waves[2].addSpawns("smolBug", 5);
        waves[2].addSpawns("midBug", 3);

        waves[3] = new Wave(p, 2400, 1000, midBugPrimary, midBugSecondary, "Bugs");
        waves[3].addSpawns("smolBug", 3);
        waves[3].addSpawns("midBug", 5);

        waves[4] = new Wave(p, 2000, 800, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[4].addSpawns("smolBug", 10);
        waves[4].addSpawns("treeSprite",5);

        waves[5] = new Wave(p, 2400, 1000, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[5].addSpawns("smolBug", 5);
        waves[5].addSpawns("midBug", 5);
        waves[5].addSpawns("treeSprite",10);

        waves[6] = new Wave(p, 2000, 400, snakePrimary, snakeSecondary, "Snakes");
        waves[6].addSpawns("snake", 30);

        waves[7] = new Wave(p, 2400, 1000, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[7].addSpawns("treeSprite", 10);
        waves[7].addSpawns("treeSpirit", 5);

        waves[8] = new Wave(p, 2800, 1100, midBugPrimary, midBugSecondary, "Bugs");
        waves[8].addSpawns("smolBug", 20);
        waves[8].addSpawns("midBug", 10);
        waves[8].addSpawns("butterfly", 5);

        waves[9] = new Wave(p, 2400, 1000, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[9].addSpawns("smolBug", 15);
        waves[9].addSpawns("midBug", 5);
        waves[9].addSpawns("bigBug", 3);

        waves[10] = new Wave(p, 2800, 1100, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[10].addSpawns("smolBug", 10);
        waves[10].addSpawns("midBug", 10);
        waves[10].addSpawns("bigBug", 7);

        waves[11] = new Wave(p, 2000, 800, new Color(215, 123, 186), new Color(102, 63, 83), "Worms");
        waves[11].addSpawns("smolBug", 10);
        waves[11].addSpawns("midBug", 6);
        waves[11].addSpawns("butterfly", 15);
        waves[11].addSpawns("littleWorm", 5);

        waves[12] = new Wave(p, 3000, 500, treeGiantPrimary, treeGiantSecondary, "Tree Giant");
        waves[12].addSpawns("treeSprite",5);
        waves[12].addSpawns("treeGiant",1);

        waves[13] = new Wave(p, 3200, 1300, hordePrimary, hordeSecondary, "Swarm");
        waves[13].addSpawns("smolBug", 25);
        waves[13].addSpawns("midBug", 15);
        waves[13].addSpawns("bigBug", 7);
        waves[13].addSpawns("butterfly", 5);
        waves[13].addSpawns("littleWorm", 5);
        waves[13].addSpawns("treeSprite", 20);
        waves[13].addSpawns("treeSpirit",10);

        waves[14] = new Wave(p, 3500, 1400, treeGiantPrimary, treeGiantSecondary, "Tree Giants");
        waves[14].addSpawns("treeSprite",15);
        waves[14].addSpawns("treeSpirit", 5);
        waves[14].addSpawns("treeGiant",4);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
