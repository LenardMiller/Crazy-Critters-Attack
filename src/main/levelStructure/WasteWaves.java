package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class WasteWaves {

    public static Wave[] genWasteWaves(PApplet p) {
        Wave[] waves = new Wave[3];

        String roboBugs = "Robugs";
        String cybugs = "Cybugs";

        waves[0] = wavePreset(p, 70, 40, roboBugs, roboBugs);
        waves[0].addSpawns(roboBugs, 5);

        waves[1] = wavePreset(p, 80, 50, roboBugs, roboBugs);
        waves[1].addSpawns(roboBugs, 10);

        waves[2] = wavePreset(p, 100, 20, cybugs, "Cybugs");
        waves[2].addSpawns(cybugs, 2);
        waves[2].addSpawns(roboBugs, 5);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String type, String... title) {
        return switch (type) {
            case "Robugs" -> new Wave(p, length, spawnLength,
                    new Color(0xAA0000),
                    new Color(0x0A0A0A),
                    new Color(0x190000),
                    title);
            case "Cybugs", "Cybug" -> new Wave(p, length, spawnLength,
                    new Color(0x0A0A0A),
                    new Color(0xAA0000),
                    new Color(255, 0, 0),
                    title);
            default -> new Wave(p, length, spawnLength,
                    new Color(255, 0, 255),
                    new Color(0),
                    new Color(0),
                    title
            );
        };
    }
}
