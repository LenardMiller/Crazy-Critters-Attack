package main.misc;

import main.pathfinding.Node;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.pathfinding.UpdateClearance.updateClearance;
import static main.pathfinding.UpdateNode.updateNode;
import static main.pathfinding.UpdatePath.updatePath;

public class MiscMethods {

    public MiscMethods() {
    }

    /**
     * For pathfinding.
     * Refreshes all pathfinding nodes
     */
    public static void updateNodes() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.setNotEnd((int) (node.position.x / nodeSize), (int) (node.position.y / nodeSize));
                node.checkTile();
            }
        }
        updateClearance();
        updateNode(start, null);
        updatePath();
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
                if (!tower.turret) tower.updateSprite();
            }
        }
    }

    /**
     * Finds the slope of a line.
     * @param p1 the first position
     * @param p2 the second position
     * @return the slope between the positions
     */
    public static float findSlope(PVector p1, PVector p2) {
        float m = (p2.y - p1.y) / (p2.x - p1.x);
        return m * -1;
    }

    /**
     * https://forum.processing.org/one/topic/pvector-anglebetween.html
     * Returns the angle of a line between two points.
     * @param p1 the first position
     * @param p2 the second position
     * @return the angle of a line between the two positions
     */
    public static float findAngleBetween(PVector p1, PVector p2) {
        float a = atan2(p1.y - p2.y, p1.x - p2.x);
        if (a < 0) a += TWO_PI;
        return a;
    }

    /**
     * Rounds down to a multiple of the rounder. Only works left of the decimal.
     * @param input number to be rounded
     * @param rounder number input will be rounded to a multiple of
     * @return input but rounded by rounder
     */
    public static int roundTo(float input, int rounder) {
        return ((int) (input / rounder)) * rounder;
    }

    /**
     * Rounds to a multiple of the rounder. Only works right of the decimal.
     * @param input number to be rounded
     * @param rounder number input will be a multiple of, must be less than 1
     * @return input rounded by the rounder
     */
    public static float roundTo(float input, float rounder) {
        return (round(input / rounder)) * rounder;
    }

    /**
     * For pathfinding.
     * Calculates the maximum cost of a path.
     * @return the path's maximum cost
     */
    public static float maxCost() {
        float maxCost = 0;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost > maxCost && (node.isOpen || node.isClosed)) maxCost = node.totalCost;
            }
        }
        return maxCost;
    }

    /**
     * For pathfinding.
     * Calculates the minimum cost of a path.
     * @return the path's minimum cost
     */
    public static float minCost(float maxCost) {
        float minCost = maxCost;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost < minCost && (node.isOpen || node.isClosed)) minCost = node.totalCost;
            }
        }
        return minCost;
    }

    /**
     * Finds a random place for an enemy to spawn just offscreen.
     * @param p the PApplet
     * @return a random spawnpoint
     */
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

    /**
     * Updates wall flooring.
     */
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

    /**
     * Counts the number of touching walls with the same name.
     * @param nameGrid a grid of wall names
     * @param x wall x position
     * @param y wall y position
     * @param name wall name
     * @return the number of touching walls with the same name
     */
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

    /**
     * Something to do with wall tile flooring?
     * TODO: move this stuff
     */
    public static void updateWallTileConnections() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.bgWName != null && !tile.bgWName.equals("stoneWall") && !tile.bgWName.equals("woodWall")) {
                tile.connectBgWICorners();
            }
            tile.connectBgWOCorners();
        }
    }

    /**
     * Returns the angle of a line between two points.
     * todo: why does this exist?
     * @param v1 the first position
     * @param v2 the second position
     * @return the angle of a line between the two positions
     */
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

    /**
     * Finds the angle of a line from the origin to a position.
     * @param v the position to draw a line to
     * @return the angle of the line
     */
    public static float findAngle(PVector v) {
        return findAngle(new PVector(0, 0), v);
    }

    /**
     * Converts an angle to be between 0 and TWO_PI.
     * @param a the angle to convert
     * @return an angle between 0 and TWO_PI
     */
    public static float clampAngle(float a) {
        return a - TWO_PI * floor(a / TWO_PI);
    }

    /**
     * Finds the difference between two angles.
     * @param target angle to compare to
     * @param current angle to be compared
     * @return the angle between target and current
     */
    public static float angleDifference(float target, float current) {
        float diffA = -(current - target);
        float diffB = diffA - TWO_PI;
        float diffC = clampAngle(diffB);
        float f = min(abs(diffA), abs(diffB), abs(diffC));
        if (f == abs(diffA)) return diffA;
        if (f == abs(diffB)) return diffB;
        if (f == abs(diffC)) return diffC;
        return 0;
    }

    /**
     * A better tint function that can brighten images.
     * BEWARE OF SHALLOW COPIES
     * @param image image to be tinted
     * @param tintColor what color it should be tinted
     * @param magnitude how much it should be tinted, 0-1
     * @return the new tinted image
     */
    public static PImage superTint(PImage image, Color tintColor, float magnitude) {
        image.loadPixels();
        for (int i = 0; i < image.pixels.length; i++) {
            if (image.pixels[i] != 0) {
                Color pixel = new Color(image.pixels[i]);
                float m = abs(magnitude - 1);
                pixel = new Color (
                        calcColor(pixel.getRed(), m, tintColor.getRed()),
                        calcColor(pixel.getGreen(), m, tintColor.getGreen()),
                        calcColor(pixel.getBlue(), m, tintColor.getBlue()),
                        calcColor(pixel.getAlpha(), m, tintColor.getAlpha())
                );
                image.pixels[i] = pixel.getRGB();
            }
        }
        return image;
    }

    /**
     * Calculates the value of one color channel for <tt>superTint()</tt>
     * @param p input value
     * @param m magnitude of the change
     * @param t target value
     * @return the value of a single channel
     */
    private static int calcColor(float p, float m, float t) {
        float d = t - p;
        float d2 = m * d;
        float c = p + d2;
        return (int) c;
    }

    /**
     * Finds the distance between two points.
     * @param p1 the first point
     * @param p2 the second point
     * @return the distance between the two points
     */
    public static float findDistBetween(PVector p1, PVector p2) {
        PVector p0 = new PVector(p1.x - p2.x, p1.y - p2.y);
        return sqrt(sq(p0.x) + sq(p0.y));
    }
}
