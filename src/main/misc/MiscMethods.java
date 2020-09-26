package main.misc;

import main.pathfinding.Node;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.pathfinding.UpdateClearance.updateClearance;
import static main.pathfinding.UpdateNode.updateNode;
import static main.pathfinding.UpdatePath.updatePath;

public class MiscMethods {

    public MiscMethods() {
    }

    public static void updateNodes() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.setNotEnd((int) (node.position.x / nSize), (int) (node.position.y / nSize));
                node.checkTile();
            }
        }
        updateClearance();
        updateNode(start, null);
        updatePath();
    }

    public static void updateTowerArray() {
        towers = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            Tower tower = tiles.get(i).tower;
            if (tower != null) {
                towers.add(tower);
                if (!tower.turret) tower.updateSprite();
            }
        }
    }

    public static float findSlope(PVector p1, PVector p2) {
        float m = (p2.y-p1.y)/(p2.x-p1.x);
        return m*-1;
    }

    public static float findAngleBetween(PVector p1, PVector p2) {
        //https://forum.processing.org/one/topic/pvector-anglebetween.html
        float a = atan2(p1.y - p2.y, p1.x - p2.x);
        if (a < 0) a += TWO_PI;
        return a;
    }

    public static int roundTo(float input, int rounder) {
        return ((int) (input / rounder)) * rounder;
    }

    public static float maxCost() {
        float maxCost = 0;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost > maxCost && (node.isOpen || node.isClosed)) maxCost = node.totalCost;
            }
        }
        return maxCost;
    }

    public static float minCost(float maxCost) {
        float minCost = maxCost;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost < minCost && (node.isOpen || node.isClosed)) minCost = node.totalCost;
            }
        }
        return minCost;
    }

    public static PVector randomSpawnPosition(PApplet p) {
        float z = p.random(0, 4);
        float low = -90;
        float high = 990;
        boolean l = z >= 0 && z < 1;
        boolean r = z >= 1 && z < 2;
        boolean u = z >= 2 && z < 3;
        boolean d = z >= 3 && z <= 4;
        float x = 0;
        float y = 0;
        if (l) x = low;
        if (r) x = high;
        if (u) y = low;
        if (d) y = high;
        if (l || r) y = p.random(low, high);
        if (u || d) x = p.random(low, high);
        return new PVector(x, y);
    }

    public static void updateWallTiles() {
        //remove
        for (int i = 0; i < tiles.size(); i++) {
            Tile bgTile = tiles.get(i);
            int x = (int) (bgTile.position.x / 50);
            int y = (int) (bgTile.position.y / 50);
            Tile towerTile = tiles.get(x + 1, y + 1);
            if (towerTile != null) {
                if (towerTile.tower == null || towerTile.tower.turret) {
                    bgTile.setBgW(null);
                }
            }
        }
        boolean change = true;
        while (change) {
            //create special grid
            String[][] nameGrid = new String[18][18];
            for (int x = 0; x < 18; x++) {
                for (int y = 0; y < 18; y++) {
                    Tile tile = tiles.get(x, y);
                    if (tile.bgW != null) nameGrid[x][y] = tile.bgWName;
                }
            }
            //place
            change = false;
            for (int x = 0; x < 18; x++) {
                for (int y = 0; y < 18; y++) {
                    Tile tile = tiles.get(x, y);
                    if (tile.bgW == null) {
                        int[] n = new int[5];
                        n[0] = countN(nameGrid, x, y, "woodWall");
                        n[1] = countN(nameGrid, x, y, "stoneWall");
                        n[2] = countN(nameGrid, x, y, "metalWall");
                        n[3] = countN(nameGrid, x, y, "crystalWall");
                        n[4] = countN(nameGrid, x, y, "titaniumWall");
                        int sum = 0;
                        for (int i : n) sum += i;
                        if (sum >= 4) {
                            int count = 0;
                            int l = 0;
                            for (int i = 0; i < 5; i++) {
                                if (n[i] >= count) {
                                    count = n[i];
                                    l = i;
                                }
                            }
                            String name = "";
                            if (l == 0) name = "woodWall";
                            if (l == 1) name = "stoneWall";
                            if (l == 2) name = "metalWall";
                            if (l == 3) name = "crystalWall";
                            if (l == 4) name = "titaniumWall";
                            tile.setBgW(name);
                            change = true;
                        }
                    }
                }
            }
        }
    }

    private static int countN(String[][] nameGrid, int x, int y, String name) {
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

    public static void updateWallTileConnections() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.bgWName != null && !tile.bgWName.equals("stoneWall") && !tile.bgWName.equals("woodWall")) {
                tile.connectBgWICorners();
            }
            tile.connectBgWOCorners();
        }
    }

    public static float findAngle(PVector v1, PVector v2) {
        float angle = 0;
        PVector ratio = PVector.sub(v2, v1);
        if (v1.x == v2.x) { //if on the same x
            if (v1.y >= v2.y) { //if below target or on same y, angle right
                angle = 0;
            } else if (v1.y < v2.y) { //if above target, angle left
                angle = PI;
            }
        } else if (v1.y == v2.y) { //if on same y
            if (v1.x > v2.x) { //if  right of target, angle down
                angle = 3 * HALF_PI;
            } else if (v1.x < v2.x) { //if left of target, angle up
                angle = HALF_PI;
            }
        } else {
            if (v1.x < v2.x && v1.y > v2.y) { //if to left and below
                angle = (atan(abs(ratio.x) / abs(ratio.y)));
            } else if (v1.x < v2.x && v1.y < v2.y) { //if to left and above
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + HALF_PI;
            } else if (v1.x > v2.x && v1.y < v2.y) { //if to right and above
                angle = (atan(abs(ratio.x) / abs(ratio.y))) + PI;
            } else if (v1.x > v2.x && v1.y > v2.y) { //if to right and below
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + 3 * HALF_PI;
            }
        }
        return angle;
    }

    public static float findAngle(PVector v) {
        return findAngle(new PVector(0,0), v);
    }

    public static float clampAngle(float a) {
        return a - TWO_PI * floor(a / TWO_PI);
    }

    public static float angleDifference(float a, float b) { //todo: fix spinning
        float diffA = a - b;
        float diffB = -diffA;
        diffA = clampAngle(diffA);
        diffB = clampAngle(diffB);
        float diff = min(diffA,diffB);
        if (b + diff != a && a > PI) diff *= -1;
        return diff;
    }
}
