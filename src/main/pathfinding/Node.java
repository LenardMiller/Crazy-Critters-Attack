package main.pathfinding;

import main.enemies.Enemy;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class Node{

    private PApplet p;

    private Node parent;
    HeapNode.ItemNode openItem;
    public PVector position;
    float movementPenalty;
    private float startCost;
    private float endCost;
    float totalCost;
    public int tint;
    private boolean isStart;
    private boolean isEnd;
    private boolean isOpen;
    private boolean isClosed;
    private boolean isNotTraversable;

    public Node(PApplet p, PVector position){
        this.p = p;

        this.position = position;
    }

    public void display() {
        p.stroke(255);
        p.noFill();
        if (isOpen) p.fill(0,255,0);
        if (isClosed) p.fill(255,125,0);
        if (isStart) p.fill(125,125,255);
        if (isEnd) p.fill(255,0,0);
        if (isNotTraversable) p.fill(255);
        p.rect(position.x,position.y,nSize,nSize);
    }

    public void setStart(int x, int y){
        if (start != null){
            start.isStart = false;
        }
        start = nodeGrid[x][y+10];
        isStart = true;
    }

    public void setEnd(int x, int y){
        if (!isEnd){
            end[numEnd] = nodeGrid[x][y];
            numEnd++;
            isEnd = true;
        }
    }

    void setOpen(Node parentNew){
        if (!(isStart || isOpen || isClosed || isNotTraversable)){
            if(!isEnd){
            }
            isOpen = true;
            findGHF();
        }
        if (parentNew.isClosed || parentNew.isStart){
            if ((isOpen || isClosed) && parent == null){
                parent = parentNew;
                findGHF();
                openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int)(position.x/nSize)][(int)(position.y/nSize)+10]));
            }
            if ((isOpen || isClosed) && parentNew.startCost < parent.startCost){ //these have to be split in two because parent might be null
                parent = parentNew;
                findGHF();
                openNodes.addItem(new HeapNode.ItemNode(nodeGrid[(int)(position.x/nSize)][(int)(position.y/nSize)+10]));
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
            AStar.updateNodes(nodeGrid[(int)(position.x/nSize)][(int)(position.y/nSize)+10]);
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

//    void checkObs(boolean addMode){ //todo: redo using new tile system, thats what its for after all
//        if (!addMode){
//            movementPenalty = 0;
//        }
//        for (main.towers.Tower tower : towers) {
//            boolean intersecting;
//            PVector op = tower.position;
//            PVector os = tower.size;
//            intersecting = (position.x > op.x - os.x - 10 && position.x < op.x + nSize + 10) && (position.y > op.y - os.y - 10 && position.y < op.y + nSize + 10);
//            if (intersecting) {
//                movementPenalty += tower.twHp;
//            }
//        }
//    }

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
            PVector d;
            if (parent != null){
                d = PVector.sub(position,parent.position);
                startCost = sqrt(sq(d.x)+sq(d.y));
                startCost += movementPenalty; //<<<<<<<<<<< mp stuff
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
        openNodes = new HeapNode((int)(sq(1000/nSize)));
        parent = null;
    }
}