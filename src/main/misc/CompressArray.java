package main.misc;

import java.util.ArrayList;

import static java.lang.Math.ceil;
import static processing.core.PApplet.abs;

public class CompressArray{

    private int oldSize;
    private int newSize;
    private int factor;
    private float period;
    private int count;
    private int changed;
    private int metaCount;
    private ArrayList<Integer> fullArray;
    private ArrayList<Integer> compArray;

    public CompressArray(int oldSize, int newSize, int metaCount, ArrayList<Integer> fullArray, ArrayList<Integer> compArray){
        this.fullArray = fullArray;
        this.compArray = compArray;
        this.oldSize = oldSize;
        this.newSize = newSize;
        factor = newSize-oldSize;
        period = (newSize/abs((float)(factor)))-1;
        count = 0;
        changed = 0;
        this.metaCount = metaCount;
    }

    public void main(){
        if (factor < 0){
            for (int i = 0; i < oldSize; i++){
                if (count > ceil(period)){
                    compArray.remove(i-changed);
                    changed++;
                    count = -1;
                }
                count++;
            }
        } else if (factor > 0){
            for (int i = 0; i <= newSize-1; i++){
                if (count >= ceil(period)){
                    if (compArray.size() < (metaCount+1)*fullArray.size()){
                        if (i != 0 && i < compArray.size()+1){
                            compArray.add(i,compArray.get(i-1));
                        }
                    }
                    count = -1;
                }
                count++;
            }
        }
    }
}