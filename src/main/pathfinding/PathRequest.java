package main.pathfinding;

import main.Main;
import main.enemies.Enemy;
import main.misc.IntVector;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.NODE_SIZE;
import static main.Main.nodeGrid;
import static main.pathfinding.PathfindingUtilities.updateNode;

public class PathRequest {

    public int id;
    public Enemy enemy;
    int size;

    public PathRequest(int id, Enemy enemy) {
        this.id = id;
        this.enemy = enemy;
        this.size = enemy.pfSize;
    }

    /** Uses A* to get a path, then converts that into a trail and sends it to the enemy */
    public void getPath() {
        //reset
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.reset();
            }
        }
        enemy.trail = new ArrayList<>();
        //put the start in place
        PVector pfPosition = new PVector(
                enemy.position.x - ((enemy.pfSize - 1) * 12.5f) + 100,
                enemy.position.y - ((enemy.pfSize - 1) * 12.5f) + 100
        );
        IntVector roundedPosition = catchOutOfBounds(
                (int) (pfPosition.x / NODE_SIZE),
                (int) (pfPosition.y / NODE_SIZE)
        );
        nodeGrid[roundedPosition.x][roundedPosition.y].setStart(
                roundedPosition.x - 4, roundedPosition.y - 4
        );
        //do the A*
        Main.start.findGHF();
        updateNode(Main.start,this);
        Main.pathFinder.done = false;
        //add points
        Main.pathFinder.find(id);
        enemy.swapPoints(false);
        if (enemy.trail.size() > 0) enemy.setCombatPoints();
    }

    private IntVector catchOutOfBounds(int x, int y) {
        if (x >= nodeGrid.length) x = nodeGrid.length - 1;
        if (x < 0) x = 0;
        if (y >= nodeGrid[x].length) y = nodeGrid.length - 1;
        if (y < 0) y = 0;
        return new IntVector(x,y);
    }
}
