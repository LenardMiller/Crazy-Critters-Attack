package main.pathfinding;

import java.util.ArrayList;

import static main.Main.openNodes;

public class AStar {

    public ArrayList<PathRequest> requestQueue;
    public int index;
    public boolean done;

    public AStar() { requestQueue = new ArrayList<>(); }

    public void find(int index) {
        this.index = index;
        while (openNodes.currentCount > 0 && !done) {
            Node current = openNodes.removeFirstItem().node;
            current.setClose();
        }
    }
}