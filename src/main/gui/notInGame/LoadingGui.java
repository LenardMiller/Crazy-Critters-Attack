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

                //TEMP
                resourceLoader.loadSprites();
                break;
            case 1:
//                profiler.startProfilingSingle("loading: gui sprites");
//                loadGui(p);
//                profiler.finishProfiling("loading: gui sprites");
                break;
            case 2:
//                profiler.startProfilingSingle("loading: enemy sprites");
//                loadEnemies(p);
//                profiler.finishProfiling("loading: enemy sprites");
                break;
            case 3:
//                profiler.startProfilingSingle("loading: machine sprites");
//                loadMachines(p);
//                profiler.finishProfiling("loading: machine sprites");
                break;
            case 4:
//                profiler.startProfilingSingle("loading: particle sprites");
//                loadParticles(p);
//                profiler.finishProfiling("loading: particle sprites");
                break;
            case 5:
//                profiler.startProfilingSingle("loading: projectile sprites");
//                loadProjectiles(p);
//                profiler.finishProfiling("loading: projectile sprites");
                break;
            case 6:
//                profiler.startProfilingSingle("loading: tile sprites");
//                loadTiles(p);
//                profiler.finishProfiling("loading: tile sprites");
                break;
            case 7:
//                profiler.startProfilingSingle("loading: turret sprites");
//                loadTurrets(p);
//                profiler.finishProfiling("loading: turret sprites");
                break;
            case 8:
                profiler.startProfilingSingle("loading: wall sprites");
                loadWalls(p);
                profiler.finishProfiling("loading: wall sprites");
                break;
            case 9:
                profiler.startProfilingSingle("loading: sounds");
                loadSounds(p);
                profiler.finishProfiling("loading: sounds");
                break;
            case 10:
                profiler.startProfilingSingle("loading: gui & game");
                Main.levelSelectGui = new LevelSelectGui(p);
                Main.settingsGui = new SettingsGui(p);
                Main.titleGui = new TitleGui(p);
                Main.game = new Game(p);
                profiler.finishProfiling("loading: gui & game");
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
