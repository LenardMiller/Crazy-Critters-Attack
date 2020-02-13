package main.levelStructure;

import processing.core.PApplet;

public class Level {

    private PApplet p;
    private Wave[] waves;
    private int currentWave;

    public Level(PApplet p, Wave[] waves) {
        this.p = p;
        this.waves = waves;
        currentWave = 0;
    }

    public void main() {
        if (currentWave < waves.length) { //temp
            Wave wave = waves[currentWave];
            if (p.frameCount > wave.endTime) currentWave++;
            else wave.spawnEnemies();
        }
    }
}
