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
        PVector pfPosition = new PVector(enemy.position.x-((enemy.pfSize-1)*12.5f),enemy.position.y-((enemy.pfSize-1)*12.5f));
        int x = (int)(pfPosition.x/nSize);
        int y = (int)(pfPosition.y/nSize);
        nodeGrid[x][y].setStart(x,y);
        Main.start.findGHF();
        updateNode(Main.start,this);
        Main.path.done = false;
        Main.path.find(id); //points are added here
        enemy.swapPoints(false);
        if (enemy.points.size() > 0) enemy.cleanTurnPoints(); //and subtracted here
    }
}
