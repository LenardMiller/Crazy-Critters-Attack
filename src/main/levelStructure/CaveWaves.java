package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class CaveWaves {

    public CaveWaves() {}

    public static Wave[] genCaveWaves(PApplet p) {
        Wave[] waves = new Wave[5];

        Color nullPrimary = new Color(255, 0, 255);
        Color nullSecondary = new Color(0, 0, 0);

        waves[0] = new Wave(p, 500, 200, nullPrimary, nullSecondary, "WIP");
        waves[0].addSpawns("smolBug",3);

        waves[1] = new Wave(p, 500, 200, nullPrimary, nullSecondary, "WIP");
        waves[1].addSpawns("smolBug",3);

        waves[2] = new Wave(p, 500, 200, nullPrimary, nullSecondary, "WIP");
        waves[2].addSpawns("smolBug",3);

        waves[3] = new Wave(p, 500, 200, nullPrimary, nullSecondary, "WIP");
        waves[3].addSpawns("smolBug",3);

        waves[4] = new Wave(p, 500, 200, nullPrimary, nullSecondary, "WIP");
        waves[4].addSpawns("smolBug",3);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
