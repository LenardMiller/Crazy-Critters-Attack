package main.gui.inGame;

import main.gui.guiObjects.buttons.PlayButton;
import main.levelStructure.Wave;
import processing.core.PApplet;

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
        for (int i = currentWave; i < Math.min(currentWave + 8, levels[currentLevel].waves.length); i++) {
            waveCards[i].display(125 * (i - currentWave), i);
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
        playButton.update();
    }

    public void build() {
        playButton = new PlayButton(P,-100,0,"null",true);
    }
}
