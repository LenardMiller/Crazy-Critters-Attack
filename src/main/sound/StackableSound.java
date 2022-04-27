package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class StackableSound {

    private final SoundFile[] soundFiles;
    private final PApplet p;

    private int currentSound;

    /**
     * A sound that can overlap count times before cutting itself off
     * @param p the PApplet
     * @param name filename, sound is found at sounds/stackables/{name}.wav
     * @param count number of times it can be overlapped
     */
    public StackableSound(PApplet p, String name, int count) {
        this.p = p;

        soundFiles = new SoundFile[count];
        for (int i = 0; i < count; i++) {
            soundFiles[i] = new SoundFile(p, "sounds/stackables/" + name + ".wav");
        }
    }

    public void playRandomSpeed(float volume) {
        play(p.random(0.8f, 1.2f), volume);
    }

    public void play(float speed, float volume) {
        SoundUtilities.playSound(soundFiles[currentSound], speed, volume);
        currentSound++;
        if (currentSound >= soundFiles.length) currentSound = 0;
    }
}
