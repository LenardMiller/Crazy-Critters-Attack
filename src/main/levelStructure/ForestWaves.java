package main.levelStructure;

import processing.core.PApplet;

import java.awt.*;

public class ForestWaves {

    public ForestWaves() {}

    public static Wave[] genForestWaves(PApplet p) {
        Wave[] waves = new Wave[6];
        waves[0] = new Wave(p,1500,260,750,new Color(150,255,0),new Color(30,50,0),"Small Bugs");
        waves[0].spawns.add(new Wave.Spawn("smolBug",25,5));
        waves[0].spawns.add(new Wave.Spawn("midBug",3,8));
        waves[1] = new Wave(p,1500,300,700,new Color(0,255,50),new Color(40,40,100),"Tree Sprites");
        waves[1].spawns.add(new Wave.Spawn("treeSprite",25,5));
        waves[1].spawns.add(new Wave.Spawn("treeSpirit",3,8));
        waves[2] = new Wave(p,1400,220,750,new Color(75,150,0),new Color(15,30,0),"More Bugs");
        waves[2].spawns.add(new Wave.Spawn("smolBug",20,4));
        waves[2].spawns.add(new Wave.Spawn("midBug",4,7));
        waves[2].spawns.add(new Wave.Spawn("littleWorm",2,10));
        waves[2].spawns.add(new Wave.Spawn("butterfly",2,10));
        waves[3] = new Wave(p,1000,30,120,new Color(150,200,0),new Color(40,20,0),"Snakes!");
        waves[3].spawns.add(new Wave.Spawn("snake",10,3));
        waves[4] = new Wave(p,1500,260,850,new Color(0,200,55),new Color(90,45,55),"Tree Spirits");
        waves[4].spawns.add(new Wave.Spawn("treeSprite",20,4));
        waves[4].spawns.add(new Wave.Spawn("treeSpirit",5,7));
        waves[5] = new Wave(p,2500,5,1,new Color(0,100,50),new Color(200,50,50),"Tree Giant");
        waves[5].spawns.add(new Wave.Spawn("treeGiant",10,1000));
        return waves;
    }
}
