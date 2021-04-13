package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class CaveWaves {

    public CaveWaves() {}

    public static Wave[] genCaveWaves(PApplet p) {
        Wave[] waves = new Wave[50];

        String albinoBug = "albinoBug";
        String bigAlbinoBug = "bigAlbinoBug";
        String albinoButterfly = "albinoButterfly";
        String smallGolem = "smallGolem";
        String midGolem = "midGolem";
        String bigGolem = "bigGolem";
        String bat = "bat";
        String bigBat = "bigBat";
        String wtf = "wtf";
        String horde = "horde";

        waves[0] = wavePreset(p, 65, 30, albinoBug);
        waves[0].addSpawns(albinoBug, 5);
        waves[0].polluter = new Polluter(p, 5, "levels/cave/polluted");

        waves[1] = wavePreset(p, 55, 20, albinoBug);
        waves[1].addSpawns(albinoBug, 5);

        waves[2] = wavePreset(p, 65, 25, albinoBug);
        waves[2].addSpawns(albinoBug, 10);

        waves[3] = wavePreset(p, 65, 25, smallGolem);
        waves[3].addSpawns(albinoBug, 5);
        waves[3].addSpawns(smallGolem, 3);

        waves[4] = wavePreset(p, 55, 25, smallGolem);
        waves[4].addSpawns(albinoBug, 5);
        waves[4].addSpawns(smallGolem, 5);

        waves[5] = wavePreset(p, 55, 25, albinoBug);
        waves[5].addSpawns(albinoBug, 10);

        waves[6] = wavePreset(p, 55, 25, smallGolem);
        waves[6].addSpawns(albinoBug, 5);
        waves[6].addSpawns(smallGolem, 5);

        waves[7] = wavePreset(p, 55, 25, albinoBug);
        waves[7].addSpawns(albinoBug, 15);

        waves[8] = wavePreset(p, 65, 30, smallGolem);
        waves[8].addSpawns(albinoBug, 10);
        waves[8].addSpawns(smallGolem, 5);

        waves[9] = wavePreset(p, 55, 25, albinoButterfly);
        waves[9].addSpawns(albinoBug, 5);
        waves[9].addSpawns(albinoButterfly, 15);

        waves[10] = wavePreset(p, 65, 25, smallGolem);
        waves[10].addSpawns(smallGolem, 20);

        waves[11] = wavePreset(p, 55, 30, albinoButterfly);
        waves[11].addSpawns(smallGolem, 5);
        waves[11].addSpawns(albinoBug, 5);
        waves[11].addSpawns(albinoButterfly, 15);

        waves[12] = wavePreset(p, 65, 20, midGolem);
        waves[12].addSpawns(smallGolem, 5);
        waves[12].addSpawns(midGolem, 3);

        waves[13] = wavePreset(p, 45, 15, albinoButterfly);
        waves[13].addSpawns(albinoButterfly, 25);

        waves[14] = wavePreset(p, 65, 30, midGolem);
        waves[14].addSpawns(midGolem, 5);
        waves[14].addSpawns(smallGolem, 10);

        waves[15] = wavePreset(p, 35, 15, albinoButterfly);
        waves[15].addSpawns(albinoButterfly, 15);
        waves[15].addSpawns(albinoBug, 20);

        waves[16] = wavePreset(p, 85, 35, midGolem);
        waves[16].addSpawns(midGolem, 15);

        waves[17] = wavePreset(p, 85, 5, bat);
        waves[17].addSpawns(smallGolem, 5);
        waves[17].addSpawns(bat, 5);

        waves[18] = wavePreset(p, 70, 25, bat);
        waves[18].addSpawns(bat, 10);
        waves[18].addSpawns(albinoButterfly, 10);

        waves[19] = wavePreset(p, 65, 30, midGolem);
        waves[19].addSpawns(midGolem, 10);
        waves[19].addSpawns(smallGolem, 10);

        waves[20] = wavePreset(p, 65, 20, bigAlbinoBug);
        waves[20].addSpawns(bigAlbinoBug, 5);
        waves[20].addSpawns(albinoButterfly, 10);

        waves[21] = wavePreset(p, 70, 20, bat);
        waves[21].addSpawns(bat, 15);

        waves[22] = wavePreset(p, 100, 35, bigAlbinoBug);
        waves[22].addSpawns(bigAlbinoBug, 10);
        waves[22].addSpawns(albinoBug, 15);

        waves[23] = wavePreset(p, 50, 30, bat);
        waves[23].addSpawns(bat, 15);
        waves[23].addSpawns(albinoButterfly, 10);

        waves[24] = wavePreset(p, 130, 50, horde);
        waves[24].addSpawns(albinoBug, 35);
        waves[24].addSpawns(smallGolem, 35);
        waves[24].addSpawns(albinoButterfly, 50);
        waves[24].addSpawns(midGolem, 40);
        waves[24].addSpawns(bigAlbinoBug, 5);

        waves[25] = wavePreset(p, 65, 25, bigAlbinoBug);
        waves[25].addSpawns(bigAlbinoBug, 10);
        waves[25].addSpawns(midGolem, 10);

        waves[26] = wavePreset(p, 85, 35, bigAlbinoBug);
        waves[26].addSpawns(bigAlbinoBug, 10);

        waves[27] = wavePreset(p, 80, 15, bat);
        waves[27].addSpawns(bat, 50);
        waves[27].addSpawns(albinoButterfly, 50);

        waves[28] = wavePreset(p, 65, 30, midGolem);
        waves[28].addSpawns(midGolem, 20);
        waves[28].addSpawns(smallGolem, 10);

        waves[29] = wavePreset(p, 130, 1, bigGolem);
        waves[29].addSpawns(bigGolem, 1);

        waves[30] = wavePreset(p, 50, 15, bigAlbinoBug);
        waves[30].addSpawns(bigAlbinoBug, 30);

        waves[31] = wavePreset(p, 100, 35, bigGolem);
        waves[31].addSpawns(bigGolem, 3);
        waves[31].addSpawns(midGolem, 5);
        waves[31].addSpawns(smallGolem, 5);

        waves[32] = wavePreset(p, 25, 10, bat);
        waves[32].addSpawns(bat, 30);
        waves[32].addSpawns(midGolem, 20);

        waves[33] = wavePreset(p, 100, 15, bigGolem);
        waves[33].addSpawns(bigGolem, 5);

        waves[34] = wavePreset(p, 100, 35, bigGolem);
        waves[34].addSpawns(bigGolem, 3);
        waves[34].addSpawns(midGolem, 10);
        waves[34].addSpawns(smallGolem, 10);

        waves[35] = wavePreset(p, 35, 5, albinoButterfly);
        waves[35].addSpawns(albinoButterfly, 50);

        waves[36] = wavePreset(p, 100, 15, bigGolem);
        waves[36].addSpawns(bigGolem, 5);

        waves[37] = wavePreset(p, 65, 5, bigBat);
        waves[37].addSpawns(bat, 5);
        waves[37].addSpawns(bigBat, 1);

        waves[38] = wavePreset(p, 100, 35, bigGolem);
        waves[38].addSpawns(bigGolem, 5);
        waves[38].addSpawns(midGolem, 5);
        waves[38].addSpawns(smallGolem, 5);

        waves[39] = wavePreset(p, 100, 35, bigGolem);
        waves[39].addSpawns(bigGolem, 10);
        waves[39].addSpawns(bigAlbinoBug, 30);

        waves[40] = wavePreset(p, 100, 15, bigBat);
        waves[40].addSpawns(bigBat, 5);
        waves[40].addSpawns(bat, 10);

        waves[41] = wavePreset(p, 100, 15, bigBat);
        waves[41].addSpawns(bigBat, 3);
        waves[41].addSpawns(bigAlbinoBug, 30);

        waves[42] = wavePreset(p, 100, 30, bigGolem);
        waves[42].addSpawns(bigGolem, 10);
        waves[42].addSpawns(bigAlbinoBug, 30);

        waves[43] = wavePreset(p, 100, 35, bigBat);
        waves[43].addSpawns(bigBat, 10);
        waves[43].addSpawns(bigGolem, 3);

        waves[44] = wavePreset(p, 150, 1, wtf);
        waves[44].addSpawns(wtf, 1);

        waves[45] = wavePreset(p, 150, 50, horde);
        waves[45].addSpawns(bigAlbinoBug, 40);
        waves[45].addSpawns(midGolem, 50);
        waves[45].addSpawns(smallGolem, 50);
        waves[45].addSpawns(bigGolem, 10);

        waves[46] = wavePreset(p, 150, 1, wtf);
        waves[46].addSpawns(wtf, 1);
        waves[46].addSpawns(bigAlbinoBug, 15);

        waves[47] = wavePreset(p, 150, 15, wtf);
        waves[47].addSpawns(wtf, 3);
        waves[47].addSpawns(bigAlbinoBug, 20);
        waves[47].addSpawns(albinoBug, 15);

        waves[48] = wavePreset(p, 150, 65, horde);
        waves[48].addSpawns(bigAlbinoBug, 35);
        waves[48].addSpawns(bigGolem, 15);
        waves[48].addSpawns(midGolem, 50);
        waves[48].addSpawns(bigBat, 15);
        waves[38].addSpawns(bat, 60);

        waves[49] = wavePreset(p, 150, 20, wtf);
        waves[49].addSpawns(bigAlbinoBug, 40);
        waves[49].addSpawns(bigBat, 10);
        waves[49].addSpawns(wtf, 10);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String type) {
        String horde = "horde";
        String hordeTitle = "Horde";
        Color hordeAccent = new Color(187, 189, 177);
        Color hordeFill = new Color(94, 93, 94);
        Color hordeText = new Color(40, 39, 40);

        String albinoBug = "albinoBug";
        String albinoBugTitle = "Bugs";
        Color albinoBugAccent = new Color(0, 0, 0);
        Color albinoBugFill = new Color(211, 211, 211);
        Color albinoBugText = new Color(0, 0, 0);

        String bigAlbinoBug = "bigAlbinoBug";
        String bigAlbinoBugTitle = "Big Bugs";
        Color bigAlbinoBugAccent = new Color(211, 211, 211);
        Color bigAlbinoBugFill = new Color(0, 0, 0);
        Color bigAlbinoBugText = new Color(211, 211, 211);

        String albinoButterfly = "albinoButterfly";
        String albinoButterflyTitle = "Butterflies";
        Color albinoButterflyAccent = new Color(0, 241, 114);
        Color albinoButterflyFill = new Color(211, 211, 211);
        Color albinoButterflyText = new Color(0, 0, 0);

        String smallGolem = "smallGolem";
        String smallGolemTitle = "Pebble Golems";
        Color smallGolemAccent = new Color(95, 205, 228);
        Color smallGolemFill = new Color(121, 129, 141);
        Color smallGolemText = new Color(206, 209, 198);

        String midGolem = "midGolem";
        String midGolemTitle = "Rock Golems";
        Color midGolemAccent = new Color(188, 255, 0);
        Color midGolemFill = new Color(115, 123, 135);
        Color midGolemText = new Color(196, 200, 187);

        String bigGolem = "bigGolem";
        String bigGolemTitle = "Boulder Golems";
        Color bigGolemAccent = new Color(255, 0, 198);
        Color bigGolemFill = new Color(105, 112, 124);
        Color bigGolemText = new Color(187, 191, 177);

        String bat = "bat";
        String batTitle = "Bats";
        Color batAccent = new Color(131, 80, 7);
        Color batFill = new Color(72, 45, 5);
        Color batText = new Color(255, 161, 0);

        String bigBat = "bigBat";
        String bigBatTitle = "Giant Bats";
        Color bigBatAccent = new Color(131, 80, 7);
        Color bigBatFill = new Color(38, 23, 1);
        Color bigBatText = new Color(200, 13, 0);

        String wtf = "wtf";
        String wtfTitle = "Colossal Bugs";
        Color wtfAccent = new Color(221, 128, 59);
        Color wtfFill = new Color(91, 110, 225);
        Color wtfText = new Color(33, 42, 95);

        if (type.equals(horde)) return new Wave(p, length, spawnLength, hordeFill, hordeAccent, hordeText, hordeTitle);
        if (type.equals(albinoBug)) return new Wave(p, length, spawnLength, albinoBugFill, albinoBugAccent, albinoBugText, albinoBugTitle);
        if (type.equals(bigAlbinoBug)) return new Wave(p, length, spawnLength, bigAlbinoBugFill, bigAlbinoBugAccent, bigAlbinoBugText, bigAlbinoBugTitle);
        if (type.equals(albinoButterfly)) return new Wave(p, length, spawnLength, albinoButterflyFill, albinoButterflyAccent, albinoButterflyText, albinoButterflyTitle);
        if (type.equals(smallGolem)) return new Wave(p, length, spawnLength, smallGolemFill, smallGolemAccent, smallGolemText, smallGolemTitle);
        if (type.equals(midGolem)) return new Wave(p, length, spawnLength, midGolemFill, midGolemAccent, midGolemText, midGolemTitle);
        if (type.equals(bigGolem)) return new Wave(p, length, spawnLength, bigGolemFill, bigGolemAccent, bigGolemText, bigGolemTitle);
        if (type.equals(bat)) return new Wave(p, length, spawnLength, batFill, batAccent, batText, batTitle);
        if (type.equals(bigBat)) return new Wave(p, length, spawnLength, bigBatFill, bigBatAccent, bigBatText, bigBatTitle);
        if (type.equals(wtf)) return new Wave(p, length, spawnLength, wtfFill, wtfAccent, wtfText, wtfTitle);
        System.out.println("Invalid wave type!");
        return new Wave(p, length, spawnLength, wtfFill, wtfAccent, wtfText, wtfTitle);
    }
}
