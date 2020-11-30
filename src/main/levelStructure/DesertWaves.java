package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class DesertWaves {

    public DesertWaves() {}

    public static Wave[] genDesertWaves(PApplet p) {
        Wave[] waves = new Wave[1];

        Color testPrimary = new Color(255, 0, 255);
        Color testSecondary = new Color(0);

        waves[0] = new Wave(p, 3200, 1600, testPrimary, testSecondary, "Not Implemented!");
        waves[0].addSpawns("smolBug",3);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
