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

    private final PApplet p;
    private final PFont font;

    private int progress;

    public LoadingGui(PApplet p, PFont font) {
        this.p = p;
        this.font = font;
    }

    public void update() {
        switch (progress) {
            case 0:
                Main.h1 = p.createFont("fonts/Rubik-Regular.ttf", 30, true);
                Main.h2 = p.createFont("fonts/Rubik-Regular.ttf", 24, true);
                Main.h3 = p.createFont("fonts/Rubik-Regular.ttf", 21, true);
                Main.h4 = p.createFont("fonts/Rubik-Regular.ttf", 18, true);
                Main.pg = p.createFont("fonts/Rubik-Light.ttf", 12, true);
                Main.monoLarge = p.createFont("fonts/IBMPlexMono-Medium.ttf", 18, true);
                Main.monoMedium = p.createFont("fonts/IBMPlexMono-Medium.ttf", 16, true);
                Main.monoSmall = p.createFont("fonts/IBMPlexMono-Medium.ttf", 14, true);
                break;
            case 1:
                loadGui(p);
                break;
            case 2:
                loadEnemies(p);
                break;
            case 3:
                loadMachines(p);
                break;
            case 4:
                loadParticles(p);
                break;
            case 5:
                loadProjectiles(p);
                break;
            case 6:
                loadTiles(p);
                break;
            case 7:
                loadTurrets(p);
                break;
            case 8:
                loadWalls(p);
                break;
            case 9:
                loadSounds(p);
                break;
            case 10:
                Main.levelSelectGui = new LevelSelectGui(p);
                Main.settingsGui = new SettingsGui(p);
                Main.titleGui = new TitleGui(p);
                Main.game = new Game(p);
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

    public void display() {
        p.fill(255);
        p.textFont(font);
        p.textAlign(PConstants.CENTER);
        p.text("Loading...", Utilities.getCenter(p).x, Utilities.getCenter(p).y);

        p.strokeWeight(10);
        p.stroke(255);
        float lineEnd = PApplet.map(progress,
                0, MAX_PROGRESS,
                LINE_EDGE_BUFFER, p.width - LINE_EDGE_BUFFER);
        p.line(LINE_EDGE_BUFFER, Utilities.getCenter(p).y + 200, lineEnd, Utilities.getCenter(p).y + 200);
        p.strokeWeight(1);
    }
}
