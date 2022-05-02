package main.gui.notInGame;

import main.Game;
import main.Main;
import main.gui.SettingsGui;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import static main.Main.transition;
import static main.misc.SpriteLoader.*;
import static main.sound.SoundLoader.loadSounds;

public class LoadingGui {

    private static final int MAX_PROGRESS = 11;
    private static final int LINE_EDGE_BUFFER = 400;

    private final PApplet P;
    private final PFont FONT;

    private int progress;

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
                Main.game = new Game(P);
                break;
            case 11:
                //buffer
                break;
            default:
                System.out.println("MAX_PROGRESS is too large");
        }

        progress = Math.min(progress + 1, MAX_PROGRESS);
        if (progress == MAX_PROGRESS) transition(Main.Screen.Title, new PVector(0, -1));
    }

    private void display() {
        P.fill(255);
        P.textFont(FONT);
        P.textAlign(PConstants.CENTER);
        P.text("Loading...", Utilities.getCenter(P).x, Utilities.getCenter(P).y);

        P.strokeWeight(10);
        P.stroke(255);
        float lineEnd = PApplet.map(progress,
                0, MAX_PROGRESS,
                LINE_EDGE_BUFFER, P.width - LINE_EDGE_BUFFER);
        P.line(LINE_EDGE_BUFFER, Utilities.getCenter(P).y + 200, lineEnd, Utilities.getCenter(P).y + 200);
        P.strokeWeight(1);
    }
}
