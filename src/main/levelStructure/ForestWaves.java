package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[4];
        waves[0] = new Wave(p,1000,100,900,new Color(150,255,0),new Color(30,50,0),"Small Bugs",0);
        waves[0].spawns.add(new Wave.Spawn("smolBug",10,3));
        waves[0].spawns.add(new Wave.Spawn("midBug",3,5));
        waves[1] = new Wave(p, 200,1,20,new Color(255,0,0),new Color(50,0,0),"River of Snakes",1);
        waves[1].spawns.add(new Wave.Spawn("snake",10,4));
        waves[2] = new Wave(p,900,120,1000,new Color(0,225,25),new Color(0,50,5),"Tree Sprites",2);
        waves[2].spawns.add(new Wave.Spawn("treeSprite",10,4));
        waves[2].spawns.add(new Wave.Spawn("treeSpirit",2,7));
        waves[3] = new Wave(p,500,10,50,new Color(0,100,25),new Color(100,255,0),"Tree Giant",3);
        waves[3].spawns.add(new Wave.Spawn("treeGiant",10,100));
        return waves;
    }
}
