package main;

import main.buffs.Buff;
import main.damagingThings.arcs.Arc;
import main.damagingThings.projectiles.Projectile;
import main.damagingThings.shockwaves.Shockwave;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.gui.*;
import main.gui.guiObjects.PopupText;
import main.gui.guiObjects.buttons.TileSelect;
import main.gui.guiObjects.buttons.TowerBuy;
import main.levelStructure.*;
import main.misc.*;
import main.particles.Particle;
import main.pathfinding.AStar;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.sound.FadeSoundLoop;
import main.sound.SoundWithAlts;
import main.sound.StartStopSoundLoop;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.Sound;
import processing.sound.SoundFile;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Character.toLowerCase;
import static main.misc.DataControl.loadSettings;
import static main.misc.SpriteLoader.loadAnimatedSprites;
import static main.misc.SpriteLoader.loadStaticSprites;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.misc.WallSpecialVisuals.updateWallTileConnections;
import static main.pathfinding.PathfindingUtilities.*;
import static main.sound.SoundLoader.loadSounds;

public class Main extends PApplet {

    public static Tile.TileDS tiles;
    public static KeyDS keysPressed = new KeyDS();
    public static InputHandler inputHandler;
    public static KeyBinds keyBinds;
    public static Sound sound;

    public static Machine machine;
    public static ArrayList<main.towers.Tower> towers;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<main.misc.Corpse> corpses;
    public static ArrayList<main.damagingThings.projectiles.Projectile> projectiles;
    public static ArrayList<main.particles.Particle> topParticles, midParticles, bottomParticles;
    public static ArrayList<Arc> arcs;
    public static ArrayList<Shockwave> shockwaves;
    public static ArrayList<TowerBuy> towerBuyButtons;
    public static ArrayList<TileSelect> tileSelectButtons;
    public static ArrayList<Buff> buffs;
    public static ArrayList<PopupText> popupTexts;

    public static int currentLevel;
    public static Level[] levels;

    public static CompressArray compress;

    public static Hand hand;
    public static Selection selection;
    public static InGameGui inGameGui;
    public static LevelBuilderGui levelBuilderGui;
    public static PauseGui pauseGui;
    public static LevelSelectGui levelSelectGui;
    public static SettingsGui settingsGui;

    //can't be final because created by PApplet
    public static PFont veryLargeFont;
    public static PFont largeFont;
    public static PFont mediumLargeFont;
    public static PFont mediumFont;
    public static PFont smallFont;

    /**
     * in-game, level select
     */
    public static int screen = 1;
    public static int money = 100;
    public static int connectWallQueues;
    public static float globalVolume = 0.25f;
    public static float matrixScale;
    public static float matrixOffset;
    public static boolean fullscreen;
    public static boolean gore;
    public static boolean hasVerticalBars;
    public static boolean alive = true;
    public static boolean won = false;
    public static boolean debug = false;
    public static boolean showSpawn = false;
    public static boolean playingLevel = false;
    public static boolean levelBuilder = false;
    public static boolean paused = false;
    public static boolean settings = false;
    public static boolean dev = true;
    public static PVector matrixMousePosition;

    public static final int FRAMERATE = 30;
    public static final int SOFT_PARTICLE_CAP = 1500;
    public static final int HARD_PARTICLE_CAP = 3000;

    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 900;
    public static final int TILE_SIZE = 50;

    public static final int SLINGSHOT_PRICE = 75;
    public static final int RANDOM_CANNON_PRICE = 150;
    public static final int CROSSBOW_PRICE = 200;
    public static final int CANNON_PRICE = 400;
    public static final int GLUER_PRICE = 300;
    public static final int SEISMIC_PRICE = 400;
    public static final int ENERGY_BLASTER_PRICE = 1000;
    public static final int FLAMETHROWER_PRICE = 1250;
    public static final int TESLA_TOWER_PRICE = 1400;
    public static final int MAGIC_MISSILEER_PRICE = 2500;
    public static final int ICE_TOWER_PRICE = 2250;
    public static final int BOOSTER_PRICE = 2000;

    public static HashMap<String, PImage> staticSprites = new HashMap<>();
    public static HashMap<String, PImage[]> animatedSprites = new HashMap<>();

    public static HashMap<String, SoundFile> sounds = new HashMap<>();
    public static HashMap<String, StartStopSoundLoop> startStopSoundLoops = new HashMap<>();
    public static HashMap<String, FadeSoundLoop> fadeSoundLoops = new HashMap<>();
    public static HashMap<String, SoundWithAlts> soundsWithAlts = new HashMap<>();

    //pathfinding stuff
    public static final int GRID_WIDTH = 1100;
    public static final int GRID_HEIGHT = 1100;
    public static final int NODE_SIZE = 25;
    public static final int DEFAULT_SIZE = 1;
    public static Node[][] nodeGrid;
    public static HeapNode openNodes;
    public static Node start;
    public static Node[] end;
    public static AStar path;
    public static float maxCost, minCost;

    public static void main(String[] args) {
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
        size(GRID_WIDTH, BOARD_HEIGHT);
        if (fullscreen) {
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
        //fonts
        veryLargeFont = createFont("STHeitiSC-Light", 48, true);
        largeFont = createFont("STHeitiSC-Light", 24, true);
        mediumLargeFont = createFont("STHeitiSC-Light", 21, true);
        mediumFont = createFont("STHeitiSC-Light", 18, true);
        smallFont = createFont("STHeitiSC-Light", 12, true);
        //loads sprites
        loadStaticSprites(this);
        loadAnimatedSprites(this);
        //sound stuff
        loadSounds(this);
        //load input
        inputHandler = new InputHandler(this);
        keyBinds = new KeyBinds(this);
        keyBinds.loadKeyBinds();
        //set level count
        levels = new Level[4];
        //guis
        levelSelectGui = new LevelSelectGui(this);
        settingsGui = new SettingsGui(this);
        //matrix
        float screenRatio = width / (float) height;
        float boardRatio = BOARD_WIDTH / (float) BOARD_HEIGHT;
        hasVerticalBars = boardRatio < screenRatio;
        if (hasVerticalBars) {
            matrixScale = height / (float) BOARD_HEIGHT;
            matrixOffset = (width - (GRID_WIDTH * matrixScale)) / 2;
        } else {
            matrixScale = width / (float) BOARD_WIDTH;
            matrixOffset = (height - (BOARD_HEIGHT * matrixScale)) / 2;
        }
    }

    /**
     * Sets all the in game stuff up.
     */
    public static void resetGame(PApplet p) {
        //creates object data structures
        tiles = new Tile.TileDS();
        for (int y = 0; y <= BOARD_HEIGHT / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_WIDTH / TILE_SIZE; x++) {
                tiles.add(new Tile(p, new PVector(x * TILE_SIZE, y * TILE_SIZE), tiles.size()), x, y);
            }
        }
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        corpses = new ArrayList<>();
        topParticles = new ArrayList<>();
        midParticles = new ArrayList<>();
        bottomParticles = new ArrayList<>();
        arcs = new ArrayList<>();
        shockwaves = new ArrayList<>();
        towerBuyButtons = new ArrayList<>();
        tileSelectButtons = new ArrayList<>();
        buffs = new ArrayList<>();
        popupTexts = new ArrayList<>();
        //pathfinding stuff
        nodeGrid = new Node[GRID_WIDTH / NODE_SIZE][GRID_HEIGHT / NODE_SIZE];
        for (int x = 0; x < GRID_WIDTH / NODE_SIZE; x++) {
            for (int y = 0; y < GRID_HEIGHT / NODE_SIZE; y++) {
                nodeGrid[x][y] = new Node(p, new PVector((NODE_SIZE * x)-100, (NODE_SIZE * y)-100));
            }
        }
        path = new AStar();
        openNodes = new HeapNode((int) (sq((float)GRID_WIDTH / NODE_SIZE)));
        //create end nodes
        end = new Node[0];
        //create start node
        nodeGrid[1][(GRID_WIDTH / NODE_SIZE) / 2].setStart(1, (GRID_HEIGHT / NODE_SIZE) / 2);
        start.findGHF();
        for (Node node : end) node.findGHF();
        updateTowerArray();
        //load level data
        playingLevel = false;
        levels[0] = new Level(p, ForestWaves.genForestWaves(p), "levels/forest", 125, 50, "dirt");
        levels[1] = new Level(p, DesertWaves.genDesertWaves(p), "levels/desert", 250, 75, "sand");
        levels[2] = new Level(p, CaveWaves.genCaveWaves(p), "levels/cave", 500, 100, "stone");
        levels[3] = new Level(p, GlacierWaves.genGlacierWaves(p), "levels/glacier", 1500, 200, "snow");
        DataControl.loadLayout(p, levels[currentLevel].layout);
        money = levels[currentLevel].startingCash;
        updateNodes();
        //gui stuff
        hand = new Hand(p);
        selection = new Selection(p);
        inGameGui = new InGameGui(p);
        levelBuilderGui = new LevelBuilderGui(p);
        pauseGui = new PauseGui(p);
        //other
        won = false;
        connectWallQueues = 0;
    }

    /**
     * From Processing.
     * Everything else, run every frame.
     */
    @Override
    public void draw() {
        if (showSpawn) {
            scale(BOARD_HEIGHT / (float) GRID_HEIGHT);
            float buffer = (GRID_HEIGHT - BOARD_HEIGHT) / 2f;
            translate(buffer, buffer);
        }
        background(50);
        tint(255);
        if (hasVerticalBars) {
            matrixMousePosition = new PVector((mouseX - matrixOffset) / matrixScale, mouseY / matrixScale);
        } else {
            matrixMousePosition = new PVector(mouseX / matrixScale, (mouseY - matrixOffset) / matrixScale);
        }
        //screens
        if (screen == 0) drawInGame();
        if (screen == 1) drawLevelSelect();
        if (settings) settingsGui.main();
        keyBinds.menuKeys();
        //sound stuff
        sound.volume(globalVolume);
        for (StartStopSoundLoop startStopSoundLoop : startStopSoundLoops.values()) startStopSoundLoop.continueLoop();
        for (FadeSoundLoop fadeSoundLoop : fadeSoundLoops.values()) fadeSoundLoop.main();
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

    /**
     * Stuff for the main game screen.
     */
    private void drawInGame() {
        pushMatrix();
        if (hasVerticalBars) translate(matrixOffset, 0);
        else translate(0, matrixOffset);
        scale(matrixScale);
        //keys
        if (dev) {
            keyBinds.debugKeys();
            keyBinds.spawnKeys();
        }
        keyBinds.inGameKeys();
        //pathfinding
        if (!path.reqQ.isEmpty()) {
            path.reqQ.get(0).getPath();
            path.reqQ.remove(0);
        }
        maxCost = maxCost();
        minCost = minCost(maxCost);
        //objects
        drawObjects();
        //hp bars
        for (Enemy enemy : enemies) {
            if (enemy.hp > 0 && !(enemy instanceof BurrowingEnemy && enemy.state == 0)) enemy.hpBar();
        }
        for (Tower tower : towers) tower.displayHpBar();
        machine.hpBar();
        //text
        for (int i = popupTexts.size()-1; i >= 0; i--) popupTexts.get(i).main();
        //gui stuff
        noStroke();
        if (!showSpawn) {
            if (!levelBuilder) inGameGui.display();
            else levelBuilderGui.display();
            hand.displayHeldInfo();
            textAlign(LEFT);
            if (!levelBuilder) inGameGui.drawText(this, 10);
        } if (dev) inGameGui.drawDebugText(this, 10);

        if (paused) { //grey stuff
            noStroke();
            if (!alive) fill(50, 0, 0, 50);
            else fill(0, 0, 0, 50);
            rect(0, 0, width, height);
        }
        //levels
        if (playingLevel) levels[currentLevel].main();
        //matrix
        popMatrix();
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
        //pause
        if (paused && !settings) pauseGui.main();
    }

    /**
     * Stuff for the level select screen.
     */
    private void drawLevelSelect() {
        if (!settings) levelSelectGui.main();
    }

    /**
     * Runs all the in-game stuff.
     */
    private void drawObjects() {
        //tiles
        if (connectWallQueues > 0) { //else
            connectWallQueues = 0;
            updateWallTileConnections();
        }
        //main background
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).displayBaseAndFlooring();
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringName != null) {
                if (tile.flooringName.equals("metalWall")) {
                    tile.displayFlooringInsideCorners();
                }
            }
            tile.displayFlooringOutsideCorners("metalWall");
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringName != null) {
                if (tile.flooringName.equals("crystalWall")) {
                    tile.displayFlooringInsideCorners();
                }
            }
            tile.displayFlooringOutsideCorners("crystalWall");
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringName != null) {
                if (tile.flooringName.equals("titaniumWall")) {
                    tile.displayFlooringInsideCorners();
                }
            }
            tile.displayFlooringOutsideCorners("titaniumWall");
        }
        //obstacle shadows, background c
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).displayBreakableAndShadow();
        }
        //pathfinding debug
        if (debug) {
            for (Node[] nodes : nodeGrid) {
                for (Node node : nodes) {
                    node.display();
                }
            }
            fill(0,0,255);
            rect(start.position.x,start.position.y, NODE_SIZE, NODE_SIZE);
        }
        //bottom particles
        for (int i = bottomParticles.size()-1; i >= 0; i--) {
            Particle particle = bottomParticles.get(i);
            particle.main(bottomParticles, i);
        }
        //corpses
        for (Corpse corpse : corpses) corpse.display();
        for (int i = corpses.size() - 1; i >= 0; i--) {
            Corpse corpse = corpses.get(i);
            corpse.main(i);
        }
        //machine
        machine.main();
        //enemies
        for (Enemy enemy : enemies) if (!(enemy instanceof FlyingEnemy)) enemy.displayShadow();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (!(enemy instanceof FlyingEnemy)) enemy.main(i);
        }
        if (enemies.isEmpty()) buffs = new ArrayList<>();
        //turret bases & walls
        for (Tower tower : towers) if (tower instanceof Turret) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.controlAnimation();
        //mid particles
        for (int i = midParticles.size()-1; i >= 0; i--) {
            Particle particle = midParticles.get(i);
            particle.main(midParticles, i);
        }
        //turret top
        for (Tower tower : towers) if (tower instanceof Turret) tower.controlAnimation();
        for (Tower tower : towers) tower.main();
        //shockwaves
        for (int i = shockwaves.size()-1; i >= 0; i--) shockwaves.get(i).main();
        //particle culling
        int p = topParticles.size() + midParticles.size() + bottomParticles.size();
        int p2 = p-SOFT_PARTICLE_CAP;
        if (p > SOFT_PARTICLE_CAP) {
            for (int i = topParticles.size() - 1; i >= 0; i--) {
                if (random(0,p2) < 5) {
                    if (i < topParticles.size()) topParticles.remove(i);
                }
            } for (int i = bottomParticles.size() - 1; i >= 0; i--) {
                if (random(0,p2) < 5) {
                    if (i < bottomParticles.size()) bottomParticles.remove(i);
                }
            } for (int i = midParticles.size() - 1; i >= 0; i--) {
                if (random(0,p2) < 5) {
                    if (i < midParticles.size()) midParticles.remove(i);
                }
            }
        } if (p > HARD_PARTICLE_CAP) {
            topParticles = new ArrayList<>();
            midParticles = new ArrayList<>();
            bottomParticles = new ArrayList<>();
        }
        //buffs
        for (int i = buffs.size() - 1; i >= 0; i--) {
            Buff buff = buffs.get(i);
            buff.main(i);
        }
        //obstacles
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).displayObstacle();
        }
        //projectiles
        for (Projectile projectile : projectiles) projectile.displayPassA();
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.main();
        }
        //arcs
        for (int i = arcs.size()-1; i >= 0; i--) {
            Arc arc = arcs.get(i);
            arc.main();
            if (arc.alpha <= 0) arcs.remove(i);
        }
        //flying enemies
        for (Enemy enemy1 : enemies) {
            if (enemy1 instanceof FlyingEnemy) enemy1.displayShadow();
        }
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy instanceof FlyingEnemy) enemy.main(i);
        }
        //top particles
        for (int i = topParticles.size()-1; i >= 0; i--) {
            Particle particle = topParticles.get(i);
            particle.main(topParticles, i);
        }
        //currently held
        hand.main();
        //reset enemy ice checking
        for (Enemy enemy : enemies) enemy.intersectingIceCount = 0;
    }

    /**
     * Checks if a key is pressed.
     * Also includes overrides.
     */
    @Override
    public void keyPressed() {
        if (!dev) key = toLowerCase(key);
        if (keyCode == 8) key = '*'; //delete
        if (keyCode == 9) key = '?'; //tab
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
        private boolean rightMousePressed;
        private boolean leftMousePressed;
        public boolean rightMousePressedPulse;
        public boolean leftMousePressedPulse;

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

        /**
         * Contains all the keys from the keyboard
         */
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
