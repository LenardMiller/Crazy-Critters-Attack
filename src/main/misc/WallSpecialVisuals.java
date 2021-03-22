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
     */
    public static void updateWallTiles() {
        //remove
        for (int i = 0; i < tiles.size(); i++) {
            Tile bgTile = tiles.get(i);
            int x = (int) (bgTile.position.x / 50);
            int y = (int) (bgTile.position.y / 50);
            Tile towerTile = tiles.get(x + 1, y + 1);
            if (towerTile != null) {
                if (towerTile.tower == null || towerTile.tower instanceof Turret) {
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

}
