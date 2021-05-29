package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class GlacierWaves {

    public static Wave[] genGlacierWaves(PApplet p) {
        Wave[] waves = new Wave[20]; //60

        String snowAntlion = "snowAntlion";
        String wolf = "wolf";
        String shark = "shark";
        String velociraptor = "velociraptor";

        waves[0] = new Wave(p, 25, 15, new Color(255, 0, 255), new Color(0),
          new Color(0), "placeholder");
        waves[0].polluter = new Polluter(p, 0, "levels/glacier/partial");

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String type) {
        switch (type) {
            case "snowAntlion":
                return new Wave(p, length, spawnLength,
                  new Color(255, 255, 255),
                  new Color(179, 214, 238),
                  new Color(0, 113, 189),
                  "Antlions"
                );
            case "wolf":
                return new Wave(p, length, spawnLength,
                  new Color(171, 170, 165),
                  new Color(117, 113, 105),
                  new Color(0),
                  "Wolves"
                );
            case "shark":
                return new Wave(p, length, spawnLength,
                  new Color(30, 155, 227),
                  new Color(0, 255, 224),
                  new Color(0, 60, 100),
                  "Snow Sharks"
                );
            case "velociraptor":
                return new Wave(p, length, spawnLength,
                  new Color(116, 156, 160),
                  new Color(0, 157, 208),
                  new Color(255, 200, 0),
                  "Velociraptors"
                );
            default:
                return new Wave(p, length, spawnLength,
                  new Color(255, 0, 255),
                  new Color(0),
                  new Color(0),
                  "placeholder"
                );
        }
    }
}
