package main;

import main.buffs.Buff;
import main.misc.*;
import main.projectiles.arcs.Arc;
import main.projectiles.Projectile;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.gui.inGame.*;
import main.levelStructure.*;
import main.particles.Particle;
import main.pathfinding.AStar;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.projectiles.shockwaves.Shockwave;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static main.Main.*;
import static main.misc.Tile.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.*;

public class Game {

    private final PApplet p;

    public Game(PApplet p) {
        this.p = p;
    }

    /** Update in game stuff **/
    public void update() {
        //keys
        if (isDev) {
            keyBinds.debugKeys();
            keyBinds.spawnKeys();
        } else {
            keyBinds.selectionKeys();
            keyBinds.inGameKeys();
        }
        //pathfinding
        profiler.startProfiling("pathfinding", Profiler.PROFILE_TIME);
        if (!pathFinder.requestQueue.isEmpty()) {
            pathFinder.requestQueue.get(0).getPath();
            pathFinder.requestQueue.remove(0);
        }
        maxCost = maxCost();
        minCost = minCost(maxCost);
        profiler.finishProfiling("pathfinding");

        machine.update();
        updateParticles();
        updateProjectiles();
        updateEnemies();
        profiler.startProfiling("updating: tiles", Profiler.PROFILE_TIME);
        //tiles
        if (connectWallQueues > 0) {
            connectWallQueues = 0;
            Tile.updateWallTileConnections();
        }
        //main background
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).baseLayer.update();
        }
        profiler.finishProfiling("updating: tiles");
        //turret top
        profiler.startProfiling("updating: towers", Profiler.PROFILE_TIME);
        for (Tower tower : towers) if (tower instanceof Turret) tower.controlAnimation();
        for (Tower tower : towers) tower.update();
        profiler.finishProfiling("updating: towers");

        //ui
        profiler.startProfiling("updating: ui", Profiler.PROFILE_TIME);
        hand.update();
        for (int i = popupTexts.size()-1; i >= 0; i--) popupTexts.get(i).update();
        if (isPlaying) levels[currentLevel].update();
        if (isPaused && !isSettings) pauseGui.update();
        if (!isShowSpawn) {
            if (!isLevelBuilder) {
                inGameGui.update();
            } else {
                levelBuilderGui.update();
            }
            waveStack.update();
        }
        profiler.finishProfiling("updating: ui");
    }

    /** Update everything that has to do with enemies; enemies, corpses, ice checks and buffs **/
    private void updateEnemies() {
        profiler.startProfiling("updating: enemies", Profiler.PROFILE_TIME);
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
        Utilities.cap_array(p, Corpse.CAP, corpses);
        //reset enemy ice checking
        for (Enemy enemy : enemies) enemy.intersectingIceCount = 0;
        //buffs
        for (int i = buffs.size() - 1; i >= 0; i--) {
            Buff buff = buffs.get(i);
            buff.update(i);
        }
        profiler.finishProfiling("updating: enemies");
    }

    /** Updates everything considered a projectile; classical projectiles, arcs and shockwaves **/
    private void updateProjectiles() {
        profiler.startProfiling("updating: projectiles", Profiler.PROFILE_TIME);
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
        profiler.finishProfiling("updating: projectiles");
    }

    /** Updates all the different layers of particles, and handles particle culling **/
    private void updateParticles() {
        profiler.startProfiling("updating: particles", Profiler.PROFILE_TIME);
        for (int i = tileParticles.size() - 1; i >= 0; i--) {
            tileParticles.get(i).update(tileParticles, i);
        }
        for (int i = bottomParticles.size()-1; i >= 0; i--) {
            Particle particle = bottomParticles.get(i);
            particle.update(bottomParticles, i);
        }
        for (int i = towerParticles.size()-1; i >= 0; i--) {
            Particle particle = towerParticles.get(i);
            particle.update(towerParticles, i);
        }
        for (int i = topParticles.size()-1; i >= 0; i--) {
            Particle particle = topParticles.get(i);
            particle.update(topParticles, i);
        }
        //particle culling
        Utilities.cap_array(p, Particle.CAP, tileParticles);
        Utilities.cap_array(p, Particle.CAP, bottomParticles);
        Utilities.cap_array(p, Particle.CAP, towerParticles);
        Utilities.cap_array(p, Particle.CAP, bottomParticles);
        profiler.finishProfiling("updating: particles");
    }

    /** Display in game stuff */
    public void display() {
        //fullscreen stuff
        p.pushMatrix();
        if (hasVerticalBars) p.translate(matrixOffset, 0);
        else p.translate(0, matrixOffset);
        p.scale(matrixScale);

        // offset for left in-game-ui
        p.pushMatrix();
        p.translate(200, 0);

        displayGameObjects();
        profiler.startProfiling("drawing: ui", Profiler.PROFILE_TIME);
        displayUpgradePrompts();
        displayHPBars();
        hand.displayHeld();
        for (main.gui.guiObjects.PopupText popupText : popupTexts) popupText.display();
        displayInGameGui();
        profiler.finishProfiling("drawing: ui");

        p.popMatrix();
        p.popMatrix();

        if (isPaused && !isSettings) pauseGui.display();
    }

    /** Displays all the UI elements that stick to the screen and scale with window size **/
    private void displayInGameGui() {
        p.noStroke();
        if (!isShowSpawn) {
            if (!isLevelBuilder) inGameGui.display();
            else levelBuilderGui.display();
            waveStack.display();
            hand.displayHeldInfo();
            p.textAlign(LEFT);
            if (!isLevelBuilder) inGameGui.displayText(p, 10);
        } if (isDev) inGameGui.displayDebugText(p, 10);
        if (isPaused) { //grey stuff
            p.noStroke();
            if (!alive) p.fill(50, 0, 0, 50);
            else p.fill(0, 0, 0, 50);
            p.rect(-200, 0, p.width + 200, p.height);
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

    private void displayUpgradePrompts() {
        for (Tower tower : towers) {
            if (tower instanceof Turret) ((Turret) tower).displayUpgradePrompt();
        }
    }

    /** Displays everything that is within the game "window" */
    private void displayGameObjects() {
        profiler.startProfiling("drawing: game objects", Profiler.PROFILE_TIME);

        profiler.startProfiling("drawing: background tiles", Profiler.PROFILE_TIME);
        displayBackgroundTiles();
        if (isDebug) displayPathfindingDebug();
        for (Particle particle : bottomParticles) particle.display();
        profiler.finishProfiling("drawing: background tiles");

        profiler.startProfiling("drawing: corpses", Profiler.PROFILE_TIME);
        for (Corpse corpse : corpses) corpse.display();
        profiler.finishProfiling("drawing: corpses");

        machine.display();

        profiler.startProfiling("drawing: grounded enemies", Profiler.PROFILE_TIME);
        for (Enemy enemy : enemies) if (!(enemy instanceof FlyingEnemy)) enemy.displayShadow();
        for (Enemy enemy : enemies) if (!(enemy instanceof FlyingEnemy)) enemy.display();
        profiler.finishProfiling("drawing: grounded enemies");

        profiler.startProfiling("drawing: towers", Profiler.PROFILE_TIME);
        for (Tower tower : towers) if (tower instanceof Turret) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.displayBase();
        for (Tower tower : towers) if (tower instanceof Wall) tower.controlAnimation();
        for (Particle particle : towerParticles) particle.display();
        for (Tower tower : towers) if (tower instanceof Turret) ((Turret) tower).displayTop();
        profiler.finishProfiling("drawing: towers");

        IntStream.range(0, tiles.size()).forEach(i -> tiles.get(i).obstacleLayer.display());

        profiler.startProfiling("drawing: projectiles", Profiler.PROFILE_TIME);
        for (Projectile projectile : projectiles) projectile.displayShadow();
        for (Projectile projectile : projectiles) projectile.display();
        for (Shockwave shockwave : shockwaves) shockwave.display();
        for (Arc arc : arcs) arc.display();
        profiler.finishProfiling("drawing: projectiles");

        profiler.startProfiling("drawing: flying enemies", Profiler.PROFILE_TIME);
        for (Enemy flying : enemies) if (flying instanceof FlyingEnemy) flying.displayShadow();
        for (Enemy flying : enemies) if (flying instanceof FlyingEnemy) flying.display();
        profiler.finishProfiling("drawing: flying enemies");

        for (Buff buff : buffs) buff.display();
        for (Particle particle : topParticles) particle.display();

        profiler.finishProfiling("drawing: game objects");
    }

    /** Displays tile base, the lowest particle layer, flooring, decorations and obstacle shadows **/
    private void displayBackgroundTiles() {
        //main background
        int bound = tiles.size();
        for (int i1 = 0; i1 < bound; i1++) {
            tiles.get(i1).baseLayer.display();
        }
        //very bottom particles
        for (Particle particle : tileParticles) particle.display();
        //decoration
        int bound1 = tiles.size();
        for (int i1 = 0; i1 < bound1; i1++) {
            tiles.get(i1).decorationLayer.display();
        }
        //flooring
        int bound2 = tiles.size();
        for (int i1 = 0; i1 < bound2; i1++) {
            tiles.get(i1).flooringLayer.display();
        }
        Tile.displayAllConcreteFlooring();
        //breakables
        int bound3 = tiles.size();
        for (int i1 = 0; i1 < bound3; i1++) {
            tiles.get(i1).breakableLayer.display();
        }
        //obstacle shadows
        int bound4 = tiles.size();
        for (int i = 0; i < bound4; i++) {
            tiles.get(i).obstacleLayer.displayShadow();
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
        towerParticles = new ArrayList<>();
        bottomParticles = new ArrayList<>();
        tileParticles = new ArrayList<>();
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
        levels[0] = new Level(ForestWaves.genForestWaves(p),
                "levels/forest",     125,  50,  "dirt");
        levels[1] = new Level(DesertWaves.genDesertWaves(p),
                "levels/desert",     250,  75,  "sand");
        levels[2] = new Level(CaveWaves.genCaveWaves(p),
                "levels/cave",       500,  100, "stone");
        levels[3] = new Level(GlacierWaves.genGlacierWaves(p),
                "levels/glacier",    1500, 200, "snow");
        levels[4] = new Level(DeepForestWaves.genDeepForestWaves(p),
                "levels/deepForest", 2500, 350, "dirt");
        LayoutLoader.loadLayout(p, levels[currentLevel].layout);
        money = levels[currentLevel].startingCash;
        updateAll();
        //gui stuff
        hand = new Hand(p);
        selection = new Selection(p);
        inGameGui = new InGameGui(p);
        waveStack = new WaveStack(p);
        levelBuilderGui = new LevelBuilderGui(p);
        pauseGui = new PauseGui(p);
        //other
        hasWon = false;
        connectWallQueues = 0;
    }
}
