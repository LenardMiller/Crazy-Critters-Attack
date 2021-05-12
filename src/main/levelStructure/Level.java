package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import static main.Main.*;

public class Level {

    private final PApplet P;

    public Wave[] waves;
    public int currentWave;
    public int startWave;
    public String layout;
    public int startingCash;
    public int reward;
    public String groundType;
    private Polluter polluter;

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
            if (wave.polluter != null) polluter = wave.polluter;
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
        if (currentWave >= waves.length) return;
        Wave cw = waves[currentWave];
        if (cw.spawns.size() > 0) return;
        if (cw.unskippable) return;
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
//        P.tint(0,60);
//        P.image(staticSprites.get("currentLineIc"),891,212+125-1);
//        P.tint(255);
//        P.image(staticSprites.get("currentLineIc"),891-1,212+125-1-1);
        P.strokeWeight(10);
        P.stroke(100, 0, 0);
        P.line(BOARD_WIDTH, 336, BOARD_WIDTH + 200, 336);
        P.strokeWeight(4);
        P.stroke(255, 0, 0);
        P.line(BOARD_WIDTH - 7, 336, BOARD_WIDTH + 200, 336);
        P.strokeWeight(1);
        P.noStroke();
    }
}
