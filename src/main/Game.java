package main;

import main.buffs.Buff;
import main.projectiles.arcs.Arc;
import main.projectiles.Projectile;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.gui.inGame.*;
import main.levelStructure.*;
import main.misc.Corpse;
import main.misc.LayoutLoader;
import main.misc.Tile;
import main.particles.Particle;
import main.pathfinding.AStar;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static main.Main.*;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.misc.WallSpecialVisuals.updateWallTileConnections;
import static main.pathfinding.PathfindingUtilities.*;

public class Game {

    private final PApplet p;

    public Game(PApplet p) {
        this.p = p;
    }

    /** Update in game stuff **/
    public void update() {
        //keys
        if (dev) {
            keyBinds.debugKeys();
            keyBinds.spawnKeys();
        } else {
            keyBinds.selectionKeys();
            keyBinds.inGameKeys();
        }
        //pathfinding
        if (!pathFinder.requestQueue.isEmpty()) {
            pathFinder.requestQueue.get(0).getPath();
            pathFinder.requestQueue.remove(0);
        }
        maxCost = maxCost();
        minCost = minCost(maxCost);

        machine.update();
        updateParticles();
        updateProjectiles();
        updateEnemies();
        //tiles
        if (connectWallQueues > 0) {
            connectWallQueues = 0;
            updateWallTileConnections();
        }
        //turret top
        for (Tower tower : towers) if (tower instanceof Turret) tower.controlAnimation();
        for (Tower tower : towers) tower.update();

        //ui
        hand.update();
        for (int i = popupTexts.size()-1; i >= 0; i--) popupTexts.get(i).update();
        if (playingLevel) levels[currentLevel].update();
        if (paused && !settings) pauseGui.update();
        if (!showSpawn && !levelBuilder) inGameGui.update();
    }

    /** Update everything that has to do with enemies; enemies, corpses, ice checks and buffs **/
    private void updateEnemies() {
        //enemies
        if (enemies.isEmpty()) buffs = new ArrayList<>();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update(i);
        }
        //corpses
        for (int i = corpses.size() - 1; i >= 0; i--) {
            Corpse corpse = corpses.get(i);
            corpse.update(i);
        }
        //reset enemy ice checking
        for (Enemy enemy : enemies) enemy.intersectingIceCount = 0;
        //buffs
        for (int i = buffs.size() - 1; i >= 0; i--) {
            Buff buff = buffs.get(i);
            buff.update(i);
        }
    }

    /** Updates everything considered a projectile; classical projectiles, arcs and shockwaves **/
    private void updateProjectiles() {
        //projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update();
        }
        //arcs
        for (int i = arcs.size()-1; i >= 0; i--) {
            Arc arc = arcs.get(i);
            arc.update(i);
        }
        //shockwaves
        for (int i = shockwaves.size()-1; i >= 0; i--) shockwaves.get(i).update();
    }

    /** Updates all the different layers of particles, and handles particle culling **/
    private void updateParticles() {
        for (int i = veryBottomParticles.size() - 1; i >= 0; i--) {
            veryBottomParticles.get(i).update(veryBottomParticles, i);
        }
        for (int i = bottomParticles.size()-1; i >= 0; i--) {
            Particle particle = bottomParticles.get(i);
            particle.update(bottomParticles, i);
        }
        for (int i = midParticles.size()-1; i >= 0; i--) {
            Particle particle = midParticles.get(i);
            particle.update(midParticles, i);
        }
        for (int i = topParticles.size()-1; i >= 0; i--) {
            Particle particle = topParticles.get(i);
            particle.update(topParticles, i);
        }
        //particle culling
        int totalParticles = topParticles.size() + midParticles.size() + bottomParticles.size();
        int allowedParticles = totalParticles-SOFT_PARTICLE_CAP;
        if (totalParticles > SOFT_PARTICLE_CAP) {
            for (int i = veryBottomParticles.size() - 1; i >= 0; i--) {
                if (p.random(allowedParticles) < 5) {
                    if (i < veryBottomParticles.size()) veryBottomParticles.remove(i);
                }
            } for (int i = topParticles.size() - 1; i >= 0; i--) {
                if (p.random(allowedParticles) < 5) {
                    if (i < topParticles.size()) topParticles.remove(i);
                }
            } for (int i = bottomParticles.size() - 1; i >= 0; i--) {
                if (p.random(allowedParticles) < 5) {
                    if (i < bottomParticles.size()) bottomParticles.remove(i);
                }
            } for (int i = midParticles.size() - 1; i >= 0; i--) {
                if (p.random(allowedParticles) < 5) {
                    if (i < midParticles.size()) midParticles.remove(i);
                }
            }
        } if (totalParticles > HARD_PARTICLE_CAP) {
            topParticles = new ArrayList<>();
            midParticles = new ArrayList<>();
            bottomParticles = new ArrayList<>();
        }
    }

    /** Display in game stuff */
    public void display() {
        //fullscreen stuff
        p.pushMatrix();
        if (hasVerticalBars) p.translate(matrixOffset, 0);
        else p.translate(0, matrixOffset);
        p.scale(matrixScale);

        displayGameObjects();
        displayHPBars();
        for (main.gui.guiObjects.PopupText popupText : popupTexts) popupText.display();
        displayInGameGui();

        p.popMatrix();

        if (paused && !settings) pauseGui.display();
    }

    /** Displays all the UI elements that stick to the screen and scale with window size **/
    private void displayInGameGui() {
        p.noStroke();
        if (!showSpawn) {
            if (!levelBuilder) inGameGui.display();
            else levelBuilderGui.display();
            hand.displayHeldInfo();
            p.textAlign(LEFT);
            if (!levelBuilder) inGameGui.displayText(p, 10);
        } if (dev) inGameGui.displayDebugText(p, 10);
        if (paused) { //grey stuff
            p.noStroke();
            if (!alive) p.fill(50, 0, 0, 50);
            else p.fill(0, 0, 0, 50);
            p.rect(0, 0, p.width, p.height);
        }
    }

    /** Display enemy, tower and machine health bars **/
    private void displayHPBars() {
        for (Enemy enemy : enemies) {
            if (enemy.hp > 0 && !(enemy instanceof BurrowingEnemy && enemy.state == Enemy.State.Moving)) {
                enemy.hpBar();
            }
        }
        for (Tower tower : towers) tower.displayHpBar();
        machine.hpBar();
    }

    /** Displays everything that is within the game "window" */
    private void displayGameObjects() {
        displayBackgroundTiles();
        if (debug) displayPathfindingDebug();
        for (Particle particle : bottomParticles) particle.display();
        for (Corpse corpse : corpses) corpse.display();
        machine.display();
        for (Enemy enemy : enemies) if (!(enemy instanceof FlyingEnemy)) enemy.displayShadow();
        for (Enemy enemy : enemies) if (!(enemy instanceof FlyingEnemy)) enemy.display();
        for (Tower tower : towers) if (tower instanceof Turret) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.controlAnimation();
        for (Particle particle : midParticles) particle.display();
        for (Tower tower : towers) if (tower instanceof Turret) ((Turret) tower).displayTop();
        IntStream.range(0, tiles.size()).forEach(i -> tiles.get(i).displayObstacle());
        for (Projectile projectile : projectiles) projectile.displayShadow();
        for (Projectile projectile : projectiles) projectile.display();
        for (Arc arc : arcs) arc.display();
        for (Enemy enemy1 : enemies) if (enemy1 instanceof FlyingEnemy) enemy1.displayShadow();
        for (Enemy enemy : enemies) if (enemy instanceof FlyingEnemy) enemy.display();
        for (Particle particle : topParticles) particle.display();
        hand.displayHeld();
    }

    /** Displays tile base, the lowest particle layer, flooring, decorations and obstacle shadows **/
    private void displayBackgroundTiles() {
        //main background
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).displayBase();
        }
        //very bottom particles
        for (Particle veryBottomParticle : veryBottomParticles) veryBottomParticle.display();
        //decoration
        IntStream.range(0, tiles.size()).forEach(i -> tiles.get(i).displayDecorationAndFlooring());
        //flooring
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
    }

    /** Displays debug info for pathfinding **/
    private void displayPathfindingDebug() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.display();
            }
        }
        p.fill(0,0,255);
        p.rect(start.position.x,start.position.y, NODE_SIZE, NODE_SIZE);
    }

    /** Sets all the in game stuff up */
    public static void reset(PApplet p) {
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
        veryBottomParticles = new ArrayList<>();
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
        pathFinder = new AStar(p);
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
        levels[0] = new Level(p, ForestWaves.genForestWaves(p),
                "levels/forest",     125,  50,  "dirt");
        levels[1] = new Level(p, DesertWaves.genDesertWaves(p),
                "levels/desert",     250,  75,  "sand");
        levels[2] = new Level(p, CaveWaves.genCaveWaves(p),
                "levels/cave",       500,  100, "stone");
        levels[3] = new Level(p, GlacierWaves.genGlacierWaves(p),
                "levels/glacier",    1500, 200, "snow");
        levels[4] = new Level(p, DeepForestWaves.genDeepForestWaves(p),
                "levels/deepForest", 2500, 350, "dirt");
        LayoutLoader.loadLayout(p, levels[currentLevel].layout);
        money = levels[currentLevel].startingCash;
        updateAll();
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
}
