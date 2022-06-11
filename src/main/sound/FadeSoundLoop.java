package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static java.lang.Math.max;
import static main.misc.Utilities.incrementByTo;

public class FadeSoundLoop {

    private final int minLength;
    private final SoundFile soundFile;

    private int timer;
    private float volume;

    public float targetVolume;

    /**
     * A constantly playing loop that fades to audible then back to inaudible.
     * @param p the PApplet
     * @param name identifier
     * @param minLength how long it will run before automatically stopping
     */
    public FadeSoundLoop(PApplet p, String name, int minLength) {
        soundFile = new SoundFile(p, "sounds/loops/" + name + ".wav");
        this.minLength = minLength;
        soundFile.loop(1, 0.001f);
        targetVolume = 0.001f;
        //never goes to 0 because that prints errors for some reason :/
    }

    public FadeSoundLoop(PApplet p, String name) {
        this(p, name, -1);
    }

    public void main() {
        volume = incrementByTo(volume, 0.05f, targetVolume);
        soundFile.amp(volume);
        if (timer > minLength && minLength != -1) {
            targetVolume = 0.01f;
        }
        timer++;
    }

    /** @param targetVolume will slowly increment in volume to this */
    public void setTargetVolume(float targetVolume) {
        targetVolume = max(targetVolume, 0.01f);
        this.targetVolume = targetVolume;
        timer = 0;
    }
}
