package main.pathfinding;

import java.util.ArrayList;

import static main.Main.nodeGrid;

public class UpdateClearance {

    private UpdateClearance() {}

    public static void updateClearance() {
        MPSmear.interestingNodes = new ArrayList<>();
        for (int x = 0; x < nodeGrid.length; x++) {
            for (int y = 0; y < nodeGrid[x].length; y++) {
                boolean clear = true;
                int kSize = 1;
                Node node = nodeGrid[x][y];
                if (node.movementPenalty) node.smearState = 1; //full
                else node.smearState = 0; //empty
                node.clearanceMp = new ArrayList<>(); //mpc
                boolean mp = false;
                int clearance = 0;
                while (clear) {
                    for (int xn = 0; xn < kSize; xn++) {
                        for (int yn = 0; yn < kSize; yn++) {
                            if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                                Node nodeB = nodeGrid[x + xn][y + yn];
                                mp = nodeB.movementPenalty; //mpc
                                if (nodeB.isNotTraversable) {
                                    clear = false;
                                    break;
                                }
                            } else {
                                clear = false;
                                break;
                            }
                        }
                        if (!clear) break;
                    }
                    node.clearanceMp.add(mp); //mpc
                    node.smearState = 2; //partial
                    if (mp) {
                        boolean dupe = false;
                        for (Node iNode : MPSmear.interestingNodes) {
                            if (iNode == node) {
                                dupe = true;
                                break;
                            }
                        }
                        if (!dupe) MPSmear.interestingNodes.add(node);
                    }
                    if (clear) {
                        kSize++;
                        clearance++;
                    }
                }
                node.clearance = clearance;
            }
        }
    }
}
