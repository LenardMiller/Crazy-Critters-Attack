package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[30];

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

        Color bigBugPrimary = new Color(10, 10, 10);
        Color bigBugSecondary = new Color(170, 0, 0);

        Color treeGiantPrimary = new Color(0, 100, 0);
        Color treeGiantSecondary = new Color(255, 0, 77);

        Color hordePrimary = new Color(0, 0, 0);
        Color hordeSecondary = new Color(255, 100, 0);

        waves[0] = new Wave(p, 2000, 800, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[0].addSpawns("smolBug",3);

        waves[1] = new Wave(p, 2400, 1000, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[1].addSpawns("smolBug", 5);

        waves[2] = new Wave(p, 2800, 1100, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[2].addSpawns("smolBug", 8);

        waves[3] = new Wave(p, 2800, 1100, midBugPrimary, midBugSecondary, "Bugs");
        waves[3].addSpawns("smolBug", 5);
        waves[3].addSpawns("midBug", 3);

        waves[4] = new Wave(p, 3000, 1200, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[4].addSpawns("smolBug", 15);

        waves[5] = new Wave(p, 3000, 1200, midBugPrimary, midBugSecondary, "Bugs");
        waves[5].addSpawns("smolBug", 10);
        waves[5].addSpawns("midBug", 5);

        waves[6] = new Wave(p, 2400, 1000, midBugPrimary, midBugSecondary, "Bugs");
        waves[6].addSpawns("smolBug", 10);
        waves[6].addSpawns("midBug", 5);

        waves[7] = new Wave(p, 2800, 1100, midBugPrimary, midBugSecondary, "Bugs");
        waves[7].addSpawns("smolBug", 10);
        waves[7].addSpawns("midBug", 10);

        waves[8] = new Wave(p, 2400, 1000, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[8].addSpawns("smolBug", 10);
        waves[8].addSpawns("treeSprite",5);

        waves[9] = new Wave(p, 2400, 1000, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[9].addSpawns("smolBug", 5);
        waves[9].addSpawns("midBug", 5);
        waves[9].addSpawns("treeSprite",5);

        waves[10] = new Wave(p, 2800, 1100, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[10].addSpawns("smolBug", 5);
        waves[10].addSpawns("midBug", 5);
        waves[10].addSpawns("treeSprite",10);

        waves[11] = new Wave(p, 3000, 1200, midBugPrimary, midBugSecondary, "Bugs");
        waves[11].addSpawns("midBug", 20);

        waves[12] = new Wave(p, 3000, 1200, treeSpritePrimary, treeSpriteSecondary, "Tree Sprites");
        waves[12].addSpawns("midBug", 10);
        waves[12].addSpawns("treeSprite",10);

        waves[13] = new Wave(p, 3500, 600, snakePrimary, snakeSecondary, "Snakes");
        waves[13].addSpawns("snake", 40);

        waves[14] = new Wave(p, 2400, 1000, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[14].addSpawns("treeSprite", 5);
        waves[14].addSpawns("treeSpirit", 3);

        waves[15] = new Wave(p, 2800, 1100, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[15].addSpawns("treeSprite", 8);
        waves[15].addSpawns("treeSpirit", 5);

        waves[16] = new Wave(p, 2800, 1100, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[16].addSpawns("treeSprite", 8);
        waves[16].addSpawns("treeSpirit", 5);

        waves[17] = new Wave(p, 2800, 1100, midBugPrimary, midBugSecondary, "Bugs");
        waves[17].addSpawns("smolBug", 10);
        waves[17].addSpawns("midBug", 10);
        waves[17].addSpawns("butterfly", 5);

        waves[18] = new Wave(p, 2800, 1100, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[18].addSpawns("treeSprite", 5);
        waves[18].addSpawns("treeSpirit", 8);

        waves[19] = new Wave(p, 2400, 1000, bigBugPrimary, bigBugSecondary, "Big Bug");
        waves[19].addSpawns("midBug", 5);
        waves[19].addSpawns("bigBug", 1);

        waves[20] = new Wave(p, 3000, 1200, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[20].addSpawns("midBug", 5);
        waves[20].addSpawns("bigBug", 3);

        waves[21] = new Wave(p, 3000, 1200, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[21].addSpawns("midBug", 5);
        waves[21].addSpawns("bigBug", 3);

        waves[22] = new Wave(p, 2800, 1100, treeSpiritPrimary, treeSpiritSecondary, "Tree Spirits");
        waves[22].addSpawns("treeSprite", 10);
        waves[22].addSpawns("treeSpirit", 10);

        waves[23] = new Wave(p, 2800, 1100, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[23].addSpawns("midBug", 7);
        waves[23].addSpawns("bigBug", 3);
        waves[23].addSpawns("littleWorm", 5);

        waves[24] = new Wave(p, 3000, 1200, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[24].addSpawns("midBug", 10);
        waves[24].addSpawns("bigBug", 5);
        waves[24].addSpawns("littleWorm", 5);

        waves[25] = new Wave(p, 2800, 1100, treeGiantPrimary, treeGiantSecondary, "Tree Giant");
        waves[25].addSpawns("treeGiant", 1);

        waves[26] = new Wave(p, 3000, 1200, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[26].addSpawns("bigBug", 10);

        waves[27] = new Wave(p, 2800, 1100, treeGiantPrimary, treeGiantSecondary, "Tree Giant");
        waves[27].addSpawns("treeSprite", 5);
        waves[27].addSpawns("treeSpirit", 5);
        waves[27].addSpawns("treeGiant", 1);

        waves[28] = new Wave(p, 4000, 1600, hordePrimary, hordeSecondary, "horde");
        waves[28].addSpawns("midBug", 10);
        waves[28].addSpawns("treeSpirit", 10);
        waves[28].addSpawns("bigBug", 3);
        waves[28].addSpawns("snake", 20);
        waves[28].addSpawns("butterfly", 5);
        waves[28].addSpawns("littleWorm", 5);

        waves[29] = new Wave(p, 4000, 600, treeGiantPrimary, treeGiantSecondary, "Tree Giants");
        waves[29].addSpawns("treeGiant", 3);
        waves[29].addSpawns("treeSpirit", 10);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
