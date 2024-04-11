package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class WasteWaves {

    public static Wave[] genWasteWaves(PApplet p) {
        Wave[] waves = new Wave[1];

        waves[0] = new Wave(p, 100, 20,
                new Color(0xAA0000),
                new Color(0x0A0A0A),
                new Color(0x190000),
                "Robugs");

        for (Wave wave : waves) wave.load();
        return waves;
    }
}
