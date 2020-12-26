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
    public ArrayList<String> spawns;
    private Color primary;
    private Color secondary;
    private String title;

    //todo: pause functionality
    public Wave(PApplet p, int length, int spawnLength, Color primary, Color secondary, String title) {
        this.p = p;
        this.length = length;
        this.spawnLength = spawnLength;
        this.primary = primary;
        this.secondary = secondary;
        this.title = title;
        spawns = new ArrayList<>();
    }

    public void init() {
        endTimer = p.frameCount + length;
        waitTimer = p.frameCount + spawnLength;
    }

    public void end() {
        for (Tower tower : towers) if (tower.turret) tower.heal();
        soundsH.get("waveEnd").stop();
        soundsH.get("waveEnd").play(1, volume);
        money += levels[currentLevel].reward;
    }

    void load() {
        betweenSpawns = spawnLength / spawns.size();
        spawnTimer = p.frameCount + betweenSpawns;
    }

    void addSpawns(String enemy, int count) {
        for (int i = count; i > 0; i--) {
            spawns.add(enemy);
        }
    }

    public void spawnEnemies() {
        if (spawns.size() > 0 && spawnTimer <= p.frameCount && waitTimer >= p.frameCount) {
            spawnTimer = p.frameCount + betweenSpawns;
            String s = spawns.get(spawns.size() - 1);
            spawns.remove(spawns.size() - 1);
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

    private Enemy getEnemy(String name, PVector pos) {
        Enemy e = null;

        switch (name) {
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
            case "scorpion":
                e = new Scorpion(p, pos.x, pos.y);
                break;
            case "sidewinder":
                e = new Sidewinder(p, pos.x, pos.y);
                break;
            case "emperor":
                e = new Emperor(p, pos.x, pos.y);
                break;
            case "midWorm":
                e = new MidWorm(p, pos.x, pos.y);
                break;
            case "bigWorm":
                e = new BigWorm(p, pos.x, pos.y);
                break;
        }
        return e;
    }
}
