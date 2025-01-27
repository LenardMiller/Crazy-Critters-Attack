package main.pathfinding;

import main.Main;
import main.enemies.Enemy;
import main.misc.Tile;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.pathfinding.PathfindingUtilities.updateNode;

public class Node {

    private final PApplet P;

    private Node parent;
    int clearance;
    HeapNode.ItemNode openItem;
    public PVector position;
    private float startCost;
    private float endCost;
    public float totalCost;
    private boolean isStart;
    private boolean isEnd;
    public boolean isOpen;
    public boolean isClosed;
    boolean isNotTraversable;
    private PathRequest request;
    public Tower tower;

    public Node(PApplet p, PVector position) {
        this.P = p;

        this.position = position;
    }

    public Tile getTile() {
        int x = (int)((position.x/ NODE_SIZE)/2);
        int y = (int)((position.y/ NODE_SIZE)/2);
        return tiles.get(x,y);
    }

    public void display() {
        P.stroke(255);
        P.noFill();
        if (isStart) P.fill(125, 125, 255);
        if (isEnd) P.fill(255, 0, 0);
        if (isNotTraversable) P.fill(255,100);
        P.rect(position.x, position.y, NODE_SIZE, NODE_SIZE);
    }

    public void setStart(int x, int y) {
        x += 4;
        y += 4;
//        if (Main.start != null) Main.start.isStart = false;
        Main.start = nodeGrid[x][y];
        start.isStart = true;
    }

    public void setEnd(int x, int y) { //issue is here!
        if (!isEnd) {
//            System.out.println(end.length);
            Node[] end2 = new Node[end.length + 1];
            arrayCopy(end, end2);
            end2[end2.length - 1] = nodeGrid[x][y];
            end = end2;
            isEnd = true;
//            System.out.println(end.length);
        }
    }

    public void setNotEnd(int x, int y) {
        if (isEnd) {
            isEnd = false;
            int index = end.length + 1;
            PVector pv = new PVector(x * NODE_SIZE, y * NODE_SIZE);
            for (int i = 0; i < end.length; i++) {
                if (end[i] != null) { //shouldn't be necessary?
                    if (end[i].position.x == pv.x && end[i].position.y == pv.y && i < end.length - 1) {
                        end[i] = end[i + 1];
                        index = i;
                    }
                    if (i > index && i < end.length - 1) end[i] = end[i + 1];
                    if (i == end.length - 1) {
                        Node[] endB = new Node[end.length-1];
                        System.arraycopy(end, 0, endB, 0, endB.length);
                        end = endB;
                    }
                }
            }
        }
    }

    void setOpen(Node parentNew, PathRequest request) {
        this.request = request;
        int size = DEFAULT_SIZE;
        if (request != null) size = request.size;
        if (!(isStart || isOpen || isClosed || isNotTraversable || clearance < size)) {
            isOpen = true;
            findGHF();
        }
        if ((parentNew.isClosed || parentNew.isStart) && (isOpen || isClosed) && (parent == null || parentNew.startCost < parent.startCost)) {
            parent = parentNew;
            findGHF();
            openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int) ((position.x + 100) / NODE_SIZE)][(int) ((position.y + 100) / NODE_SIZE)]));
        }
    }

    public void checkTile() {
        boolean ended = false;
        int tX = (int) (position.x / 50);
        int tY = (int) (position.y / 50);
        int nX = (int) (position.x / 25) + 4;
        int nY = (int) (position.y / 25) + 4;
        Tile towerTile = tiles.get(tX+1, tY+1);
        tower = null;
        if (towerTile != null) tower = towerTile.tower;
        Tile obsTile = null;
        if (position.x >= 0 && position.y >= 0) obsTile = tiles.get(tX,tY);
        if (obsTile != null) {
            isNotTraversable = obsTile.obstacleLayer.exists();
            if (obsTile.machine) {
                setEnd(nX,nY);
                ended = true;
            }
        } if (!ended) setNotEnd(tX,tY);
    }

    /** Closes the node, adds turn points */
    void setClose() {
        isClosed = true;
        isOpen = false;
        if (isEnd) {
            pathFinder.done = true;
            Enemy enemy = null;
            if (enemies.size() - 1 > pathFinder.index) enemy = enemies.get(pathFinder.index);
            // Add points to end node
            if (pathFinder.index != -1 && enemies.size() != 0 && enemy != null) {
                enemy.trail.add(new Enemy.TurnPoint(P, position, tower));
                enemy.trail.add(new Enemy.TurnPoint(P, parent.position, tower));
            }
            // Add other points
            setDone();
        } else updateNode(nodeGrid[(int) ((position.x + 100) / NODE_SIZE)][(int) ((position.y + 100) / NODE_SIZE)], request);
        findGHF();
    }

    /** Add point to enemy trail, set next node done */
    private void setDone() {
        if (pathFinder.index != -1) {
            if (pathFinder.index < enemies.size()) {
                Enemy enemy = enemies.get(pathFinder.index);
                enemy.trail.add(new Enemy.TurnPoint(P, position, tower));
            }
        } if (!isStart) parent.setDone();
    }

    public void findGHF() {
        if (isEnd) endCost = 0;
        else if (end.length > 0) {
            HeapFloat endH = new HeapFloat(end.length);
            for (Node node : end) {
                if (node != null) {
                    node.isEnd = true;
                    node.findGHF();
                    PVector d = PVector.sub(position, node.position);
                    endCost = sqrt(sq(d.x) + sq(d.y));
                    endH.addItem(new HeapFloat.ItemFloat(endCost));
                }
            }
            endCost = endH.removeFirstItem().value;
        } if (isStart) startCost = 0;
        else {
            PVector offset;
            if (parent != null) {
                offset = PVector.sub(position, parent.position);
                startCost = sqrt(sq(offset.x) + sq(offset.y));
                startCost += parent.startCost;
            }
        }
        totalCost = startCost + endCost;
    }

    void reset() {
        isOpen = false;
        isClosed = false;
        if (this != start) isStart = false;
        if (!isStart) {
            totalCost = 0;
            endCost = 0;
            startCost = 0;
        }
        openNodes = new HeapNode((int) (sq((float) GRID_HEIGHT / NODE_SIZE)));
        parent = null;
    }
}
