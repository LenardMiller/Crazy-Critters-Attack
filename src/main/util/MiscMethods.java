package main.util;

import main.pathfinding.Node;
import processing.core.PVector;

import static main.Main.nodeGrid;
import static processing.core.PApplet.atan2;
import static processing.core.PConstants.TWO_PI;

public class MiscMethods {

    public MiscMethods() {}

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
}
