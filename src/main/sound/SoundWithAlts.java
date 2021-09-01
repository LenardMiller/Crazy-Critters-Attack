package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class SoundWithAlts {

    private final PApplet P;
    private final SoundFile[] SOUNDS;

    /**
     * A collection of related sound files that can be shuffled
     * @param p the PApplet
     * @param name identifier
     * @param count how many alts there are
     */
    public SoundWithAlts(PApplet p, String name, int count) {
        P = p;
        SOUNDS = new SoundFile[count];
        for (int i = 0; i < count; i++) {
            SOUNDS[i] = new SoundFile(P, "sounds/withAlts/" + name + "/" + PApplet.nf(i, 3) + ".wav");
        }
    }

    /**
     * Plays a random song from the collection with random pitch shifting.
     * @param volume play at this amp
     */
    public void playRandomWithRandomSpeed(float volume) {
        playSoundRandomSpeed(P, SOUNDS[(int) P.random(SOUNDS.length)], volume);
    }
}
