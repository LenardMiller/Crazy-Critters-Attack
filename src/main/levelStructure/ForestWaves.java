package main.levelStructure;

import processing.core.PApplet;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[1];
        waves[0] = new Wave(p,2400,120);
        waves[0].spawns.add(new Wave.Spawn("smolBug",10,3));
        waves[0].spawns.add(new Wave.Spawn("midBug",3,5));
        return waves;
    }
}
