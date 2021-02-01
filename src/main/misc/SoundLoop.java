package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class SoundLoop {

    private final PApplet P;

    private float speed;
    private float volume;
    private int endTime;

    private final int LENGTH;
    private final SoundFile START;
    private final SoundFile LOOP;
    private final SoundFile END;

    public int state;

    public SoundLoop(PApplet p, String path, int length) {
        this.P = p;

        START = new SoundFile(p, path + "Start.wav");
        LOOP = new SoundFile(p, path + "Loop.wav");
        END = new SoundFile(p, path + "End.wav");
        this.LENGTH = length;

        state = 0; //not playing, starting, looping, ending
    }

    private void reset() {
        START.stop();
        LOOP.stop();
        END.stop();
    }

    public void startLoop(float speed, float volume) {
        if (state == 0) {
            this.speed = speed;
            this.volume = volume;
            reset();
            state = 1;
            endTime = P.frameCount + LENGTH;
            START.play(speed, volume);
        }
    }

    private void midLoop() {
        reset();
        state = 2;
        LOOP.loop(speed, volume);
    }

    public void continueLoop() {
        if (state == 1 && P.frameCount >= endTime) midLoop();
        if (state == 3 && P.frameCount >= endTime) endLoop();
    }

    public void stopLoop() {
        if (state > 0) state = 3;
    }

    private void endLoop() {
        reset();
        END.play(speed, volume);
        state = 0;
    }
}
