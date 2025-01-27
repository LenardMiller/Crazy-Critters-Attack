package main.pathfinding;

import main.enemies.Enemy;

import java.util.ArrayList;

import static main.Main.*;

public class PathfindingUtilities {

    /** Updates the clearance of all nodes. */
    public static void updateClearance() {
        for (int x = 0; x < nodeGrid.length; x++) {
            for (int y = 0; y < nodeGrid[x].length; y++) {
                boolean clear = true;
                int kSize = 1;
                Node node = nodeGrid[x][y];
                int clearance = 0;
                while (clear) {
                    for (int xn = 0; xn < kSize; xn++) {
                        for (int yn = 0; yn < kSize; yn++) {
                            if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                                Node nodeB = nodeGrid[x + xn][y + yn];
                                if (nodeB.isNotTraversable) {
                                    clear = false;
                                    break;
                                }
                            } else {
                                clear = false;
                                break;
                            }
                        }
                        if (!clear) break;
                    }
                    if (clear) {
                        kSize++;
                        clearance++;
                    }
                }
                node.clearance = clearance;
            }
        }
    }

    /**
     * Updates all the nodes around current
     * @param current nodes around this will be updated
     * @param request stores some info, can be null; will default to defaultSize
     */
    public static void updateNode(Node current, PathRequest request) {
        int x = (int) ((current.position.x+100) / NODE_SIZE);
        int y = (int) ((current.position.y+100) / NODE_SIZE);
        if (x == 0 && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && (y > 0) && y < (GRID_HEIGHT / (NODE_SIZE)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (NODE_SIZE)) - 1 && y == 0) {
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if ((x > 0 && x < ((GRID_WIDTH) / (NODE_SIZE)) - 1) && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (NODE_SIZE)) - 1 && y == (GRID_HEIGHT / (NODE_SIZE)) - 1) {
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x][y - 1].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (NODE_SIZE)) - 1 && y < (GRID_HEIGHT / (NODE_SIZE)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && y == (GRID_HEIGHT / (NODE_SIZE)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
        } else if (x < ((GRID_WIDTH) / (NODE_SIZE)) - 1 && y == (GRID_HEIGHT / (NODE_SIZE)) - 1) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else {
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        }
    }

    /** Resets all the paths saved by each enemy. */
    public static void updatePaths() {
        for (main.pathfinding.Node[] nodes : nodeGrid) {
            for (main.pathfinding.Node node : nodes) {
                node.reset();
            }
        }
        pathFinder.requestQueue = new ArrayList<>();
        if (enemies.size() > 0) {
            for (int i = enemies.size()-1; i >= 0; i--) {
                enemies.get(i).requestPath(i);
            }
        } else {
            start.setStart(
                    (int) (start.position.x / NODE_SIZE),
                    (int) (start.position.y / NODE_SIZE)
            );
            start.findGHF();
            updateNode(start,null);
            pathFinder.done = false;
            pathFinder.find(-1);
        }
    }

    private static void refreshNodes() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.setNotEnd((int) (node.position.x / NODE_SIZE), (int) (node.position.y / NODE_SIZE));
                node.checkTile();
            }
        }
    }

    /** Refreshes just combat points for when towers change. */
    public static void updateCombatPoints() {
        refreshNodes();
        updateClearance();
        for (Enemy enemy : enemies) enemy.setCombatPoints();
    }

    /** Refreshes all paths for when tiles change. */
    public static void updateAll() {
        refreshNodes();
        updateClearance();
        updateNode(start, null);
        updatePaths();
        for (Enemy enemy : enemies) enemy.setCombatPoints();
    }

    /**
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
}
