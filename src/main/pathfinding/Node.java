package main.pathfinding;

import main.Main;
import main.enemies.Enemy;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.pathfinding.UpdateNodes.updateNodes;

public class Node{

    private PApplet p;

    private Node parent;
    int clearance;
    ArrayList<Float> clearanceMp;
    HeapNode.ItemNode openItem;
    public PVector position;
    float movementPenalty;
    private float startCost;
    private float endCost;
    float totalCost;
    private boolean isStart;
    private boolean isEnd;
    private boolean isOpen;
    private boolean isClosed;
    boolean isNotTraversable;
    private PathRequest request;

    public Node(PApplet p, PVector position){
        this.p = p;

        this.position = position;
    }

    public void setStart(int x, int y){
        if (Main.start != null){
            Main.start.isStart = false;
        }
        Main.start = nodeGrid[x][y];
        isStart = true;
    }

    public void setEnd(int x, int y){
        if (!isEnd){
            end[numEnd] = nodeGrid[x][y];
            numEnd++;
            isEnd = true;
        }
    }

    void setOpen(Node parentNew, PathRequest request) {
        this.request = request;
        int size = defaultSize;
        if (request != null) size = request.size;
        if (!(isStart || isOpen || isClosed || isNotTraversable || clearance < size)){
            isOpen = true;
            findGHF();
        }
        if (parentNew.isClosed || parentNew.isStart){
            if ((isOpen || isClosed) && parent == null){
                parent = parentNew;
                findGHF();
                openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int )(position.x/nSize)][(int) (position.y/nSize)]));
            }
            if ((isOpen || isClosed) && parentNew.startCost < parent.startCost){ //these have to be split in two because parent might be null
                parent = parentNew;
                findGHF();
                openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int) (position.x/nSize)][(int) (position.y/nSize)]));
            }
        }
    }

    void setClose(){
        isClosed = true;
        isOpen = false;
        if (isEnd){
            path.done = true;
            if (path.index != -1 && enemies.size() > path.index){
                enemies.get(path.index).points.add(new Enemy.TurnPoint(p,position));
                enemies.get(path.index).points.add(new Enemy.TurnPoint(p,parent.position));
            }
            parent.setDone();
        }
        else{
            updateNodes(p,nodeGrid[(int) (position.x/nSize)][(int) (position.y/nSize)],request);
        }
        findGHF();
    }

    private void setDone(){
        if (!isStart){
            if (path.index != -1 && enemies.size() > path.index){
                enemies.get(path.index).points.add(new Enemy.TurnPoint(p,position));
            }
            parent.setDone();
        }
    }

    public void findGHF(){
        if (isEnd){
            endCost = 0;
        }
        else{
            if (numEnd > 0){
                HeapFloat endH = new HeapFloat(numEnd);
                for (int i = 0; i < numEnd; i++){
                    end[i].findGHF();
                    PVector d = PVector.sub(position,end[i].position);
                    endCost = sqrt(sq(d.x)+sq(d.y));
                    endH.addItem(new HeapFloat.ItemFloat(endCost));
                }
                endCost = endH.removeFirstItem().value;
            }
        }
        if (isStart){
            startCost = 0;
        }
        else{
            PVector offset;
            if (parent != null){
                offset = PVector.sub(position,parent.position);
                startCost = sqrt(sq(offset.x)+sq(offset.y));
                int size = defaultSize;
                if (request != null) size = request.size;
                float mpn = movementPenalty;
                if (clearanceMp.size() >= size) mpn += clearanceMp.get(size-1); //mpc
                if (mpn > 0){
                    startCost += mpn;
                }
                startCost += parent.startCost;
            }
        }
        totalCost = startCost + endCost;
    }

    void reset(){
        isOpen = false;
        isClosed = false;
        if(!isStart){
            totalCost = 0;
            endCost = 0;
            startCost = 0;
        }
        openNodes = new HeapNode((int)(sq(1000f/nSize)));
        parent = null;
    }
}