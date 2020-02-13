package main.levelStructure;

import processing.core.PApplet;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[2];
        waves[0] = new Wave(p,1000,120);
        waves[0].spawns.add(new Wave.Spawn("smolBug",10,3));
        waves[0].spawns.add(new Wave.Spawn("midBug",3,5));
        waves[1] = new Wave(p, 20,1);
        waves[1].spawns.add(new Wave.Spawn("snake",10,4));
        return waves;
    }
}
