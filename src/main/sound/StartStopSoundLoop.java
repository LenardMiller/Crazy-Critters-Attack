package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class StartStopSoundLoop {

    private final PApplet P;

    private float speed;
    private float volume;
    private int endTime;

    private final int length;
    private final SoundFile start;
    private final SoundFile loop;
    private final SoundFile end;

    public final boolean autoStop;

    /**
     * not playing, starting, looping, ending
     */
    public int state;

    /**
     * A loop that contains a starting sound, a looping sound, and an ending sound.
     * @param p the PApplet
     * @param name identifier
     * @param length how long it is in frames, all files must be this length
     * @param autoStop whether it should end on its own after a while.
     */
    public StartStopSoundLoop(PApplet p, String name, int length, boolean autoStop) {
        this.P = p;

        start = new SoundFile(p, "sounds/loops/startstop/" + name + "start.wav");
        loop = new SoundFile(p, "sounds/loops/startstop/" + name + "loop.wav");
        end = new SoundFile(p, "sounds/loops/startstop/" + name + "end.wav");
        this.length = length;
        this.autoStop = autoStop;

        state = 0;
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
            endTime = P.frameCount + length;
            start.play(speed, volume);
        }
    }

    private void midLoop() {
        reset();
        state = 2;
        loop.loop(speed, volume);
    }

    public void continueLoop() {
        if (state == 1 && P.frameCount >= endTime) midLoop();
        if (state == 2 && P.frameCount >= endTime && autoStop) state = 3;
        if (state == 3 && P.frameCount >= endTime) endLoop();
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
