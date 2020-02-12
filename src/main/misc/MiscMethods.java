package main.misc;

import main.pathfinding.Node;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.pathfinding.UpdateClearance.updateClearance;
import static main.pathfinding.UpdateNode.updateNode;
import static main.pathfinding.UpdatePath.updatePath;
import static processing.core.PApplet.atan2;
import static processing.core.PConstants.TWO_PI;

public class MiscMethods {

    public MiscMethods() {}

    public static void updateNodes() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.setNotEnd((int)(node.position.x/nSize),(int)(node.position.y/nSize));
                node.checkObs();
            }
        }
        updateClearance();
        updateNode(start,null);
        updatePath();
    }

    public static void updateTowerArray() {
        towers = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            Tower tower = tiles.get(i).tower;
            if (tower != null) {
                towers.add(tower);
                if (!tower.turret) tower.updateSprite();
            }
        }
    }

    public static float findAngleBetween(PVector p1, PVector p2){
        //https://forum.processing.org/one/topic/pvector-anglebetween.html
        float a = atan2(p1.y-p2.y, p1.x-p2.x);
        if (a<0) { a+=TWO_PI; }
        return a;
    }

    public static boolean isBetween(PVector pointA, PVector pointB, PVector testPoint) {
        //inclusive top point, exclusive bottom
        boolean xAB = testPoint.x >= pointA.x && testPoint.x < pointB.x;
        boolean xBA = testPoint.x >= pointB.x && testPoint.x < pointA.x;
        boolean yAB = testPoint.y >= pointA.y && testPoint.y < pointB.y;
        boolean yBA = testPoint.y >= pointB.y && testPoint.y < pointA.y;
        return (xAB||xBA)&&(yAB||yBA);
    }

    public static int roundTo(float input, int rounder) {
        return ((int)(input/rounder)) * rounder;
    }

    public static float maxCost() {
        float maxCost = 0;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost > maxCost && (node.isOpen || node.isClosed)) maxCost = node.totalCost;
            }
        }
        return maxCost;
    }

    public static float minCost(float maxCost) {
        float minCost = maxCost;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost < minCost && (node.isOpen || node.isClosed)) minCost = node.totalCost;
            }
        }
        return minCost;
    }

    public static PVector randomSpawnPosition(PApplet p) {
        float x;
        float y;
        boolean xy = p.random(0,1) > 0.5;
        if (xy) {
            x = -99;
            y = p.random(-100,1000);
        } else {
            x = p.random(-100,1000);
            y = -99;
        }
        if (p.random(0,1) > 0.5 && xy) x += 1000;
        if (p.random(0,1) > 0.5 && !xy) y += 1000;
        return new PVector(x,y);
    }
}
