package main.pathfinding;

import main.enemies.Enemy;
import processing.core.PApplet;

import java.util.ArrayList;

import static main.Main.*;

public class AStar {

    public ArrayList<PathRequest> requestQueue;
    public int index;
    public boolean done;

    private final PApplet p;

    public AStar(PApplet p) {
        this.p = p;

        requestQueue = new ArrayList<>();
    }

    public void find(int index) {
        this.index = index;

//        Enemy enemy = null;
//        if (enemies.size() - 1 > pathFinder.index) enemy = enemies.get(pathFinder.index);
//        if (enemy != null) {
//            enemy.trail.add(new Enemy.TurnPoint(p, enemy.position, null));
//        }

        while (openNodes.currentCount > 0 && !done) {
            Node current = openNodes.removeFirstItem().node;
            current.setClose();
        }
    }
}