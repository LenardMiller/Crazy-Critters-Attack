package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.misc.Utilities.playSoundRandomSpeed;

public class SoundWithAlts {

    private final PApplet P;
    private final SoundFile[] SOUNDS;

    public SoundWithAlts(PApplet p, String name, int count) {
        P = p;
        SOUNDS = new SoundFile[count];
        for (int i = 0; i < count; i++) {
            SOUNDS[i] = new SoundFile(P, "sounds/withAlts/" + name + "/" + PApplet.nf(i, 3) + ".wav");
        }
    }

    public void playRandom(float volume) {
        playSoundRandomSpeed(P, SOUNDS[(int) P.random(SOUNDS.length)], volume);
    }
}
