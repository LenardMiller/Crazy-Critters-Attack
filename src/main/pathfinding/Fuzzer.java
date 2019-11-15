package main.pathfinding;

import static main.Main.*;
import static processing.core.PApplet.ceil;

public class Fuzzer{

    private int ks;
    private float mp;
    private float[] mps;

    Fuzzer(int ks){
        this.ks = ks;
        mp = 0;
        fuzz();
    }

    private void fuzz(){
        leftRight();
        topBottom();
    }

    private void leftRight(){
        for (int y = 0; y < GRID_HEIGHT/nSize; y++){
            mps = new float[GRID_WIDTH/nSize];
            for (int x = 0; x < mps.length; x++){
                mps[x] = nodeGrid[x][y].movementPenalty;
            }
            mp = mps[0];
            for (int i = 1; i <= (int)(ks/2); i++){
                mp += mps[0];
                mp += mps[i];
            }
            nodeGrid[0][y].movementPenalty = mp/ks;
            for (int x = 1; x < GRID_WIDTH/nSize; x++){
                if (x - (int)(ks/2) <= 0){
                    mp -= mps[0];
                }
                else{
                    mp -= mps[x-ceil((float)(ks)/2)];
                }
                if (x + (int)(ks/2) >= GRID_WIDTH/nSize){
                    mp += mps[mps.length-1];
                }
                else{
                    mp += mps[x+(int)(ks/2)];
                }
                nodeGrid[x][y].movementPenalty = mp/ks;
            }
        }
    }
    private void topBottom(){
        for (int x = 0; x < GRID_WIDTH/nSize; x++){
            mps = new float[GRID_HEIGHT/nSize];
            for (int y = 0; y < mps.length; y++){
                mps[y] = nodeGrid[x][y].movementPenalty;
            }
            mp = mps[0];
            for (int i = 1; i <= (int)(ks/2); i++){
                mp += mps[0];
                mp += mps[i];
            }
            nodeGrid[x][0].movementPenalty = mp/ks;
            for (int y = 1; y < GRID_HEIGHT/nSize; y++){
                if (y - (int)(ks/2) <= 0){
                    mp -= mps[0];
                }
                else{
                    mp -= mps[y-ceil((float)(ks)/2)];
                }
                if (y + (int)(ks/2) >= GRID_HEIGHT/nSize){
                    mp += mps[mps.length-1];
                }
                else{
                    mp += mps[y+(int)(ks/2)];
                }
                nodeGrid[x][y].movementPenalty = mp/ks;
            }
        }
    }
}