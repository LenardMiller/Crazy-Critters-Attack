package main.pathfinding;

import static main.Main.*;

public class UpdateNodes {

    private UpdateNodes() {}

    public static void updateNodes(Node current, PathRequest request) { //request can be null; will default to defaultSize
        int x = (int) (current.position.x / nSize);
        int y = (int) (current.position.y / nSize);
        if (x == 0 && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && y > 0 && y < (GRID_HEIGHT / nSize) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == (GRID_WIDTH / nSize) - 1 && y == 0) {
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if ((x > 0 && x < (GRID_WIDTH / nSize) - 1) && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if (x == (GRID_WIDTH / nSize) - 1 && y == (GRID_HEIGHT / nSize) - 1) {
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x][y - 1].setOpen(current,request);
        } else if (x == (GRID_WIDTH / nSize) - 1 && y < (GRID_HEIGHT / nSize) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && y == (GRID_HEIGHT / nSize) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
        } else if (x < (GRID_WIDTH / nSize) - 1 && y == (GRID_HEIGHT / nSize) - 1) {
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
}
