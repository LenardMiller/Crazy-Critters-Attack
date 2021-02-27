package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class CaveWaves {

    public CaveWaves() {}

    public static Wave[] genCaveWaves(PApplet p) {
        Wave[] waves = new Wave[5];

        Color hordeAccent = new Color(187, 189, 177);
        Color hordeFill = new Color(94, 93, 94);
        Color hordeText = new Color(40, 39, 40);

        waves[0] = new Wave(p, 500, 200, hordeFill, hordeAccent, hordeText, "WIP");
        waves[0].addSpawns("albinoBug",3);

        waves[1] = new Wave(p, 500, 200, hordeFill, hordeAccent, hordeText, "WIP");
        waves[1].addSpawns("albinoBug",3);

        waves[2] = new Wave(p, 500, 200, hordeFill, hordeAccent, hordeText, "WIP");
        waves[2].addSpawns("albinoBug",3);

        waves[3] = new Wave(p, 500, 200, hordeFill, hordeAccent, hordeText, "WIP");
        waves[3].addSpawns("albinoBug",3);

        waves[4] = new Wave(p, 500, 200, hordeFill, hordeAccent, hordeText, "WIP");
        waves[4].addSpawns("bigAlbinoBug",3);

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
