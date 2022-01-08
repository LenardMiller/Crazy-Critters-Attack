package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class DeepForestWaves {

    public static Wave[] genDeepForestWaves(PApplet p) {
        Wave[] waves = new Wave[37];

        String bigBug = "Big Bugs";
        String midBug = "midBug";
        String smallBug = "smolBug";
        String butterfly = "butterfly";
        String smallWorm = "littleWorm";
        String smallTree = "treeSprite";
        String midTree = "Tree Spirits";
        String treeGiant = "Tree Giants";
        String enragedGiant = "Enraged Giants";
        String mantis = "Mantises";
        String midWorm = "midWorm";
        String bigWorm = "Worms";
        String roach = "Roaches";
        String mudCreature = "Mud Creatures";
        String mudFlinger = "Mud Flingers";
        String root = "Roots";
        String mantoid = "Mantoids";

        waves[0] = wavePreset(p, 80, 40, "Bugs");
        waves[0].polluter = new Polluter(p, 4, "deepForest/yellow");
        waves[0].addSpawns(midBug, 10);
        waves[0].addSpawns(smallBug, 20);
        waves[0].addSpawns(butterfly, 5);
        waves[0].addSpawns(smallWorm, 5);

        waves[1] = wavePreset(p, 80, 40, midTree);
        waves[1].addSpawns(smallTree, 20);
        waves[1].addSpawns(midTree, 3);

        //first big bug
        waves[2] = wavePreset(p, 60, 1, "Big Bug");
        waves[2].addSpawns(bigBug, 1);

        waves[3] = wavePreset(p, 80, 40, bigBug);
        waves[3].addSpawns(bigBug, 3);
        waves[3].addSpawns(midBug, 10);

        waves[4] = wavePreset(p, 40, 20, midTree);
        waves[4].addSpawns("snake",10);
        waves[4].addSpawns(smallTree, 20);
        waves[4].addSpawns(midTree, 10);

        waves[5] = wavePreset(p, 40, 30, bigBug);
        waves[5].addSpawns(bigBug, 5);
        waves[5].addSpawns(midBug, 20);
        waves[5].addSpawns(smallWorm, 5);

        //first tree giant
        waves[6] = wavePreset(p, 80, 1, "Tree Giant");
        waves[6].addSpawns(treeGiant, 1);

        waves[7] = wavePreset(p, 60, 30, bigBug);
        waves[7].addSpawns(bigBug, 10);
        waves[7].addSpawns(butterfly, 5);

        waves[8] = wavePreset(p, 60, 30, midTree);
        waves[8].addSpawns(midTree, 40);

        //first mantis
        waves[9] = wavePreset(p, 80, 1, "Mantis");
        waves[9].addSpawns(mantis, 1);

        waves[10] = wavePreset(p, 80, 40, treeGiant);
        waves[10].addSpawns(treeGiant, 3);
        waves[10].addSpawns(midTree, 10);

        waves[11] = wavePreset(p, 60, 30, bigBug);
        waves[11].addSpawns(bigBug, 15);
        waves[11].addSpawns(butterfly, 5);
        waves[11].addSpawns(smallWorm, 5);

        waves[12] = wavePreset(p, 80, 40, mantis);
        waves[12].addSpawns(mantis, 3);
        waves[12].addSpawns(bigBug, 5);
        waves[12].addSpawns(midBug, 20);

        waves[13] = wavePreset(p, 150, 120, "Horde");
        waves[13].addSpawns(treeGiant, 5);
        waves[13].addSpawns(midTree, 30);
        waves[13].addSpawns(smallTree, 10);
        waves[13].addSpawns(bigBug, 25);
        waves[13].addSpawns(midBug, 10);
        waves[13].addSpawns("snake", 20);
        waves[13].addSpawns(smallBug, 10);
        waves[13].addSpawns(smallWorm, 20);
        waves[13].addSpawns(butterfly, 20);

        //first enraged giant
        waves[14] = wavePreset(p, 40, 1, "Enraged Giant");
        waves[14].addSpawns(enragedGiant, 1);

        waves[15] = wavePreset(p, 80, 20, mantis);
        waves[15].addSpawns(mantis, 5);

        waves[16] = wavePreset(p, 60, 10, bigWorm);
        waves[16].addSpawns(smallWorm, 20);
        waves[16].addSpawns(midWorm, 10);
        waves[16].addSpawns(bigWorm, 5);

        waves[17] = wavePreset(p, 80, 20, "Giants");
        waves[17].addSpawns(enragedGiant, 1);
        waves[17].addSpawns(treeGiant, 2);

        waves[18] = wavePreset(p, 80, 20, mantis);
        waves[18].addSpawns(mantis, 10);

        waves[19] = wavePreset(p, 80, 30, "Giants");
        waves[19].addSpawns(enragedGiant, 2);
        waves[19].addSpawns(treeGiant, 3);
        waves[19].addSpawns(bigBug, 3);

        waves[20] = wavePreset(p, 60, 10, bigWorm);
        waves[20].addSpawns(smallWorm, 20);
        waves[20].addSpawns(midWorm, 20);
        waves[20].addSpawns(bigWorm, 15);

        waves[21] = wavePreset(p, 80, 20, enragedGiant);
        waves[21].polluter = new Polluter(p, 1, "deepForest/yellow");
        waves[21].addSpawns(enragedGiant, 5);

        //first roach
        waves[22] = wavePreset(p, 60, 20, roach);
        waves[22].addSpawns(roach, 40);

        //first mud creature
        waves[23] = wavePreset(p, 50, 1, mudCreature);
        waves[23].addSpawns(mudCreature, 3);

        waves[24] = wavePreset(p, 240, 200, "Bug Horde");
        waves[24].addSpawns(bigBug, 25);
        waves[24].addSpawns(roach, 100);
        waves[24].addSpawns(mantis, 40);
        waves[24].addSpawns(bigWorm, 20);
        waves[24].addSpawns(butterfly, 20);
        waves[24].addSpawns("wtf", 1);

        //first mantoid
        waves[25] = wavePreset(p, 50, 1, "Mantoid");
        waves[25].addSpawns(mantoid, 1);

        waves[26] = wavePreset(p, 50, 20, enragedGiant);
        waves[26].polluter = new Polluter(p, 4, "deepForest/brown");
        waves[26].addSpawns(enragedGiant, 10);
        waves[26].addSpawns(mantis, 10);

        //first mud flinger
        waves[27] = wavePreset(p, 50, 1, mudCreature);
        waves[27].addSpawns(mudCreature, 5);
        waves[27].addSpawns(mudFlinger, 1);

        waves[28] = wavePreset(p, 60, 20, roach);
        waves[28].addSpawns(roach, 50);

        waves[29] = wavePreset(p, 80, 30, mantoid);
        waves[29].addSpawns(mantoid, 5);
        waves[29].addSpawns(mantis, 10);

        waves[30] = wavePreset(p, 40, 20, roach);
        waves[30].addSpawns(roach, 60);

        waves[31] = wavePreset(p, 40, 20, mantoid);
        waves[31].addSpawns(mantoid, 5);
        waves[31].addSpawns(enragedGiant, 10);

        waves[32] = wavePreset(p, 50, 10, mudCreature);
        waves[32].addSpawns(mudCreature, 10);
        waves[32].addSpawns(mudFlinger, 2);

        waves[33] = wavePreset(p, 50, 5, root);
        waves[33].addSpawns(root, 10);

        waves[34] = wavePreset(p, 40, 20, mantoid);
        waves[34].addSpawns(mantoid, 10);
        waves[34].addSpawns(roach, 60);

        waves[35] = wavePreset(p, 50, 5, root);
        waves[35].addSpawns(root, 20);

        waves[36] = wavePreset(p, 50, 20, mudCreature);
        waves[36].addSpawns(mudCreature, 20);
        waves[36].addSpawns(mudFlinger, 3);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String title) {
        switch (title) {
            case "Bugs":
                return new Wave(p, length, spawnLength,
                        new Color(0xAA0000),
                        new Color(0x0A0A0A),
                        new Color(0x190000),
                        title);
            case "Big Bug":
            case "Big Bugs":
                return new Wave(p, length, spawnLength,
                        new Color(10, 10, 10),
                        new Color(170, 0, 0),
                        new Color(255, 0, 0),
                        title);
            case "Tree Spirits":
                return new Wave(p, length, spawnLength,
                        new Color(20, 183, 83),
                        new Color(123, 78, 15),
                        new Color(0, 50, 0),
                        title);
            case "Tree Giant":
            case "Tree Giants":
                return new Wave(p, length, spawnLength,
                        new Color(0, 100, 0),
                        new Color(255, 0, 77),
                        new Color(32, 255, 32),
                        title);
            case "Bug Horde":
            case "Horde":
                return new Wave(p, length, spawnLength,
                        new Color(143, 86, 59),
                        new Color(20, 160, 46),
                        new Color(47, 28, 1),
                         title);
            case "Worms":
                return new Wave(p, length, spawnLength,
                        new Color(232, 106, 115),
                        new Color(255, 255, 255),
                        new Color(45, 19, 21),
                        title);
            case "Giants":
            case "Enraged Giant":
            case "Enraged Giants":
                return new Wave(p, length, spawnLength,
                        new Color(0x689A32),
                        new Color(0x5B4B1C),
                        new Color(0x6b5823),
                        title);
            case "Mantises":
            case "Mantis":
                return new Wave(p, length, spawnLength,
                        new Color(0x82D240),
                        new Color(0xCAEC43),
                        new Color(0x407E15),
                        title);
            case "Roaches":
                return new Wave(p, length, spawnLength,
                        new Color(0x5C241A),
                        new Color(0x804E2A),
                        new Color(0x30130E),
                        title);
            case "Mud Creature":
            case "Mud Creatures":
                return new Wave(p, length, spawnLength,
                        new Color(74, 39, 0),
                        new Color(188, 186, 130),
                        new Color(23, 13, 0),
                        title);
            case "Roots":
                return new Wave(p, length, spawnLength,
                        new Color(0x714200),
                        new Color(0xb76e09),
                        new Color(0x412600),
                        title);
            case "Mantoids":
            case "Mantoid":
                return new Wave(p, length, spawnLength,
                        new Color(0xbe3055),
                        new Color(0xc0ec3d),
                        new Color(0xfb8e37),
                        title);
            default:
                return new Wave(p, length, spawnLength,
                        new Color(0),
                        new Color(255, 0, 255),
                        new Color(0),
                        title);
        }
    }
}
