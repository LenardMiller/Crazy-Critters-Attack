package main;

import main.buffs.Buff;
import main.buffs.Burning;
import main.buffs.Poisoned;
import main.buffs.Wet;
import main.enemies.*;
import main.gui.Gui;
import main.gui.Hand;
import main.gui.Selection;
import main.guiObjects.Icon;
import main.particles.*;
import main.pathfinding.Fuzzer;
import main.pathfinding.HeapNode;
import main.pathfinding.Node;
import main.pathfinding.AStar;
import main.projectiles.*;
import main.towers.DevWall;
import main.towers.Tower;
import main.util.CompressArray;
import main.util.EnemyTracker;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

import static main.util.SpriteLoader.loadSprites;
import static main.util.SpriteLoader.loadSpritesAnim;

public class Main extends PApplet {

    public static EnemyTracker enemyTracker;

    public static ArrayList<main.enemies.Enemy> enemies;
    public static ArrayList<Tower> towers;
    public static ArrayList<main.projectiles.Projectile> projectiles;
    public static ArrayList<main.particles.Particle> particles;
    public static ArrayList<Icon> icons; //todo: rename class to GuiObject
    public static ArrayList<Buff> buffs;

    private Gui gui;
    public static CompressArray compress;

    public static Icon towerTabButton;
    public static Icon sellButton;
    public static Icon targetButton;
    public static Icon repairButton;
    public static Icon upgradeButtonZero;
    public static Icon upgradeButtonOne;
    public static Icon upgradeIconZero;
    public static Icon upgradeIconOne;

    public static Hand hand;
    public static Selection selection;

    public static int backRed = 25;
    public static int redSpeed = 8;

    public static PFont veryLargeFont;
    public static PFont largeFont;
    public static PFont mediumFont;
    public static PFont smallFont;

    public static int hp = 2000;
    public static int money = 2000;
    public static boolean alive = true;

    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 900;
    public static final int GRID_WIDTH = 700;
    public static final int GRID_HEIGHT = 1100;

    public static HashMap<String,PImage> spritesH = new HashMap<>();
    public static HashMap<String,PImage[]> spritesAnimH = new HashMap<>();

    //pathfinding stuff
    public static Node[][] nodeGrid;
    public static HeapNode openNodes;
    public static Node start;
    public static Node[] end;
    public static AStar path;
    public static Fuzzer fuzz;
    public static int nSize;
    public static int numEnd;
    public static boolean pathLines = false;

    public static void main(String[] args) { PApplet.main("main.Main",args);}

    public void settings() {
        size(900,900);
    }

    public void setup() {
        veryLargeFont = createFont("STHeitiSC-Light", 48);
        largeFont = createFont("STHeitiSC-Light", 24);
        mediumFont = createFont("STHeitiSC-Light", 18);
        smallFont = createFont("STHeitiSC-Light", 12);
        //creates ArrayLists
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        particles = new ArrayList<>();
        icons = new ArrayList<>();
        buffs = new ArrayList<>();
        //loads sprites
        loadSprites(this);
        loadSpritesAnim(this);
        //other stuff
        hand = new Hand(this);
        selection = new Selection(this);
        enemyTracker = new EnemyTracker(this);
        gui = new Gui(this);
        //pathfinding stuff
        nSize = 10;
        nodeGrid = new Node[GRID_WIDTH /nSize][GRID_HEIGHT /nSize];
        for (int x = 0; x < GRID_WIDTH /nSize; x++){
            for (int y = 0; y < GRID_HEIGHT /nSize; y++){
                nodeGrid[x][y] = new Node(this,new PVector(nSize*x,(nSize*y)-100));
            }
        }
        path = new AStar();
        openNodes = new HeapNode((int)(sq(GRID_WIDTH /nSize)));
        end = new Node[(int)(sq(1000/nSize))];
        for (int i = (GRID_WIDTH /nSize)-1; i >= 0; i--){
            nodeGrid[i][(GRID_HEIGHT /nSize)-1].setEnd(i,(GRID_HEIGHT /nSize)-1);
        }
        nodeGrid[1][(GRID_WIDTH /nSize)/2].setStart(1,(GRID_HEIGHT /nSize)/2);
        start.findGHF();
        for (int i = 0; i < numEnd; i++){
            end[i].findGHF();
        }
        AStar.updateNodes(start);
        path.updatePath();
    }

    public void draw() {
        //bg
        noStroke();
        fill(backRed,25,25);
        rect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        backRed -= redSpeed;
        //keys
        debugKeys();
        spawnKeys();
        //pathfinding
        if (path.reqQ.size() > 0){
            path.reqQ.get(0).getPath();
            path.reqQ.remove(0);
        }
        //self explanitory
        drawObjects(enemies,projectiles,towers,particles,buffs);
        //bg part 2: red todo: this will need to be redone when bg textures are thrown in
        if (backRed < 25 ){
            backRed = 25;
            redSpeed = 8;
        }
        //gui stuff
        noStroke();
        fill(200);
        rect(BOARD_WIDTH, 0, BOARD_WIDTH + 250, BOARD_HEIGHT);
        gui.drawIcons();
        //text
        textAlign(LEFT);
        gui.drawText(this,10);
    }

    private void drawObjects(ArrayList <Enemy> enemies, ArrayList<Projectile> projectiles, ArrayList<Tower> towers, ArrayList<Particle> particles, ArrayList<Buff> buffs){
        //enemy tracker
        enemyTracker.main(enemies);
        //towers
        for (int i = towers.size()-1; i >= 0; i--){
            Tower tower = towers.get(i);
            tower.main(towers, i);
        }
        //enemies
        for (int i = enemies.size()-1; i >= 0; i--){
            Enemy enemy = enemies.get(i);
            enemy.main(i);
        }
        if (enemies.size() == 0){
            buffs = new ArrayList<>();
        }
        //projectiles
        for (int i = projectiles.size()-1; i >= 0; i--){
            Projectile projectile = projectiles.get(i);
            projectile.main(projectiles, i);
        }
        //particles
        for (int i = particles.size()-1; i >= 0; i--){
            Particle particle = particles.get(i);
            particle.main(particles, i);
        }
        //buffs
        for (int i = buffs.size()-1; i >= 0; i--){
            Buff buff = buffs.get(i);
            buff.main(i);
        }
        //currently held
        hand.main();
    }

    //todo: redo using what I've learned from USG
    public void keyReleased() {
        //tower form: spawn x, spawn y
        if (key == 'l' && alive) { //cheaty wall
            towers.add(new DevWall(this,10*(round(mouseX/10))+60, 10*(round(mouseY/10))));
            path.nodeCheckObs();
        }
        //projectile form: spawn x, spawn y, angle
        if (key == 'q' && alive) { //pebble
            projectiles.add(new Pebble(this,mouseX, mouseY, 0, 10));
        }
        if (key == 'w' && alive) { //bolt
            projectiles.add(new Bolt(this,mouseX, mouseY, 0, 20, 2));
        }
        if (key == 'e' && alive) { //dev projectile
            projectiles.add(new DevProjectile(this,mouseX, mouseY, 0));
        }
        if (key == 'r' && alive) { //misc projectile
            projectiles.add(new MiscProjectile(this,mouseX, mouseY, 0, round(random(0,5)),6));
        }
        if (key == 't' && alive) { //energy blast
            projectiles.add(new EnergyBlast(this,mouseX, mouseY, 0, 20, 20, false));
        }
        if (key == 'T' && alive) { //energy blast
            projectiles.add(new EnergyBlast(this,mouseX, mouseY, 0, 20, 30, true));
        }
        if (key == 'y' && alive) { //magic missle
            projectiles.add(new MagicMissile(this,mouseX, mouseY, 0, 5, 0));
        }
        //enemy form: spawn x, spawn y
        if (key == '1' && alive && mouseX < BOARD_WIDTH) { //little bug
            enemies.add(new SmolBug(this,mouseX,mouseY));
            enemies.get(enemies.size()-1).requestPath(enemies.size()-1);
        }
        if (key == '2' && alive && mouseX < BOARD_WIDTH) { //medium bug
            enemies.add(new MidBug(this,mouseX,mouseY));
            enemies.get(enemies.size()-1).requestPath(enemies.size()-1);
        }
        if (key == '3' && alive && mouseX < BOARD_WIDTH) { //big bug
            enemies.add(new BigBug(this,mouseX,mouseY));
            enemies.get(enemies.size()-1).requestPath(enemies.size()-1);
        }
        if (key == '4' && alive && mouseX < BOARD_WIDTH) { //tree sprite
            enemies.add(new TreeSprite(this,mouseX,mouseY));
            enemies.get(enemies.size()-1).requestPath(enemies.size()-1);
        }
        if (key == '5' && alive && mouseX < BOARD_WIDTH) { //tree spirit
            enemies.add(new TreeSpirit(this,mouseX,mouseY));
            enemies.get(enemies.size()-1).requestPath(enemies.size()-1);
        }
        //buff form: enemy id
        if (key == ',' && alive) { //poison
            buffs.add(new Poisoned(this,(int)(random(0,enemies.size()))));
        }
        if (key == '.' && alive) { //wet
            buffs.add(new Wet(this,(int)(random(0,enemies.size()))));
        }
        if (key == '/' && alive) { //burning
            buffs.add(new Burning(this,(int)(random(0,enemies.size()))));
        }
        if (key == 'g') {
            pathLines = !pathLines;
        }
    }

    private void debugKeys() {
        //kill all enemies: s
        if (keyPressed && key == 's' && alive) {
            enemies = new ArrayList <Enemy>();
            buffs = new ArrayList <>();
            path.updatePath();
        }
        //kill all towers: d
        if (keyPressed && key == 'd' && alive) {
            towers = new ArrayList <>();
            path.nodeCheckObs();
        }
        //kill all projectiles: f
        if (keyPressed && key == 'f' && alive) {
            projectiles = new ArrayList <>();
        }
    }

    private void spawnKeys() {
        //create cheaty wall
        if (keyPressed && key == 'l' && alive) {
            if (money >= 0) {
                stroke(102,153,204);
                fill(102, 153, 204, 100);
                rect((10*(round(mouseX/10)))-60, (10*(round(mouseY/10)))-37, 120, 37);
            } else {
                stroke(255,0,0);
                fill(255,0,0,100);
                rect((10*(round(mouseX/10)))-60, (10*(round(mouseY/10)))-37, 120, 37);
            }
        }
        //particle form: spawn x, spawn y, angle
        if (keyPressed && key == 'z' && alive) { //hurt
            int num = round(random(0,2));
            String type = "redOuch";
            if (num == 0) {
                type = "redOuch";
            } else if (num == 1) {
                type = "greenOuch";
            } else if (num == 2) {
                type = "pinkOuch";
            }
            particles.add(new Ouch(this,mouseX, mouseY, random(0,360), type));
        }
        if (keyPressed && key == 'x' && alive) { //die
            particles.add(new Ouch(this,mouseX, mouseY, random(0,360), "greyPuff"));
        }
        if (keyPressed && key == 'c' && alive) { //debris
            int num = round(random(0,4));
            String type = "null";
            if (num == 0) {
                type = "wood";
            } else if (num == 1) {
                type = "stone";
            } else if (num == 2) {
                type = "metal";
            } else if (num == 3) {
                type = "crystal";
            } else if (num == 4) {
                type = "devWood";
            }
            particles.add(new Debris(this,mouseX, mouseY, random(0,360), type));
        }
        if (keyPressed && key == 'v' && alive) { //buff
            int num = floor(random(0,4.9f));
            String type = "poison";
            if (num == 0) {
                type = "poison";
            } else if (num == 1) {
                type = "water";
            } else if (num == 2) {
                type = "fire";
            } else if (num == 3) {
                type = "energy";
            } else if (num == 4) {
                type = "greenMagic";
            }
            particles.add(new BuffParticle(this,mouseX, mouseY, random(0,360), type));
        }
        if (keyPressed && key == 'b' && alive) { //medium explosion
            particles.add(new MediumExplosion(this,mouseX, mouseY, random(0,360)));
        }
        if (keyPressed && key == 'n' && alive) { //large explosion
            particles.add(new LargeExplosion(this,mouseX, mouseY, random(0,360)));
        }
    }
}
