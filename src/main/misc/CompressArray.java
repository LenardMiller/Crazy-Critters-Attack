package main.misc;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;

public class CompressArray{

    private int oldSize;
    private int newSize;
    private int factor;
    private float period;
    private int count;
    private int changed;
    public ArrayList<Integer> compArray;

    public CompressArray(int oldSize, int newSize, ArrayList<Integer> compArray){
        this.compArray = compArray;
        this.oldSize = oldSize;
        this.newSize = newSize;
        factor = newSize-oldSize;
        period = (newSize/abs((float)(factor)))-1;
        count = 0;
        changed = 0;
    }

    public void main(){
        if (factor < 0) { //shrink
            for (int i = 0; i < oldSize; i++) {
                if (count >= ceil(period)) {
                    compArray.remove(i-changed);
                    changed++;
                    count = -1;
                }
                count++;
            }
        } else if (factor > 0) { //expand
            int[] cels = new int[oldSize+1];
            int perCel = newSize / cels.length;
            Arrays.fill(cels, perCel);
            float overflow = ((float)newSize / (float)cels.length) - perCel;
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