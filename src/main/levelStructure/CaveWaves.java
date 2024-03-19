package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class CaveWaves {

    public static Wave[] genCaveWaves(PApplet p) {
        Wave[] waves = new Wave[40];

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

        waves[0] = wavePreset(p, 65, 30, albinoBug, new String[]{"Cave", "Beetles"});
        waves[0].addSpawns(albinoBug, 5);
        waves[0].polluter = new Polluter(p, 5, "cave/polluted");

        waves[1] = wavePreset(p, 55, 20, albinoBug, new String[]{"Cave", "Beetles"});
        waves[1].addSpawns(albinoBug, 5);

        waves[2] = wavePreset(p, 65, 25, albinoBug, new String[]{"Cave", "Beetles"});
        waves[2].addSpawns(albinoBug, 10);

        waves[3] = wavePreset(p, 65, 25, smallGolem, new String[]{"Few Pebble", "Golems"});
        waves[3].addSpawns(albinoBug, 5);
        waves[3].addSpawns(smallGolem, 3);

        waves[4] = wavePreset(p, 55, 25, smallGolem, new String[]{"Pebble", "Golems"});
        waves[4].addSpawns(albinoBug, 5);
        waves[4].addSpawns(smallGolem, 5);

        waves[5] = wavePreset(p, 55, 25, albinoBug, new String[]{"Cave", "Beetles"});
        waves[5].addSpawns(albinoBug, 10);

        waves[6] = wavePreset(p, 55, 25, smallGolem, new String[]{"Pebble", "Golems"});
        waves[6].addSpawns(albinoBug, 5);
        waves[6].addSpawns(smallGolem, 5);

        waves[7] = wavePreset(p, 55, 25, albinoBug, new String[]{"Many Cave", "Beetles"});
        waves[7].addSpawns(albinoBug, 15);

        waves[8] = wavePreset(p, 65, 30, smallGolem, new String[]{"Pebble", "Golems"});
        waves[8].addSpawns(albinoBug, 10);
        waves[8].addSpawns(smallGolem, 5);

        waves[9] = wavePreset(p, 55, 25, albinoButterfly, "Many Moths");
        waves[9].addSpawns(albinoBug, 5);
        waves[9].addSpawns(albinoButterfly, 10);

        waves[10] = wavePreset(p, 65, 25, smallGolem, new String[]{"Pebble", "Golems"});
        waves[10].addSpawns(smallGolem, 20);

        waves[11] = wavePreset(p, 55, 30, albinoButterfly, "Many Moths");
        waves[11].addSpawns(smallGolem, 5);
        waves[11].addSpawns(albinoBug, 5);
        waves[11].addSpawns(albinoButterfly, 10);

        waves[12] = wavePreset(p, 65, 20, midGolem, new String[]{"Rock", "Golems"});
        waves[12].addSpawns(smallGolem, 5);
        waves[12].addSpawns(midGolem, 3);

        waves[13] = wavePreset(p, 45, 15, albinoButterfly, "Moth Swarm");
        waves[13].addSpawns(albinoButterfly, 15);

        waves[14] = wavePreset(p, 65, 30, midGolem, new String[]{"Rock", "Golems"});
        waves[14].addSpawns(midGolem, 5);
        waves[14].addSpawns(smallGolem, 10);

        waves[15] = wavePreset(p, 35, 15, albinoButterfly, new String[]{"Various", "Cave Bugs"});
        waves[15].addSpawns(albinoButterfly, 10);
        waves[15].addSpawns(albinoBug, 20);

        waves[16] = wavePreset(p, 85, 35, midGolem, new String[]{"Many Rock", "Golems"});
        waves[16].addSpawns(midGolem, 8);

        waves[17] = wavePreset(p, 85, 5, bat, "Few Bats");
        waves[17].addSpawns(bat, 3);

        waves[18] = wavePreset(p, 70, 25, bat, "Bats");
        waves[18].addSpawns(bat, 5);
        waves[18].addSpawns(albinoButterfly, 10);

        waves[19] = wavePreset(p, 65, 30, midGolem, new String[]{"Many Rock", "Golems"});
        waves[19].addSpawns(midGolem, 10);
        waves[19].addSpawns(smallGolem, 10);

        waves[20] = wavePreset(p, 65, 20, bigAlbinoBug, new String[]{"Hercules", "Beetle"});
        waves[20].addSpawns(bigAlbinoBug, 5);
        waves[20].addSpawns(albinoButterfly, 10);

        waves[21] = wavePreset(p, 70, 20, bat, "Many Bats");
        waves[21].addSpawns(bat, 10);

        waves[22] = wavePreset(p, 100, 35, bigAlbinoBug, new String[]{"Various", "Beetles"});
        waves[22].addSpawns(bigAlbinoBug, 10);
        waves[22].addSpawns(albinoBug, 15);

        waves[23] = wavePreset(p, 50, 30, bat, "Bat Swarm");
        waves[23].addSpawns(bat, 15);
        waves[23].addSpawns(albinoButterfly, 10);

        waves[24] = wavePreset(p, 130, 50, horde, "Horde");
        waves[24].addSpawns(albinoBug, 35);
        waves[24].addSpawns(smallGolem, 35);
        waves[24].addSpawns(albinoButterfly, 50);
        waves[24].addSpawns(midGolem, 40);
        waves[24].addSpawns(bigAlbinoBug, 5);

        waves[25] = wavePreset(p, 50, 20, bigAlbinoBug, new String[]{"Beetles &", "Golems"});
        waves[25].addSpawns(bigAlbinoBug, 10);
        waves[25].addSpawns(midGolem, 10);

        waves[26] = wavePreset(p, 50, 20, bigAlbinoBug, new String[]{"Many Big", "Beetles"});
        waves[26].addSpawns(bigAlbinoBug, 20);

        waves[27] = wavePreset(p, 80, 15, bat, "Bat Swarm");
        waves[27].addSpawns(bat, 35);
        waves[27].addSpawns(albinoButterfly, 50);

        waves[28] = wavePreset(p, 65, 30, midGolem, new String[]{"Rock", "Golem Army"});
        waves[28].addSpawns(midGolem, 20);
        waves[28].addSpawns(smallGolem, 10);

        waves[29] = wavePreset(p, 130, 1, bigGolem, new String[]{"Boulder", "Golem"});
        waves[29].polluter = new Polluter(p, 3, "cave/polluted");
        waves[29].addSpawns(bigGolem, 1);

        waves[30] = wavePreset(p, 50, 15, bigAlbinoBug, new String[]{"Big Beetle", "Swarm"});
        waves[30].addSpawns(bigAlbinoBug, 30);

        waves[31] = wavePreset(p, 100, 35, bigGolem, new String[]{"Boulder", "Golems"});
        waves[31].addSpawns(bigGolem, 3);
        waves[31].addSpawns(midGolem, 5);
        waves[31].addSpawns(smallGolem, 5);

        waves[32] = wavePreset(p, 25, 10, bat, "Bat Swarm");
        waves[32].addSpawns(bat, 30);
        waves[32].addSpawns(midGolem, 20);

        waves[33] = wavePreset(p, 100, 35, bigGolem, new String[]{"Boulder", "Golems"});
        waves[33].addSpawns(bigGolem, 3);
        waves[33].addSpawns(midGolem, 10);
        waves[33].addSpawns(smallGolem, 10);

        waves[34] = wavePreset(p, 35, 5, albinoButterfly, "Moth Swarm");
        waves[34].addSpawns(albinoButterfly, 50);

        waves[35] = wavePreset(p, 65, 5, bigBat, "Giant Bat");
        waves[35].addSpawns(bat, 5);
        waves[35].addSpawns(bigBat, 1);

        waves[36] = wavePreset(p, 100, 35, bigGolem, new String[]{"Golems &", "Beetles"});
        waves[36].addSpawns(bigGolem, 5);
        waves[36].addSpawns(bigAlbinoBug, 30);

        waves[37] = wavePreset(p, 100, 15, bigBat, "Giant Bats");
        waves[37].addSpawns(bigBat, 5);
        waves[37].addSpawns(bat, 10);

        waves[38] = wavePreset(p, 150, 1, wtf, new String[]{"Colossal", "Bug"});
        waves[38].addSpawns(wtf, 1);

        waves[39] = wavePreset(p, 150, 100, horde, new String[]{"Mega", "Horde"});
        waves[39].addSpawns(bigAlbinoBug, 50);
        waves[39].addSpawns(bigGolem, 10);
        waves[39].addSpawns(midGolem, 75);
        waves[39].addSpawns(bigBat, 15);
        waves[39].addSpawns(bat, 75);
        waves[39].addSpawns(wtf, 1);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String type, String title) {
        return wavePreset(p, length, spawnLength, type, new String[]{title});
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String type, String[] title) {
        switch (type) {
            case "horde" -> {
                return new Wave(p, length, spawnLength,
                        new Color(94, 93, 94),
                        new Color(187, 189, 177),
                        new Color(40, 39, 40), title);
            }
            case "albinoBug" -> {
                return new Wave(p, length, spawnLength,
                        new Color(211, 211, 211),
                        Color.BLACK,
                        Color.BLACK, title);
            }
            case "bigAlbinoBug" -> {
                return new Wave(p, length, spawnLength,
                        Color.BLACK,
                        new Color(211, 211, 211),
                        new Color(211, 211, 211), title);
            }
            case "albinoButterfly" -> {
                return new Wave(p, length, spawnLength,
                        new Color(211, 211, 211),
                        new Color(0, 241, 114),
                        Color.BLACK, title);
            }
            case "smallGolem" -> {
                return new Wave(p, length, spawnLength,
                        new Color(121, 129, 141),
                        new Color(95, 205, 228),
                        new Color(206, 209, 198), title);
            }
            case "midGolem" -> {
                return new Wave(p, length, spawnLength,
                        new Color(115, 123, 135),
                        new Color(188, 255, 0),
                        new Color(196, 200, 187), title);
            }
            case "bigGolem" -> {
                return new Wave(p, length, spawnLength,
                        new Color(105, 112, 124),
                        new Color(255, 0, 198),
                        new Color(187, 191, 177), title);
            }
            case "bat" -> {
                return new Wave(p, length, spawnLength,
                        new Color(72, 45, 5),
                        new Color(131, 80, 7),
                        new Color(255, 161, 0), title);
            }
            case "bigBat" -> {
                return new Wave(p, length, spawnLength,
                        new Color(38, 23, 1),
                        new Color(131, 80, 7),
                        new Color(200, 13, 0), title);
            }
            case "wtf" -> {
                return new Wave(p, length, spawnLength,
                        new Color(91, 110, 225),
                        new Color(221, 128, 59),
                        new Color(33, 42, 95), title);
            }
            default -> {
                System.out.println("Invalid wave type!");
                return new Wave(p, length, spawnLength,
                        Color.BLACK,
                        Color.BLACK,
                        Color.WHITE, "INVALID");
            }
        }
    }
}
