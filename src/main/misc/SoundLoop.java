package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class SoundLoop {

    private PApplet p;

    private float speed;
    private float volume;
    private int length;
    private int endTime;
    private SoundFile start;
    private SoundFile loop;
    private SoundFile end;

    public int state;

    public SoundLoop(PApplet p, String path, int length) {
        this.p = p;

        start = new SoundFile(p, path + "Start.wav");
        loop = new SoundFile(p, path + "Loop.wav");
        end = new SoundFile(p, path + "End.wav");
        this.length = length;

        state = 0; //not playing, starting, looping, ending
    }

    private void reset() {
        start.stop();
        loop.stop();
        end.stop();
    }

    public void startLoop(float speed, float volume) {
        if (state == 0) {
            this.speed = speed;
            this.volume = volume;
            reset();
            state = 1;
            endTime = p.frameCount + length;
            start.play(speed, volume);
        }
    }

    private void midLoop() {
        reset();
        state = 2;
        loop.loop(speed, volume);
    }

    public void continueLoop() {
        if (state == 1 && p.frameCount >= endTime) midLoop();
        if (state == 3 && p.frameCount >= endTime) endLoop();
    }

    public void stopLoop() {
        if (state > 0) state = 3;
    }

    private void endLoop() {
        reset();
        end.play(speed, volume);
        state = 0;
    }
}
