package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class StartStopSoundLoop {

    private final PApplet P;

    private float speed;
    private float volume;
    private int endTime;

    private final int LENGTH;
    private final SoundFile START;
    private final SoundFile LOOP;
    private final SoundFile END;

    public final boolean AUTO_STOP;

    /**
     * not playing, starting, looping, ending
     */
    public int state;

    /**
     * A loop that contains a starting sound, a looping sound, and an ending sound.
     * @param p the PApplet
     * @param name identifier
     * @param length how long it is in frames, all files must be this length
     * @param autoStop whether or not it should end on its own after a while.
     */
    public StartStopSoundLoop(PApplet p, String name, int length, boolean autoStop) {
        this.P = p;

        START = new SoundFile(p, "sounds/loops/" + name + "start.wav");
        LOOP = new SoundFile(p, "sounds/loops/" + name + "loop.wav");
        END = new SoundFile(p, "sounds/loops/" + name + "end.wav");
        LENGTH = length;
        AUTO_STOP = autoStop;

        state = 0;
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
        if (state == 2 && P.frameCount >= endTime && AUTO_STOP) state = 3;
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
