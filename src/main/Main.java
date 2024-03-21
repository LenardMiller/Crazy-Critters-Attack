package main;

import main.buffs.Buff;
import main.projectiles.arcs.Arc;
import main.projectiles.Projectile;
import main.projectiles.shockwaves.Shockwave;
import main.enemies.Enemy;
import main.gui.*;
import main.gui.guiObjects.PopupText;
import main.gui.guiObjects.buttons.TileSelect;
import main.gui.guiObjects.buttons.TowerBuy;
import main.gui.inGame.*;
import main.gui.notInGame.LevelSelectGui;
import main.gui.notInGame.LoadingGui;
import main.gui.notInGame.TitleGui;
import main.levelStructure.*;
import main.misc.*;
import main.particles.Particle;
import main.pathfinding.AStar;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.sound.*;
import main.towers.Tower;
import processing.core.*;
import processing.sound.Sound;
import processing.sound.SoundFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Character.toLowerCase;
import static main.misc.LayoutLoader.loadSettings;

public class Main extends PApplet {

    public enum Screen {
        InGame,
        LevelSelect,
        Loading,
        Title,
        Exit,
        PlayOrLevelSelect,
        Restart
    }

    public static Tile.TileDS tiles;
    public static KeyDS keysPressed = new KeyDS();
    public static InputHandler inputHandler;
    public static KeyBinds keyBinds;
    public static Sound sound;

    public static Machine machine;
    public static ArrayList<Tower>      towers;
    public static ArrayList<Enemy>      enemies;
    public static ArrayList<Corpse>     corpses;
    public static ArrayList<Projectile> projectiles;
    public static ArrayList<Particle>   topParticles, midParticles, bottomParticles, veryBottomParticles;
    public static ArrayList<Arc>        arcs;
    public static ArrayList<Shockwave>  shockwaves;
    public static ArrayList<TowerBuy>   towerBuyButtons;
    public static ArrayList<TileSelect> tileSelectButtons;
    public static ArrayList<Buff>       buffs;
    public static ArrayList<PopupText>  popupTexts;

    public static int currentLevel;
    public static Level[] levels;

    public static CompressArray compress;

    public static Game game;
    public static Hand hand;
    public static Selection selection;
    public static InGameGui inGameGui;
    public static WaveStack waveStack;
    public static LevelBuilderGui levelBuilderGui;
    public static PauseGui pauseGui;
    public static SettingsGui settingsGui;

    public static LevelSelectGui levelSelectGui;
    public static LoadingGui loadingGui;
    public static TitleGui titleGui;

    public static PFont title, h1, h2, h3, h4, pg, monoHuge, monoLarge, monoMedium, monoSmall;

    public static Screen screen = Screen.Loading;
    public static Screen targetScreen;
    public static int money = 100;
    public static int connectWallQueues;
    public static float globalVolume = 0.25f;
    public static float matrixScale, matrixOffset;
    /** initialized to false */
    public static boolean
            won, debug, showSpawn, isPlaying, levelBuilder, paused, settings, isFullscreen, isOpenGL, isGore, hasVerticalBars;
    public static boolean alive = true;
    /** controls spawning, level building, infinite money etc. */
    public static boolean dev = false;
    public static PVector boardMousePosition;

    public static final int FRAMERATE = 30;
    public static final int SOFT_PARTICLE_CAP = 1500, HARD_PARTICLE_CAP = 3000;

    public static final int WINDOW_WIDTH = 1300, WINDOW_HEIGHT = 900;
    public static final int BOARD_WIDTH = 900, BOARD_HEIGHT = 900;
    public static final int TILE_SIZE = 50;
    public static final int DEFAULT_MODE = CORNER;

    //sprites
    public static HashMap<String, PImage> staticSprites = new HashMap<>();
    public static HashMap<String, PImage[]> animatedSprites = new HashMap<>();

    //sound
    public static HashMap<String, SoundFile> sounds = new HashMap<>();
    public static HashMap<String, StartStopSoundLoop> startStopSoundLoops = new HashMap<>();
    public static HashMap<String, FadeSoundLoop> fadeSoundLoops = new HashMap<>();
    public static HashMap<String, SoundWithAlts> soundsWithAlts = new HashMap<>();
    public static HashMap<String, StackableSound> stackableSounds = new HashMap<>();
    public static HashMap<String, MoveSoundLoop> moveSoundLoops = new HashMap<>();

    //transitions
    private static final int TRANS_SIZE = 2000;
    private static final float TRANS_SPEED = 250;
    private static PVector transCenter;
    private static PVector transRotation;

    //pathfinding stuff
    public static final int GRID_WIDTH = 1100, GRID_HEIGHT = 1100;
    public static final int NODE_SIZE = 25;
    public static final int DEFAULT_SIZE = 1;
    public static Node[][] nodeGrid;
    public static HeapNode openNodes;
    public static Node start;
    public static Node[] end;
    public static AStar pathFinder;
    public static float maxCost, minCost;

    public static Random random = new Random();

    public static void main(String[] args) {
        dev = args.length > 0 && args[0].equals("dev");

        loadSettings();
        PApplet.main("main.Main", args);
    }

    /**
     * From Processing.
     * Just controls window size and renderer type,
     * run once at program start
     */
    @Override
    public void settings() {
        if (isOpenGL) size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);
        else size(WINDOW_WIDTH, WINDOW_HEIGHT);
        if (isFullscreen) {
            fullScreen();
            noSmooth();
        }
    }

    /**
     * From Processing.
     * Primary initialization,
     * run once at program start
     */
    @Override
    public void setup() {
        frameRate(FRAMERATE);
        surface.setTitle("Crazy Critters Attack");
        sound = new Sound(this);
        imageMode(DEFAULT_MODE);
        rectMode(DEFAULT_MODE);
        System.out.println(sketchPath(""));

        //fonts
        title = createFont("STHeitiSC-Medium", 48, true);
        //load input
        inputHandler = new InputHandler(this);
        keyBinds = new KeyBinds(this);
        keyBinds.loadKeyBinds();
        //set level count, it has to be this way :(
        levels = new Level[5];
        //guis
        loadingGui = new LoadingGui(this, title);
        //matrix
        float screenRatio = width / (float) height;
        float boardRatio = BOARD_WIDTH / (float) BOARD_HEIGHT;
        hasVerticalBars = boardRatio < screenRatio;
        if (hasVerticalBars) {
            matrixScale = height / (float) WINDOW_HEIGHT;
            matrixOffset = (width - (WINDOW_WIDTH * matrixScale)) / 2;
        } else {
            matrixScale = width / (float) BOARD_WIDTH;
            matrixOffset = (height - (BOARD_HEIGHT * matrixScale)) / 2;
        }
    }

    /**
     * From Processing.
     * Main loop
     */
    @Override
    public void draw() {
        update();
        display();
    }

    /** Main update loop **/
    private void update() {
        if (hasVerticalBars) {
            boardMousePosition = new PVector((mouseX - matrixOffset) / matrixScale - 200, mouseY / matrixScale);
        } else {
            boardMousePosition = new PVector(mouseX / matrixScale - 200, (mouseY - matrixOffset) / matrixScale);
        }
        //screens
        switch (screen) {
            case InGame -> game.update();
            case LevelSelect -> {
                if (!settings) levelSelectGui.update();
            }
            case Loading -> loadingGui.update();
            case Title -> {
                if (!settings) titleGui.update();
            }

            // immediate action branches
            case Exit -> exit();
            case Restart -> {
                isPlaying = false;
                Game.reset(this);
                paused = false;
                Saver.wipe();
                screen = Screen.InGame;
                targetScreen = screen;
            }
            case PlayOrLevelSelect -> {
                //this runs three times
                try {
                    Loader.load(this);
                } catch (RuntimeException ex) {
                    System.out.println("Could not load from saves because:\n    " +
                            ex + "\n    " +
                            Arrays.toString(ex.getStackTrace()));
                    screen = Screen.LevelSelect;
                    targetScreen = Screen.LevelSelect;
                }
            }
        }
        if (settings) settingsGui.update();
        updateTransition();

        updateInput();
        updateSound();
    }

    /** Updates volume and sound loops **/
    private void updateSound() {
        sound.volume(globalVolume);
        for (StartStopSoundLoop startStopSoundLoop : startStopSoundLoops.values()) startStopSoundLoop.continueLoop();
        for (FadeSoundLoop fadeSoundLoop : fadeSoundLoops.values()) fadeSoundLoop.update();
        for (MoveSoundLoop moveSoundLoop : moveSoundLoops.values()) moveSoundLoop.update();
    }

    /** Updates menu keys input and resets mouse pulses **/
    private void updateInput() {
        keyBinds.menuKeys();

        //reset mouse pulses
        inputHandler.rightMouseReleasedPulse = false;
        inputHandler.leftMouseReleasedPulse = false;
        inputHandler.rightMousePressedPulse = false;
        inputHandler.leftMousePressedPulse = false;
        for (KeyDS.KeyDSItem key : keysPressed.items) {
            key.pressedPulse = false;
            key.releasedPulse = false;
        }
    }

    /** Updates the screen transition animation **/
    private void updateTransition() {
        if (titleGui == null) return;

        transCenter.add(transRotation.copy().setMag(TRANS_SPEED));

        if ((abs((WINDOW_WIDTH / 2f) - transCenter.x) < TRANS_SPEED
                && abs((WINDOW_HEIGHT / 2f) - transCenter.y) < TRANS_SPEED)
                && targetScreen != screen) {
            if (targetScreen == Screen.InGame || screen == Screen.InGame) {
                Game.reset(this);
                paused = false;
            }
            screen = targetScreen;
        }
    }

    /** Main display loop **/
    private void display() {
        if (showSpawn) {
            scale(BOARD_HEIGHT / (float) WINDOW_HEIGHT);
            float buffer = (WINDOW_WIDTH - BOARD_HEIGHT) / 2f;
            translate(buffer, buffer);
        }
        background(50);
        tint(255);
        //screens
        switch (screen) {
            case InGame -> game.display();
            case LevelSelect -> {
                if (!settings) levelSelectGui.display();
            }
            case Loading -> loadingGui.display();
            case Title -> {
                if (!settings) titleGui.display();
            }
        }
        if (settings) settingsGui.display();
        displayTransition();
        //black bars
        if (!showSpawn) {
            fill(0);
            noStroke();
            if (hasVerticalBars) {
                rect(0, 0, matrixOffset, height);
                rect(width - matrixOffset, 0, matrixOffset, height);
            } else {
                rect(0, 0, width, matrixOffset);
                rect(0, height - matrixOffset, width, matrixOffset);
            }
        }
    }

    /** Displays the screen transition animation **/
    private void displayTransition() {
        if (titleGui == null) return;

        PVector edge = PVector.add(transCenter, transRotation.copy().setMag(TRANS_SIZE));
        PVector p1 = PVector.add(edge, Utilities.turnRight(transRotation.copy().setMag(TRANS_SIZE), 1));
        PVector p2 = PVector.add(p1, Utilities.turnRight(transRotation.copy().setMag(TRANS_SIZE * 2), 2));
        PVector p3 = PVector.add(p2, Utilities.turnRight(transRotation.copy().setMag(TRANS_SIZE * 2), 3));
        PVector p4 = PVector.add(p3, Utilities.turnRight(transRotation.copy().setMag(TRANS_SIZE * 2), 4));

        PShape transBox = createShape();
        transBox.beginShape();
        transBox.fill(0);
        transBox.noStroke();
        transBox.vertex(p1.x, p1.y);
        transBox.vertex(p2.x, p2.y);
        transBox.vertex(p3.x, p3.y);
        transBox.vertex(p4.x, p4.y);
        transBox.endShape(PConstants.CLOSE);

        shape(transBox, 0, 0);
    }

    /**
     * Transitions from one screen to another with a little sweeping animation.
     * @param screen screen to transition to
     * @param direction direction to sweep towards
     */
    public static void transition(Screen screen, PVector direction) {
        if (targetScreen == screen) return;
        direction.add(PVector.fromAngle(random.nextFloat() * TWO_PI).setMag(0.2f));
        transCenter = PVector.add(
                direction.copy().setMag(TRANS_SIZE * -2),
                new PVector(WINDOW_WIDTH / 2f, WINDOW_HEIGHT / 2f));
        transRotation = PVector.sub(new PVector(WINDOW_WIDTH / 2f, WINDOW_HEIGHT / 2f), transCenter).normalize();
        targetScreen = screen;
    }

    /**
     * Checks if a key is pressed.
     * Also includes overrides.
     */
    @Override
    public void keyPressed() {
        if (!dev) key = toLowerCase(key);
        if (keyCode == 8)  key = '*'; //delete
        if (keyCode == 9)  key = '?'; //tab
        if (keyCode == 27) key = '|'; //esc
        if (keyCode == 37) key = '<'; //left
        if (keyCode == 38) key = '^'; //up
        if (keyCode == 39) key = '>'; //right
        if (keyCode == 40) key = '&'; //down
        inputHandler.key(true);
    }

    @Override
    public void keyReleased() {
        inputHandler.key(false);
    }

    @Override
    public void mousePressed() {
        inputHandler.mouse(true);
    }

    @Override
    public void mouseReleased() {
        inputHandler.mouse(false);
    }

    public class InputHandler {

        private final PApplet P;

        public boolean rightMouseReleasedPulse;
        public boolean leftMouseReleasedPulse;
        public boolean rightMousePressedPulse;
        public boolean leftMousePressedPulse;

        private boolean rightMousePressed;
        private boolean leftMousePressed;

        /**
         * Handles input from keyboard and mouse.
         * @param p the PApplet
         */
        public InputHandler(PApplet p) {
            this.P = p;
        }

        /**
         * Handles input from the mouse.
         * @param b mouse pressed
         */
        void mouse(boolean b) {
            if (b) {
                if (P.mouseButton == RIGHT) {
                    if (!rightMousePressed) rightMousePressedPulse = true;
                    rightMousePressed = true;
                }
                if (P.mouseButton == LEFT) {
                    if (!leftMousePressed) leftMousePressedPulse = true;
                    leftMousePressed = true;
                }
            } else {
                if (rightMousePressed) {
                    rightMouseReleasedPulse = true;
                    rightMousePressed = false;
                }
                if (leftMousePressed) {
                    leftMouseReleasedPulse = true;
                    leftMousePressed = false;
                }
            }
        }

        /**
         * Handles input from the keyboard.
         * @param b any key pressed
         */
        void key(boolean b) {
            for (KeyDS.KeyDSItem item : keysPressed.items) {
                if (item.key == key) {
                    item.pressed = b;
                    item.pressedPulse = b;
                    item.releasedPulse = !b;
                }
            }
        }
    }

    public static class KeyDS {

        public KeyDSItem[] items;

        /** Contains all the keys from the keyboard */
        public KeyDS() {
            items = new KeyDSItem[0];
        }

        public void add(char key) {
            KeyDSItem[] newItems = new KeyDSItem[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new KeyDSItem(key);
            items = newItems;
        }

        public boolean getPressed(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.pressed;
            return r;
        }

        public boolean getPressedPulse(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.pressedPulse;
            return r;
        }

        public boolean getReleasedPulse(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.releasedPulse;
            return r;
        }

        static class KeyDSItem {

            char key;
            boolean pressed;
            boolean pressedPulse;
            boolean releasedPulse;

            KeyDSItem(char key) {
                this.key = key;
                pressed = false;
            }
        }
    }
}
