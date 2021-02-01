package main.misc;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;

public class CompressArray{

    private final int OLD_SIZE;
    private final int NEW_SIZE;
    private final int FACTOR;
    private final float PERIOD;

    private int count;
    private int changed;
    public ArrayList<Integer> compArray;

    public CompressArray(int oldSize, int newSize, ArrayList<Integer> compArray){
        this.compArray = compArray;
        this.OLD_SIZE = oldSize;
        this.NEW_SIZE = newSize;
        FACTOR = newSize-oldSize;
        PERIOD = (newSize/abs((float)(FACTOR)))-1;
        count = 0;
        changed = 0;
    }

    public void main(){
        if (FACTOR < 0) { //shrink
            for (int i = 0; i < OLD_SIZE; i++) {
                if (count >= ceil(PERIOD)) {
                    compArray.remove(i-changed);
                    changed++;
                    count = -1;
                }
                count++;
            }
        } else if (FACTOR > 0) { //expand
            int[] cels = new int[OLD_SIZE +1];
            int perCel = NEW_SIZE / cels.length;
            Arrays.fill(cels, perCel);
            float overflow = ((float) NEW_SIZE / (float)cels.length) - perCel;
            float counter = 0;
            for (int i = 0; i < cels.length; i++) {
                counter+=overflow;
                if (counter >= 1) {
                    counter -= 1;
                    cels[i]++;
                }
            }
//            System.out.println(Arrays.toString(cels));
            for (int i = 0; i < cels.length; i++) {
                for (int j = 0; j < cels[i]; j++) {
                    compArray.add(i);
                }
            }
//            System.out.println(compArray.size());
        }
    }
}