package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class CaveWaves {

    public CaveWaves() {}

    public static Wave[] genCaveWaves(PApplet p) {
        Wave[] waves = new Wave[10];

        String horde = "horde";
        String albinoBug = "albinoBug";
        String bigAlbinoBug = "bigAlbinoBug";
        String albinoButterfly = "albinoButterfly";
        String smallGolem = "smallGolem";
        String midGolem = "midGolem";
        String bigGolem = "bigGolem";
        String bat = "bat";
        String bigBat = "bigBat";
        String wtf = "wtf";

        waves[0] = waveVizPreset(p, 500, 200, albinoBug);
        waves[0].addSpawns(albinoBug,3);

        waves[1] = waveVizPreset(p, 500, 200, bigAlbinoBug);
        waves[1].addSpawns(bigAlbinoBug,3);

        waves[2] = waveVizPreset(p, 500, 200, albinoButterfly);
        waves[2].addSpawns(albinoButterfly,3);

        waves[3] = waveVizPreset(p, 500, 200, smallGolem);
        waves[3].addSpawns(smallGolem,3);

        waves[4] = waveVizPreset(p, 500, 200, midGolem);
        waves[4].addSpawns(midGolem,3);

        waves[5] = waveVizPreset(p, 500, 200, bigGolem);
        waves[5].addSpawns(bigGolem,3);

        waves[6] = waveVizPreset(p, 500, 200, bat);
        waves[6].addSpawns(bat,3);

        waves[7] = waveVizPreset(p, 500, 200, bigBat);
        waves[7].addSpawns(bigBat,3);

        waves[8] = waveVizPreset(p, 500, 200, wtf);
        waves[8].addSpawns(wtf,3);

        waves[9] = waveVizPreset(p, 500, 200, horde);
        waves[9].addSpawns(midGolem,3);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave waveVizPreset(PApplet p, int length, int spawnLength, String type) {
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
        Color smallGolemFill = new Color(115, 123, 135);
        Color smallGolemText = new Color(206, 209, 198);

        String midGolem = "midGolem";
        String midGolemTitle = "Rock Golems";
        Color midGolemAccent = new Color(188, 255, 0);
        Color midGolemFill = new Color(115, 123, 135);
        Color midGolemText = new Color(206, 209, 198);

        String bigGolem = "bigGolem";
        String bigGolemTitle = "Boulder Golems";
        Color bigGolemAccent = new Color(255, 0, 198);
        Color bigGolemFill = new Color(115, 123, 135);
        Color bigGolemText = new Color(206, 209, 198);

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
        Color wtfText = new Color(172, 50, 50);

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
