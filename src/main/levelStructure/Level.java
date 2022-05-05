package main.levelStructure;

import main.misc.Polluter;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
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

    public void main() {
        if (currentWave < waves.length) {
            Wave wave = waves[currentWave];
            if (wave.polluter != null) {
                if (polluter != null && !Objects.equals(wave.polluter.NAME, polluter.NAME)) lastPolluterName = polluter.NAME;
                polluter = wave.polluter;
            }
            if (wave.groundType != null) groundType = wave.groundType;
            if (wave.lengthTimer > wave.LENGTH) setWave(currentWave + 1);
            else if (!paused && alive) {
                wave.spawnEnemies();
                if (polluter != null) polluter.main();
            }
            if (wave.spawnLengthTimer > wave.SPAWN_LENGTH && enemies.size() == 0 && !paused && alive) {
                wave.lengthTimer += wave.LENGTH / 500;
            }
        } else if (enemies.size() == 0) { //win condition
            if (!won) paused = true; //prevent stuck on pause
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

    public void display() {
        float playY = 0;
        for (int i = currentWave-3; i <= currentWave+6; i++) {
            if (i < waves.length && i > -1) {
                Wave wave = waves[i];
                Wave current = null;
                if (currentWave < waves.length) current = waves[currentWave];
                int y = (125*(i-currentWave));
                int y2 = 125;
                if (currentWave < waves.length) y2 = (int) (125*(((current.lengthTimer)+1)/(float)current.LENGTH));
                if (playingLevel) y -= y2 - 125;
                else y += 125;
                wave.display(212 + y, i+1);
                if (i == startWave) playY = y;
            } else if (currentWave == waves.length) {
                waves[currentWave-1].display(212, currentWave);
            }
        }
        inGameGui.playButton.display((int)playY);
        //current line
        P.strokeWeight(10);
        P.stroke(100, 0, 0);
        P.line(BOARD_WIDTH, 336, BOARD_WIDTH + 200, 336);
        P.strokeWeight(4);
        P.stroke(255, 0, 0);
        P.line(BOARD_WIDTH - 7, 336, BOARD_WIDTH + 200, 336);
        P.strokeWeight(1);
        P.noStroke();
        //skip text
        if (canBeSkipped()) Utilities.highlightedText(P, "[SPACE] to skip",
                new PVector(BOARD_WIDTH + 100, 325),
                new Color(0xFCFFFFFF, true), new Color(50, 50, 50, 230),
                mediumFont, PConstants.CENTER);
    }

    private boolean canBeSkipped() {
        if (currentWave >= waves.length - 1) return false;
        Wave cw = waves[currentWave];
        return !cw.unskippable && cw.spawns.size() == 0;
    }
}
