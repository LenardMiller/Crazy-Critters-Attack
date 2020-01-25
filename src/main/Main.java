package main;

import main.buffs.Buff;
import main.enemies.Enemy;
import main.gui.Gui;
import main.gui.Hand;
import main.gui.Selection;
import main.guiObjects.GuiObject;
import main.guiObjects.buttons.Button;
import main.guiObjects.buttons.TowerBuy;
import main.particles.Particle;
import main.pathfinding.AStar;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.projectiles.Projectile;
import main.towers.Tile;
import main.towers.Tower;
import main.util.CompressArray;
import main.util.EnemyTracker;
import main.util.KeyBinds;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

import static main.util.MiscMethods.*;
import static main.util.SpriteLoader.loadSprites;
import static main.util.SpriteLoader.loadSpritesAnim;

public class Main extends PApplet {

    public static EnemyTracker enemyTracker;
    public static Tile.TileDS tiles;
    public static KeyDS keysPressed = new KeyDS();
    public static InputHandler inputHandler;
    public static KeyBinds keyBinds;

    public static ArrayList<main.towers.Tower> towers;
    public static ArrayList<main.enemies.Enemy> enemies;
    public static ArrayList<main.projectiles.Projectile> projectiles;
    public static ArrayList<main.particles.Particle> particles;
    public static ArrayList<TowerBuy> towerBuyButtons;
    public static ArrayList<Buff> buffs;

    public static Gui gui;
    public static CompressArray compress;

    public static Button addHp;
    public static Button addMoney;
    public static GuiObject hpIcon;
    public static GuiObject moneyIcon;
    public static Button towerTabButton;
    public static Button sellButton;
    public static Button targetButton;
    public static Button upgradeButtonA;
    public static Button upgradeButtonB;
    public static GuiObject upgradeIconA;
    public static GuiObject upgradeIconB;

    public static Hand hand;
    public static Selection selection;

    public static PFont veryLargeFont;
    public static PFont largeFont;
    public static PFont mediumLargeFont;
    public static PFont mediumFont;
    public static PFont smallFont;

    public static int hp = 2000; //todo: remove
    public static int money = 2000;
    public static boolean alive = true;
    public static boolean debug = false;

    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 900;
    public static final int GRID_WIDTH = 900; //todo: extend outside of window
    public static final int GRID_HEIGHT = 900;

    public static HashMap<String, PImage> spritesH = new HashMap<>();
    public static HashMap<String, PImage[]> spritesAnimH = new HashMap<>();

    //pathfinding stuff
    public static int defaultSize = 1;
    public static Node[][] nodeGrid;
    public static HeapNode openNodes;
    public static Node start;
    public static Node[] end;
    public static AStar path;
    public static int nSize;
    public static int numEnd;
    public static float maxCost;
    public static float minCost;

    public static void main(String[] args) {
        PApplet.main("main.Main", args);
    }

    public void settings() {
        size(1100, 900);
    }

    public void setup() {
        veryLargeFont = createFont("STHeitiSC-Light", 48);
        largeFont = createFont("STHeitiSC-Light", 24);
        mediumLargeFont = createFont("STHeitiSC-Light", 21);
        mediumFont = createFont("STHeitiSC-Light", 18);
        smallFont = createFont("STHeitiSC-Light", 12);
        //creates object data structures
        tiles = new Tile.TileDS();
        for (int y = 0; y <= BOARD_HEIGHT / 50; y++) {
            for (int x = 0; x <= BOARD_WIDTH / 50; x++) {
                tiles.add(new Tile(this, new PVector(x * 50, y * 50), tiles.size()), x, y);
            }
        }
        enemies = new ArrayList<>();
//        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        particles = new ArrayList<>();
        towerBuyButtons = new ArrayList<>();
        buffs = new ArrayList<>();
        //loads sprites
        loadSprites(this);
        loadSpritesAnim(this);
        //other stuff
        inputHandler = new InputHandler(this);
        keyBinds = new KeyBinds(this);
        keyBinds.loadKeyBinds();
        hand = new Hand(this);
        selection = new Selection(this);
        enemyTracker = new EnemyTracker(this);
        gui = new Gui(this);
        //pathfinding stuff
        nSize = 25;
        nodeGrid = new Node[GRID_WIDTH / nSize][GRID_HEIGHT / nSize];
        for (int x = 0; x < GRID_WIDTH / nSize; x++) {
            for (int y = 0; y < GRID_HEIGHT / nSize; y++) {
                nodeGrid[x][y] = new Node(this, new PVector(nSize * x, nSize * y));
            }
        }
        path = new AStar();
        openNodes = new HeapNode((int) (sq((float)GRID_WIDTH / nSize)));
        //create end nodes
        end = new Node[0];
//        end = new Node[(int) (sq(1000f / nSize))];
//        for (int i = (GRID_WIDTH / nSize) - 1; i >= 0; i--) {
//            nodeGrid[i][(GRID_HEIGHT / nSize)-5].setEnd(i, (GRID_HEIGHT / nSize)-5);
//        }
        //create start node
        nodeGrid[1][(GRID_WIDTH / nSize) / 2].setStart(1, (GRID_HEIGHT / nSize) / 2);
        start.findGHF();
        for (int i = 0; i < numEnd; i++) {
            end[i].findGHF();
        }
        updateTowerArray();
        updateNodes();
    }

    public void draw() {
        //bg todo: make background
        noStroke();
        fill(25, 25, 25);
        rect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        //keys
        keyBinds.debugKeys();
        keyBinds.spawnKeys();
        //pathfinding
        if (path.reqQ.size() > 0) {
            path.reqQ.get(0).getPath();
            path.reqQ.remove(0);
        }
        maxCost = maxCost();
        minCost = minCost(maxCost);
        //objects
        drawObjects();
        //gui stuff
        noStroke();
        fill(200);
        rect(BOARD_WIDTH, 0, BOARD_WIDTH + 250, BOARD_HEIGHT);
        gui.drawIcons();
        hand.displayHeldInfo(); //so text appears on top
        //text
        textAlign(LEFT);
        gui.drawText(this, 10);
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

    private void drawObjects() {
        //pathfinding debug
        if (debug) {
            for (Node[] nodes : nodeGrid) {
                for (Node node : nodes) {
                    node.display();
                }
            }
            fill(0,0,255);
            rect(start.position.x,start.position.y,nSize,nSize);
        }
        //enemy tracker
        enemyTracker.main(enemies);
        //towers
        for (Tower tower : towers) {
            tower.main();
        }
        //tower hp bars
        for (Tower tower : towers) {
            tower.hpBar();
        }
        //enemies
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.main(i);
        }
        if (enemies.size() == 0) {
            buffs = new ArrayList<>();
        }
        //projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.main(projectiles, i);
        }
        //particles
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle particle = particles.get(i);
            particle.main(particles, i);
        }
        //buffs
        for (int i = buffs.size() - 1; i >= 0; i--) {
            Buff buff = buffs.get(i);
            buff.main(i);
        }
        //currently held
        hand.main();
    }

    public void keyPressed() {
        inputHandler.key(true);
    }

    public void keyReleased() { inputHandler.key(false);
    }

    public void mousePressed() {
        inputHandler.mouse(true);
    }

    public void mouseReleased() {
        inputHandler.mouse(false);
    }

    public class InputHandler {

        private PApplet p;

        public boolean rightMouseReleasedPulse;
        public boolean leftMouseReleasedPulse;
        private boolean rightMousePressed;
        private boolean leftMousePressed;
        public boolean rightMousePressedPulse;
        public boolean leftMousePressedPulse;

        public InputHandler(PApplet p) {
            this.p = p;
        }

        void mouse(boolean b) {
            if (b) {
                if (p.mouseButton == RIGHT) {
                    if (!rightMousePressed) rightMousePressedPulse = true;
                    rightMousePressed = true;
                }
                if (p.mouseButton == LEFT) {
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
