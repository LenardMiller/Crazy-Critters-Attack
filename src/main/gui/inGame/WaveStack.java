package main.gui.inGame;

import main.gui.guiObjects.buttons.PlayButton;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.Main.waveStack;

public class WaveStack {

    public static final Color PANEL_COLOR = new Color(0x2F2F2F);

    private final PApplet P;
    private final WaveCard[] waveCards;

    public PlayButton playButton;

    public WaveStack(PApplet p) {
        this.P = p;

        waveCards = new WaveCard[levels[currentLevel].waves.length];
        for (int i = 0; i < waveCards.length; i++) {
            waveCards[i] = levels[currentLevel].waves[i].getWaveCard();
        }

        build();
    }

    public void display() {
        P.fill(PANEL_COLOR.getRGB());
        P.rect(-199, 0, 199, 36);

        int currentWave = levels[currentLevel].currentWave;
        for (int i = Math.max(currentWave - 1, 0); i < Math.min(currentWave + 8, waveCards.length); i++) {
            waveCards[i].display(i + 1, waveCards.length);
        }
        waveStack.playButton.display();

        //current line
        P.strokeWeight(10);
        P.stroke(100, 0, 0);
        P.line(-200, 250, 0, 250);
        P.strokeWeight(4);
        P.stroke(255, 0, 0);
        P.line(-200, 250, 0, 250);

        P.strokeWeight(1);
        P.noStroke();
    }

    public void update() {
        if (paused) return;

        playButton.update();

        int currentWaveNum = levels[currentLevel].currentWave;
        WaveCard lastWave = currentWaveNum > 0 ? waveCards[currentWaveNum - 1] : null;

        //last wave
        if (lastWave != null)
            lastWave.position.x = Math.max(lastWave.position.x - 10, -450);

        if (currentWaveNum > waveCards.length) return;

        WaveCard currentWave = waveCards[currentWaveNum];

        //current wave
        if (lastWave == null || lastWave.position.x <= -400) {
            currentWave.slide(isPlaying ? 125 : 250);
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
        System.out.println(currentWaveNum);
//        waveCards[currentWaveNum].position = new PVector(-450, 125);
        for (int i = currentWaveNum; i < Math.min(currentWaveNum + 8, waveCards.length); i++) {
            System.out.println(i);
            waveCards[i].position = new PVector(-200, 125 + 125 * (i - currentWaveNum));
        }
    }
}
