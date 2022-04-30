package main.gui.notInGame;

import main.Main;
import main.gui.SettingsGui;
import main.gui.notInGame.LevelSelectGui;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import static main.misc.SpriteLoader.*;
import static main.sound.SoundLoader.loadSounds;

public class LoadingGui {

    private static final int MAX_PROGRESS = 10;
    private static final int DARK_CHANGE = 6;

    private final PApplet P;
    private final PFont FONT;

    private int progress;
    private float darkLevel = 0;

    public LoadingGui(PApplet p, PFont font) {
        P = p;
        FONT = font;
    }

    public void main() {
        update();
        display();
    }

    private void update() {
        switch (progress) {
            case 0:
                Main.largeFont       = P.createFont("STHeitiSC-Light", 24, true);
                Main.mediumLargeFont = P.createFont("STHeitiSC-Light", 21, true);
                Main.mediumFont      = P.createFont("STHeitiSC-Light", 18, true);
                Main.smallFont       = P.createFont("STHeitiSC-Light", 12, true);
                break;
            case 1:
                loadGui(P);
                break;
            case 2:
                loadEnemies(P);
                break;
            case 3:
                loadMachines(P);
                break;
            case 4:
                loadParticles(P);
                break;
            case 5:
                loadProjectiles(P);
                break;
            case 6:
                loadTiles(P);
                break;
            case 7:
                loadTurrets(P);
                break;
            case 8:
                loadWalls(P);
                break;
            case 9:
                loadSounds(P);
                break;
            case 10:
                Main.levelSelectGui = new LevelSelectGui(P);
                Main.settingsGui = new SettingsGui(P);
                Main.titleGui = new TitleGui(P);
                break;
            default:
                System.out.println("MAX_PROGRESS is too large");
        }

        progress = Math.min(progress + 1, MAX_PROGRESS);
        if (progress == MAX_PROGRESS) {
            darkLevel += DARK_CHANGE;
            if (darkLevel > 255) Main.screen = 3;
        }
    }

    private void display() {
        P.fill(50);
//        P.rect(0, 0, Main.GRID_WIDTH, Main.BOARD_WIDTH);

        P.fill(255);
        P.textFont(FONT);
        P.textAlign(PConstants.CENTER);
        P.text("Loading...", Utilities.getCenter(P).x, Utilities.getCenter(P).y);

        P.strokeWeight(10);
        P.stroke(255);
        float lineEnd = PApplet.map(progress,
                0, MAX_PROGRESS,
                100, P.width - 100);
        P.line(100, Utilities.getCenter(P).y + 200, lineEnd, Utilities.getCenter(P).y + 200);
        P.strokeWeight(1);

        P.noStroke();
        P.fill(0, darkLevel);
        P.rect(0, 0, P.width, P.height);
    }
}
