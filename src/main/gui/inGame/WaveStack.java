package main.gui.inGame;

import main.gui.guiObjects.buttons.PlayButton;
import processing.core.PApplet;

import java.awt.*;

import static main.Main.currentLevel;
import static main.Main.levels;

public class WaveStack {

    public static final Color PANEL_COLOR = new Color(0x2F2F2F);

    private final PApplet P;

    public PlayButton playButton;

    public WaveStack(PApplet p) {
        this.P = p;
        build();
    }

    public void display() {
        P.fill(PANEL_COLOR.getRGB());
        P.rect(-199, 0, 199, 36);
        levels[currentLevel].display();
    }

    public void update() {
        playButton.update();
    }

    public void build() {
        playButton = new PlayButton(P,-100,0,"null",true);
    }
}
