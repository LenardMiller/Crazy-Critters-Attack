package main.pathfinding;

import java.util.ArrayList;

import static main.Main.nSize;
import static main.Main.nodeGrid;

public class MPSmear {

    private MPSmear() {
    }

    public static ArrayList<Node> interestingNodes;
    private static Node focusedNode;

    public static void smear() { //todo: finish
        for (Node node : interestingNodes) {
            focusedNode = node;
            simulate(1, new int[]{2, -1, 1, -1, -1, -1, -1}, 3);
            simulate(1, new int[]{-1, -1, -1, -1, 1, -1, 2}, 3);

            simulate(2, new int[]{2, -1, 1, -1, 0, -1, 2}, 4);
            simulate(2, new int[]{2, -1, 0, -1, 1, -1, 2}, 4);

            simulate(3, new int[]{3, -1, -2, -1, 1, 4, 2}, 4);
            simulate(3, new int[]{2, 4, 1, -1, -1, -1, 3}, 4);

            simulate(2, new int[]{2, 2, 4, 0, 0, 0, 2}, 4);
            simulate(2, new int[]{2, 0, 0, 0, 4, 2, 2}, 4);
        }
    }

    private static void simulate(int oldState, int[] surroundingStates, int newState) { //nw is always -1
        int x = (int)((focusedNode.position.x/nSize)/2);
        int y = (int)((focusedNode.position.y/nSize)/2);
        Node[] nodes = new Node[7];
        nodes[0] = nodeGrid[x][y-1];
        nodes[1] = nodeGrid[x+1][y-1];
        nodes[2] = nodeGrid[x+1][y];
        nodes[3] = nodeGrid[x+1][y+1];
        nodes[4] = nodeGrid[x][y+1];
        nodes[5] = nodeGrid[x-1][y+1];
        nodes[6] = nodeGrid[x-1][y];
        boolean b = true;
        for (Node node : nodes) {
            if (node == null) {
                b = false;
                break;
            }
        }
        if (b) {
            b = (focusedNode.smearState == oldState);
            if (b) {
                for (int i = 0; i < 7; i++) {
                    if (!check(nodes[i], surroundingStates[i])) {
                        b = false;
                        break;
                    }
                }
            }
        }
        if (b) focusedNode.smearState = newState;
    }

    private static boolean check(Node n, int s) {
        if (s == -2) return (n.smearState == 0 || n.smearState == 1);
        else return (n.smearState == s || s == -1);
    }

}
