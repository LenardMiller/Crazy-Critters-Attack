package main.misc;

public class IntVector {

    public int x, y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector(int[] arr) {
        this(arr[0], arr[1]);
        if (arr.length > 2) System.out.println("IntVector must have a length of two");
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
