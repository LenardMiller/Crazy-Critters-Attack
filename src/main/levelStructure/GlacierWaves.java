package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class GlacierWaves {

    public static Wave[] genGlacierWaves(PApplet p) {
        Wave[] waves = new Wave[1];

        waves[0] = new Wave(p, 25, 15, new Color(255, 0, 255), new Color(0),
          new Color(0), "placeholder");
        waves[0].polluter = new Polluter(p, 0, "levels/glacier/partial");

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
