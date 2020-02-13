package main.levelStructure;

import processing.core.PApplet;

public class Level {

    private PApplet p;
    public Wave[] waves;
    public int currentWave;

    public Level(PApplet p, Wave[] waves) {
        this.p = p;
        this.waves = waves;
        currentWave = 0;
    }

    public void main() {
        if (currentWave < waves.length) { //temp, replace with win condition
            Wave wave = waves[currentWave];
            if (p.frameCount >= wave.endTime) {
                currentWave++;
                if (currentWave < waves.length) {
                    wave = waves[currentWave];
                    wave.endTime = p.frameCount + wave.length;
                }
            } else wave.spawnEnemies();
        }
    }
}
