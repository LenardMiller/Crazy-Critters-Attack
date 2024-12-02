package main.misc;

import main.particles.Ripple;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.Wall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.ResourceLoader.getResource;
import static main.misc.Utilities.roundTo;
import static main.pathfinding.PathfindingUtilities.updateAll;

public class Tile {

    public abstract static class Layer {

        protected String name;
        protected PImage sprite;

        public abstract void display();
        public abstract void set(String name);
    }
    
    public class BaseLayer extends Layer {

        PImage[] spill;
        int spillHierarchyPosition;

        public BaseLayer() {
            spill = new PImage[4];
        }

        @Override
        public void display() {
            if (sprite == null) return;
            p.image(sprite, position.x, position.y);
            spillEdges();
        }

        public void update() {
            if (name == null || isPaused) return;
            for (Ripple.Type rippleType : Ripple.Type.values()) {
                if (name.equals(rippleType.name())) spawnRipples(rippleType);
            }
        }

        /**
         * Sets the base layer of the tile.
         * @param name name of base
         */
        public void set(String name) {
            name = name.replace("Ba_Tl", "");
            this.name = name;
            sprite = getResource(name + "Ba_Tl", staticSprites);
            // nullable, don't flood output
            spill[0] = staticSprites.get(name + "Ba_T_Tl");
            spill[1] = staticSprites.get(name + "Ba_R_Tl");
            spill[2] = staticSprites.get(name + "Ba_B_Tl");
            spill[3] = staticSprites.get(name + "Ba_L_Tl");
            //update hierarchies as tiles are added
            for (int i = 0; i < baseHierarchy.length; i++) {
                if (name.equals(baseHierarchy[i])) {
                    spillHierarchyPosition = i;
                    break;
                }
            }
        }

        /**
         * @param i what edge to check
         * @param tile neighbor tile to check
         * @return whether this tile can spill its base
         */
        public boolean canSpill(int i, Tile tile) {
            if (name == null) return false;
            boolean nameDoesNotMatch = !baseLayer.name.equals(tile.baseLayer.name);
            boolean tileTypeCanSpill = tile.baseLayer.spill[i] != null;
            boolean higherHierarchy = spillHierarchyPosition < tile.baseLayer.spillHierarchyPosition;
            return nameDoesNotMatch && tileTypeCanSpill && higherHierarchy;
        }

        /**
         * Spills the edges of the base into surrounding tiles
         */
        private void spillEdges() {
            int x = (int) (position.x / 50);
            int y = (int) (position.y / 50);
            if (y != 0) {
                Tile tile = tiles.get(x, y - 1);
                if (canSpill(0, tile)) p.image(tile.baseLayer.spill[0], position.x, position.y);
            }
            if (x != 0) {
                Tile tile = tiles.get(x - 1, y);
                if (canSpill(3, tile)) p.image(tile.baseLayer.spill[3], position.x, position.y);
            }
            if (y != 18) {
                Tile tile = tiles.get(x, y + 1);
                if (canSpill(2, tile)) p.image(tile.baseLayer.spill[2], position.x, position.y);
            }
            if (x != 18) {
                Tile tile = tiles.get(x + 1, y);
                if (canSpill(1, tile)) p.image(tile.baseLayer.spill[1], position.x, position.y);
            }
        }
    }

    public class FlooringLayer extends Layer {

        PImage[] edges;
        private PImage[] outsideCorners;
        private String[] outsideCornerNames;
        private PImage[] insideCorners;
        private boolean drawMain;

        public FlooringLayer() {
            edges = new PImage[4];
            outsideCorners = new PImage[4];
            outsideCornerNames = new String[4];
            insideCorners = new PImage[4];
        }

        @Override
        public void display() {
            if (name == null) return;
            if (edges != null) connectEdges();
            if (!isConcrete(name)) {
                if (isDebug) p.tint(255,0,255);
                p.image(sprite, position.x, position.y);
                if (isDebug) p.tint(255);
            }
        }

        @Override
        public void set(String name) {
            edges = new PImage[4];
            if (name == null || baseLayer.name.equals("water")) {
                sprite = null;
                this.name = null;
            } else {
                if (breakableLayer.name != null) {
                    if (!breakableLayer.name.contains("Debris")) {
                        breakableLayer.sprite = null;
                        breakableLayer.name = null;
                    }
                }
                name = name.replace("Fl_Tl", "");
                name = name.replace("ultimate", "titanium");
                this.name = name;
                if (getResource(name + "Fl_Tl", staticSprites) != sprite) {
                    sprite = getResource(name + "Fl_Tl", staticSprites);
                    if (name.contains("woodWall") || name.contains("stoneWall")) {
                        edges[0] = getResource(name + "Fl_T_Tl", staticSprites);
                        edges[1] = getResource(name + "Fl_R_Tl", staticSprites);
                        edges[2] = getResource(name + "Fl_B_Tl", staticSprites);
                        edges[3] = getResource(name + "Fl_L_Tl", staticSprites);
                    }
                }
            }
        }

        private void connectEdges() {
            if (isDebug) p.tint(0,255,0);
            int x = (int) (position.x / 50);
            int y = (int) (position.y / 50);
            if (y != 0) {
                Tile tile = tiles.get(x, y - 1);
                if (isConnected(0, tile))
                    p.image(tile.flooringLayer.edges[0], position.x, position.y);
                if (edges[0] != null && isConcrete(tile.flooringLayer.name))
                    p.image(edges[0], position.x, position.y);
            } if (x != 0) {
                Tile tile = tiles.get(x - 1, y);
                if (isConnected(3, tile))
                    p.image(tile.flooringLayer.edges[3], position.x, position.y);
                if (edges[3] != null && isConcrete(tile.flooringLayer.name))
                    p.image(edges[3], position.x, position.y);
            } if (y != 18) {
                Tile tile = tiles.get(x, y + 1);
                if (isConnected(2, tile))
                    p.image(tile.flooringLayer.edges[2], position.x, position.y);
                if (edges[2] != null && isConcrete(tile.flooringLayer.name))
                    p.image(edges[2], position.x, position.y);
            } if (x != 18) {
                Tile tile = tiles.get(x + 1, y);
                if (isConnected(1, tile))
                    p.image(tile.flooringLayer.edges[1], position.x, position.y);
                if (edges[1] != null && isConcrete(tile.flooringLayer.name))
                    p.image(edges[1], position.x, position.y);
            }
            if (isDebug) p.tint(255);
        }

        private boolean isConnected(int i, Tile tile) {
            if (name != null && tile.flooringLayer.name != null) {
                if (!isConcrete(name)) {
                    return name.equals(tile.flooringLayer.name) && tile.flooringLayer.edges[i] != null;
                } else return tile.flooringLayer.edges[i] != null;
            } else return false;
        }

        public void connectInsideCorners() {
            insideCorners = new PImage[4];
            int x = (int) (position.x / 50);
            int y = (int) (position.y / 50);
            drawMain = true;
            if (x != 18 && y != 0) {
                if (doubleDiagonalIn(x, y, 1, -1, name)) { //tri
                    insideCorners[0] = getResource(name + "Fl_TRI_Tl", staticSprites);
                    drawMain = false;
                }
            } if (x != 18 && y != 18) {
                if (doubleDiagonalIn(x, y, 1, 1, name)) { //bri
                    insideCorners[1] = getResource(name + "Fl_BRI_Tl", staticSprites);
                    drawMain = false;
                }
            } if (x != 0 && y != 18) {
                if (doubleDiagonalIn(x, y, -1, 1, name)) { //bli
                    insideCorners[2] = getResource(name + "Fl_BLI_Tl", staticSprites);
                    drawMain = false;
                }
            } if (x != 0 && y != 0) {
                if (doubleDiagonalIn(x, y, -1, -1, name)) { //tli
                    insideCorners[3] = getResource(name + "Fl_TLI_Tl", staticSprites);
                    drawMain = false;
                }
            }
            String s = checkMissing(x,y, name);
            if (s != null) { //s
                int i = 0;
                if (s.equals("BRI")) i = 1;
                if (s.equals("BLI")) i = 2;
                if (s.equals("TLI")) i = 3;
                insideCorners[i] = getResource(name + "Fl_" + s + "_Tl", staticSprites);
                drawMain = false;
            }
            if (countTouchingVN(x,y) > 2) drawMain = true;
        }

        public void displayInsideCorners() {
            if (isConcrete(name)) {
                if (drawMain) p.image(sprite, position.x, position.y);
                for (int i = 0; i < 4; i++) {
                    if (insideCorners[i] != null) {
                        if (isDebug) p.tint(255,0,0);
                        p.image(insideCorners[i], position.x, position.y);
                        if (isDebug) p.tint(255);
                    }
                }
            }
        }

        public void displayOutsideCorners(String name) {
            for (int i = 0; i < 4; i++) {
                if (outsideCorners[i] != null) {
                    if (outsideCornerNames[i].equals(name)) {
                        if (isDebug) p.tint(0,0,255);
                        p.image(outsideCorners[i], position.x, position.y);
                        if (isDebug) p.tint(255);
                    }
                }
            }
        }

        public void connectOutsideCorners() {
            outsideCorners = new PImage[4];
            outsideCornerNames = new String[4];
            int x = (int) (position.x / 50);
            int y = (int) (position.y / 50);
            if (isDebug) p.tint(0,0,255);
            if (x != 18 && y != 0) { //tro
                String n = doubleDiagonalOut(x,y,1,-1);
                if (n != null) {
                    outsideCorners[0] = getResource(n + "Fl_TRO_Tl", staticSprites);
                    outsideCornerNames[0] = n;
                }
            } if (x != 18 && y != 18) { //bro
                String n = doubleDiagonalOut(x,y,1,1);
                if (n != null) {
                    outsideCorners[1] = getResource(n + "Fl_BRO_Tl", staticSprites);
                    outsideCornerNames[1] = n;
                }
            } if (x != 0 && y != 18) { //blo
                String n = doubleDiagonalOut(x,y,-1,1);
                if (n != null) {
                    outsideCorners[2] = getResource(n + "Fl_BLO_Tl", staticSprites);
                    outsideCornerNames[2] = n;
                }
            } if (x != 0 && y != 0) { //tlo
                String n = doubleDiagonalOut(x,y,-1,-1);
                if (n != null) {
                    outsideCorners[3] = getResource(n + "Fl_TLO_Tl", staticSprites);
                    outsideCornerNames[3] = n;
                }
            }
            if (isDebug) p.tint(255);
        }

        private String checkMissing(int x, int y, String name) {
            int numAccounted = 0;
            boolean tr;
            boolean br;
            boolean bl;
            boolean tl;
            if (!(x > 0 && x < 18 && y > 0 && y < 18)) return null;
            bl = account(x,y,1,-1,name);
            tl = account(x,y,1,1,name);
            tr = account(x,y,-1,1,name);
            br = account(x,y,-1,-1,name);
            if (bl) numAccounted++;
            if (tl) numAccounted++;
            if (tr) numAccounted++;
            if (br) numAccounted++;
            if (numAccounted < 3 || numAccounted == 4) return null;
            if (!tr) return "TRI";
            if (!br) return "BRI";
            if (!bl) return "BLI";
            return "TLI";
        }

        private boolean account(int x, int y, int dx, int dy, String name) {
            Tile tileM = tiles.get(x+dx,y+dy);
            Tile tileX = tiles.get(x+dx,y);
            Tile tileY = tiles.get(x,y+dy);
            if (tileX.flooringLayer.name != null && tileX.flooringLayer.name.equals(name)) return true;
            if (tileY.flooringLayer.name != null && tileY.flooringLayer.name.equals(name)) return true;
            return tileM.flooringLayer.name != null && tileM.flooringLayer.name.equals(name);
        }

        private int countTouchingVN(int x, int y) {
            int r = 0;
            if (y > 0) {
                Tile tile = tiles.get(x,y-1);
                if (tile.flooringLayer.name != null && tile.flooringLayer.name.equals(name)) r++;
            } if (x > 0) {
                Tile tile = tiles.get(x-1,y);
                if (tile.flooringLayer.name != null && tile.flooringLayer.name.equals(name)) r++;
            } if (x < 17) {
                Tile tile = tiles.get(x+1,y);
                if (tile.flooringLayer.name != null && tile.flooringLayer.name.equals(name)) r++;
            } if (y < 17) {
                Tile tile = tiles.get(x,y+1);
                if (tile.flooringLayer.name != null && tile.flooringLayer.name.equals(name)) r++;
            }
            return r;
        }

        private String doubleDiagonalOut(int x, int y, int dx, int dy) {
            Tile tileA = tiles.get(x + dx, y);
            Tile tileB = tiles.get(x, y + dy);
            if (!isConcrete(tileA.flooringLayer.name)) return null;
            if (!isConcrete(tileA.flooringLayer.name)) return null;
            if (!tileA.flooringLayer.name.equals(tileB.flooringLayer.name)) return null;
            else return tileA.flooringLayer.name;
        }

        private boolean doubleDiagonalIn(int x, int y, int dx, int dy, String name) {
            Tile tileA = tiles.get(x + dx, y);
            Tile tileB = tiles.get(x, y + dy);
            Tile tileC = null;
            int cx = x - dx;
            int cy = y - dy;
            if (cx <= 18 && cx >= 0 && cy <= 18 && cy >= 0) tileC = tiles.get(cx, cy);
            if (tileA.flooringLayer.name == null) return false;
            if (tileB.flooringLayer.name == null) return false;
            if (!tileA.flooringLayer.name.equals(name)) return false;
            if (tileC != null &&
                    tileC.flooringLayer.name != null &&
                    tileC.flooringLayer.name.equals(tileA.flooringLayer.name))
                return false;
            return tileB.flooringLayer.name.equals(name);
        }

        private boolean isConcrete(String name) {
            if (name == null) return false;
            boolean m = name.equals("metalWall");
            boolean c = name.equals("crystalWall");
            boolean t = name.equals("titaniumWall");
            return m || c || t;
        }

        /**
         * If there is no wall on top and new type is not same as current type, set flooring.
         * @param name type of flooring
         * @return if there wasn't a wall on top
         */
        public boolean update(String name) {
            Tower tower = tiles.get(
                    (roundTo(position.x, 50)/50) + 1,
                    (roundTo(position.y, 50)/50) + 1).tower;
            if (name.equals(this.name)) return false;
            if (tower instanceof IceWall) return true;
            if (obstacleLayer.name != null) return false;
            if (tower instanceof Wall) return false;
            else {
                set(name);
                return true;
            }
        }
    }

    public class DecorationLayer extends Layer {

        @Override
        public void display() {
            if (sprite == null) return;
            p.image(sprite, position.x, position.y);
        }

        @Override
        public void set(String name) {
            if (name == null) {
                sprite = null;
                this.name = null;
            } else {
                sprite = getResource(name, staticSprites);
                this.name = name;
            }
        }
    }

    public class BreakableLayer extends Layer {

        @Override
        public void display() {
            if (sprite == null) return;
            p.image(sprite, position.x, position.y);
        }

        @Override
        public void set(String name) {
            if (name != null) name = name.replace("ultimate","titanium");
            if (name == null) {
                sprite = null;
                this.name = null;
            } else {
                sprite = getResource(name, staticSprites);
                this.name = name;
            }
        }
    }

    public class ObstacleLayer extends Layer {

        int obstacleShadowLength;

        public ObstacleLayer() {
            obstacleShadowLength = 3;
        }

        @Override
        public void display() {
            p.tint(255);
            if (sprite == null) return;
            p.image(sprite, position.x, position.y);
            if (isDebug) {
                p.fill(Color.RED.getRGB());
                p.circle(position.x, position.y, 5);
            }
        }

        @Override
        public void set(String name) {
            if (name == null) {
                if (this.name != null) updateAll();
                sprite = null;
                this.name = null;
            } else {
                int x = getGridPosition().x;
                int y = getGridPosition().y;
                if (x < 39 && y < 39 && tiles.get(x + 1, y + 1).tower != null){
                    tiles.get(x + 1, y + 1).tower.die(false);
                }
                flooringLayer.set(null);
                breakableLayer.set(null);
                if (this.name == null) updateAll();
                sprite = getResource(name, staticSprites);
                this.name = name;
                if (name.contains("smallTree")) obstacleShadowLength = 3;
                if (name.contains("smallYellowTree")) obstacleShadowLength = 3;
                if (name.contains("smallBrownTree")) obstacleShadowLength = 3;
                if (name.contains("smallDeadTree")) obstacleShadowLength = 3;
                if (containsCorners(name,"tree")) obstacleShadowLength = 8;
                if (containsCorners(name,"yellowTree")) obstacleShadowLength = 8;
                if (containsCorners(name,"brownTree")) obstacleShadowLength = 8;
                if (containsCorners(name,"deadTree")) obstacleShadowLength = 8;
            }
        }

        public void displayShadow() {
            if (sprite == null) return;
            p.tint(0, 60);
            p.image(sprite, position.x + obstacleShadowLength, position.y + obstacleShadowLength);
            p.tint(255);
        }

        public boolean exists() {
            return sprite != null;
        }
    }

    /** lowest at top, highest at bottom */
    private static final String[] baseHierarchy = {
            "sludge",
            "dirtyWater",
            "water",
            "stone",
            "sand",
            "dirt",
            "deadGrass",
            "brownGrass",
            "yellowGrass",
            "grass",
            "pinkSnow",
            "snow"
    };

    private final PApplet p;

    public boolean machine;
    public int id;
    public PVector position;
    public Tower tower;
    public BaseLayer baseLayer;
    public FlooringLayer flooringLayer;
    public DecorationLayer decorationLayer;
    public BreakableLayer breakableLayer;
    public ObstacleLayer obstacleLayer;

    public Tile(PApplet p, PVector position, int id) {
        this.p = p;

        this.position = position;
        this.id = id;
        baseLayer = new BaseLayer();
        flooringLayer = new FlooringLayer();
        decorationLayer = new DecorationLayer();
        breakableLayer = new BreakableLayer();
        obstacleLayer = new ObstacleLayer();
    }

    public PVector getCenter() {
        return PVector.sub(position, new PVector(TILE_SIZE / 2f, TILE_SIZE / 2f));
    }

    private void spawnRipples(Ripple.Type type) {
        int chance = 60;
        if (type.equals(Ripple.Type.sludge)) chance = 180;
        if (p.random(chance) < 1) {
            Tile rightTile = tiles.get(getGridPosition().x + 1, getGridPosition().y);
            boolean right = rightTile == null;
            if (!right && rightTile.baseLayer.name != null) right = !rightTile.baseLayer.name.equals(type.name());
            Tile leftTile = tiles.get(getGridPosition().x - 1, getGridPosition().y);
            boolean left = rightTile == null;
            if (!left && leftTile.baseLayer.name != null) left = !leftTile.baseLayer.name.equals(type.name());
            PVector topLeftCorner = new PVector(!left ? position.x : position.x + 20, position.y + 7);
            PVector bottomRightCorner = PVector.add(
                    position,
                    new PVector(!right ? TILE_SIZE : TILE_SIZE - 20, TILE_SIZE - 7));
            PVector spawnPos = new PVector(
                    p.random(topLeftCorner.x, bottomRightCorner.x),
                    p.random(topLeftCorner.y, bottomRightCorner.y));
            tileParticles.add(new Ripple(p, spawnPos.x, spawnPos.y, type));
        }
    }

    /**
     * Refreshes connected tiles for all towers
     */
    public static void updateTowerArray() {
        towers = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            Tower tower = tiles.get(i).tower;
            if (tower != null) {
                towers.add(tower);
                if (tower instanceof Wall) tower.updateSprite();
            }
        }
    }

    // Flooring --------------------------------------------------

    public static void displayAllConcreteFlooring() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringLayer.name != null) {
                if (tile.flooringLayer.name.equals("metalWall")) {
                    tile.flooringLayer.displayInsideCorners();
                }
            }
            tile.flooringLayer.displayOutsideCorners("metalWall");
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringLayer.name != null) {
                if (tile.flooringLayer.name.equals("crystalWall")) {
                    tile.flooringLayer.displayInsideCorners();
                }
            }
            tile.flooringLayer.displayOutsideCorners("crystalWall");
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringLayer.name != null) {
                if (tile.flooringLayer.name.equals("titaniumWall")) {
                    tile.flooringLayer.displayInsideCorners();
                }
            }
            tile.flooringLayer.displayOutsideCorners("titaniumWall");
        }
    }

    /**
     * Updates wall flooring.
     * Goes through every tile over and over again,
     * if there are 4 tiles with flooring nearby set flooring to that,
     * until it does nothing in a whole loop.
     */
    public static void updateFlooring() {
        boolean didSomething;
        do {
            didSomething = false;
            String[][] typeGrid = getTypeGrid();
            for (int x = 0; x < 18; x++) {
                for (int y = 0; y < 18; y++) {
                    Tile tile = tiles.get(x, y);
                    int[] typesTouching = getTypesTouching(typeGrid, x, y);
                    int numFlooringTilesTouching = 0;
                    for (int numOfType : typesTouching)
                        numFlooringTilesTouching += numOfType;
                    if (numFlooringTilesTouching >= 4)
                        didSomething = tile.flooringLayer.update(getMostCommonType(typesTouching));
                }
            }
        } while (didSomething);
    }

    /**
     * @param typeGrid a grid of the flooring types of all tiles
     * @param x x position to check
     * @param y y position to check
     * @return an array of the number of floorings of each type touching the tile at checked position
     */
    private static int[] getTypesTouching(String[][] typeGrid, int x, int y) {
        int[] nameCount = new int[5];
        nameCount[0] = countType(typeGrid, x, y, "woodWall");
        nameCount[1] = countType(typeGrid, x, y, "stoneWall");
        nameCount[2] = countType(typeGrid, x, y, "metalWall");
        nameCount[3] = countType(typeGrid, x, y, "crystalWall");
        nameCount[4] = countType(typeGrid, x, y, "titaniumWall");
        return nameCount;
    }

    /**
     * Counts the number of touching walls with the same name.
     * @param nameGrid a grid of wall names
     * @param x wall x position
     * @param y wall y position
     * @param name wall name
     * @return the number of touching walls with the same name
     */
    private static int countType(String[][] nameGrid, int x, int y, String name) {
        int r = 0;
        if (x > 0 && y > 0) if (name.equals(nameGrid[x - 1][y - 1])) r++;
        if (y > 0) if (name.equals(nameGrid[x][y - 1])) r++;
        if (x < 17 && y > 0) if (name.equals(nameGrid[x + 1][y - 1])) r++;
        if (x > 0) if (name.equals(nameGrid[x - 1][y])) r++;
        if (x < 17) if (name.equals(nameGrid[x + 1][y])) r++;
        if (x > 0 && y < 17) if (name.equals(nameGrid[x - 1][y + 1])) r++;
        if (y < 17) if (name.equals(nameGrid[x][y + 1])) r++;
        if (x < 17 && y < 17) if (name.equals(nameGrid[x + 1][y + 1])) r++;
        return r;
    }

    /**
     * @param nameCount the number of floorings of each type touching the tile
     * @return the most common type of flooring
     */
    private static String getMostCommonType(int[] nameCount) {
        int mostCommonTypeId = getMostCommonNameId(nameCount);
        String type = "woodWall";
        if (mostCommonTypeId == 1) type = "stoneWall";
        if (mostCommonTypeId == 2) type = "metalWall";
        if (mostCommonTypeId == 3) type = "crystalWall";
        if (mostCommonTypeId == 4) type = "titaniumWall";
        return type;
    }

    /**
     * @param nameCount the number of floorings of each type touching the tile
     * @return a numeric id associated with the most common type of flooring
     */
    private static int getMostCommonNameId(int[] nameCount) {
        int mostCommonName = 0;
        int numNamesOfMostCommonName = 0;
        for (int i = 0; i < 5; i++) {
            if (nameCount[i] >= numNamesOfMostCommonName) {
                numNamesOfMostCommonName = nameCount[i];
                mostCommonName = i;
            }
        }
        return mostCommonName;
    }

    /**
     * @return a grid of the flooring types of every tile
     */
    private static String[][] getTypeGrid() {
        String[][] nameGrid = new String[18][18];
        for (int x = 0; x < 18; x++) {
            for (int y = 0; y < 18; y++) {
                Tile tile = tiles.get(x, y);
                if (tile.flooringLayer.sprite != null) nameGrid[x][y] = tile.flooringLayer.name;
            }
        }
        return nameGrid;
    }

    /**
     * Something to do with wall tile flooring?
     */
    public static void updateWallTileConnections() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringLayer.name != null &&
                    !tile.flooringLayer.name.equals("stoneWall") &&
                    !tile.flooringLayer.name.equals("woodWall")) {
                tile.flooringLayer.connectInsideCorners();
            }
            tile.flooringLayer.connectOutsideCorners();
        }
    }

    // End flooring --------------------------------------------------

    /** This has to do with obstacles */
    public static boolean containsCorners(String name, String subName) {
        if (name == null) return false;
        boolean bl = name.contains(subName + "BL");
        boolean br = name.contains(subName + "BR");
        boolean tl = name.contains(subName + "TL");
        boolean tr = name.contains(subName + "TR");
        return bl || br || tl || tr;
    }

    public IntVector getGridPosition() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        return new IntVector(x, y);
    }

    public static class TileDS {

        public TileDSItem[] items;

        public TileDS() {
            items = new TileDSItem[0];
        }

        public Tile get(int id) {
            return items[id].tile;
        }

        public Tile get(int x, int y) {
            Tile r = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) r = item.tile;
            return r;
        }

        public Tile get(IntVector gridPosition) {
            return get(gridPosition.x, gridPosition.y);
        }

        public void add(Tile tile, int x, int y) {
            TileDSItem[] newItems = new TileDSItem[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new TileDSItem(tile, x, y);
            items = newItems;
        }

        public int size() {
            return items.length;
        }

        public void remove(int id) {
            TileDSItem removeItem = items[id];
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(int x, int y) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(Tile tile) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.tile == tile) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public static class TileDSItem {

            public Tile tile;
            public int x;
            public int y;

            public TileDSItem(Tile tile, int x, int y) {
                this.tile = tile;
                this.x = x;
                this.y = y;
            }
        }
    }
}
