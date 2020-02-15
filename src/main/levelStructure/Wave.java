package main.levelStructure;

import main.enemies.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.randomSpawnPosition;

public class Wave {

    private PApplet p;
    public int endTime;
    private int betweenSpawns;
    private int spawnTimer;
    private int spawnLength;
    private int waitTimer;
    public int length;
    public ArrayList<Spawn> spawns;
    private Color bgColor;
    private Color textColor;
    private String title;
    private int iconId;

    public Wave(PApplet p, int length, int betweenSpawns, int spawnLength, Color bgColor, Color textColor, String title, int iconId) {
        this.p = p;
        this.betweenSpawns = betweenSpawns;
        this.length = length;
        this.spawnLength = spawnLength;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.title = title;
        this.iconId = iconId;
        spawns = new ArrayList<>();
        spawnTimer = p.frameCount + betweenSpawns + (int)p.random(-(betweenSpawns/10f),betweenSpawns/10f);
    }

    public void init() {
        endTime = p.frameCount + length;
        waitTimer = p.frameCount + spawnLength;
    }

    public void spawnEnemies() {
        if (spawns.size() > 0 && spawnTimer <= p.frameCount && waitTimer >= p.frameCount) {
            spawnTimer = p.frameCount + betweenSpawns + (int)p.random(-(betweenSpawns/10f),betweenSpawns/10f);
            Spawn s = getEnemySpawn();
            int cs = 1;
            while (true) {
                if ((int)(p.random(0,s.clusterChance)) == 0) cs++; //todo: see if floats work
                else break;
            }
            PVector pos;
            for (int i = 0; i < cs; i++) { //cluster stuff
                pos = randomSpawnPosition(p);
                enemies.add(getEnemy(s,pos));
                enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
            }
        }
    }

    public void display(float y, int id) {
        p.tint(bgColor.getRed(),bgColor.getGreen(),bgColor.getBlue());
        p.image(spritesH.get("waveBgIc"),900,y);
        p.tint(255);
        p.fill(textColor.getRed(),textColor.getGreen(),textColor.getBlue());
        p.textAlign(CENTER);
        p.textFont(largeFont);
        p.text(title,1000,y+30);
        p.textAlign(LEFT);
        p.text(id,910,y+115);
        p.image(spritesAnimH.get("waveIC")[iconId],974,y+48);
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
        float clusterChance;

        Spawn(String enemyName, int weight, float clusterChance) {
            this.enemyName = enemyName;
            this.weight = weight;
            this.clusterChance = clusterChance;
        }
    }
}
