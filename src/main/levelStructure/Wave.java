package main.levelStructure;

import main.enemies.*;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static main.Main.*;
import static main.misc.Utilities.*;

public class Wave {

    private final PApplet P;
    private final Color FILL_COLOR;
    private final Color ACCENT_COLOR;
    private final Color TEXT_COLOR;
    private final String TITLE;
    final int SPAWN_LENGTH;
    public final int LENGTH;

    private int betweenSpawns;
    /**
     * Time until wave end
     */
    public int lengthTimer;
    /**
     * Time until next spawn
     */
    private int betweenSpawnTimer;
    /**
     * Time until stop spawning
     */
    int spawnLengthTimer;

    public ArrayList<String> spawns;

    public Wave(PApplet p, int length, int spawnLength, Color fillColor, Color accentColor, Color textColor, String title) {
        P = p;
        LENGTH = secondsToFrames(length);
        SPAWN_LENGTH = secondsToFrames(spawnLength);
        FILL_COLOR = fillColor;
        ACCENT_COLOR = accentColor;
        TEXT_COLOR = textColor;
        TITLE = title;
        spawns = new ArrayList<>();
    }

    public void end() {
        for (Tower tower : towers) {
            if (tower instanceof Turret) tower.heal(1);
            else tower.heal(0.2f);
        }
        playSound(sounds.get("waveEnd"), 1, 1);
        money += levels[currentLevel].reward;
    }

    /**
     * Calculates the time between spawns.
     */
    void load() {
        betweenSpawns = SPAWN_LENGTH / spawns.size();
    }

    void addSpawns(String enemy, int count) {
        for (int i = 0; i < count; i++) spawns.add(enemy);
        Collections.shuffle(spawns);
    }

    public void spawnEnemies() {
        betweenSpawnTimer++;
        spawnLengthTimer++;
        lengthTimer++;
        if (spawns.size() > 0 && betweenSpawnTimer >= betweenSpawns) {
            betweenSpawnTimer = 0;
            String s = spawns.get(spawns.size() - 1);
            spawns.remove(spawns.size() - 1);
            PVector pos;
            pos = randomSpawnPosition(P);
            enemies.add(getEnemy(s, pos));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
    }

    /**
     * Draws wave icons.
     * @param y displacement of icons
     * @param id current wave id
     */
    public void display(int y, int id) {
        P.tint(FILL_COLOR.getRGB());
        P.image(staticSprites.get("wavePrimaryIc"), 890, y);
        P.tint(ACCENT_COLOR.getRGB());
        P.image(staticSprites.get("waveSecondaryIc"), 890, y);
        P.tint(255);

        P.fill(TEXT_COLOR.getRGB());
        P.textAlign(CENTER);
        P.textFont(largeFont);
        P.text(TITLE, 1000, y + 110);
        P.textFont(veryLargeFont);
        P.text(id, 1000, y + 70);
    }

    private Enemy getEnemy(String name, PVector pos) {
        Enemy e = null;

        switch (name) {
            case "smolBug":
                e = new SmolBug(P, pos.x, pos.y);
                break;
            case "midBug":
                e = new MidBug(P, pos.x, pos.y);
                break;
            case "bigBug":
                e = new BigBug(P, pos.x, pos.y);
                break;
            case "treeSprite":
                e = new TreeSprite(P, pos.x, pos.y);
                break;
            case "treeSpirit":
                e = new TreeSpirit(P, pos.x, pos.y);
                break;
            case "treeGiant":
                e = new TreeGiant(P, pos.x, pos.y);
                break;
            case "snake":
                e = new Snake(P, pos.x, pos.y);
                break;
            case "littleWorm":
                e = new LittleWorm(P, pos.x, pos.y);
                break;
            case "butterfly":
                e = new Butterfly(P, pos.x, pos.y);
                break;
            case "scorpion":
                e = new Scorpion(P, pos.x, pos.y);
                break;
            case "sidewinder":
                e = new Sidewinder(P, pos.x, pos.y);
                break;
            case "emperor":
                e = new Emperor(P, pos.x, pos.y);
                break;
            case "midWorm":
                e = new MidWorm(P, pos.x, pos.y);
                break;
            case "bigWorm":
                e = new BigWorm(P, pos.x, pos.y);
                break;
            case "albinoBug":
                e = new AlbinoBug(P, pos.x, pos.y);
                break;
            case "bigAlbinoBug":
                e = new BigAlbinoBug(P, pos.x, pos.y);
                break;
            case "albinoButterfly":
                e = new AlbinoButterfly(P, pos.x, pos.y);
                break;
            case "smallGolem":
                e = new SmallGolem(P, pos.x, pos.y);
                break;
            case "midGolem":
                e = new Golem(P, pos.x, pos.y);
                break;
            case "bigGolem":
                e = new GiantGolem(P, pos.x, pos.y);
                break;
            case "bat":
                e = new Bat(P, pos.x, pos.y);
                break;
            case "bigBat":
                e = new GiantBat(P, pos.x, pos.y);
                break;
            case "wtf":
                e = new Wtf(P, pos.x, pos.y);
                break;
        }
        return e;
    }
}
