package main.gui.inGame;

import main.gui.guiObjects.buttons.PlayButton;
import main.levelStructure.Wave;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.Main.waveStack;

public class WaveStack {

    public static final Color PANEL_COLOR = new Color(0x2F2F2F);

    private final PApplet P;
    private final WaveCard[] waveCards;
    private final PImage progressBar;

    public PlayButton playButton;

    public WaveStack(PApplet p) {
        this.P = p;

        waveCards = new WaveCard[levels[currentLevel].waves.length];
        for (int i = 0; i < waveCards.length; i++) {
            waveCards[i] = levels[currentLevel].waves[i].getWaveCard();
        }

        progressBar = staticSprites.get("waveProgressIc");

        build();
    }

    public void display() {
        P.fill(PANEL_COLOR.getRGB());
        P.rect(-199, 0, 199, 36);

        int currentWave = levels[currentLevel].currentWave;
        for (int i = Math.max(currentWave - 1, 0); i < Math.min(currentWave + 8, waveCards.length); i++) {
            waveCards[i].display(i + 1, waveCards.length);
        }
        playButton.display();

        progressBar(
                currentWave == levels[currentLevel].waves.length ?
                        1 : levels[currentLevel].waves[currentWave].getProgress(),
                -200, 125);

        P.strokeWeight(1);
        P.noStroke();
    }

    private void progressBar(float progress, int x, int y) {
        P.image(progressBar, x, y);
        P.fill(new Color(0x4b5e26).getRGB());
        int width = 188;
        int height = 20;
        int innerX = x + 6;
        int innerY = y + 6;
        int sep = 3;
        int count = 6;
        if (debug) P.text(
                nf(progress, 1, 3) + ", " + nf(((float) count * progress) - 1, 1, 3),
                innerX + 100, innerY);
        float blockWidth = width / (float) count - sep + sep / (float) count;
        for (int i = 0; i <= ((float) count * progress) - 1; i++) {
            P.rect(innerX + i * (blockWidth + sep), innerY, blockWidth, height);
        }
    }

    public void update() {
        if (paused) return;

        playButton.update();

        int currentWaveNum = levels[currentLevel].currentWave;
        WaveCard lastWave = currentWaveNum > 0 ? waveCards[currentWaveNum - 1] : null;

        //last wave
        if (lastWave != null)
            lastWave.position.x = Math.max(lastWave.position.x - 10, -450);

        if (currentWaveNum >= waveCards.length) return;

        WaveCard currentWave = waveCards[currentWaveNum];

        //current wave
        if (lastWave == null || lastWave.position.x <= -400) {
            currentWave.slide(157);
        }

        //future waves
        for (int i = currentWaveNum + 1; i < Math.min(currentWaveNum + 8, waveCards.length); i++) {
            waveCards[i].slide(waveCards[i-1].position.y + 125);
        }
    }

    public void build() {
        playButton = new PlayButton(P,-100,0,"null",true);
    }

    public void presetWaveCards() {
        int currentWaveNum = levels[currentLevel].currentWave;
        for (int i = currentWaveNum; i < Math.min(currentWaveNum + 8, waveCards.length); i++) {
            waveCards[i].preset(
                    new PVector(-200, 157 + 125 * (i - currentWaveNum)),
                    i == currentWaveNum);
        }
    }
}
