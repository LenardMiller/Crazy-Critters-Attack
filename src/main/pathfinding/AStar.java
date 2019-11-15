package main.pathfinding;

import main.enemies.Enemy;

import java.util.ArrayList;

import static java.lang.Math.round;
import static main.Main.*;

public class AStar{

    public ArrayList<PathRequest> reqQ;
    public float mpNegation;
    public int index;
    public boolean done;

    public AStar(){
        reqQ = new ArrayList<PathRequest>();
        mpNegation = 0;
    }

    public void find(float mpNegation, int index){
        this.mpNegation = mpNegation;
        this.index = index;
        while (openNodes.currentCount > 0 && !done){
            Node current = openNodes.removeFirstItem().node;
            current.setClose();
        }
    }

    public void updatePath() {
        for (Node[] nodes : nodeGrid) {
            for (int y = 0; y < nodes.length; y++) {
                nodes[y].reset();
            }
        }
        if (enemies.size() > 0){
            for (int i = enemies.size()-1; i >= 0; i--){
                boolean d = false;
                for (int j = path.reqQ.size()-1; j >= 0; j--){
                    if (enemies.get(i) == path.reqQ.get(j).e){
                        d = true;
                    }
                }
                if (!d){
                    enemies.get(i).requestPath(i);
                }
            }
        }
        else{
            start.setStart((int)(start.position.x/nSize),(int)(start.position.y/nSize));
            start.findGHF();
            updateNodes(start);
            path.done = false;
            path.find(0,-1);
        }
    }

    public static void updateNodes(Node current){
        int x = (int)(current.position.x/nSize);
        int y = (int)(((current.position.y)/nSize)+10);
        if (x == 0 && y == 0){
            nodeGrid[x+1][y].setOpen(current);
            nodeGrid[x+1][y+1].setOpen(current);
            nodeGrid[x][y+1].setOpen(current);
        }
        else if (x == 0 && (y > 0) && y < (GRID_HEIGHT/nSize)-1){
            nodeGrid[x][y-1].setOpen(current);
            nodeGrid[x+1][y-1].setOpen(current);
            nodeGrid[x+1][y].setOpen(current);
            nodeGrid[x+1][y+1].setOpen(current);
            nodeGrid[x][y+1].setOpen(current);
        }
        else if (x == (GRID_WIDTH/nSize)-1 && y == 0){
            nodeGrid[x][y+1].setOpen(current);
            nodeGrid[x-1][y+1].setOpen(current);
            nodeGrid[x-1][y].setOpen(current);
        }
        else if ((x > 0 && x < (GRID_WIDTH/nSize)-1) && y == 0){
            nodeGrid[x+1][y].setOpen(current);
            nodeGrid[x+1][y+1].setOpen(current);
            nodeGrid[x][y+1].setOpen(current);
            nodeGrid[x-1][y+1].setOpen(current);
            nodeGrid[x-1][y].setOpen(current);
        }
        else if (x == (GRID_WIDTH/nSize)-1 && y == (GRID_HEIGHT/nSize)-1){
            nodeGrid[x-1][y].setOpen(current);
            nodeGrid[x-1][y-1].setOpen(current);
            nodeGrid[x][y-1].setOpen(current);
        }
        else if (x == (GRID_WIDTH/nSize)-1 && y < (GRID_HEIGHT/nSize)-1){
            nodeGrid[x][y-1].setOpen(current);
            nodeGrid[x-1][y-1].setOpen(current);
            nodeGrid[x-1][y].setOpen(current);
            nodeGrid[x-1][y+1].setOpen(current);
            nodeGrid[x][y+1].setOpen(current);
        }
        else if (x == 0 && y == (GRID_HEIGHT/nSize)-1){
            nodeGrid[x][y-1].setOpen(current);
            nodeGrid[x+1][y-1].setOpen(current);
            nodeGrid[x+1][y].setOpen(current);
        }
        else if (x < (GRID_WIDTH/nSize)-1 && y == (GRID_HEIGHT/nSize)-1){
            nodeGrid[x+1][y].setOpen(current);
            nodeGrid[x+1][y-1].setOpen(current);
            nodeGrid[x][y-1].setOpen(current);
            nodeGrid[x-1][y-1].setOpen(current);
            nodeGrid[x-1][y].setOpen(current);
        }
        else{
            nodeGrid[x-1][y-1].setOpen(current);
            nodeGrid[x][y-1].setOpen(current);
            nodeGrid[x+1][y-1].setOpen(current);
            nodeGrid[x+1][y].setOpen(current);
            nodeGrid[x+1][y+1].setOpen(current);
            nodeGrid[x][y+1].setOpen(current);
            nodeGrid[x-1][y+1].setOpen(current);
            nodeGrid[x-1][y].setOpen(current);
        }
    }

    public void nodeCheckObs(){
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.checkObs(false);
            }
        }
        fuzz = new Fuzzer(11);
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.checkObs(true);
            }
        }
        updatePath();
    }

    public static class PathRequest{
        int i;
        Enemy e;
        public PathRequest(int i, Enemy e){
            this.i = i;
            this.e = e;
        }
        public void getPath(){
            for (Node[] nodes : nodeGrid) {
                for (Node node : nodes) {
                    node.reset();
                }
            }
            e.points = new ArrayList<>();
            nodeGrid[round((e.position.x)/nSize)][round(((e.position.y))/nSize)+10].setStart(round((e.position.x)/nSize),round(((e.position.y))/nSize));
            start.findGHF();
            updateNodes(start);
            path.done = false;
            path.find(e.mpNegation, i);
            e.swapPoints(false);
        }
    }
}