package main.sound;

import processing.core.PApplet;

import static java.lang.Math.min;

public class MoveSoundLoop {

    private final FadeSoundLoop loop;
    private final float maxEnemyNum;

    private int count;

    public MoveSoundLoop(PApplet p, String name, float maxEnemyNum) {
        loop = new FadeSoundLoop(p, name);
        this.maxEnemyNum = maxEnemyNum;
    }

    public void main() {
        loop.setTargetVolume(min(1, count / maxEnemyNum));
        loop.main();
        count = 0;
    }

    public void increment() {
        count++;
    }
}
