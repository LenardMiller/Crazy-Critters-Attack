package main.levelStructure;

import main.enemies.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.randomSpawnPosition;

public class Wave {

    private PApplet p;
    public int endTime;
    private int betweenSpawns;
    private int spawnTime;
    public int length;
    public ArrayList<Spawn> spawns;

    public Wave(PApplet p, int length, int betweenSpawns) {
        this.p = p;
        this.betweenSpawns = betweenSpawns;
        this.length = length;
        spawns = new ArrayList<>();
        spawnTime = p.frameCount + betweenSpawns + (int)p.random(-(betweenSpawns/10f),betweenSpawns/10f);
    }

    public void spawnEnemies() {
        if (spawns.size() > 0 && spawnTime <= p.frameCount) {
            spawnTime = p.frameCount + betweenSpawns + (int)p.random(-(betweenSpawns/10f),betweenSpawns/10f);
            Spawn s = getEnemySpawn();
            int cs = 1;
            while (true) {
                if ((int)(p.random(0,s.clusterChance)) == 0) cs++;
                else break;
            }
            PVector pos = randomSpawnPosition(p);
            for (int i = 0; i < cs; i++) { //cluster stuff
                Enemy e = getEnemy(s, pos); //just to find enemy size
                pos = new PVector(pos.x+p.random(e.size.x,e.size.x*4),pos.y+p.random(e.size.y,e.size.y*4));
                if (pos.x < 1000 && pos.x > -100 && pos.y < 1000 && pos.y > -100) {
                    enemies.add(getEnemy(s,pos));
                    enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
                }
            }
        }
    }

    private Spawn getEnemySpawn() {
        int m = 0;
        for (Spawn spawn : spawns) m += spawn.weight;
        int r = (int)p.random(0,m+0.99f);
        Spawn s = spawns.get(0);
        int t = 0;
        for (Spawn spawn : spawns) {
            int w = spawn.weight;
            if (r >= t && r < t+w) {
                s = spawn;
                return s;
            } else t += w;
        }
        return s;
    }

    private Enemy getEnemy(Spawn s, PVector pos) {
        Enemy e = null;

        switch (s.enemyName) {
            case "smolBug":
                e = new SmolBug(p,pos.x,pos.y);
                break;
            case "midBug":
                e = new MidBug(p,pos.x,pos.y);
                break;
            case "bigBug":
                e = new BigBug(p,pos.x,pos.y);
                break;
            case "treeSprite":
                e = new TreeSprite(p,pos.x,pos.y);
                break;
            case "treeSpirit":
                e = new TreeSpirit(p,pos.x,pos.y);
                break;
            case "treeGiant":
                e = new TreeGiant(p,pos.x,pos.y);
                break;
            case "snake":
                e = new Snake(p,pos.x,pos.y);
                break;
            case "littleWorm":
                e = new LittleWorm(p,pos.x,pos.y);
                break;
            case "butterfly":
                e = new Butterfly(p,pos.x,pos.y);
                break;
        }
        return e;
    }

    static class Spawn {

        String enemyName;
        int weight;
        int clusterChance;

        Spawn(String enemyName, int weight, int clusterChance) {
            this.enemyName = enemyName;
            this.weight = weight;
            this.clusterChance = clusterChance;
        }
    }
}
