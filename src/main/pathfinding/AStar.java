package main.pathfinding;

import java.util.ArrayList;

import static main.Main.openNodes;

public class AStar {

    public ArrayList<PathRequest> reqQ;
    public int index;
    public boolean done;

    public AStar() {
        reqQ = new ArrayList<>();
    }

    public void find(int index) {
        this.index = index;
        while (openNodes.currentCount > 0 && !done) {
            Node current = openNodes.removeFirstItem().node;
            current.setClose();
        }
    }
}