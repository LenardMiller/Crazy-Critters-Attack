package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class DesertWaves {

    public DesertWaves() {}

    public static Wave[] genDesertWaves(PApplet p) {
        Wave[] waves = new Wave[40];

        Color smolBugPrimary = new Color(255, 100, 100);
        Color smolBugSecondary = new Color(10, 10, 10);

        Color midBugPrimary = new Color(170, 0, 0);
        Color midBugSecondary = new Color(10, 10, 10);

        Color bigBugPrimary = new Color(10, 10, 10);
        Color bigBugSecondary = new Color(170, 0, 0);

        Color hordePrimary = new Color(0, 0, 0);
        Color hordeSecondary = new Color(200, 200, 200);

        Color scorpionPrimary = new Color(229, 164, 75);
        Color scorpionSecondary = new Color(138, 94, 55);

        Color wormPrimary = new Color(215, 123, 186);
        Color wormSecondery = new Color(147, 97, 122);

        Color emperorPrimary = new Color(0, 0,0);
        Color emperorSecondry = new Color(241, 96, 255);

        Color midWormPrimary = new Color(220, 133, 101);
        Color midWormSecondary = new Color(136, 108, 87);

        Color bigWormPrimary = new Color(232, 106, 115);
        Color bigWormSecondary = new Color(255, 255, 255);

        Color sidewinderPrimary = new Color(154, 193, 66);
        Color sindewinderSecondary = new Color(0, 0,0);

        waves[0] = new Wave(p, 3200, 1300, smolBugPrimary, smolBugSecondary, "Small Bugs");
        waves[0].addSpawns("smolBug",6);

        waves[1] = new Wave(p, 3400, 1400, midBugPrimary, midBugSecondary, "Bugs");
        waves[1].addSpawns("midBug", 3);
        waves[1].addSpawns("smolBug", 5);

        waves[2] = new Wave(p, 3800, 1600, midBugPrimary, midBugSecondary, "Bugs");
        waves[2].addSpawns("midBug", 5);
        waves[2].addSpawns("smolBug", 15);

        waves[3] = new Wave(p, 3600, 1500, midBugPrimary, midBugSecondary, "Bugs");
        waves[3].addSpawns("midBug", 10);
        waves[3].addSpawns("smolBug", 5);

        waves[4] = new Wave(p, 3400, 1400, midBugPrimary, midBugSecondary, "Bugs");
        waves[4].addSpawns("midBug", 5);
        waves[4].addSpawns("smolBug", 15);

        waves[5] = new Wave(p, 3400, 1400, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[5].addSpawns("scorpion", 3);
        waves[5].addSpawns("smolBug", 5);

        waves[6] = new Wave(p, 3600, 1500, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[6].addSpawns("scorpion", 5);
        waves[6].addSpawns("midBug", 3);
        waves[6].addSpawns("smolBug", 5);

        waves[7] = new Wave(p, 3600, 1500, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[7].addSpawns("scorpion", 5);
        waves[7].addSpawns("midBug", 3);
        waves[7].addSpawns("smolBug", 5);

        waves[8] = new Wave(p, 3800, 1600, midBugPrimary, midBugSecondary, "Bugs");
        waves[8].addSpawns("midBug", 20);
        waves[8].addSpawns("smolBug", 5);

        waves[9] = new Wave(p, 3500, 600, sidewinderPrimary, sindewinderSecondary, "Sidewinders");
        waves[9].addSpawns("sidewinder", 15);

        waves[10] = new Wave(p, 3700, 900, sidewinderPrimary, sindewinderSecondary, "Sidewinders");
        waves[10].addSpawns("sidewinder", 20);
        waves[10].addSpawns("scorpion", 5);

        waves[11] = new Wave(p, 3600, 1500, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[11].addSpawns("scorpion", 10);
        waves[11].addSpawns("midBug", 5);

        waves[12] = new Wave(p, 3800, 1600, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[12].addSpawns("scorpion", 15);

        waves[13] = new Wave(p, 4000, 600, wormPrimary, wormSecondery, "Worms");
        waves[13].addSpawns("littleWorm", 5);

        waves[14] = new Wave(p, 2400, 200, bigBugPrimary, bigBugSecondary, "Big Bug");
        waves[14].addSpawns("bigBug", 1);

        waves[15] = new Wave(p, 4000, 600, wormPrimary, wormSecondery, "Worms");
        waves[15].addSpawns("littleWorm", 10);
        waves[15].addSpawns("scorpion", 5);

        waves[16] = new Wave(p, 3600, 1200, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[16].addSpawns("bigBug", 5);

        waves[17] = new Wave(p, 3800, 1600, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[17].addSpawns("scorpion", 20);
        waves[17].addSpawns("sidewinder", 5);

        waves[18] = new Wave(p, 4000, 600, wormPrimary, wormSecondery, "Worms");
        waves[18].addSpawns("littleWorm", 10);
        waves[18].addSpawns("scorpion", 5);

        waves[19] = new Wave(p, 3800, 1300, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[19].addSpawns("bigBug", 5);
        waves[19].addSpawns("scorpion", 15);

        waves[20] = new Wave(p, 4000, 200, midWormPrimary, midWormSecondary, "Sandworm");
        waves[20].addSpawns("midWorm", 1);
        waves[20].addSpawns("littleWorm", 5);

        waves[21] = new Wave(p, 4000, 1000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[21].addSpawns("midWorm", 5);

        waves[22] = new Wave(p, 3800, 1600, scorpionPrimary, scorpionSecondary, "Scorpions");
        waves[22].addSpawns("scorpion", 30);

        waves[23] = new Wave(p, 5000, 2000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[23].addSpawns("midWorm", 15);

        waves[24] = new Wave(p, 4000, 1000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[24].addSpawns("midWorm", 5);
        waves[24].addSpawns("littleWorm", 25);

        waves[25] = new Wave(p, 5000, 2000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[25].addSpawns("midWorm", 15);

        waves[26] = new Wave(p, 2800, 200, emperorPrimary, emperorSecondry, "Emperor");
        waves[26].addSpawns("emperor", 1);
        waves[26].addSpawns("scorpion", 5);

        waves[27] = new Wave(p, 3600, 1500, emperorPrimary, emperorSecondry, "Emperors");
        waves[27].addSpawns("emperor", 5);
        waves[27].addSpawns("midWorm", 3);

        waves[28] = new Wave(p, 3700, 900, sidewinderPrimary, sindewinderSecondary, "Sidewinders");
        waves[28].addSpawns("sidewinder", 30);
        waves[28].addSpawns("scorpion", 5);

        waves[29] = new Wave(p, 3600, 1500, emperorPrimary, emperorSecondry, "Emperors");
        waves[29].addSpawns("emperor", 5);
        waves[29].addSpawns("scorpion", 15);

        waves[30] = new Wave(p, 3800, 1500, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[30].addSpawns("bigBug", 15);

        waves[31] = new Wave(p, 3800, 1500, bigBugPrimary, bigBugSecondary, "Big Bugs");
        waves[31].addSpawns("bigBug", 15);
        waves[31].addSpawns("midBug", 15);
        waves[31].addSpawns("smolBug", 15);

        waves[32] = new Wave(p, 4000, 1000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[32].addSpawns("midWorm", 5);
        waves[32].addSpawns("littleWorm", 30);

        waves[33] = new Wave(p, 4000, 200, bigWormPrimary, bigWormSecondary, "Megaworm");
        waves[33].addSpawns("bigWorm", 1);

        waves[34] = new Wave(p, 3600, 1500, emperorPrimary, emperorSecondry, "Emperors");
        waves[34].addSpawns("emperor", 10);
        waves[34].addSpawns("bigBug", 5);

        waves[35] = new Wave(p, 4000, 1000, midWormPrimary, midWormSecondary, "Sandworms");
        waves[35].addSpawns("midWorm", 20);
        waves[35].addSpawns("littleWorm", 30);

        waves[36] = new Wave(p, 3600, 1500, emperorPrimary, emperorSecondry, "Emperors");
        waves[36].addSpawns("emperor", 15);

        waves[37] = new Wave(p, 4000, 1200, bigWormPrimary, bigWormSecondary, "Megaworms");
        waves[37].addSpawns("bigWorm", 3);

        waves[38] = new Wave(p, 4000, 1600, hordePrimary, hordeSecondary, "Horde");
        waves[38].addSpawns("midBug", 15);
        waves[38].addSpawns("bigBug", 20);
        waves[38].addSpawns("emperor", 15);
        waves[38].addSpawns("sidewinder", 15);
        waves[38].addSpawns("scorpion", 30);

        waves[39] = new Wave(p, 4000, 1600, hordePrimary, hordeSecondary, "Mega Horde");
        waves[39].addSpawns("bigBug", 30);
        waves[39].addSpawns("emperor", 20);
        waves[39].addSpawns("bigWorm", 6);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
