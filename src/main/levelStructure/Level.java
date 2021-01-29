package main.levelStructure;

import processing.core.PApplet;

import static main.Main.*;

public class Level {

    private PApplet p;
    public Wave[] waves;
    public int currentWave;
    public int startWave;
    public String layout;
    public int startingCash;
    public int reward;
    public String groundType;

    public Level(PApplet p, Wave[] waves, String layout, int startingCash, int reward, String groundType) {
        this.p = p;
        this.waves = waves;
        this.layout = layout;
        this.startingCash = startingCash;
        this.reward = reward;
        this.groundType = groundType;
        currentWave = 0;
        startWave = 0;
    }

    public void main() {
        if (currentWave < waves.length) { //temp, replace with win condition
            Wave wave = waves[currentWave];
            if (p.frameCount >= wave.endTimer) setWave(currentWave + 1);
            else wave.spawnEnemies();
            if (p.frameCount >= wave.waitTimer && enemies.size() == 0) {
                wave.endTimer -= wave.length / 500;
            }
        }
    }

    public void setWave(int waveNum) {
        Wave wave = waves[currentWave];
        wave.end();
        currentWave = waveNum;
        if (currentWave < waves.length) {
            wave = waves[currentWave];
            wave.init();
        }
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
                if (currentWave < waves.length) y2 = 125*(((current.endTimer - p.frameCount)+1)/(float)current.length);
                if (playingLevel) y += y2;
                else y += 125;
                wave.display(212 + y, i+1);
                if (i == startWave) playY = y;
            } else if (currentWave == waves.length) {
                waves[currentWave-1].display(212, currentWave);
            }
        }
        p.tint(0,60);
        p.image(spritesH.get("currentLineIc"),891,212+125-1);
        p.tint(255);
        p.image(spritesH.get("currentLineIc"),891-1,212+125-1-1);
        playButton.display((int)playY);
    }
}
