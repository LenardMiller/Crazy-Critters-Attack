package main.levelStructure;

import processing.core.PApplet;

import static main.Main.*;

public class Level {

    private PApplet p;
    public Wave[] waves;
    public int currentWave;
    public int startWave;

    public Level(PApplet p, Wave[] waves) {
        this.p = p;
        this.waves = waves;
        currentWave = 0;
        startWave = 0;
    }

    public void main() {
        if (currentWave < waves.length) { //temp, replace with win condition
            Wave wave = waves[currentWave];
            if (p.frameCount >= wave.endTimer) {
                currentWave++;
                if (currentWave < waves.length) {
                    wave = waves[currentWave];
                    wave.init();
                }
            } else wave.spawnEnemies();
            if (p.frameCount >= wave.waitTimer && enemies.size() == 0) {
                wave.endTimer -= wave.length / 500;
            }
        }
    }

    public void display() {
        float playY = 0;
        for (int i = currentWave-1; i <= currentWave+6; i++) {
            if (i < waves.length && i > -1 && currentWave < waves.length) {
                Wave wave = waves[i];
                Wave current = waves[currentWave];
                float y = (125*(i-currentWave));
                float y2 = 125*(((current.endTimer - p.frameCount)+1)/(float)current.length);
                if (playingLevel) y += y2;
                else y+= 125;
                wave.display(212 + y, i+1);
                if (i == startWave) playY = y;
            } else if (currentWave == waves.length) {
                waves[currentWave-1].display(212, currentWave);
            }
        }
        p.image(spritesH.get("currentLineIc"),900,212+125-3);
        p.tint(0,60);
        p.image(spritesH.get("currentLineIc"),900+1,212+125-3+1);
        p.tint(255);
        playButton.display((int)playY);
    }
}