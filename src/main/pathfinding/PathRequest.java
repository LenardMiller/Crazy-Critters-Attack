package main.pathfinding;

import main.Main;
import main.enemies.Enemy;

import java.util.ArrayList;

import static main.Main.nSize;
import static main.Main.nodeGrid;
import static main.pathfinding.UpdateNodes.updateNodes;
import static processing.core.PApplet.round;

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
        //issue here?
        nodeGrid[round((enemy.position.x-(enemy.size.x/2f))/nSize)][round((enemy.position.y-(enemy.size.y/2f))/nSize)].setStart(round((enemy.position.x-(enemy.size.x/2f))/nSize), round((enemy.position.y-(enemy.size.y/2f))/nSize));
        Main.start.findGHF();
        updateNodes(Main.start,this);
        Main.path.done = false;
        Main.path.find(id); //points are added here
        enemy.swapPoints(false);
        enemy.cleanTurnPoints(); //and subtracted here
    }
}
