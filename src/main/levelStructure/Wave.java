package main.levelStructure;

import main.enemies.*;
import main.enemies.burrowingEnemies.*;
import main.enemies.flyingEnemies.*;
import main.enemies.shootingEnemies.*;
import main.gui.guiObjects.PopupText;
import main.gui.inGame.WaveCard;
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

    private final WaveCard waveCard;

    private final PApplet p;

    final int spawnLength;
    final int length;

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
    public Wave(PApplet p, int length, int spawnLength, Color fillColor, Color accentColor, Color textColor, String[] title) {
        this.p = p;
        this.length = secondsToFrames(length);
        this.spawnLength = secondsToFrames(spawnLength);
        assert(spawnLength <= length);

        waveCard = new WaveCard(p, fillColor, accentColor, textColor, title);
        spawns = new ArrayList<>();

        setBetweenPollutesAtEnd = -1;
    }

    public float getProgress() {
        return Math.min(spawnLengthTimer / (float) spawnLength, 1);
    }

    public Wave(PApplet p, int length, int spawnLength, Color fillColor, Color accentColor, Color textColor, String title) {
        this(p, length, spawnLength, fillColor, accentColor, textColor, new String[]{title});
    }

    public WaveCard getWaveCard() {
        return waveCard;
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
        int reward = getReward();
        money += reward;
        popupTexts.add(new PopupText(p, new PVector(BOARD_WIDTH / 2f, BOARD_HEIGHT / 2f), reward));
        Saver.save();
    }

    public int getReward() {
        return (int) (levels[currentLevel].reward + levels[currentLevel].reward * 0.2f * enemies.size());
    }

    /** Calculates the time between spawns. */
    void load() {
        if (spawns.size() > 0) betweenSpawns = spawnLength / spawns.size();
    }

    void addSpawns(String enemy, int count) {
        waveCard.addEnemyType(getEnemy(enemy));
        for (int i = 0; i < count; i++) spawns.add(enemy);
        Collections.shuffle(spawns);
    }

    public void spawnEnemies() {
        betweenSpawnTimer++;
        spawnLengthTimer++;
        lengthTimer++;
        if (spawns.size() == 0 || betweenSpawnTimer < betweenSpawns) return;

        betweenSpawnTimer = 0;
        String s = spawns.get(spawns.size() - 1);
        spawns.remove(spawns.size() - 1);
        PVector pos;
        pos = randomSpawnPosition(p);
        enemies.add(Enemy.get(p, s, pos));
        enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
    }

    private Enemy getEnemy(String name) {
        return Enemy.get(p, name, new PVector(0,0));
    }
}
