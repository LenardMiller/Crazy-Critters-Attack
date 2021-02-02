package main.pathfinding;

import main.Main;
import main.enemies.Enemy;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.nSize;
import static main.Main.nodeGrid;
import static main.pathfinding.UpdateNode.updateNode;

public class PathRequest {

    public int id;
    public Enemy enemy;
    int size;

    public PathRequest(int id, Enemy enemy) {
        this.id = id;
        this.enemy = enemy;
        this.size = enemy.pfSize;
    }

    public void getPath() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.reset();
            }
        }

        enemy.points = new ArrayList<>();
        PVector pfPosition = new PVector(enemy.position.x-((enemy.pfSize-1)*12.5f)+100,enemy.position.y-((enemy.pfSize-1)*12.5f)+100);
        PVector roundedPosition = catchOutOfBounds((int)((pfPosition.x)/nSize), (int)((pfPosition.y)/nSize));
        nodeGrid[(int) roundedPosition.x][(int) roundedPosition.y].setStart((int) roundedPosition.x-4, (int) roundedPosition.y-4);
        Main.start.findGHF();
        updateNode(Main.start,this);
        Main.path.done = false;
        Main.path.find(id); //points are added here
        enemy.swapPoints(false);
        if (enemy.points.size() > 0) enemy.cleanTurnPoints(); //and subtracted here
    }

    private PVector catchOutOfBounds(int x, int y) {
        if (x >= nodeGrid.length) x = nodeGrid.length - 1;
        if (x < 0) x = 0;
        if (y >= nodeGrid[x].length) y = nodeGrid.length - 1;
        if (y < 0) y = 0;
        return new PVector(x,y);
    }
}
