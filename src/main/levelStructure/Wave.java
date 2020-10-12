package main.levelStructure;

import main.enemies.*;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.randomSpawnPosition;

public class Wave {

    private PApplet p;
    public int endTimer;
    private int betweenSpawns;
    private int spawnTimer;
    private int spawnLength;
    int waitTimer;
    public int length;
    public ArrayList<Spawn> spawns;
    private Color primary;
    private Color secondary;
    private String title;

    //todo: pause functionality
    public Wave(PApplet p, int length, int betweenSpawns, int spawnLength, Color primary, Color secondary, String title) {
        this.p = p;
        this.betweenSpawns = betweenSpawns;
        this.length = length;
        this.spawnLength = spawnLength;
        this.primary = primary;
        this.secondary = secondary;
        this.title = title;
        spawns = new ArrayList<>();
        spawnTimer = p.frameCount + betweenSpawns + (int) p.random(-(betweenSpawns / 10f), betweenSpawns / 10f);
    }

    public void init() {
        endTimer = p.frameCount + length;
        waitTimer = p.frameCount + spawnLength;
    }

    public void end() {
        for (Tower tower : towers) if (tower.turret) tower.hp = tower.maxHp;
    }

    public void spawnEnemies() {
        if (spawns.size() > 0 && spawnTimer <= p.frameCount && waitTimer >= p.frameCount) {
            spawnTimer = p.frameCount + betweenSpawns + (int) p.random(-(betweenSpawns / 10f), betweenSpawns / 10f);
            Spawn s = getEnemySpawn();
            PVector pos;
            pos = randomSpawnPosition(p);
            enemies.add(getEnemy(s, pos));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
    }

    public void display(float y, int id) { //todo: fix jiggle text
        p.tint(primary.getRed(), primary.getGreen(), primary.getBlue());
        p.image(spritesH.get("wavePrimaryIc"), 890, y);
        p.tint(secondary.getRed(), secondary.getGreen(), secondary.getBlue());
        p.image(spritesH.get("waveSecondaryIc"), 890, y);
        p.tint(255);
        p.fill(secondary.getRed(), secondary.getGreen(), secondary.getBlue());
        p.textAlign(CENTER);
        p.textFont(largeFont);
        p.text(title, 1000, y + 110);
        p.textAlign(CENTER);
        p.textFont(veryLargeFont);
        p.text(id, 1000, y + 70);
    }

    private Spawn getEnemySpawn() {
        int m = 0;
        for (Spawn spawn : spawns) m += spawn.weight;
        int r = (int) p.random(0, m + 0.99f);
        Spawn s = spawns.get(0);
        int t = 0;
        for (Spawn spawn : spawns) {
            int w = spawn.weight;
            if (r >= t && r < t + w) {
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
                e = new SmolBug(p, pos.x, pos.y);
                break;
            case "midBug":
                e = new MidBug(p, pos.x, pos.y);
                break;
            case "bigBug":
                e = new BigBug(p, pos.x, pos.y);
                break;
            case "treeSprite":
                e = new TreeSprite(p, pos.x, pos.y);
                break;
            case "treeSpirit":
                e = new TreeSpirit(p, pos.x, pos.y);
                break;
            case "treeGiant":
                e = new TreeGiant(p, pos.x, pos.y);
                break;
            case "snake":
                e = new Snake(p, pos.x, pos.y);
                break;
            case "littleWorm":
                e = new LittleWorm(p, pos.x, pos.y);
                break;
            case "butterfly":
                e = new Butterfly(p, pos.x, pos.y);
                break;
        }
        return e;
    }

    static class Spawn {

        String enemyName;
        int weight;

        Spawn(String enemyName, int weight) {
            this.enemyName = enemyName;
            this.weight = weight;
        }
    }
}
