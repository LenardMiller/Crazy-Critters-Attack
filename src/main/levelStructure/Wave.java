package main.levelStructure;

import main.enemies.*;
import main.enemies.burrowingEnemies.*;
import main.enemies.flyingEnemies.*;
import main.enemies.shootingEnemies.*;
import main.gui.guiObjects.PopupText;
import main.misc.Polluter;
import main.misc.Saver;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.turrets.Booster;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static main.Main.*;
import static main.misc.Utilities.randomSpawnPosition;
import static main.misc.Utilities.secondsToFrames;
import static main.sound.SoundUtilities.playSound;

public class Wave {

    private final PApplet P;
    private final Color FILL_COLOR;
    private final Color ACCENT_COLOR;
    private final Color TEXT_COLOR;
    private final String TITLE;

    final int SPAWN_LENGTH;
    final int LENGTH;

    public Polluter polluter;
    public String groundType;
    public int setBetweenPollutesAtEnd;

    boolean unskippable;
    private int betweenSpawns;
    /**Time until wave end*/
    int lengthTimer;
    /**Time until next spawn*/
    private int betweenSpawnTimer;
    /**Time until stop spawning*/
    int spawnLengthTimer;

    ArrayList<String> spawns;

    boolean hasFlying;
    boolean hasBurrowing;
    boolean hasShooting;

    /**
     * A wave of enemies
     * @param p the PApplet
     * @param length how long it lasts in seconds
     * @param spawnLength how long it spawns enemies in seconds, must be less than length
     * @param fillColor main icon color
     * @param accentColor border color
     * @param textColor color of title and number
     * @param title main enemy of wave
     */
    public Wave(PApplet p, int length, int spawnLength, Color fillColor, Color accentColor, Color textColor, String title) {
        P = p;
        LENGTH = secondsToFrames(length);
        SPAWN_LENGTH = secondsToFrames(spawnLength);
        if (SPAWN_LENGTH >= LENGTH) {
            throw new RuntimeException("Wave spawn length should always be shorter than its total length!");
        }
        FILL_COLOR = fillColor;
        ACCENT_COLOR = accentColor;
        TEXT_COLOR = textColor;
        TITLE = title;
        spawns = new ArrayList<>();

        setBetweenPollutesAtEnd = -1;
    }

    public void end() {
        if (setBetweenPollutesAtEnd > -1) levels[currentLevel].polluter.setBetweenPollutes(setBetweenPollutesAtEnd);
        for (Tower tower : towers) {
            if (tower instanceof Turret) tower.heal(1);
            else if (!(tower instanceof IceWall)) tower.heal(0.35f);
        }
        machine.heal(0.05f);
        for (Tower tower : towers) {
            if (tower.name.equals("moneyBooster")) ((Booster) tower).giveMoney();
        }
        playSound(sounds.get("waveEnd"), 1, 1);
        int reward = (int) (levels[currentLevel].reward + levels[currentLevel].reward * 0.2f * enemies.size());
        money += reward;
        popupTexts.add(new PopupText(P, new PVector(BOARD_WIDTH / 2f, BOARD_HEIGHT / 2f), reward));
        Saver.save();
    }

    /** Calculates the time between spawns. */
    void load() {
        if (spawns.size() > 0) betweenSpawns = SPAWN_LENGTH / spawns.size();
    }

    void addSpawns(String enemy, int count) {
        if (getEnemy(enemy) instanceof FlyingEnemy) hasFlying = true;
        if (getEnemy(enemy) instanceof BurrowingEnemy) hasBurrowing = true;
        if (getEnemy(enemy) instanceof ShootingEnemy) hasShooting = true;
        for (int i = 0; i < count; i++) spawns.add(enemy);
        Collections.shuffle(spawns);
    }

    public void spawnEnemies() {
        betweenSpawnTimer++;
        spawnLengthTimer++;
        lengthTimer++;
//        System.out.println("spawnSize: " + spawns.size() + ", spawnLengthTimer: " + spawnLengthTimer +
//                ", betweenSpawnTimer: " + betweenSpawnTimer + ", betweenSpawns: " + betweenSpawns);
        if (spawns.size() > 0 && betweenSpawnTimer >= betweenSpawns) {
            betweenSpawnTimer = 0;
            String s = spawns.get(spawns.size() - 1);
            spawns.remove(spawns.size() - 1);
            PVector pos;
            pos = randomSpawnPosition(P);
            enemies.add(Enemy.get(P, s, pos));
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
        P.image(staticSprites.get("wavePrimaryIc"), -200, y);
        P.tint(ACCENT_COLOR.getRGB());
        P.image(staticSprites.get("waveSecondaryIc"), -200, y);
        P.tint(255);

        //title
        P.fill(TEXT_COLOR.getRGB(), 254);
        P.textAlign(CENTER);
        P.textFont(largeFont);
        P.text(TITLE, -100, y + 110);
        P.textFont(veryLargeFont);
        //number
        P.text(id, -100, y + 75);
        //enemy types
        int letterCount = 0;
        if (hasBurrowing) letterCount++;
        if (hasFlying) letterCount++;
        if (hasShooting) letterCount++;

        P.textFont(mediumLargeFont);
        StringBuilder enemyTypes = new StringBuilder();
        if (hasBurrowing) {
            if (letterCount > 1) enemyTypes.append(" B");
            else enemyTypes.append(" Burrowing");
        } if (hasFlying) {
            if (letterCount > 1) {
                if (hasBurrowing) enemyTypes.append(" &");
                enemyTypes.append(" F");
            }
            else enemyTypes.append(" Flying");
        } if (hasShooting) {
            if (letterCount > 1) enemyTypes.append(" & S");
            else enemyTypes.append(" Shooting");
        }
        P.text(enemyTypes.toString(), -100, y + 25);
    }

    private Enemy getEnemy(String name) {
        return Enemy.get(P, name, new PVector(0,0));
    }
}
