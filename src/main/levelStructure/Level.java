package main.levelStructure;

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
            if (wave.lengthTimer > wave.LENGTH) setWave(currentWave + 1);
            else if (!paused && alive) wave.spawnEnemies();
            if (wave.spawnLengthTimer > wave.SPAWN_LENGTH && enemies.size() == 0 && !paused && alive) {
                wave.lengthTimer += wave.LENGTH / 500;
            }
        } else if (enemies.size() == 0) { //win condition
            if (!won) paused = true; //prevent stuck on pause
            won = true;
        }
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
                float y = (125*(i-currentWave));
                float y2 = 125;
                if (currentWave < waves.length) y2 = 125*(((current.lengthTimer)+1)/(float)current.LENGTH);
                if (playingLevel) y -= y2 - 125;
                else y += 125;
                wave.display(212 + y, i+1);
                if (i == startWave) playY = y;
            } else if (currentWave == waves.length) {
                waves[currentWave-1].display(212, currentWave);
            }
        }
        P.tint(0,60);
        P.image(spritesH.get("currentLineIc"),891,212+125-1);
        P.tint(255);
        P.image(spritesH.get("currentLineIc"),891-1,212+125-1-1);
        inGameGui.playButton.display((int)playY);
    }
}
