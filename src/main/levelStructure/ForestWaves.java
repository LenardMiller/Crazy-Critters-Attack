package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[30];

        Color smolBugAccent = new Color(10, 10, 10);
        Color smolBugFill = new Color(255, 100, 100);
        Color smolBugText = new Color(255, 255, 255);

        Color midBugAccent = new Color(10, 10, 10);
        Color midBugFill = new Color(170, 0, 0);
        Color midBugText = new Color(25, 0, 0);

        Color treeSpriteAccent = new Color(30, 80, 130);
        Color treeSpriteFill = new Color(32, 222, 32);
        Color treeSpriteText = new Color(0, 50, 0);

        Color snakeAccent = new Color(255, 0, 0);
        Color snakeFill = new Color(44, 235, 83);
        Color snakeText = new Color(50, 0, 0);

        Color treeSpiritAccent = new Color(123, 78, 15);
        Color treeSpiritFill = new Color(20, 183, 83);
        Color treeSpiritText = new Color(0, 50, 0);

        Color bigBugAccent = new Color(170, 0, 0);
        Color bigBugFill =   new Color(10, 10, 10);
        Color bigBugText =   new Color(255, 0, 0);

        Color treeGiantAccent = new Color(255, 0, 77);
        Color treeGiantFill = new Color(0, 100, 0);
        Color treeGiantText = new Color(32, 255, 32);

        Color hordeAccent = new Color(20, 160, 46);
        Color hordeFill = new Color(143, 86, 59);
        Color hordeText = new Color(47, 28, 1);

        waves[0] = new Wave(p, 70, 40, smolBugFill, smolBugAccent, smolBugText, "Small Bugs");
        waves[0].addSpawns("smolBug",3);
        waves[0].polluter = new Polluter(p, 5, "forest/polluted");

        waves[1] = new Wave(p, 70, 40, smolBugFill, smolBugAccent, smolBugText, "Small Bugs");
        waves[1].addSpawns("smolBug", 5);

        waves[2] = new Wave(p, 50, 30, smolBugFill, smolBugAccent, smolBugText, "Small Bugs");
        waves[2].addSpawns("smolBug", 8);

        waves[3] = new Wave(p, 50, 30, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[3].addSpawns("smolBug", 5);
        waves[3].addSpawns("midBug", 3);

        waves[4] = new Wave(p, 60, 35, smolBugFill, smolBugAccent, smolBugText, "Small Bugs");
        waves[4].addSpawns("smolBug", 15);

        waves[5] = new Wave(p, 60, 35, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[5].addSpawns("smolBug", 10);
        waves[5].addSpawns("midBug", 5);

        waves[6] = new Wave(p, 40, 25, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[6].addSpawns("smolBug", 10);
        waves[6].addSpawns("midBug", 5);

        waves[7] = new Wave(p, 45, 18, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[7].addSpawns("smolBug", 10);
        waves[7].addSpawns("midBug", 10);

        waves[8] = new Wave(p, 40, 15, treeSpriteFill, treeSpriteAccent, treeSpriteText, "Tree Sprites");
        waves[8].addSpawns("smolBug", 10);
        waves[8].addSpawns("treeSprite",5);

        waves[9] = new Wave(p, 40, 15, treeSpriteFill, treeSpriteAccent, treeSpriteText, "Tree Sprites");
        waves[9].addSpawns("smolBug", 5);
        waves[9].addSpawns("midBug", 5);
        waves[9].addSpawns("treeSprite",5);

        waves[10] = new Wave(p, 45, 18, treeSpriteFill, treeSpriteAccent, treeSpriteText, "Tree Sprites");
        waves[10].addSpawns("smolBug", 5);
        waves[10].addSpawns("midBug", 5);
        waves[10].addSpawns("treeSprite",10);

        waves[11] = new Wave(p, 50, 20, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[11].addSpawns("midBug", 20);

        waves[12] = new Wave(p, 50, 20, treeSpriteFill, treeSpriteAccent, treeSpriteText, "Tree Sprites");
        waves[12].addSpawns("midBug", 10);
        waves[12].addSpawns("treeSprite",10);

        waves[13] = new Wave(p, 60, 10, snakeFill, snakeAccent, snakeText, "Snakes");
        waves[13].addSpawns("snake", 20);

        waves[14] = new Wave(p, 40, 15, treeSpiritFill, treeSpiritAccent, treeSpiritText, "Tree Spirits");
        waves[14].addSpawns("treeSprite", 5);
        waves[14].addSpawns("treeSpirit", 3);

        waves[15] = new Wave(p, 45, 18, treeSpiritFill, treeSpiritAccent, treeSpiritText, "Tree Spirits");
        waves[15].addSpawns("treeSprite", 8);
        waves[15].addSpawns("treeSpirit", 5);

        waves[16] = new Wave(p, 45, 18, treeSpiritFill, treeSpiritAccent, treeSpiritText, "Tree Spirits");
        waves[16].addSpawns("treeSprite", 8);
        waves[16].addSpawns("treeSpirit", 5);

        waves[17] = new Wave(p, 45, 18, midBugFill, midBugAccent, midBugText, "Bugs");
        waves[17].addSpawns("smolBug", 10);
        waves[17].addSpawns("midBug", 10);
        waves[17].addSpawns("butterfly", 5);

        waves[18] = new Wave(p, 45, 18, treeSpiritFill, treeSpiritAccent, treeSpiritText, "Tree Spirits");
        waves[18].addSpawns("treeSprite", 5);
        waves[18].addSpawns("treeSpirit", 8);

        waves[19] = new Wave(p, 40, 15, bigBugFill, bigBugAccent, bigBugText, "Big Bug");
        waves[19].addSpawns("midBug", 5);
        waves[19].addSpawns("bigBug", 1);

        waves[20] = new Wave(p, 50, 20, bigBugFill, bigBugAccent, bigBugText, "Big Bugs");
        waves[20].addSpawns("midBug", 5);
        waves[20].addSpawns("bigBug", 3);

        waves[21] = new Wave(p, 50, 20, bigBugFill, bigBugAccent, bigBugText, "Big Bugs");
        waves[21].addSpawns("midBug", 5);
        waves[21].addSpawns("bigBug", 3);

        waves[22] = new Wave(p, 45, 18, treeSpiritFill, treeSpiritAccent, treeSpiritText, "Tree Spirits");
        waves[22].addSpawns("treeSprite", 10);
        waves[22].addSpawns("treeSpirit", 10);

        waves[23] = new Wave(p, 45, 18, bigBugFill, bigBugAccent, bigBugText, "Big Bugs");
        waves[23].addSpawns("midBug", 7);
        waves[23].addSpawns("bigBug", 3);
        waves[23].addSpawns("littleWorm", 5);

        waves[24] = new Wave(p, 50, 20, bigBugFill, bigBugAccent, bigBugText, "Big Bugs");
        waves[24].addSpawns("midBug", 10);
        waves[24].addSpawns("bigBug", 5);
        waves[24].addSpawns("littleWorm", 5);
        waves[24].addSpawns("snake", 20);

        waves[25] = new Wave(p, 45, 1, treeGiantFill, treeGiantAccent, treeGiantText, "Tree Giant");
        waves[25].addSpawns("treeGiant", 1);

        waves[26] = new Wave(p, 50, 20, bigBugFill, bigBugAccent, bigBugText, "Big Bugs");
        waves[26].addSpawns("bigBug", 10);
        waves[26].addSpawns("snake", 10);

        waves[27] = new Wave(p, 45, 18, treeGiantFill, treeGiantAccent, treeGiantText, "Tree Giant");
        waves[27].addSpawns("treeSprite", 5);
        waves[27].addSpawns("treeSpirit", 5);
        waves[27].addSpawns("treeGiant", 1);

        waves[28] = new Wave(p, 65, 20, hordeFill, hordeAccent, hordeText, "Horde");
        waves[28].addSpawns("midBug", 10);
        waves[28].addSpawns("treeSpirit", 10);
        waves[28].addSpawns("bigBug", 3);
        waves[28].addSpawns("snake", 30);
        waves[28].addSpawns("butterfly", 5);
        waves[28].addSpawns("littleWorm", 5);

        waves[29] = new Wave(p, 65, 10, treeGiantFill, treeGiantAccent, treeGiantText, "Tree Giants");
        waves[29].addSpawns("treeGiant", 3);
        waves[29].addSpawns("treeSpirit", 10);
        waves[29].addSpawns("snake", 10);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
