package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class DeepForestWaves {

    public static Wave[] genDeepForestWaves(PApplet p) {
        String bigBug = "Big Bugs";

        Wave[] waves = new Wave[1];

        waves[0] = wavePreset(p, 60, 30, bigBug);
        waves[0].addSpawns(bigBug, 20);

        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String title) {
        switch (title) {
            case "Big Bugs":
                return new Wave(p, length, spawnLength,
                        new Color(170, 0, 0),
                        new Color(10, 10, 10),
                        new Color(255, 0, 0),
                        title);
            default:
                return new Wave(p, length, spawnLength,
                        new Color(255, 0, 255),
                        new Color(0),
                        new Color(0),
                        title
                );
        }
    }
}
