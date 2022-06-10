package main.sound;

import processing.core.PApplet;

import static java.lang.Math.min;

public class MoveSoundLoop {

    private final FadeSoundLoop loop;
    private final float enemyNum;

    private int count;

    public MoveSoundLoop(PApplet p, String name, float enemyNum) {
        loop = new FadeSoundLoop(p, name);
        this.enemyNum = enemyNum;
    }

    public void main() {
        loop.setTargetVolume(min(1, count / enemyNum));
        loop.main();
        count = 0;
    }

    public void increment() {
        count++;
    }
}
