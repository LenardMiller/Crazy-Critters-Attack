package main.pathfinding;

import main.Main;
import main.enemies.Enemy;
import main.misc.Tile;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.pathfinding.UpdateNode.updateNode;

public class Node {

    private PApplet p;

    private Node parent;
    int clearance;
    ArrayList<Float> clearanceMp;
    HeapNode.ItemNode openItem;
    public PVector position;
    float movementPenalty;
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
        this.p = p;

        this.position = position;
    }

    public void display() {
        p.stroke(255);
        p.noFill();
        if (isOpen) p.fill(0, 255, 0);
        if (isClosed) p.fill(255, 125, 0);
        if (isStart) p.fill(125, 125, 255);
        if (isEnd) p.fill(255, 0, 0);
        if (isNotTraversable) p.fill(255);
        p.rect(position.x, position.y, nSize, nSize);
    }

    public void setStart(int x, int y) {
        x += 4;
        y += 4;
        if (Main.start != null) Main.start.isStart = false;
        Main.start = nodeGrid[x][y];
        start.isStart = true;
    }

    public void setEnd(int x, int y) {
        if (!isEnd) {
            Node[] end2 = new Node[end.length + 1];
            arrayCopy(end, end2);
            end2[end2.length - 1] = nodeGrid[x][y];
            end = end2;
            isEnd = true;
        }
    }

    public void setNotEnd(int x, int y) {
        if (isEnd) {
            isEnd = false;
            int index = end.length + 1;
            PVector pv = new PVector(x * nSize, y * nSize);
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
        int size = defaultSize;
        if (request != null) size = request.size;
        if (!(isStart || isOpen || isClosed || isNotTraversable || clearance < size)) {
            isOpen = true;
            findGHF();
        }
        if ((parentNew.isClosed || parentNew.isStart) && (isOpen || isClosed) && (parent == null || parentNew.startCost < parent.startCost)) {
            parent = parentNew;
            findGHF();
            openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int) ((position.x + 100) / nSize)][(int) ((position.y + 100) / nSize)]));
        }
    }

    public void checkObs() {
        int towerX = (int) (position.x / 50) + 1;
        int towerY = (int) (position.y / 50) + 1;
        Tile tile = tiles.get(towerX, towerY);
        tower = null;
        if (tile != null) tower = tile.tower;
        if (tower != null) {
            movementPenalty = tower.maxHp;
            if (tower.turret) setEnd((int) ((position.x + 100) / nSize), (int) ((position.y + 100) / nSize));
        } else movementPenalty = 0;
    }

    void setClose() {
        isClosed = true;
        isOpen = false;
        if (isEnd) {
            path.done = true;
            Enemy enemy = null;
            if (enemies.size() - 1 > path.index) enemy = enemies.get(path.index);
            if (path.index != -1 && enemies.size() != 0 && enemy != null) { //points added HERE
                enemy.points.add(new Enemy.TurnPoint(p, position, tower));
                enemy.points.add(new Enemy.TurnPoint(p, parent.position, tower));
            }
            setDone();
        } else updateNode(nodeGrid[(int) ((position.x + 100) / nSize)][(int) ((position.y + 100) / nSize)], request);
        findGHF();
    }

    private void setDone() {
        if (path.index != -1) {
            if (path.index < enemies.size()) {
                Enemy enemy = enemies.get(path.index);
                enemy.points.add(new Enemy.TurnPoint(p, position, tower));
            }
        }
        if (!isStart) parent.setDone();
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
        }
        if (isStart) startCost = 0;
        else {
            PVector offset;
            if (parent != null) {
                offset = PVector.sub(position, parent.position);
                startCost = sqrt(sq(offset.x) + sq(offset.y));
                int size = defaultSize;
                if (request != null) size = request.size;
                float mpn = movementPenalty;
                if (clearanceMp.size() >= size) mpn += clearanceMp.get(size - 1); //mpc
                if (request != null && request.enemy != null && request.enemy.flying && !isEnd) mpn = 0;
                if (mpn > 0) startCost += mpn;
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
        openNodes = new HeapNode((int) (sq((float) GRID_HEIGHT / nSize)));
        parent = null;
    }
}
