package main.gui.notInGame;

import main.Game;
import main.Main;
import main.gui.SettingsGui;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import static main.Main.*;

public class LoadingGui {

    private static final int MAX_PROGRESS = 5;
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
            case 1:
                profiler.startProfilingSingle("loading: fonts");
                Main.h1 = p.createFont("fonts/Rubik-Regular.ttf", 30, true);
                Main.h2 = p.createFont("fonts/Rubik-Regular.ttf", 24, true);
                Main.h3 = p.createFont("fonts/Rubik-Regular.ttf", 21, true);
                Main.h4 = p.createFont("fonts/Rubik-Regular.ttf", 18, true);
                Main.pg = p.createFont("fonts/Rubik-Light.ttf", 16, true);
                Main.monoHuge = p.createFont("fonts/IBMPlexMono-Medium.ttf", 24, true);
                Main.monoLarge = p.createFont("fonts/IBMPlexMono-Medium.ttf", 18, true);
                Main.monoMedium = p.createFont("fonts/IBMPlexMono-Medium.ttf", 16, true);
                Main.monoSmall = p.createFont("fonts/IBMPlexMono-Medium.ttf", 14, true);
                profiler.finishProfiling("loading: fonts");
                break;
            case 2:
                profiler.startProfilingSingle("loading: sprites");
                resourceLoader.loadSprites();
                profiler.finishProfiling("loading: sprites");
                break;
            case 3:
                profiler.startProfilingSingle("loading: sounds");
                resourceLoader.loadSounds();
                profiler.finishProfiling("loading: sounds");
                break;
            case 4:
                profiler.startProfilingSingle("loading: gui & game");
                Main.levelSelectGui = new LevelSelectGui(p);
                Main.settingsGui = new SettingsGui(p);
                Main.titleGui = new TitleGui(p);
                Main.game = new Game(p);
                profiler.finishProfiling("loading: gui & game");
                break;
            case 0, 5:
                // buffer
                break;
            default:
                System.err.println("MAX_PROGRESS is too large");
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
        p.line(LINE_EDGE_BUFFER, Utilities.getCenter(p).y + 200,
                lineEnd, Utilities.getCenter(p).y + 200);
        p.strokeWeight(1);
    }
}
