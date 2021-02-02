package main.pathfinding;

import static main.Main.*;

public class UpdateNode {

    private UpdateNode() {}

    public static void updateNode(Node current, PathRequest request) { //request can be null; will default to defaultSize
        int x = (int) ((current.position.x+100) / nodeSize);
        int y = (int) ((current.position.y+100) / nodeSize);
        if (x == 0 && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && (y > 0) && y < (GRID_HEIGHT / (nodeSize)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (nodeSize)) - 1 && y == 0) {
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if ((x > 0 && x < ((GRID_WIDTH) / (nodeSize)) - 1) && y == 0) {
            nodeGrid[x + 1][y].setOpen(current,request);
            nodeGrid[x + 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (nodeSize)) - 1 && y == (GRID_HEIGHT / (nodeSize)) - 1) {
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x][y - 1].setOpen(current,request);
        } else if (x == ((GRID_WIDTH) / (nodeSize)) - 1 && y < (GRID_HEIGHT / (nodeSize)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y - 1].setOpen(current,request);
            nodeGrid[x - 1][y].setOpen(current,request);
            nodeGrid[x - 1][y + 1].setOpen(current,request);
            nodeGrid[x][y + 1].setOpen(current,request);
        } else if (x == 0 && y == (GRID_HEIGHT / (nodeSize)) - 1) {
            nodeGrid[x][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y - 1].setOpen(current,request);
            nodeGrid[x + 1][y].setOpen(current,request);
        } else if (x < ((GRID_WIDTH) / (nodeSize)) - 1 && y == (GRID_HEIGHT / (nodeSize)) - 1) {
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
