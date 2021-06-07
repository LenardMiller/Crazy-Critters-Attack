package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class SoundUtlities {

    /**
     * Stops and starts playing the given sound.
     * @param sound sound to play, won't crash if null
     * @param speed speed and to pitch to play at, 1 for normal
     * @param volume volume to play at, 1 for normal
     */
    public static void playSound(SoundFile sound, float speed, float volume) {
        if (sound != null) {
            sound.stop();
            sound.play(speed, volume);
        }
    }

    /**
     * Plays the given sound with a 20% pitch and speed variation.
     * @param p the PApplet
     * @param sound sound to play, won't crash if null
     * @param volume volume to play at, 1 for normal
     */
    public static void playSoundRandomSpeed(PApplet p, SoundFile sound, float volume) {
        playSound(sound, p.random(0.8f, 1.2f), volume);
    }
}
