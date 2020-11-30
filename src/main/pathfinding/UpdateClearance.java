package main.pathfinding;

import static main.Main.nodeGrid;

public class UpdateClearance {

    private UpdateClearance() {}

    public static void updateClearance() {
        for (int x = 0; x < nodeGrid.length; x++) {
            for (int y = 0; y < nodeGrid[x].length; y++) {
                boolean clear = true;
                int kSize = 1;
                Node node = nodeGrid[x][y];
                int clearance = 0;
                while (clear) {
                    for (int xn = 0; xn < kSize; xn++) {
                        for (int yn = 0; yn < kSize; yn++) {
                            if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                                Node nodeB = nodeGrid[x + xn][y + yn];
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
