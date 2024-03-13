package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static java.lang.Math.max;
import static main.misc.Utilities.incrementByTo;

public class FadeSoundLoop {

    private static final float MINIMUM_VOLUME = 0.01f;

    private final int autoStopTime;
    private final float increment;
    private final SoundFile soundFile;

    private int timer;
    private float volume;

    public float targetVolume;

    /**
     * A constantly playing loop that fades to audible then back to inaudible.
     * @param p the PApplet
     * @param name identifier
     * @param autoStopTime how long it will run before automatically stopping
     */
    public FadeSoundLoop(PApplet p, String name, int autoStopTime, float increment) {
        soundFile = new SoundFile(p, "sounds/loops/" + name + ".wav");
        this.autoStopTime = autoStopTime;
        soundFile.loop(1, 0.001f);
        targetVolume = 0.001f;
        this.increment = increment;
        //never goes to 0 because that prints errors for some reason :/
    }

    public FadeSoundLoop(PApplet p, String name, int autoStopTime) {
        this(p, name, autoStopTime, 0.05f);
    }

    public FadeSoundLoop(PApplet p, String name) {
        this(p, name, -1, 0.05f);
    }

    public void update() {
        volume = incrementByTo(volume, increment, targetVolume);
        if (volume > MINIMUM_VOLUME && !soundFile.isPlaying()) soundFile.loop(1, volume);
        soundFile.amp(volume);
        if (timer > autoStopTime && autoStopTime != -1) targetVolume = MINIMUM_VOLUME;
        timer++;
        if (volume <= MINIMUM_VOLUME) soundFile.stop();
    }

    /** @param targetVolume will slowly increment in volume to this */
    public void setTargetVolume(float targetVolume) {
        targetVolume = max(targetVolume, 0.01f);
        this.targetVolume = targetVolume;
        timer = 0;
    }
}
