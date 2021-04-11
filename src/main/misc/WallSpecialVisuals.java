package main.misc;

import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Turret;

import java.util.ArrayList;

import static main.Main.tiles;
import static main.Main.towers;

public class WallSpecialVisuals {

    public WallSpecialVisuals() {}

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
                    for (int numOfType : typesTouching) numFlooringTilesTouching += numOfType;
                    if (numFlooringTilesTouching >= 4) didSomething = tile.updateFlooring(getMostCommonType(typesTouching));
                }
            }
        } while (didSomething);
    }

    /**
     * @param typeGrid a grid of the flooring types of all tiles
     * @param x x position to check
     * @param y y position to check
     * @return the number of floorings of each type touching the tile at checked position
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
                if (tile.flooring != null) nameGrid[x][y] = tile.flooringName;
            }
        }
        return nameGrid;
    }

    /**
     * clears wall flooring.
     */
    private static void removeWallTiles() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile bgTile = tiles.get(i);
            int x = (int) (bgTile.position.x / 50);
            int y = (int) (bgTile.position.y / 50);
            Tile towerTile = tiles.get(x + 1, y + 1);
            if (towerTile != null) {
                if (towerTile.tower == null || towerTile.tower instanceof Turret) {
                    bgTile.setFlooring(null);
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
     * Something to do with wall tile flooring?
     */
    public static void updateWallTileConnections() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.flooringName != null && !tile.flooringName.equals("stoneWall") && !tile.flooringName.equals("woodWall")) {
                tile.connectFlooringInsideCorners();
            }
            tile.connectFlooringOutsideCorners();
        }
    }

}
