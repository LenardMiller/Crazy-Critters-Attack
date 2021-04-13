package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class GlacierWaves {

    public static Wave[] genGlacierWaves(PApplet p) {
        Wave[] waves = new Wave[2];

        waves[0] = new Wave(p, 25, 15, new Color(255, 0, 255), new Color(0),
          new Color(0), "placeholder");
        waves[0].addSpawns("smolBug", 1);

        waves[1] = new Wave(p, 25, 15, new Color(255, 0, 255), new Color(0),
          new Color(0), "placeholder");
        waves[1].addSpawns("smolBug", 1);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
