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
    ArrayList<Boolean> clearanceMp;
    HeapNode.ItemNode openItem;
    public PVector position;
    boolean movementPenalty;
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
    public int mPSmearState;

    public Node(PApplet p, PVector position) {
        this.p = p;

        this.position = position;
        mPSmearState = 0;
    }

    public Tile getTile() {
        int x = (int)((position.x/nSize)/2);
        int y = (int)((position.y/nSize)/2);
        return tiles.get(x,y);
    }

    public void display() {
        p.stroke(255);
        p.noFill();
        if (isStart) p.fill(125, 125, 255);
        if (isEnd) p.fill(255, 0, 0);
        if (isNotTraversable) p.fill(255,100);
        p.rect(position.x, position.y, nSize, nSize);
    }

    public void setStart(int x, int y) {
        x += 4;
        y += 4;
        if (Main.start != null) Main.start.isStart = false;
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

    public void checkTile() {
        boolean ended = false;
        int tX = (int) (position.x / 50);
        int tY = (int) (position.y / 50);
        int nX = (int) (position.x / 25) + 4;
        int nY = (int) (position.y / 25) + 4;
        Tile towerTile = tiles.get(tX+1, tY+1);
        tower = null;
        if (towerTile != null) tower = towerTile.tower;
        if (tower != null) {
            if (!tower.turret) movementPenalty = true;
            else {
                movementPenalty = true;
                setEnd(nX, nY);
                ended = true;
            }
        } else movementPenalty = false;
        Tile obsTile = tiles.get(tX,tY);
        if (obsTile != null) {
            isNotTraversable = obsTile.obstacle != null;
            if (obsTile.machine) {
                setEnd(nX,nY);
                ended = true;
            }
        }
        if (!ended) setNotEnd(tX,tY);
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
                boolean mpn = movementPenalty;
                if (clearanceMp.size() >= size) mpn = clearanceMp.get(size - 1); //mpc
                if (request != null && request.enemy != null && request.enemy.flying && !isEnd) mpn = false;
                if (mpn) startCost += 1000;
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
