package main.misc;

import main.particles.Ripple;
import main.pathfinding.Node;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.Wall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.roundTo;
import static main.pathfinding.PathfindingUtilities.updateNodes;

public class Tile {

    private final PApplet P;

    public boolean machine;
    public int id;
    public PVector position;
    public Tower tower;
    public PImage base;
    public String baseName;
    public PImage flooring;
    public String flooringName;
    public PImage decoration;
    public String decorationName;
    public PImage breakable;
    public String breakableName;
    public PImage obstacle;
    public String obstacleName;

    int baseHierarchy;
    PImage[] baseEdges;
    PImage[] flooringEdges;

    private int obstacleShadowLength;
    private boolean drawMain;
    private PImage[] flooringOutsideCorners;
    private String[] flooringOutsideCornerNames;
    private PImage[] flooringInsideCorners;

    public Tile(PApplet p, PVector position, int id) {
        this.P = p;

        this.position = position;
        this.id = id;
        obstacleShadowLength = 3;
        baseEdges = new PImage[4];
        flooringEdges = new PImage[4];
        flooringOutsideCorners = new PImage[4];
        flooringOutsideCornerNames = new String[4];
        flooringInsideCorners = new PImage[4];
    }

    public PVector getCenter() {
        return PVector.sub(position, new PVector(TILE_SIZE / 2f, TILE_SIZE / 2f));
    }

    public void main() {
        if (tower != null) tower.main();
    }

    public void displayDecorationAndFlooring() {
        if (decoration != null) P.image(decoration, position.x, position.y);
        if (flooringEdges != null) connectFlooringEdges();
        if (flooringName != null) {
            if (!isConcrete(flooringName)) {
                if (debug) P.tint(255,0,255);
                P.image(flooring, position.x, position.y);
                if (debug) P.tint(255);
            }
        }
    }

    public void displayBase() {
        if (base != null) P.image(base, position.x, position.y);
        if (baseName != null && !paused) {
            if (baseName.equals("water")) spawnRipples("water");
            if (baseName.equals("dirtyWater")) spawnRipples("dirtyWater");
        }
        spillBaseEdges();
    }

    private void spawnRipples(String type) {
        if (P.random(60) < 1) {
            Tile rightTile = tiles.get(getGridPosition().x + 1, getGridPosition().y);
            boolean right = rightTile == null;
            if (!right && rightTile.baseName != null) right = !rightTile.baseName.equals(type);
            Tile leftTile = tiles.get(getGridPosition().x - 1, getGridPosition().y);
            boolean left = rightTile == null;
            if (!left && leftTile.baseName != null) left = !leftTile.baseName.equals(type);
            PVector topLeftCorner = new PVector(!left ? position.x : position.x + 20, position.y + 7);
            PVector bottomRightCorner = PVector.add(position, new PVector(!right ? TILE_SIZE : TILE_SIZE - 20, TILE_SIZE - 7));
            PVector spawnPos = new PVector(P.random(topLeftCorner.x, bottomRightCorner.x), P.random(topLeftCorner.y, bottomRightCorner.y));
            veryBottomParticles.add(new Ripple(P, spawnPos.x, spawnPos.y, type));
        }
    }

    public void displayBreakableAndShadow() {
        if (breakable != null) P.image(breakable, position.x, position.y);
        if (obstacle != null) {
            P.tint(0, 60);
            P.image(obstacle, position.x + obstacleShadowLength, position.y + obstacleShadowLength);
            P.tint(255);
        }
    }

    public void displayObstacle() {
        P.tint(255);
        if (obstacle != null) P.image(obstacle, position.x, position.y);
    }

    /**
     * Spills the edges of the base into surrounding tiles
     */
    private void spillBaseEdges() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (canSpill(0, tile)) P.image(tile.baseEdges[0], position.x, position.y);
        }
        if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (canSpill(3, tile)) P.image(tile.baseEdges[3], position.x, position.y);
        }
        if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (canSpill(2, tile)) P.image(tile.baseEdges[2], position.x, position.y);
        }
        if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (canSpill(1, tile)) P.image(tile.baseEdges[1], position.x, position.y);
        }
    }

    /**
     * @param i what edge to check
     * @param tile neighbor tile to check
     * @return whether this tile can spill its base
     */
    private boolean canSpill(int i, Tile tile) {
        if (baseName == null) return false;
        boolean nameDoesNotMatch = !baseName.equals(tile.baseName);
        boolean tileTypeCanSpill = tile.baseEdges[i] != null;
        boolean higherHierarchy = baseHierarchy < tile.baseHierarchy;
        return nameDoesNotMatch && tileTypeCanSpill && higherHierarchy;
    }

    private void connectFlooringEdges() {
        if (debug) P.tint(0,255,0);
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (isConnected(0, tile)) P.image(tile.flooringEdges[0], position.x, position.y);
            if (flooringEdges[0] != null && isConcrete(tile.flooringName)) P.image(flooringEdges[0], position.x, position.y);
        } if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (isConnected(3, tile)) P.image(tile.flooringEdges[3], position.x, position.y);
            if (flooringEdges[3] != null && isConcrete(tile.flooringName)) P.image(flooringEdges[3], position.x, position.y);
        } if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (isConnected(2, tile)) P.image(tile.flooringEdges[2], position.x, position.y);
            if (flooringEdges[2] != null && isConcrete(tile.flooringName)) P.image(flooringEdges[2], position.x, position.y);
        } if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (isConnected(1, tile)) P.image(tile.flooringEdges[1], position.x, position.y);
            if (flooringEdges[1] != null && isConcrete(tile.flooringName)) P.image(flooringEdges[1], position.x, position.y);
        }
        if (debug) P.tint(255);
    }

    private boolean isConnected(int i, Tile tile) {
        if (flooringName != null && tile.flooringName != null) {
            if (!isConcrete(flooringName)) {
                return flooringName.equals(tile.flooringName) && tile.flooringEdges[i] != null;
            } else return tile.flooringEdges[i] != null;
        } else return false;
    }

    public void connectFlooringInsideCorners() {
        flooringInsideCorners = new PImage[4];
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        drawMain = true;
        if (x != 18 && y != 0) {
            if (doubleDiagonalIn(x, y, 1, -1, flooringName)) { //tri
                flooringInsideCorners[0] = staticSprites.get(flooringName + "Fl_TRI_TL");
                drawMain = false;
            }
        } if (x != 18 && y != 18) {
            if (doubleDiagonalIn(x, y, 1, 1, flooringName)) { //bri
                flooringInsideCorners[1] = staticSprites.get(flooringName + "Fl_BRI_TL");
                drawMain = false;
            }
        } if (x != 0 && y != 18) {
            if (doubleDiagonalIn(x, y, -1, 1, flooringName)) { //bli
                flooringInsideCorners[2] = staticSprites.get(flooringName + "Fl_BLI_TL");
                drawMain = false;
            }
        } if (x != 0 && y != 0) {
            if (doubleDiagonalIn(x, y, -1, -1, flooringName)) { //tli
                flooringInsideCorners[3] = staticSprites.get(flooringName + "Fl_TLI_TL");
                drawMain = false;
            }
        }
        String s = checkMissing(x,y, flooringName);
        if (s != null) { //s
            int i = 0;
            if (s.equals("BRI")) i = 1;
            if (s.equals("BLI")) i = 2;
            if (s.equals("TLI")) i = 3;
            flooringInsideCorners[i] = staticSprites.get(flooringName + "Fl_" + s + "_TL");
            drawMain = false;
        }
        if (countTouchingVN(x,y) > 2) drawMain = true;
    }

    public void displayFlooringInsideCorners() {
        if (isConcrete(flooringName)) {
            if (drawMain) P.image(flooring, position.x, position.y);
            for (int i = 0; i < 4; i++) {
                if (flooringInsideCorners[i] != null) {
                    if (debug) P.tint(255,0,0);
                    P.image(flooringInsideCorners[i], position.x, position.y);
                    if (debug) P.tint(255);
                }
            }
        }
    }

    public void displayFlooringOutsideCorners(String name) {
        for (int i = 0; i < 4; i++) {
            if (flooringOutsideCorners[i] != null) {
                if (flooringOutsideCornerNames[i].equals(name)) {
                    if (debug) P.tint(0,0,255);
                    P.image(flooringOutsideCorners[i], position.x, position.y);
                    if (debug) P.tint(255);
                }
            }
        }
    }

    public void connectFlooringOutsideCorners() {
        flooringOutsideCorners = new PImage[4];
        flooringOutsideCornerNames = new String[4];
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (debug) P.tint(0,0,255);
        if (x != 18 && y != 0) { //tro
            String n = doubleDiagonalOut(x,y,1,-1);
            if (n != null) {
                flooringOutsideCorners[0] = staticSprites.get(n + "Fl_TRO_TL");
                flooringOutsideCornerNames[0] = n;
            }
        } if (x != 18 && y != 18) { //bro
            String n = doubleDiagonalOut(x,y,1,1);
            if (n != null) {
                flooringOutsideCorners[1] = staticSprites.get(n + "Fl_BRO_TL");
                flooringOutsideCornerNames[1] = n;
            }
        } if (x != 0 && y != 18) { //blo
            String n = doubleDiagonalOut(x,y,-1,1);
            if (n != null) {
                flooringOutsideCorners[2] = staticSprites.get(n + "Fl_BLO_TL");
                flooringOutsideCornerNames[2] = n;
            }
        } if (x != 0 && y != 0) { //tlo
            String n = doubleDiagonalOut(x,y,-1,-1);
            if (n != null) {
                flooringOutsideCorners[3] = staticSprites.get(n + "Fl_TLO_TL");
                flooringOutsideCornerNames[3] = n;
            }
        }
        if (debug) P.tint(255);
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
        if (tileX.flooringName != null && tileX.flooringName.equals(name)) return true;
        if (tileY.flooringName != null && tileY.flooringName.equals(name)) return true;
        return tileM.flooringName != null && tileM.flooringName.equals(name);
    }

    private int countTouchingVN(int x, int y) {
        int r = 0;
        if (y > 0) {
            Tile tile = tiles.get(x,y-1);
            if (tile.flooringName != null && tile.flooringName.equals(flooringName)) r++;
        } if (x > 0) {
            Tile tile = tiles.get(x-1,y);
            if (tile.flooringName != null && tile.flooringName.equals(flooringName)) r++;
        } if (x < 17) {
            Tile tile = tiles.get(x+1,y);
            if (tile.flooringName != null && tile.flooringName.equals(flooringName)) r++;
        } if (y < 17) {
            Tile tile = tiles.get(x,y+1);
            if (tile.flooringName != null && tile.flooringName.equals(flooringName)) r++;
        }
        return r;
    }

    private String doubleDiagonalOut(int x, int y, int dx, int dy) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        if (!isConcrete(tileA.flooringName)) return null;
        if (!isConcrete(tileA.flooringName)) return null;
        if (!tileA.flooringName.equals(tileB.flooringName)) return null;
        else return tileA.flooringName;
    }

    private boolean doubleDiagonalIn(int x, int y, int dx, int dy, String name) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        Tile tileC = null;
        int cx = x - dx;
        int cy = y - dy;
        if (cx <= 18 && cx >= 0 && cy <= 18 && cy >= 0) tileC = tiles.get(cx, cy);
        if (tileA.flooringName == null) return false;
        if (tileB.flooringName == null) return false;
        if (!tileA.flooringName.equals(name)) return false;
        if (tileC != null && tileC.flooringName != null && tileC.flooringName.equals(tileA.flooringName)) return false;
        return tileB.flooringName.equals(name);
    }

    private boolean isConcrete(String name) {
        if (name == null) return false;
        boolean m = name.equals("metalWall");
        boolean c = name.equals("crystalWall");
        boolean t = name.equals("titaniumWall");
        return m || c || t;
    }

    /**
     * Sets the base layer of the tile.
     * @param name name of base
     */
    public void setBase(String name) {
        name = name.replace("Ba_TL", "");
        baseName = name;
        base = staticSprites.get(name + "Ba_TL");
        baseEdges[0] = staticSprites.get(name + "Ba_T_TL");
        baseEdges[1] = staticSprites.get(name + "Ba_R_TL");
        baseEdges[2] = staticSprites.get(name + "Ba_B_TL");
        baseEdges[3] = staticSprites.get(name + "Ba_L_TL");
        //update hierarchies as tiles are added
        switch (name) {
            case "snow":
                baseHierarchy = 7;
                break;
            case "grass":
                baseHierarchy = 6;
                break;
            case "yellowGrass":
                baseHierarchy = 5;
                break;
            case "dirt":
                baseHierarchy = 4;
                break;
            case "sand":
                baseHierarchy = 3;
                break;
            case "stone":
                baseHierarchy = 2;
                break;
            case "water":
                baseHierarchy = 1;
                break;
            case "dirtyWater":
                baseHierarchy = 0;
                break;
        }
    }

    /**
     * If there is no wall on top and new type is not same as current type, set flooring.
     * @param name type of flooring
     * @return if there wasn't a wall on top
     */
    public boolean updateFlooring(String name) {
        Tower tower = tiles.get((roundTo(position.x, 50)/50) + 1, (roundTo(position.y, 50)/50) + 1).tower;
        if (name.equals(flooringName)) return false;
        if (tower instanceof IceWall) return true;
        if (obstacleName != null) return false;
        if (tower instanceof Wall) return false;
        else {
            setFlooring(name);
            return true;
        }
    }

    public void setFlooring(String name) {
        flooringEdges = new PImage[4];
        if (name == null) {
            flooring = null;
            flooringName = null;
        } else {
            if (breakableName != null) {
                if (!breakableName.contains("Debris")) {
                    breakable = null;
                    breakableName = null;
                }
            }
            name = name.replace("Fl_TL", "");
            name = name.replace("ultimate", "titanium");
            flooringName = name;
            if (staticSprites.get(name + "Fl_TL") != flooring) {
                flooring = staticSprites.get(name + "Fl_TL");
                if (name.contains("woodWall") || name.contains("stoneWall")) {
                    flooringEdges[0] = staticSprites.get(name + "Fl_T_TL");
                    flooringEdges[1] = staticSprites.get(name + "Fl_R_TL");
                    flooringEdges[2] = staticSprites.get(name + "Fl_B_TL");
                    flooringEdges[3] = staticSprites.get(name + "Fl_L_TL");
                }
            }
        }
    }

    public void setDecoration(String name) {
        if (name == null) {
            decoration = null;
            decorationName = null;
        } else {
            decoration = staticSprites.get(name);
            decorationName = name;
        }
    }

    public void setBreakable(String name) {
        if (name != null) name = name.replace("ultimate","titanium");
        if (name == null) {
            breakable = null;
            breakableName = null;
        } else {
            breakable = staticSprites.get(name);
            breakableName = name;
        }
    }

    public void setObstacle(String name) {
        if (name == null) {
            if (obstacleName != null) updateNodes();
            obstacle = null;
            obstacleName = null;
        } else {
            int x = getGridPosition().x;
            int y = getGridPosition().y;
            if (x < 39 && y < 39 && tiles.get(x + 1, y + 1).tower != null){
                tiles.get(x + 1, y + 1).tower.die(false);
            }
            setFlooring(null);
            if (obstacleName == null) updateNodes();
            obstacle = staticSprites.get(name);
            obstacleName = name;
            if (name.contains("smallTree")) obstacleShadowLength = 3;
            if (containsCorners(name,"tree")) obstacleShadowLength = 8;
            if (containsCorners(name,"yellowTree")) obstacleShadowLength = 8;
        }
    }

    private boolean containsCorners(String name, String subName) {
        boolean bl = name.contains(subName + "BL");
        boolean br = name.contains(subName + "BR");
        boolean tl = name.contains(subName + "TL");
        boolean tr = name.contains(subName + "TR");
        return bl || br || tl || tr;
    }

    public Node findClosestNode(PVector posEnemy, boolean tower) {
        PVector posTile;
        if (tower) posTile = new PVector(position.x + 50, position.y + 50); //compensate for tower position weirdness
        else posTile = new PVector(position.x, position.y);
        boolean left = posEnemy.x < posTile.x + 25;
        boolean top = posEnemy.y < posTile.y + 25;
        int x = (int)posTile.x / 25;
        int y = (int)posTile.y / 25;
        if (!left) x++;
        if (!top) y++;
        return nodeGrid[x][y];
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
