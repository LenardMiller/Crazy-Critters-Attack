package main.levelStructure;

import main.misc.Polluter;
import main.misc.Saver;
import processing.core.PApplet;

import java.util.Objects;

import static main.Main.*;

public class Level {

    public Wave[] waves;
    public int currentWave;
    public int startWave;
    public String layout;
    public int startingCash;
    public int reward;
    public String groundType;
    public Polluter polluter;
    public String lastPolluterName;

    private final PApplet P;

    public Level(PApplet p, Wave[] waves, String layout, int startingCash, int reward, String groundType) {
        this.P = p;
        this.waves = waves;
        this.layout = layout;
        this.startingCash = startingCash;
        this.reward = reward;
        this.groundType = groundType;
        currentWave = 0;
        startWave = 0;
    }

    public void update() {
        if (currentWave < waves.length) {
            Wave wave = waves[currentWave];
            if (wave.polluter != null) {
                if (polluter != null && !Objects.equals(wave.polluter.NAME, polluter.NAME)) lastPolluterName = polluter.NAME;
                polluter = wave.polluter;
            }
            if (wave.groundType != null) groundType = wave.groundType;
            if (wave.lengthTimer > wave.length) setWave(currentWave + 1);
            else if (!paused && alive) {
                wave.spawnEnemies();
                if (polluter != null) polluter.update();
            }
            if (wave.spawnLengthTimer > wave.spawnLength && enemies.size() == 0 && !paused && alive) {
                wave.lengthTimer += wave.length / 500;
            }
        } else if (enemies.size() == 0) { //win condition
            if (!won) paused = true; //prevent stuck on pause
            Saver.wipe();
            won = true;
        }
    }

    public void advance() {
        if (!canBeSkipped()) return;
        levels[currentLevel].setWave(levels[currentLevel].currentWave + 1);
    }

    public void setWave(int waveNum) {
        Wave wave = waves[currentWave];
        wave.end();
        currentWave = waveNum;
    }

    public boolean canBeSkipped() {
        if (currentWave >= waves.length - 1) return false;
        return !waves[currentWave].unskippable && waves[currentWave].getProgress() == 1;
    }
}
